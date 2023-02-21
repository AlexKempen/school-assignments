package src.operatingsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import src.cpu.CpuManager;
import src.cpu.Register;
import src.memory.MemoryManager;

public class CpuInterface {
    public CpuInterface(MemoryManager memoryManager, CpuManager cpuManager) {
        this.memoryManager = memoryManager;
        this.cpuManager = cpuManager;
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

    private static final Pattern INSTRUCTION_REGEX = Pattern.compile("^\\.?(\\d+)", Pattern.MULTILINE);

    public void exit() {
        memoryManager.exit();
        cpuManager.exit();
    }

    public Instruction fetchNextInstruction() {
        return Instruction.getInstruction(fetchNext());
    }

    /**
     * Returns the next program instruction and increments the program counter.
     */
    public int fetchNext() {
        int result = memoryManager.read(cpuManager.readRegister(Register.PROGRAM_COUNTER));
        cpuManager.incrementProgramCounter();
        return result;
    }

    public int fetchAddress(int address) {
        return memoryManager.read(address);
    }

    public void setRegister(Register register, int value) {
        cpuManager.writeRegister(register, value);
    }

    public void incrementCounter() {
        cpuManager.incrementProgramCounter();
    }

    private MemoryManager memoryManager;
    private CpuManager cpuManager;
}