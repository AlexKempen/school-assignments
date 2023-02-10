import java.util.Scanner;

/**
 * Creates a memory in a separate process and exposes its methods.
 */
public class MemoryProcess {
    /*
     * The main method instantiated by this class.
     */
    public static void main(String[] args) {
        Memory memory = new Memory();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            if (command.equals("read")) {
                int address = scanner.nextInt();
                System.out.println(memory.read(address));
            } else if (command.equals("write")) {
                int address = scanner.nextInt();
                int data = scanner.nextInt();
                memory.write(address, data);
            } else if (command.equals("exit")) {
                break;
            }
        }
        scanner.close();
    }
}
