package src.command;

public class CommandProcess {
    /**
     * The main function of a CommandReceiver process.
     * Uses a CommandReceiver object to repeatedly execute
     * commands on an Executor.
     */
    public static void main(String[] args) {
        CommandStream stream = new CommandStream(System.out);
        stream.addInputStream(System.in);
        CommandReceiver receiver = new CommandReceiver(stream);
        receiver.processCommands();
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

    public CommandStream makeCommandStream() {
        CommandStream stream = new CommandStream(process.getOutputStream());
        stream.addInputStream(process.getInputStream());
        return stream;
    }

    public void waitForProcess() {
        ProcessUtils.waitForProcess(process);
    }

    private Process process;
}
