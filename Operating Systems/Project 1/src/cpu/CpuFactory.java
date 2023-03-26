package src.cpu;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.Random;

import src.memory.MemoryInterface;

/**
 * A factory class for constructing a Cpu.
 */
public class CpuFactory {
    /**
     * Initializes the input stream of the input to a random stream with the given
     * seed.
     */
    public void setInput(long seed) {
        input = new Random(seed).ints(1, 101).iterator();
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public Cpu makeCpu(MemoryInterface memory, int timerIncrement) {
        if (input == null) {
            setInput(0);
        }
        if (out == null) {
            setOut(System.out);
        }

        InstructionHandler handler = new InstructionHandler(new Registers(), memory, input, out);
        return new Cpu(handler, new Timer(timerIncrement));
    }

    private Iterator<Integer> input;
    private OutputStream out;
}