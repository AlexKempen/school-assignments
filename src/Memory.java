package src;

import java.util.List;
import java.util.ArrayList;

public class Memory {
    public int read(int address) {
        return memory.get(address);
    }

    public void write(int address, int data) {
        memory.set(address, data);
    }

    private List<Integer> memory = new ArrayList<>(2000);
}