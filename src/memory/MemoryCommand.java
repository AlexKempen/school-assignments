package src.memory;

import src.command.Command;

abstract public class MemoryCommand extends Command {
    MemoryCommand(String name, Memory memory) {
        super(name);
        this.memory = memory;
    }

    MemoryCommand(String name) {
        super(name);
    }

    protected Memory memory;
}