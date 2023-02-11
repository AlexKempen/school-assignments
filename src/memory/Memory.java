package src.memory;

import java.util.List;

import src.command.Executor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

public class Memory implements Executor {
    public Memory(PrintStream out) {
        this.out = out;
    }

    public void read(int address) {
        out.println(memory.get(address));
        out.flush();
    }

    public void write(int address, int data) {
        memory.set(address, data);
    }

    // initialize array list with 0
    private List<Integer> memory = new ArrayList<>(Collections.nCopies(2000, 0));
    private PrintStream out;
}