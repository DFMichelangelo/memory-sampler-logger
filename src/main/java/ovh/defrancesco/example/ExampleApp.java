package ovh.defrancesco.example;

import java.util.HashMap;
import java.util.Map;

public class ExampleApp {

  public static void main(String[] args) {
    System.out.println("Memory Sampler Example");
    System.out.println("===================================");

    test1();
    test2();
    test3();
    test4();
    test5();
  }

  private static void test1() {
    MemorySampler.start();
    MemorySampler.end();
    MemorySampler.printSituation();
  }

  private static void test2() {
    MemorySampler.start();
    int x = 10;
    MemorySampler.end();
    MemorySampler.printSituation();
  }

  private static void test3() {
    MemorySampler.start();
    String s = new String("trash");
    MemorySamplerAgent.trackAllocation(s);
    MemorySampler.end();
    MemorySampler.printSituation();
  }

  private static void test4() {
    MemorySampler.start();
    for (int i = 0; i < 10; i++) {
      String s = new String("trash!");
      MemorySamplerAgent.trackAllocation(s);
    }
    MemorySampler.end();
    MemorySampler.printSituation();
  }

  private static void test5() {
    Map<String, String> map = new HashMap<>();
    String key = "key";
    String value = "value";

    MemorySampler.start();
    map.put(key, value);
    MemorySampler.end();
    MemorySampler.printSituation();
  }
}
