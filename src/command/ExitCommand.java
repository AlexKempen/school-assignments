package src.command;

import java.io.PrintStream;
import java.util.Scanner;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit");
    }

    @Override
    public void send(PrintStream out) {
        out.println(name);
    }

    @Override
    public void recieve(Scanner scanner) {
    }
}
