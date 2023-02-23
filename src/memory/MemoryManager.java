package src.memory;

import java.util.List;

import src.command.CommandInvoker;
import src.memory.command.BatchWriteCommand;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

/**
 * A class which manages a Memory inside a CommandProcess.
 */
public class MemoryManager implements MemoryInterface {
    public MemoryManager(CommandInvoker<Memory> invoker) {
        this.invoker = invoker;
    }

    public void loadProgram(List<Integer> program) {
        batchWrite(0, program);
    }

    private void batchWrite(int address, List<Integer> data) {
        invoker.send(new BatchWriteCommand(address, data));
    }

    @Override
    public void write(int address, int data) {
        invoker.send(new WriteCommand(address, data));
    }

    @Override
    public int read(int address) {
        return invoker.send(new ReadCommand(address));
    }

    public CommandInvoker<Memory> invoker;
}