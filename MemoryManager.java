import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A class which uses a MemoryProcess.
 */
public class MemoryManager {
    MemoryManager(Process process) throws IOException {
        this.process = process;
        // true to enable autoflushing
        printWriter = new PrintWriter(process.getOutputStream(), true);
        scanner = new Scanner(process.getInputStream());
    }

    public static MemoryManager startMemoryManager() {
        try {
            // compile MemoryProcess.java
            Process compileProcess = Runtime.getRuntime().exec("javac MemoryProcess.java");
            compileProcess.waitFor();
            return new MemoryManager(Runtime.getRuntime().exec("java MemoryProcess"));
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }

    public void exit() throws InterruptedException {
        printWriter.println("exit");
        printWriter.flush();
        process.waitFor();
        printWriter.close();
    }

    public void write(int address, int data) {
        printWriter.println("write");
        printWriter.println(address);
        printWriter.println(data);
        printWriter.flush();
    }

    public int read(int address) {
        printWriter.printf("read\n");
        printWriter.flush();
        printWriter.printf(address + "\n");
        printWriter.flush();

        int data = this.scanner.nextInt();
        return data;
    }

    private Process process;
    private Scanner scanner;
    private PrintWriter printWriter;
}
