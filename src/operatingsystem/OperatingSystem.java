package src.operatingsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import src.cpu.CpuManager;
import src.cpu.Register;
import src.memory.MemoryManager;

/**
 * Manages a cpu and memory process.
 */
public class OperatingSystem {
    // Direct injection
    public OperatingSystem(MemoryManager memoryManager, CpuManager cpuManager, Timer timer) {
        this.memoryManager = memoryManager;
        this.cpuManager = cpuManager;
        this.timer = timer;
    }

    /**
     * Write a program to memory.
     */
    public void loadProgram(List<Integer> program) {
        memoryManager.batchWrite(0, program);
    }

    /**
     * Parses a program from an input stream into a list of Integers.
     */
    public static List<Integer> parseProgram(InputStream in) throws IOException {
        try (Scanner scanner = new Scanner(in)) {
            // Regex matches the start of the string, any amount of whitespace, a digit, and
            // then any characters before the end
             return scanner.findAll(INSTRUCTION_REGEX)
                    .map(match -> Integer.parseInt(match.group(1)))
                    .toList();
        }
    }

    private static final Pattern INSTRUCTION_REGEX = Pattern.compile("^\\.?(\\d+).*$", Pattern.MULTILINE);

    public void exit() {
        memoryManager.exit();
        cpuManager.exit();
    }

    // public void runInstruction() {

    // }

    public int fetchInstruction() {
        memoryManager.write(0, 10);
        return memoryManager.read(cpuManager.readRegister(Register.INSTRUCTION_REGISTER));
    }

    private MemoryManager memoryManager;
    private CpuManager cpuManager;

    private OperatingMode mode = OperatingMode.USER;
    private Timer timer;
}