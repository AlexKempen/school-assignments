package src.memory.command;

import src.command.Command;
import src.memory.Memory;

public abstract class MemoryCommand extends Command<Memory> {
    protected MemoryCommand() {
    }

    public abstract void execute(Memory memory);
}