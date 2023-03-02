package src.memory.command;

import java.util.List;

import src.command.Command;
import src.memory.Memory;

/**
 * A command which batch writes data into memory, starting at the given address.
 */
public class BatchWriteCommand extends Command<Memory> {
    public BatchWriteCommand(int address, List<Integer> data) {
        this.address = address;
        this.data = data;
    }

    @Override
    public void execute(Memory memory) {
        for (int i = address; i < address + data.size(); ++i) {
            memory.write(i, data.get(i));
        }
    }

    private List<Integer> data;
    private int address;
}