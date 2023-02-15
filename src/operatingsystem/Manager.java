package src.operatingsystem;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.command.Executor;

/**
 * A generic base class for managing a CommandProcess via a CommandInvoker.
 */
public class Manager<T extends Executor> {
    protected Manager(T executor) {
        commandProcess = CommandProcess.startCommandProcess();
        invoker = new CommandInvoker<T>(executor, commandProcess.getCommandStream());
    }

    public void exit() {
        invoker.exit();
        ProcessUtils.waitForProcess(commandProcess.getProcess());
    }

    private CommandProcess commandProcess;
    protected CommandInvoker<T> invoker;
}
