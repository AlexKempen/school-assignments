package src.memory.command;

import src.command.ResultCommand;
import src.memory.Memory;

public class ReadCommand extends ResultCommand<Memory, Integer> {
    public ReadCommand(int address) {
        this.address = address;
    }

    @Override
    public Integer execute(Memory memory) {
        return memory.read(address);
    }

    private int address;
}