package src;

import java.io.IOException;

/**
 * A class which uses a MemoryProcess.
 */
public class MemoryManager {
    MemoryManager() throws IOException {
        this.memoryProcess = new MemoryProcess();
        memoryProcess.write(1, 10);
        System.out.println(memoryProcess.read(1));
    }

    private MemoryProcess memoryProcess;
}
