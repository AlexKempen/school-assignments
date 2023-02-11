package src.command;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Executes Commands by sending them to a given ObjectOutputStream.
 */
public class CommandInvoker {
    public CommandInvoker(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * Writes a command to out.
     */
    // T is a Command with a type which extends Executor
    public <T extends Command<? extends Executor>> void send(T command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private ObjectOutputStream out;
}
