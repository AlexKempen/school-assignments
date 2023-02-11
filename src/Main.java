package src;

import src.memory.MemoryManager;
import src.operatingsystem.ProcessUtils;

class Main {
    public static void main(String[] args) {
        ProcessUtils.compile();

        try {
            MemoryManager manager = MemoryManager.startMemoryManager();

            // manager.write(10, 5);
            // System.out.println(manager.read(10));
            manager.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
