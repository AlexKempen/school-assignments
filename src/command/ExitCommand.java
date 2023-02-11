package src.command;

public class ExitCommand extends Command<Executor> {
    @Override
    public boolean exit() {
        return true;
    }

    @Override
    public void execute(Executor executor) {
        // do nothing
    }

    private static final long serialVersionUID = 0;
}
