package src.command;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import src.operatingsystem.ProcessUtils;

/**
 * Executes Commands by sending them to a given CommandProcess.
 * Note the given command process assumes ownership of a given executor.
 */
public class CommandInvoker<T extends Executor> {
    public CommandInvoker(T executor) {
        process = CommandProcess.startCommandProcess();
        out = ProcessUtils.getObjectOutputStream(process.getOutputStream());
        // send executor to the process
        writeObject(executor);
    }

    /**
     * @return : A Scanner connected to the given process instance.
     */
    public Scanner getScanner() {
        return new Scanner(process.getInputStream());
    }

    /**
     * Writes a command to out.
     */
    public <C extends Command<T>> void send(C command) {
        writeObject(command);
    }

    public void exit() {
        writeObject(new ExitCommand());
        ProcessUtils.waitForProcess(process);
    }

    /**
     * Writes an Object to out.
     */
    private void writeObject(Object object) {
        try {
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            throw new AssertionError("Failed to send object.", e);
        }
    }

    private Process process;
    private ObjectOutputStream out;
}
