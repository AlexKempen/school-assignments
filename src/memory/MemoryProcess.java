package src.memory;

import java.io.IOException;
import java.io.ObjectInputStream;

import src.command.CommandReciever;

/**
 * Creates a memory in a separate process and exposes its methods.
 */
public class MemoryProcess {
    /*
     * The main method instantiated by this class.
     */
    public static void main(String[] args) {
        try {
            CommandReciever<Memory> reciever = new CommandReciever<>(
                    new Memory(System.out),
                    new ObjectInputStream(System.in));

            reciever.handleCommand();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        // Scanner scan = new Scanner(System.in);
        // if (scan.hasNext()) {
        // }
        // scan.close();
    }
}
