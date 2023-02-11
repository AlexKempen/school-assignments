package src.command;

import java.io.ObjectInputStream;

public class CommandReciever {
    public CommandReciever(Executor executor, ObjectInputStream in) {
        this.executor = executor;
        this.in = in;
    }

    public void handleCommands() {
        while (true) {
            Object object = getCommandObject();
            if (executeCommandObject(object)) {
                return;
            }
        }
    }

    /**
     * Retrievs a command object from the input stream.
     */
    private Object getCommandObject() {
        try {
            return in.readObject();
        } catch (Exception e) {
            throw new AssertionError("Failed to read in Command object.", e);
        }
    }

    /**
     * Executes a command object.
     * 
     * @return true if the process should exit.
     */
    private boolean executeCommandObject(Object object) {
        if (!(object instanceof Command<?>)) {
            throw new AssertionError("Expected valid command.");
        }

        // Suppress unchecked cast warning
        // T cannot be verified due to type erasure
        @SuppressWarnings("unchecked")
        Command<Executor> command = (Command<Executor>) object;

        command.execute(executor);
        return command.exit();
    }

    private Executor executor;
    private ObjectInputStream in;
}