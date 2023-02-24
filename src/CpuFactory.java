package src;

import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.cpu.Cpu;
import src.cpu.InstructionHandler;
import src.cpu.Registers;
import src.cpu.Timer;
import src.memory.MemoryManager;
import src.memory.Memory;
import src.memory.MemoryInterface;

public class CpuFactory {
    public CpuFactory() {
        random = new Random();
        out = System.out;
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public void setMemory(MemoryInterface memory) {
        this.memory = memory;
    }

    public void setMemoryManager() {
        CommandProcess commandProcess = CommandProcess.startCommandProcess();
        memory = new MemoryManager(
                new CommandInvoker<Memory>(new Memory(), commandProcess.makeCommandStream()));
    }

    public Cpu makeCpu(List<Integer> program, int timerIncrement) {
        loadProgram(program);
        InstructionHandler handler = new InstructionHandler(new Registers(), memory, random, out);
        return new Cpu(handler, new Timer(timerIncrement));
    }

    private void loadProgram(List<Integer> program) {
        if (memory == null) {
            throw new AssertionError("Memory must be set before loading program.");
        }

        for (int i = 0; i < program.size(); ++i) {
            memory.write(i, program.get(i));
        }
    }

    private Random random;
    private OutputStream out;
    private MemoryInterface memory;
}