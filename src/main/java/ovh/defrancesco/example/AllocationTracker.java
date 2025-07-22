package ovh.defrancesco.example;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class AllocationTracker implements ClassFileTransformer {

  @Override
  public byte[] transform(
      ClassLoader loader,
      String className,
      Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain,
      byte[] classfileBuffer)
      throws IllegalClassFormatException {

    if (className != null
        && !className.startsWith("java/")
        && !className.startsWith("sun/")
        && !className.startsWith("jdk/")
        && !className.startsWith("ovh/defrancesco/example/MemorySampler")) {

      return instrumentClass(classfileBuffer);
    }
    return null;
  }

  private byte[] instrumentClass(byte[] classfileBuffer) {
    return classfileBuffer;
  }
}
