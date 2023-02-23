package src;


import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.cpu.Cpu;
import src.cpu.Timer;
import src.memory.MemoryManager;
import src.memory.Memory;

public class CpuFactory {
    public CpuFactory() {
    }

    public Cpu makeCpu(int timerIncrement) {
        CommandProcess commandProcess = CommandProcess.startCommandProcess();
        MemoryManager memory = new MemoryManager(new CommandInvoker<Memory>(new Memory(), commandProcess.makeCommandStream()), commandProcess);
        return new Cpu(memory, new Timer(timerIncrement));
    }

    private CommandProcess memoryProcess;
    private CommandInvoker<Memory> memoryInvoker;
}
