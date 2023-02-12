package src.operatingsystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Utilities for starting processes.
 */
public class ProcessUtils {
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

    public static ObjectOutputStream getObjectOutputStream(OutputStream out) {
        try {
            return new ObjectOutputStream(out);
        } catch (IOException e) {
            throw new AssertionError("Failed to construct object output stream.", e);
        }
    }

    public static ObjectInputStream getObjectInputStream(InputStream in) {
        try {
            return new ObjectInputStream(in);
        } catch (IOException e) {
            throw new AssertionError("Failed to construct object input stream.", e);
        }
    }
}