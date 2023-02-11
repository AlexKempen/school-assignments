package src.operating_system;

import src.cpu.CpuManager;
import src.memory.MemoryManager;

/**
 * Manages a cpu and memory process.
 */
public class OperatingSystem {
    public OperatingSystem() {
        this.memoryManager = MemoryManager.startMemoryManager();
        this.cpuManager = CpuManager.startCpuManager();
        this.mode = OperatingMode.USER;
    }

    public MemoryManager memoryManager;
    public CpuManager cpuManager;

    public OperatingMode mode;
    // private Timer timer;
}
