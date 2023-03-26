package src.memory;

/**
 * A basic interface defining operations on memory.
 */
public interface MemoryInterface {
    public void write(int address, int data);
    public int read(int address);
}
