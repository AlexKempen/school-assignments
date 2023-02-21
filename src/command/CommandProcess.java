package src.command;

import src.operatingsystem.ProcessUtils;

public class CommandProcess {
    /**
     * The main function of a CommandReciever process.
     * Uses a CommandReciever object to repeatedly execute
     * commands on an Executor.
     */
    public static void main(String[] args) {
        CommandStream stream = new CommandStream();
        stream.addInputStream(System.in);
        stream.addOutputStream(System.out);
        CommandReceiver reciever = new CommandReceiver(stream);
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
        CommandStream stream = new CommandStream();
        stream.addInputStream(process.getInputStream());
        stream.addOutputStream(process.getOutputStream());
        return stream;
    }

    public Process getProcess() {
        return process;
    }

    private Process process;
}
