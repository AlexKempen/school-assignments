package src.command;

import java.io.Serializable;

public abstract class Command<T extends Executor> implements Serializable {
    /**
     * Indicates whether the process should exit after running the command.
     * Overriden by ExitCommand.
     * 
     * @return : `true` if the Process should terminate, and false otherwise.
     */
    public boolean exit() {
        return false;
    }

    /**
     * Executes the command using the given executor.
     */
    public abstract void execute(T executor);
}
