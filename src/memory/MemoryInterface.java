package src.memory;

public interface MemoryInterface {
    public void write(int address, int data);
    public int read(int address);
}
