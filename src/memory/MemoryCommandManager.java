package src.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.command.ExitCommand;
import src.command.Command;

public class MemoryCommandManager {
    MemoryCommandManager(Memory memory) {
        commands = new ArrayList<>();
        commands.add(new WriteCommand(memory));
        commands.add(new ReadCommand(memory));
        commands.add(new ExitCommand());
    }

    Command parseCommand(Scanner scanner) {
        return Command.parseCommand(commands, scanner);
    }

    private List<Command> commands;
}