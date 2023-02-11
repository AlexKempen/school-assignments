package src.memory;

import src.Manager;
import src.ProcessUtils;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;
import src.operating_system.Subsystem;

/**
 * A class which creates and manages a MemoryProcess.
 */
public class MemoryManager extends Manager {
    MemoryManager(Process process) {
        super(process);
    }

    public static MemoryManager startMemoryManager() {
        return new MemoryManager(ProcessUtils.startSubsystemProcess(Subsystem.MEMORY));
    }

    public void write(int address, int data) {
        invoker.send(new WriteCommand(address, data));
    }

    public int read(int address) {
        invoker.send(new ReadCommand(address));
        return Integer.parseInt(scanner.nextLine());
    }
}
