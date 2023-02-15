package src.command;

import java.io.Serializable;

/**
 * Executes Commands by sending them to a given CommandProcess.
 * Note the given command process assumes ownership of a given executor.
 */
public class CommandInvoker<T extends Executor> {
    public CommandInvoker(T executor, CommandStream stream) {
        this.stream = stream;
        // send executor to the process
        stream.writeObject(executor);
    }

    /**
     * Writes a command to out.
     */
    public void send(Command<T> command) {
        stream.writeObject(command);
    }

    /**
     * Writes a ResultCommand to out and returns the result.
     */
    public <R extends Serializable> R send(ResultCommand<T, R> command) {
        stream.writeObject(command);
        Object object = stream.readObject();
        return stream.<R>castObject(object);
    }

    /**
     * Writes an exit command to the stream.
     * Note the underlying Process may still take some time to exit.
     */
    public void exit() {
        stream.writeObject(new ExitCommand());
    }

    private CommandStream stream;
}