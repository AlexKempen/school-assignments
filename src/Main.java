package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import src.command.CommandProcess;
import src.cpu.Cpu;
import src.cpu.Timer;
import src.memory.MemoryManager;

class Main {
    public static void main(String[] args) throws IOException {

        // System.out.println(operatingSystem.fetchInstruction());
    }

    public static Cpu startCpu(String[] args) {
        if (args.length != 2) {
            throw new IOException("Expected two arguments.");
        }

        List<Integer> program;
        try {
            program = Cpu.parseProgram(new FileInputStream(args[0]));
        } catch (IOException e) {
            throw new IOException("Failed to parse program file.", e);
        }

        int timerIncrement;
        try {
            timerIncrement = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IOException("Failed to parse timer increment.", e);
        }

        return new Cpu(new Timer(timerIncrement));
    }
}
