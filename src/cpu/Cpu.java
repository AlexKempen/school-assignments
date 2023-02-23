package src.cpu;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import src.command.Executor;
import src.memory.MemoryManager;

public class Cpu extends Executor {
    public Cpu(MemoryManager memory, Timer timer) {
        this.timer = timer;
    }

    /**
     * Write a program to memory.
     */
    public void loadProgram(List<Integer> program) {
        memory.batchWrite(0, program);
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

    public Instruction fetchNextInstruction() {
        return Instruction.getInstruction(fetchNext());
    }

    /**
     * Returns the next program instruction and increments the program counter.
     */
    public int fetchNext() {
        return memory.read(registers.incrementProgramCounter());
    }

    public int fetchAddress(int address) {
        return memory.read(address);
    }

    public void setRegister(Register register, int value) {
        registers.write(register, value);
    }

    private OperatingMode mode = OperatingMode.USER;
    private Timer timer;
    private Registers registers;
    private MemoryManager memory;
}