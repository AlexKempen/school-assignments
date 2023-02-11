package src.command;

import java.io.Serializable;

public abstract class Command<T> implements Serializable {
    public boolean exit() {
        return false;
    }

    public abstract void execute(T executor);
}
