import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        try {
            MemoryManager manager = new MemoryManager();
            manager.write(10, 5);
            System.out.println(manager.read(10));
            // manager.exit();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
