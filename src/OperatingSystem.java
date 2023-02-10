package src;

enum OperatingMode {
    USER,
    KERNEL
}

/**
 * Manages a cpu and memory process.
 */
public class OperatingSystem {
    OperatingSystem() {
        // ProcessBuilder memoryProcessBuilder = new
        // ProcessBuilder("MemoryManager.java");
        // memoryProcessBuilder.redirectOutput(Redirect.appendTo());
    }

    private MemoryManager memoryManager;
    // private CpuManager cpuManager;

    private OperatingMode mode;

    private Timer timer;
}
