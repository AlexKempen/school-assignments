package src.memory;

import java.util.List;

import src.memory.command.BatchWriteCommand;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;
import src.operatingsystem.Manager;

/**
 * A class which manages a Memory inside a CommandProcess.
 */
public class MemoryManager extends Manager<Memory> {
    public MemoryManager() {
        super(new Memory());
    }

    public void batchWrite(int address, List<Integer> data) {
        invoker.send(new BatchWriteCommand(address, data));
    }

    public void write(int address, int data) {
        invoker.send(new WriteCommand(address, data));
    }

    public int read(int address) {
        return invoker.send(new ReadCommand(address));
    }
}
