package src.command;

import java.io.PrintStream;
import java.io.Serializable;

/**
 * Defines a command Executor which may be passed as an argument to a Command.
 * An Executor does not need to have any fields.
 */
public abstract class Executor implements Serializable {
    /**
     * Sets the output stream of the invoker.
     * Called by a CommandReciever, which sets
     * the output stream of the executor.
     */
    public void SetOutput(PrintStream out) {
        this.out = out;
    }

    protected PrintStream out;

    private static final long serialVersionUID = 1L;
};