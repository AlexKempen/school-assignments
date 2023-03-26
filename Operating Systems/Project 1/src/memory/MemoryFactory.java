package src.memory;

import java.util.List;

import src.command.CommandInvoker;
import src.command.CommandProcess;

/**
 * A factory for a MemoryInterface.
 */
public class MemoryFactory {
    /**
     * Starts a CommandProcess which manages a Memory.
     * 
     * @return A MemoryInterface which automatically communicates with the
     *         underlying process.
     */
    public MemoryInterface makeMemoryManager(List<Integer> program) {
        CommandProcess commandProcess = CommandProcess.startCommandProcess();
        MemoryManager memory = new MemoryManager(
                // The basic memory constructed here is immediately passed to the commandProcess by invoker
                new CommandInvoker<Memory>(new Memory(), commandProcess.makeCommandStream()));
        memory.loadProgram(program);
        return memory;
    }

    /**
     * Constructs a basic memory. Used primarily for testing.
     */
    public MemoryInterface makeMemory(List<Integer> program) {
        Memory memory = new Memory();
        for (int i = 0; i < program.size(); ++i) {
            memory.write(i, program.get(i));
        }
        return memory;
    }
}
