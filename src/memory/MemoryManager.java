package src.memory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.memory.command.BatchWriteCommand;
import src.operatingsystem.Manager;
import src.operatingsystem.ProcessUtils;
import src.operatingsystem.Subsystem;

/**
 * A class which creates and manages a MemoryProcess.
 */
public class MemoryManager extends Manager {
    MemoryManager(Process process) {
        super(process);
    }

    public static MemoryManager startMemoryManager() {
        return new MemoryManager(ProcessUtils.startSubsystemProcess(Subsystem.MEMORY));
    }

    /**
     * Loads a program into memory.
     */
    public void loadProgram(InputStream stream) {
        try (Scanner scanner = new Scanner(stream)) {
            List<Integer> program = new ArrayList<>();
            while (scanner.hasNextLine()) {
                // extracts the first digit in each line
                program.add(Integer.parseInt(scanner.findInLine("\\d+")));
            }
            invoker.send(new BatchWriteCommand(program));
        }
    }

    // public void write(int address, int data) {
    // invoker.send(new WriteCommand(address, data));
    // }

    // public int read(int address) {
    // invoker.send(new ReadCommand(address));
    // return Integer.parseInt(scanner.nextLine());
    // }
}
