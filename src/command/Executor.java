package src.command;

import java.io.PrintStream;

/**
 * Defines a command Executor which may be passed as an argument to a Command.
 * An Executor does not need to have any fields.
 */
public abstract class Executor {
    public Executor(PrintStream out) {
        this.out = out;
    }

    protected PrintStream out;
};