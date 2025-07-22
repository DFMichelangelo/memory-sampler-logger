package ovh.defrancesco.example;

public class MemorySampler {

  public static void start() {
    MemorySamplerAgent.start();
  }

  public static void end() {
    MemorySamplerAgent.end();
  }

  public static void printSituation() {
    MemorySamplerAgent.printSituation();
  }
}
