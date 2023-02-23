package src.command;

/**
 * A generic base class for managing a CommandProcess via a CommandInvoker.
 */
public class Manager<T extends Executor> {
    protected Manager(CommandInvoker<T> invoker, CommandProcess commandProcess) {
        this.commandProcess = commandProcess;
        this.invoker = invoker;
    }

    public void exit() {
        invoker.exit();
        commandProcess.waitForProcess();
    }

    private CommandProcess commandProcess;
    protected CommandInvoker<T> invoker;
}