package src.memory;

import src.ProcessGenerator;
import src.command.ExitCommand;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * A class which creates and manages a MemoryProcess.
 */
public class MemoryManager {
    MemoryManager(Process process) {
        // this.process = process;
        // true to enable autoflushing
        out = new PrintStream(process.getOutputStream(), true);
        scanner = new Scanner(process.getInputStream());
    }

    public static MemoryManager startMemoryManager() {
        return new MemoryManager(ProcessGenerator.start("src/memory/MemoryProcess"));
    }

    public void exit() throws InterruptedException {
        new ExitCommand().send(out);
    }

    public void write(int address, int data) {
        new WriteCommand(address, data).send(out);
    }

    public int read(int address) {
        new ReadCommand(address).send(out);
        return scanner.nextInt();
    }

    // private Process process;
    private Scanner scanner;
    private PrintStream out;
}
