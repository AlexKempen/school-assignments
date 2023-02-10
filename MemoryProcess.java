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
        PrintWriter printWriter = new PrintWriter(System.out);

        while (!scanner.hasNext()) {
            String command = scanner.nextLine();
            if (command == "read") {
                int address = Integer.parseInt(scanner.nextLine());
                System.out.println(memory.read(address));
                System.out.flush();
            } else if (command == "write") {
                int address = Integer.parseInt(scanner.nextLine());
                int data = Integer.parseInt(scanner.nextLine());
                memory.write(address, data);
            } else if (command == "exit") {
                break;
            }
        }
        scanner.close();
        printWriter.close();
    }
}
