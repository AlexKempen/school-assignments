package src;

/**
 * Utilities for starting processes.
 */
public class ProcessGenerator {
    static public Process start(String name) {
        try {
            String[] compileArgs = { "javac", name + ".java" };
            // compile MemoryProcess.java
            Process compileProcess = Runtime.getRuntime().exec(compileArgs);
            compileProcess.waitFor();

            String[] runArgs = { "java", name };
            return Runtime.getRuntime().exec(runArgs);
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }
}