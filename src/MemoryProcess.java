package src;

import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Creates a memory in a separate process and exposes its methods.
 */
public class MemoryProcess {
    /**
     * The main method instantiated by this class.
     */
    public static void main(String[] args) {
        Memory memory = new Memory();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            if (command == "read") {
                int address = scanner.nextInt();
                System.out.println(memory.read(address));
            } else if (command == "write") {
                memory.write(scanner.nextInt(), scanner.nextInt());
            } else if (command == "exit") {
                break;
            }
        }
        scanner.close();
    }

    public MemoryProcess() throws IOException {
        this.process = startMemoryProcess();
        this.scanner = new Scanner(process.getInputStream());
        // true to enable autoflushing
        this.printWriter = new PrintWriter(process.getOutputStream(), true);
    }

    public void exit() throws InterruptedException {
        this.printWriter.println("exit");
        this.process.waitFor();
    }

    public void write(int address, int data) {
        this.printWriter.println("write");
        this.printWriter.print(address);
        this.printWriter.print(data);
    }

    public int read(int address) {
        this.printWriter.println("read");
        this.printWriter.println(address);
        return this.scanner.nextInt();
    }

    /**
     * @return {Process} : A process which wraps Memory.
     */
    private Process startMemoryProcess() throws IOException {
        return Runtime.getRuntime().exec("java MemoryProcess");
    }

    private Process process;
    private Scanner scanner;
    private PrintWriter printWriter;
}
