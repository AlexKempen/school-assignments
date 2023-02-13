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

    /**
     * A factory for a Command Process.
     * 
     * @return A Command Process.
     */
    public static Process startCommandProcess() {
        ProcessUtils.compile();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(PROCESS_COMMAND);
            return processBuilder.start();
        } catch (Exception exception) {
            throw new AssertionError("Failed to start process.", exception);
        }
    }

    private static final String[] PROCESS_COMMAND = { "java", "-cp", "out", "src/command/CommandProcess" };
}
