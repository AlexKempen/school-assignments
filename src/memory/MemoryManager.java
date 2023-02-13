package src.memory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.memory.command.BatchWriteCommand;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;
import src.operatingsystem.Manager;

/**
 * A class which creates and manages a MemoryProcess.
 */
public class MemoryManager extends Manager<Memory> {
    public MemoryManager() {
        super(new Memory());
    }

    /**
     * Loads a program into memory at address 0.
     */
    public void loadProgram(InputStream in) {
        try (Scanner scanner = new Scanner(in)) {
            List<Integer> program = new ArrayList<>();
            while (scanner.hasNextLine()) {
                // extracts the first digit in each line
                program.add(Integer.parseInt(scanner.findInLine("\\d+")));
            }
            invoker.send(new BatchWriteCommand(0, program));
        }
    }

    public void write(int address, int data) {
        invoker.send(new WriteCommand(address, data));
    }

    public int read(int address) {
        return invoker.send(new ReadCommand(address));
    }
}
