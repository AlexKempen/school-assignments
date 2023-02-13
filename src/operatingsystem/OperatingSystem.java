package src.operatingsystem;

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
    public OperatingSystem() {
        this.memoryManager = new MemoryManager();
        this.cpuManager = new CpuManager();
        this.mode = OperatingMode.USER;
    }

    public void exit() {
        memoryManager.exit();
        cpuManager.exit();
    }

    /**
     * Loads a program into memory at address 0.
     */
    public void loadProgram(InputStream in) {
        try (Scanner scanner = new Scanner(in)) {
            // Regex matches the start of the string, any amount of whitespace, a digit, and
            // then any characters before the end
            List<Integer> program = scanner.findAll(INSTRUCTION_REGEX)
                    .map(match -> Integer.parseInt(match.group(1)))
                    .toList();
            memoryManager.batchWrite(0, program);
        }
    }

    private static final Pattern INSTRUCTION_REGEX = Pattern.compile("^.?(\\d+).*$", Pattern.MULTILINE);

    public int fetchInstruction() {
        return memoryManager.read(cpuManager.readRegister(Register.INSTRUCTION_REGISTER));
    }

    private MemoryManager memoryManager;
    private CpuManager cpuManager;

    private OperatingMode mode;
    // private Timer timer;
}