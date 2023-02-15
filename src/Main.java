package src;

import java.io.IOException;

import src.memory.MemoryManager;

class Main {
    public static void main(String[] args) throws IOException {
        // OperatingSystem operatingSystem = OperatingSystem.startOperatingSystem(args);
        // System.out.println(operatingSystem.fetchInstruction());
        MemoryManager memory = new MemoryManager();
        memory.write(10, 5);
        System.out.println(memory.read(10));

    }
}
