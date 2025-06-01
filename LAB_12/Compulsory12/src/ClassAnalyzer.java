import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * A small command‐line application that:
 *  1) Loads a .class (by fully‐qualified name) from the classpath.
 *  2) Uses reflection to print out all declared methods (their prototypes).
 *  3) Finds every static, no‐argument method annotated with @Test and invokes it.
 *
 * Usage:
 *   java ClassAnalyzer <fully.qualified.ClassName>
 *
 * For example:
 *   java ClassAnalyzer com.example.MyClass
 *
 * Preconditions:
 *   • The target class must be in the classpath when you run this program.
 *   • We assume the user knows the package+class name (so we simply do Class.forName()).
 */
public class ClassAnalyzer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java ClassAnalyzer <fully.qualified.ClassName>");
            System.exit(1);
        }

        String fqcn = args[0];
        Class<?> targetClass;

        try {
            // 1) Load the class by its fully‐qualified name.
            targetClass = Class.forName(fqcn);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Could not find class '" + fqcn + "'. Make sure it's on the classpath.");
            System.exit(2);
            return;
        }

        System.out.println("Loaded class: " + targetClass.getName());
        System.out.println();

        // 2) Reflectively list all declared methods and print their prototypes.
        System.out.println("---- Method prototypes ----");
        Method[] methods = targetClass.getDeclaredMethods();
        if (methods.length == 0) {
            System.out.println("  (No declared methods found.)");
        } else {
            for (Method m : methods) {
                // Build a string like: [modifiers] returnType methodName(paramType1, paramType2) throws Exception1,Exception2
                StringBuilder sb = new StringBuilder();

                // Modifiers (e.g., public static)
                String modifiers = Modifier.toString(m.getModifiers());
                if (!modifiers.isEmpty()) {
                    sb.append(modifiers).append(" ");
                }

                // Return type
                sb.append(m.getReturnType().getSimpleName()).append(" ");

                // Method name
                sb.append(m.getName());

                // Parameters
                sb.append("(");
                Class<?>[] params = m.getParameterTypes();
                for (int i = 0; i < params.length; i++) {
                    sb.append(params[i].getSimpleName());
                    if (i < params.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");

                // Thrown exceptions
                Class<?>[] exceptions = m.getExceptionTypes();
                if (exceptions.length > 0) {
                    sb.append(" throws ");
                    for (int i = 0; i < exceptions.length; i++) {
                        sb.append(exceptions[i].getSimpleName());
                        if (i < exceptions.length - 1) {
                            sb.append(", ");
                        }
                    }
                }

                System.out.println("  " + sb.toString());
            }
        }
        System.out.println();

        // 3) Find all static, zero‐arg methods annotated with @Test and invoke them.
        System.out.println("---- Invoking @Test methods ----");
        boolean foundAnyTest = false;

        for (Method m : methods) {
            // Check if annotated with our custom @Test
            if (m.isAnnotationPresent(Test.class)) {
                foundAnyTest = true;

                // Check that it is static
                if (!Modifier.isStatic(m.getModifiers())) {
                    System.err.println("  [WARN] Skipping " + m.getName() + "(): @Test methods must be static.");
                    continue;
                }

                // Check that it has zero parameters
                if (m.getParameterCount() != 0) {
                    System.err.println("  [WARN] Skipping " + m.getName() + "(): @Test methods must have no arguments.");
                    continue;
                }

                // Make it accessible if it's non‐public
                if (!m.canAccess(null)) {
                    m.setAccessible(true);
                }

                // Invoke it
                System.out.print("  Running test: " + m.getName() + "()");
                try {
                    Object result = m.invoke(null);
                    System.out.println(" -> OK");
                } catch (Exception invokeEx) {
                    System.out.println(" -> FAILED with exception: " + invokeEx.getCause());
                    invokeEx.getCause().printStackTrace(System.out);
                }
            }
        }

        if (!foundAnyTest) {
            System.out.println("  (No methods annotated with @Test found.)");
        }
    }
}
