package src.command;

import src.operatingsystem.ProcessUtils;

public class CommandProcess {
    /**
     * The main function of a CommandReciever process.
     * Uses a CommandReciever object to repeatedly execute
     * commands on an Executor.
     */
    public static void main(String[] args) {
        CommandStream stream = new CommandStream(System.in, System.out);
        CommandReciever reciever = new CommandReciever(stream);
        reciever.processCommands();
    }

    public CommandProcess(Process process) {
        this.process = process;
    }

    /**
     * A factory for a Command Process.
     */
    public static CommandProcess startCommandProcess() {
        ProcessUtils.compile();
        Process process;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(PROCESS_COMMAND);
            process = processBuilder.start();
        } catch (Exception exception) {
            throw new AssertionError("Failed to start process.", exception);
        }

        if (!process.isAlive()) {
            throw new AssertionError("Failed to start process.");
        }
        return new CommandProcess(process);
    }

    private static final String[] PROCESS_COMMAND = { "java", "-cp", "out", "src/command/CommandProcess" };

    public CommandStream getCommandStream() {
        return new CommandStream(process.getInputStream(), process.getOutputStream());
    }

    public Process getProcess() {
        return process;
    }

    private Process process;
}
