package src;

import src.memory.MemoryManager;

class Main {
    public static void main(String[] args) {
        MemoryManager manager = new MemoryManager();
        manager.write(1, 5);
        System.out.println(manager.read(1));
    }
}
