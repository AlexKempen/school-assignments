package src;


import java.util.List;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.cpu.Cpu;
import src.cpu.InstructionHandler;
import src.cpu.Registers;
import src.cpu.Timer;
import src.memory.MemoryManager;
import src.memory.Memory;

public class CpuFactory {
    public CpuFactory() {}

    public Cpu makeCpu(List<Integer> program, int timerIncrement) {
        CommandProcess commandProcess = CommandProcess.startCommandProcess();
        MemoryManager memory = new MemoryManager(new CommandInvoker<Memory>(new Memory(), commandProcess.makeCommandStream()), commandProcess);
        memory.loadProgram(program);

        InstructionHandler handler = new InstructionHandler(new Registers(), memory);
        return new Cpu(handler, new Timer(timerIncrement));
    }
}
