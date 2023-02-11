package src;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Utilities for starting processes.
 */
public class ProcessUtils {
    /**
     * Compiles the project using javac.
     */
    static public void compile() {
        String[] compileArgs = { "javac", "-d", "out", "src/*.java" };
        try {
            Process compileProcess = new ProcessBuilder(compileArgs).start();
            waitForProcess(compileProcess);
        } catch (IOException e) {
            throw new AssertionError("Failed to compile project.", e);
        }
    }

    static public void waitForProcess(Process process) {
        try {
            process.waitFor(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new AssertionError("Process failed to exit.", e);
        }
    }

    static public Process start(String name) {
        try {
            String[] runArgs = { "java", "-cp", "out", name };
            ProcessBuilder pb = new ProcessBuilder(runArgs);
            pb.redirectError(new File(name + "error.txt"));
            return pb.start();
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }
}