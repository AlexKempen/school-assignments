package src.operatingsystem;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.command.Executor;

/**
 * A generic base class for managing a CommandProcess via a CommandInvoker.
 */
public class Manager<T extends Executor> {
    protected Manager(T executor) {
        invoker = new CommandInvoker<T>(executor, CommandProcess.startCommandProcess());
    }

    public void exit() {
        invoker.exit();
    }

    protected CommandInvoker<T> invoker;
}
