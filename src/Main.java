package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import src.cpu.Cpu;
import src.memory.MemoryManager;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        checkArgs(args);
        List<Integer> program = parseProgram(args);
        int timerIncrement = parseTimerIncrement(args);
        Cpu cpu = getCpu(program, timerIncrement);
        cpu.executeProgram();
    }

    public static List<Integer> parseProgram(String[] args) throws IOException {
        try {
            return MemoryManager.parseProgram(new FileInputStream(args[0]));
        } catch (IOException e) {
            throw new IOException("Failed to parse program file.", e);
        }
    }

    public static int parseTimerIncrement(String[] args) throws IOException {
        try {
            return Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IOException("Failed to parse timer increment.", e);
        }
    }

    public static void checkArgs(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IOException("Expected two arguments.");
        }
    }

    public static Cpu getCpu(List<Integer> program, int timerIncrement) {
        MemoryFactory memoryFactory = new MemoryFactory();
        CpuFactory cpuFactory = new CpuFactory();

        File outFile = new File("out.txt");
        try {
            cpuFactory.setOut(new PrintStream(outFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cpuFactory.makeCpu(memoryFactory.makeMemoryManager(program), timerIncrement);
    }
}
