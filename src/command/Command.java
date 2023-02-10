package src.command;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public abstract class Command {
    protected Command(String name) {
        this.name = name;
    }

    public static Command parseCommand(List<Command> commands, Scanner scanner) {
        String line = scanner.nextLine();
        for (Command command : commands) {
            if (command.matches(line)) {
                command.recieve(scanner);
                return command;
            }
        }
        System.out.println(line);
        throw new AssertionError("Failed to parse command.");
    }

    public boolean matches(String line) {
        return line.equals(name);
    }

    public abstract void send(PrintStream out);

    /**
     * Parses a command from scanner which matches
     * and executes it.
     */
    public abstract void recieve(Scanner scanner);

    protected String name;
}
