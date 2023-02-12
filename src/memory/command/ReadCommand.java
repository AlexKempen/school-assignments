package src.memory.command;

import src.command.Command;
import src.memory.Memory;

public class ReadCommand extends Command<Memory> {
    public ReadCommand(int address) {
        this.address = address;
    }

    @Override
    public void execute(Memory memory) {
        memory.read(address);
    }

    private int address;
}