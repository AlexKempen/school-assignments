package src.memory;

import java.util.Scanner;
import src.command.Command;
import src.command.ExitCommand;

/**
 * Creates a memory in a separate process and exposes its methods.
 */
public class MemoryProcess {
    /*
     * The main method instantiated by this class.
     */
    public static void main(String[] args) {
        MemoryCommandManager manager = new MemoryCommandManager(new Memory());

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            Command command = manager.parseCommand(scanner);
            if (command instanceof WriteCommand) {
                continue;
            } else if (command instanceof ReadCommand) {
                System.out.println(((ReadCommand) command).data);
            } else if (command instanceof ExitCommand) {
                break;
            }
        }
        scanner.close();
    }
}
