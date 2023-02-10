enum OperatingMode {
    USER,
    KERNEL
}

/**
 * Manages a cpu and memory process.
 */
public class OperatingSystem {
    OperatingSystem() {
    }

    private MemoryManager memoryManager;
    // private CpuManager cpuManager;

    private OperatingMode mode;

    private Timer timer;
}
