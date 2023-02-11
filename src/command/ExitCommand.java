package src.command;

import javax.lang.model.type.NullType;

public class ExitCommand extends Command<NullType> {
    @Override
    public boolean exit() {
        return true;
    }

    @Override
    public void execute(NullType exectuor) {
        throw new AssertionError("An exit command may not be executed.");
    }

    private static final long serialVersionUID = 0;
}
