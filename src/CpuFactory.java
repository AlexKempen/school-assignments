package src;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.Random;

import src.cpu.Cpu;
import src.cpu.InstructionHandler;
import src.cpu.Registers;
import src.cpu.Timer;
import src.memory.MemoryInterface;

public class CpuFactory {
    public CpuFactory() {
        out = System.out;
    }

    public void setMemory(MemoryInterface memory) {
        this.memory = memory;
    }

    public void setInput(long seed) {
        input = new Random(seed).ints(1, 101).iterator();
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public void setTimerIncrement(int timerIncrement) {
        this.timerIncrement = timerIncrement;
    }

    public Cpu makeCpu() {
        if (memory == null) { throw new NullPointerException("setMemory must be called before makeCpu."); }
        if (input == null) { setInput(0); }

        InstructionHandler handler = new InstructionHandler(new Registers(), memory, input, out);
        return new Cpu(handler, new Timer(timerIncrement));
    }

    private Iterator<Integer> input;
    private MemoryInterface memory;
    private OutputStream out;
    private int timerIncrement = Integer.MAX_VALUE;
}