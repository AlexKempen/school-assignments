package src.memory.command;

import src.memory.Memory;

public class ReadCommand extends MemoryCommand {
    public ReadCommand() {
    }

    public ReadCommand(int address) {
        this.address = address;
    }

    @Override
    public void execute(Memory memory) {
        memory.read(address);
    }

    private int address;
    private static final long serialVersionUID = 3;
}
