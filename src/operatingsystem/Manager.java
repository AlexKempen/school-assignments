package src.operatingsystem;

import java.util.Scanner;

import src.command.CommandInvoker;
import src.command.ExitCommand;

/**
 * A generic base class for implementing a Subsystem Manager.
 */
public class Manager {
    protected Manager(Process process) {
        this.process = process;
        scanner = new Scanner(process.getInputStream());
        invoker = new CommandInvoker(ProcessUtils.getObjectOutputStream(process.getOutputStream()));
    }

    public void exit() {
        invoker.send(new ExitCommand());
        ProcessUtils.waitForProcess(process);
    }

    private Process process;
    protected Scanner scanner;
    protected CommandInvoker invoker;
}
