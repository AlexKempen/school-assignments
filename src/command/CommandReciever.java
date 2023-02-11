package src.command;

import java.io.ObjectInputStream;

public class CommandReciever<T> {
    public CommandReciever(T executor, ObjectInputStream objectIn) {
        this.executor = executor;
        this.objectIn = objectIn;
    }

    public void handleCommand() {
        while (true) {
            Object object = getCommandObject();
            if (object instanceof Command<?>) {
                // Suppress unchecked cast warning - type erasure
                @SuppressWarnings("unchecked")
                Command<T> command = (Command<T>) object;
                if (command.exit()) {
                    return;
                }
                command.execute(executor);
            } else {
                throw new AssertionError("Expected valid command.");
            }
        }
    }

    private Object getCommandObject() {
        try {
            return objectIn.readObject();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private T executor;
    private ObjectInputStream objectIn;
}