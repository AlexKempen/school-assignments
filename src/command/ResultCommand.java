package src.command;

import java.io.Serializable;

public abstract class ResultCommand<T extends Executor, R extends Serializable> {
    public abstract R execute(T executor);
}