package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import src.cpu.Cpu;

public class Main {
    public static void main(String[] args) throws IOException {
        Cpu cpu = startCpu(getCpuFactory(), args);
        cpu.executeProgram();
    }


    public static CpuFactory getCpuFactory() {
        CpuFactory factory = new CpuFactory();
        factory.setMemoryManager();
        return factory;
    }

    public static Cpu startCpu(CpuFactory factory, String[] args) throws IOException {
        if (args.length != 2) {
            throw new IOException("Expected two arguments.");
        }

        List<Integer> program;
        try {
            program = parseProgram(new FileInputStream(args[0]));
        } catch (IOException e) {
            throw new IOException("Failed to parse program file.", e);
        }

        int timerIncrement;
        try {
            timerIncrement = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IOException("Failed to parse timer increment.", e);
        }

        return factory.makeCpu(program, timerIncrement);
    }
}
