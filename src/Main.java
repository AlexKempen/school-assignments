package src;

import src.memory.MemoryManager;

class Main {
    public static void main(String[] args) {
        try {
            MemoryManager manager = MemoryManager.startMemoryManager();
            manager.write(10, 5);
            System.out.println(manager.read(10));
            // manager.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
