package src.memory;

import java.io.PrintStream;
import java.util.Scanner;

public class WriteCommand extends MemoryCommand {
    WriteCommand(Memory memory) {
        super("write", memory);
    }

    WriteCommand(int address, int data) {
        super("write");
        this.address = address;
        this.data = data;
    }

    @Override
    public void send(PrintStream out) {
        out.println(name);
        out.println(address);
        out.println(data);
    }

    @Override
    public void recieve(Scanner scanner) {
        address = Integer.parseInt(scanner.nextLine());
        data = Integer.parseInt(scanner.nextLine());
        memory.write(address, data);
    }

    private int address;
    private int data;
}