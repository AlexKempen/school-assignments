package src.command;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Utilities for starting processes.
 */
public abstract class ProcessUtils {
    /**
     * Compiles the project using javac.
     * Repeated invocations of compile() will have no effect;
     * compile will only run once.
     */
    public static void compile() {
        // only compile once
        if (compiled) {
            return;
        }
        compiled = true;

        String[] compileArgs = { "javac", "-d", "./out/",
                "./src/command/CommandProcess.java",
                "./src/Main.java" };
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(compileArgs);
            processBuilder.redirectError(new File("compile-error.txt"));
            waitForProcess(processBuilder.start());
        } catch (IOException e) {
            throw new AssertionError("Failed to compile project.", e);
        }
    }

    private static boolean compiled = false;

    static public void waitForProcess(Process process) {
        try {
            if (!process.waitFor(10, TimeUnit.SECONDS)) {
                throw new AssertionError("Process timed out.");
            }
        } catch (InterruptedException e) {
            throw new AssertionError("Process failed to exit.", e);
        }
    }
}