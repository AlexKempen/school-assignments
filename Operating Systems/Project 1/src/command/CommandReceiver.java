package src.command;

/**
 * A class defining a generic receiver for Commands
 * which receives them from an ObjectInputStream.
 * The first object in the ObjectInputStream is the
 * executor; it receives an output stream which
 * can be used to stream data back to the CommandInvoker.
 */
public class CommandReceiver {
    public CommandReceiver(CommandStream stream) {
        this.stream = stream;
        executor = stream.<Executor>read();
    }

    public void processCommands() {
        while (true) {
            Object object = stream.readObject();
            if (executeCommandObject(object)) {
                stream.close();
                return;
            }
        }
    }

    /**
     * Executes a command object.
     * 
     * @return true if the process should exit.
     */
    private boolean executeCommandObject(Object object) {
        if (object instanceof Command<?>) {
            Command<Executor> command = stream.<Command<Executor>>castObject(object);
            command.execute(executor);
            return command.exit();
        } else if (object instanceof ResultCommand<?, ?>) {
            ResultCommand<Executor, ?> resultCommand = stream
                    .<ResultCommand<Executor, ?>>castObject(object);
            stream.writeObject(resultCommand.execute(executor));
            return false; // always continue after ResultCommand
        }
        throw new AssertionError("Expected valid command.");
    }

    private Executor executor;
    private CommandStream stream;
}