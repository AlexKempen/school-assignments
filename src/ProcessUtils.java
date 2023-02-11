package src;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import src.command.CommandReciever;
import src.command.Executor;
import src.cpu.Cpu;
import src.memory.Memory;
import src.operating_system.Subsystem;

/**
 * Utilities for starting processes.
 */
public class ProcessUtils {

    /**
     * The process main function which runs the Memory and Cpu subsystems.
     * 
     * @param args[1] : The name of the subsystem to run.
     */
    public static void main(String[] args) {
        CommandReciever reciever = new CommandReciever(getExecutor(args[0]),
                getObjectInputStream(System.in));
        reciever.handleCommands();
    }

    /**
     * Retrieves the Executor of a specific subsystem for use in a sub-process.
     */
    private static Executor getExecutor(String arg) {
        if (arg.equals(Subsystem.MEMORY.toString())) {
            return new Memory(System.out);
        } else if (arg.equals(Subsystem.CPU.toString())) {
            return new Cpu();
        }
        throw new IllegalArgumentException("Invalid subsystem name: " + arg);
    }

    /**
     * Starts a process which manages a single subsystem.
     * 
     * @return : The started process.
     */
    public static Process startSubsystemProcess(Subsystem subsystem) {
        try {
            String[] runArgs = { "java", "-cp", "out", "src/ProcessUtils", subsystem.toString() };
            // Use processbuilder instead of runtime.exec to conviniently enable features
            // like redirecting std::error
            // (runtime.exec is just a processbuilder under the hood anyways)
            ProcessBuilder pb = new ProcessBuilder(runArgs);
            pb.redirectError(new File(subsystem.toString() + "-error.txt"));
            return pb.start();
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }

    /**
     * Compiles the project using javac.
     */
    public static void compile() {
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