package example;

import org.junit.Test;

public class SampleToTest {
    public static void hello() {
        System.out.println("Hello, world!");
    }

    @Test
    public void testHello() {
        System.out.println("Running testHello");
        // (no exceptions means success)
    }

    @Test
    public void testFailing() {
        System.out.println("Running testFailing");
        throw new RuntimeException("Intentional failure");
    }

    public void nonStaticMethod() {
        System.out.println("I am not static.");
    }

    @Test
    public void badTestSignature() {
        // This has parameters, so it should be skipped.
    }
}
