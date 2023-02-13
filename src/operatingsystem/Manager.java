package src.operatingsystem;

import src.command.CommandInvoker;
import src.command.Executor;

/**
 * A generic base class for implementing a Subsystem Manager.
 */
public class Manager<T extends Executor> {
    protected Manager(T executor) {
        invoker = new CommandInvoker<T>(executor);
    }

    public void exit() {
        invoker.exit();
    }

    protected CommandInvoker<T> invoker;
}
