package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import src.CpuFactory;
import src.MemoryFactory;
import src.cpu.Cpu;
import src.memory.Memory;
import src.memory.MemoryManager;

public class MainTest {
    @BeforeEach
    public void setupCpu() throws IOException {
        // workingDir = Path.of("", "tests/testcases");

        cpuFactory = new CpuFactory();
        // use a basic memory to avoid spawning process for testing
        cpuFactory.setMemory(new Memory());

        int seed = 42;
        cpuFactory.setInput(seed);
        inputs = new Random(seed).ints(1, 101);

        PipedInputStream pipeIn = new PipedInputStream();
        in = new Scanner(pipeIn);
        cpuFactory.setOut(new PipedOutputStream(pipeIn));

        memoryFactory = new MemoryFactory();
    }

    private CpuFactory cpuFactory;
    private MemoryFactory memoryFactory;
    private IntStream inputs;
    private Scanner in;

    public File getFile(String fileName) {
        URL url = this.getClass().getResource("/tests/resources/" + fileName);
        return new File(url.getFile());
    }

    @ParameterizedTest
    @Timeout(2)
    @CsvSource({ "test1.txt, -1", "test2.txt, -1", "timerTest.txt, 2" })
    public void testProgram(String fileName, Integer timerIncrement) throws FileNotFoundException, IOException {
        File file = getFile(fileName);
        List<Integer> program = MemoryManager.parseProgram(new FileInputStream(file));
        memoryFactory.setProgram(program);
        // use a simple memory for testing
        cpuFactory.setMemory(memoryFactory.makeMemory());

        Cpu cpu = cpuFactory.makeCpu();

        cpu.executeProgram();

        if (fileName.equals("test1.txt")) {
            assertEquals(inputs.limit(3).sum(), in.nextInt());
        }
        else if (fileName.equals("test2.txt")) {
            assertEquals("HI", in.nextLine());
        }
        else if (fileName.equals("timerTest.txt")) {
            assertEquals("H", in.nextLine());
            assertEquals("I", in.nextLine());
        }
    }
}