package tests;

import static org.junit.Assert.assertThrows;
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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import src.cpu.Cpu;
import src.cpu.CpuFactory;
import src.memory.MemoryFactory;
import src.memory.MemoryManager;

public class MainTest {
    @BeforeEach
    public void setup() throws IOException {
        cpuFactory = new CpuFactory();
        // use a basic memory to avoid spawning process for testing

        int seed = 42;
        cpuFactory.setInput(seed);
        inputs = new Random(seed).ints(1, 101);

        PipedInputStream pipeIn = new PipedInputStream();
        in = new Scanner(pipeIn);
        cpuFactory.setOut(new PipedOutputStream(pipeIn));

        memoryFactory = new MemoryFactory();
    }

    @AfterEach
    public void cleanup() {
        in.close();
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
    @CsvSource({ "test1.txt, 999", "test2.txt, 999", "timerTest.txt, 2", "testInt.txt, 999" })
    public void testProgram(String fileName, Integer timerIncrement)
            throws FileNotFoundException, IOException, IllegalAccessException {
        File file = getFile(fileName);
        List<Integer> program = MemoryManager.parseProgram(new FileInputStream(file));

        // use a simple memory for testing
        Cpu cpu = cpuFactory.makeCpu(memoryFactory.makeMemory(program), timerIncrement);

        cpu.executeProgram();

        if (fileName.equals("test1.txt")) {
            assertEquals(inputs.limit(3).sum(), in.nextInt());
        } else if (fileName.equals("test2.txt")) {
            assertEquals("HIH", in.nextLine());
        } else if (fileName.equals("timerTest.txt")) {
            assertEquals("H", in.nextLine());
            assertEquals("I", in.nextLine());
        } else if (fileName.equals("testInt.txt")) {
            assertEquals("HI", in.next());
        }
    }

    @ParameterizedTest
    @Timeout(5)
    @CsvSource({ "sample1, 3", "sample2, 3", "sample3, 7", "sample4, 7", "sample5, 999" })
    public void testSampleProgram(String fileName, Integer timerIncrement)
            throws IllegalAccessException, FileNotFoundException, IOException {
        File file = getFile(fileName + ".txt");
        List<Integer> program = MemoryManager.parseProgram(new FileInputStream(file));

        // use a simple memory for testing
        Cpu cpu = cpuFactory.makeCpu(memoryFactory.makeMemory(program), timerIncrement);

        if (fileName.equals("sample4")) {
            assertThrows(new IllegalAccessException().getClass(), cpu::executeProgram);
        } else {
            cpu.executeProgram();
        }

        if (fileName.equals("sample1")) {
            assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678910", in.nextLine());
        }
    }
}