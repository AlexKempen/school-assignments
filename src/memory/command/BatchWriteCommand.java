package src.memory.command;

import java.util.List;

import src.command.Command;
import src.memory.Memory;

/**
 * A command which batch writes data into memory at address 0.
 */
public class BatchWriteCommand extends Command<Memory> {
    public BatchWriteCommand(List<Integer> data) {
        this.data = data;
    }

    @Override
    public void execute(Memory memory) {
        for (int i = 0; i < data.size(); ++i) {
            memory.write(i, data.get(i));
        }
    }

    private List<Integer> data;
}