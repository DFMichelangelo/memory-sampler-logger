package ovh.defrancesco.example;

import java.util.HashMap;
import java.util.Map;

public class StartStopExample {
  public static void main(String[] args) {
    testNoAllocation();
    testPrimitiveAllocation();
    testStringAllocation();
    testMultipleStrings();
    testHashMapAllocation();
  }

  private static void testNoAllocation() {
    System.out.println("=== Test 1: No allocation ===");
    MemorySamplerAgent.start();
    MemorySamplerAgent.end();
    MemorySamplerAgent.printSituation();
  }

  private static void testPrimitiveAllocation() {
    System.out.println("=== Test 2: Primitive allocation ===");
    MemorySamplerAgent.start();
    int x = 10;
    MemorySamplerAgent.end();
    MemorySamplerAgent.printSituation();
  }

  private static void testStringAllocation() {
    System.out.println("=== Test 3: String allocation ===");
    MemorySamplerAgent.start();
    String s = new String("trash");
    MemorySamplerAgent.trackAllocation(s);
    MemorySamplerAgent.end();
    MemorySamplerAgent.printSituation();
  }

  private static void testMultipleStrings() {
    System.out.println("=== Test 4: Multiple string allocations ===");
    MemorySamplerAgent.start();
    for (int i = 0; i < 10; i++) {
      String s = new String("trash!");
      MemorySamplerAgent.trackAllocation(s);
    }
    MemorySamplerAgent.end();
    MemorySamplerAgent.printSituation();
  }

  private static void testHashMapAllocation() {
    System.out.println("=== Test 5: HashMap allocation ===");
    Map<String, String> map = new HashMap<>();
    String key = "key";
    String value = "value";

    MemorySamplerAgent.start();
    map.put(key, value);
    MemorySamplerAgent.end();
    MemorySamplerAgent.printSituation();
  }
}
