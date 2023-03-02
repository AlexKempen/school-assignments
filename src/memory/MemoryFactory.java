package src.memory;

import java.util.List;

import src.command.CommandInvoker;
import src.command.CommandProcess;

public class MemoryFactory {
    public MemoryInterface makeMemoryManager(List<Integer> program) {
        CommandProcess commandProcess = CommandProcess.startCommandProcess();
        MemoryManager memory = new MemoryManager(
                new CommandInvoker<Memory>(new Memory(), commandProcess.makeCommandStream()));
        memory.loadProgram(program);
        return memory;
    }

    public MemoryInterface makeMemory(List<Integer> program) {
        Memory memory = new Memory();
        for (int i = 0; i < program.size(); ++i) {
            memory.write(i, program.get(i));
        }
        return memory;
    }
}
