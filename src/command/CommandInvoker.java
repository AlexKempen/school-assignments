package src.command;

import java.io.Serializable;

import src.operatingsystem.ProcessUtils;

/**
 * Executes Commands by sending them to a given CommandProcess.
 * Note the given command process assumes ownership of a given executor.
 */
public class CommandInvoker<T extends Executor> {
    public CommandInvoker(T executor) {
        process = CommandProcess.startCommandProcess();
        stream = new CommandStream(process.getInputStream(), process.getOutputStream());
        // send executor to the process
        stream.writeObject(executor);
    }

    /**
     * Writes a command to out.
     */
    public void send(Command<T> command) {
        stream.writeObject(command);
    }

    public <R extends Serializable> R send(ResultCommand<T, R> command) {
        stream.writeObject(command);
        Object object = stream.readObject();
        return stream.<R>castObject(object);
    }

    public void exit() {
        stream.writeObject(new ExitCommand());
        ProcessUtils.waitForProcess(process);
    }

    private Process process;
    private CommandStream stream;
}
