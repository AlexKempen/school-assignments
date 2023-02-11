package src.command;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Executes `Command`s by sending them to a given `ObjectOutputStream`.
 */
public class CommandInvoker {
    public CommandInvoker(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * Writes a command to the output stream.
     */
    public <T extends Executor> void send(Command<T> command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private ObjectOutputStream out;
}
