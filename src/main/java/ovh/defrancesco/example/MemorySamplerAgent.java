package ovh.defrancesco.example;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class MemorySamplerAgent {

  private static Instrumentation instrumentation;
  private static final Map<String, Long> allocationMap = new ConcurrentHashMap<>();
  private static final Map<String, StackTraceElement[]> stackTraceMap = new ConcurrentHashMap<>();
  private static final AtomicBoolean isTracking = new AtomicBoolean(false);
  private static final AtomicLong totalMemoryAllocated = new AtomicLong(0);
  private static final AtomicLong lastPassMemoryAllocated = new AtomicLong(0);

  private static final int MAX_STACK_TRACE_DEPTH = 5;

  public static void premain(String agentArgs, Instrumentation inst) {
    instrumentation = inst;
    System.out.println("MemorySamplerAgent started.");

    AllocationTracker transformer = new AllocationTracker();
    inst.addTransformer(transformer);
  }

  public static void start() {
    isTracking.set(true);
    lastPassMemoryAllocated.set(0);
    allocationMap.clear();
    stackTraceMap.clear();
  }

  public static void end() {
    isTracking.set(false);
  }

  public static void trackAllocation(Object obj) {
    if (!isTracking.get() || instrumentation == null) return;

    long size = instrumentation.getObjectSize(obj);

    Throwable t = new Throwable();
    StackTraceElement[] stackTrace = t.getStackTrace();

    StackTraceElement[] truncatedStackTrace =
        Arrays.copyOfRange(stackTrace, 1, Math.min(MAX_STACK_TRACE_DEPTH + 1, stackTrace.length));

    String key = obj.getClass().getName();
    allocationMap.put(key, allocationMap.getOrDefault(key, 0L) + size);
    stackTraceMap.put(key, truncatedStackTrace);

    lastPassMemoryAllocated.addAndGet(size);
    totalMemoryAllocated.addAndGet(size);
  }

  public static void printSituation() {
    System.out.println("Memory allocated on last pass: " + lastPassMemoryAllocated.get());
    System.out.println("Memory allocated total: " + totalMemoryAllocated.get());
    System.out.println();

    for (var entry : allocationMap.entrySet()) {
      String className = entry.getKey();
      StackTraceElement[] stackTrace = stackTraceMap.get(className);
      if (stackTrace != null && stackTrace.length > 0) {
        System.out.println("Stack Trace for " + className);
        for (var element : stackTrace) {
          System.out.println("    " + element);
        }
        System.out.println();
      }
    }
  }
}
