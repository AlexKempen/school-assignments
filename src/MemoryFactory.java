package src;

import java.util.List;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.memory.Memory;
import src.memory.MemoryInterface;
import src.memory.MemoryManager;

public class MemoryFactory {
    public MemoryFactory() {
    }

    public void setProgram(List<Integer> program) {
    this.program = program;
    }

    public MemoryInterface makeMemoryManager() {
        if (program == null) {
            throw new NullPointerException("setProgram must be called before make.");
        }

        CommandProcess commandProcess = CommandProcess.startCommandProcess();
        MemoryManager memory = new MemoryManager(
                new CommandInvoker<Memory>(new Memory(), commandProcess.makeCommandStream()));
        memory.loadProgram(program);
        return memory;
    }

    public MemoryInterface makeMemory() {
        if (program == null) {
            throw new NullPointerException("setProgram must be called before make.");
        }

        Memory memory = new Memory();
        for (int i = 0; i < program.size(); ++i) {
            memory.write(i, program.get(i));
        }
        return memory;
    }

    List<Integer> program;
}
