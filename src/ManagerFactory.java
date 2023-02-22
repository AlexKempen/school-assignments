package src;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.cpu.Cpu;
import src.cpu.CpuManager;
import src.memory.MemoryManager;
import src.memory.Memory;

public class ManagerFactory {
    public ManagerFactory() {
        memoryProcess = CommandProcess.startCommandProcess();
        memoryInvoker = new CommandInvoker<Memory>(new Memory(), memoryProcess.makeCommandStream());
    }

    public MemoryManager makeMemoryManager() {
        return new MemoryManager(memoryInvoker, memoryProcess);
    }

    public CpuManager makeCpuManager() {
        CommandProcess cpuProcess = CommandProcess.startCommandProcess();
        return new CpuManager(new CommandInvoker<Cpu>(new Cpu(), cpuProcess.makeCommandStream()), cpuProcess);
    }

    private CommandProcess memoryProcess;
    private CommandInvoker<Memory> memoryInvoker;
}
