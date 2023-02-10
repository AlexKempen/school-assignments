package src.memory;

import java.io.PrintStream;
import java.util.Scanner;

public class ReadCommand extends MemoryCommand {
    ReadCommand(Memory memory) {
        super("read", memory);
    }

    ReadCommand(int address) {
        super("read");
        this.address = address;
    }

    @Override
    public void send(PrintStream out) {
        out.println(name);
        out.println(address);
    }

    @Override
    public void recieve(Scanner scanner) {
        address = Integer.parseInt(scanner.nextLine());
        data = memory.read(address);
    }

    private int address;
    public int data;
}