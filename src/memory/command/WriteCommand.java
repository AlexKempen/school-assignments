package src.memory.command;

import src.command.Command;
import src.memory.Memory;

public class WriteCommand extends Command<Memory> {
    public WriteCommand(int address, int data) {
        this.address = address;
        this.data = data;
    }

    @Override
    public void execute(Memory memory) {
        memory.write(address, data);
    }

    private int address;
    private int data;
    private static final long serialVersionUID = Integer.valueOf("write");
}