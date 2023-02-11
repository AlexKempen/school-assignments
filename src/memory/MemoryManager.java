package src.memory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import src.ProcessUtils;
import src.command.CommandInvoker;
import src.command.ExitCommand;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

/**
 * A class which creates and manages a MemoryProcess.
 */
public class MemoryManager {
    MemoryManager(Process process) {
        this.process = process;
        scanner = new Scanner(process.getInputStream());

        try {
            invoker = new CommandInvoker(new ObjectOutputStream(process.getOutputStream()));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static MemoryManager startMemoryManager() {
        return new MemoryManager(ProcessUtils.start("src/memory/MemoryProcess"));
    }

    public void exit() {
        invoker.send(new ExitCommand());
    }

    public void write(int address, int data) {
        invoker.send(new WriteCommand(address, data));
    }

    public int read(int address) {
        invoker.send(new ReadCommand(address));
        return Integer.parseInt(scanner.nextLine());
    }

    private Process process;
    private Scanner scanner;
    private CommandInvoker invoker;
}
