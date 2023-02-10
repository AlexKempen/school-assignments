import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * A class which uses a MemoryProcess.
 */
public class MemoryManager {
    MemoryManager() throws IOException {
        process = startMemoryProcess();
        // true to enable autoflushing
        printWriter = new PrintWriter(process.getOutputStream(), true);
        scanner = new Scanner(process.getInputStream());
    }

    public static Process startMemoryProcess() throws IOException {
        return Runtime.getRuntime().exec("java MemoryProcess");
    }

    public void exit() throws InterruptedException {
        printWriter.println("exit");
        printWriter.flush();

        process.waitFor();

        scanner.close();
        printWriter.close();
    }

    public void write(int address, int data) {
        printWriter.println("write");
        printWriter.println(address);
        printWriter.println(data);
        printWriter.flush();
    }

    public int read(int address) {
        printWriter.println("read");
        printWriter.println(address);
        printWriter.flush();

        while (!scanner.hasNext()) {
            continue;
        }

        return Integer.parseInt(scanner.nextLine());
    }

    private Process process;
    private Scanner scanner;
    private PrintWriter printWriter;
}
