package src.command;

/**
 * A class defining a generic receiver for Commands
 * which recieves them from an ObjectInputStream.
 * The first object in the ObjectInputStream is the
 * executor; it recieves an output stream which
 * can be used to stream data back to the CommandInvoker.
 */
public class CommandReciever {
    public CommandReciever(CommandStream stream) {
        this.stream = stream;
        executor = stream.<Executor>read();
    }

    public void processCommands() {
        while (true) {
            Object object = stream.readObject();
            if (executeCommandObject(object)) {
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
            return true; // always continue after ResultCommand
        }
        throw new AssertionError("Expected valid command.");
    }

    private Executor executor;
    private CommandStream stream;
}