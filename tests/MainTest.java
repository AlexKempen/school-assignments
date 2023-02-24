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
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import src.CpuFactory;
import src.Main;
import src.cpu.Cpu;
import src.memory.Memory;

public class MainTest {
    @BeforeEach
    public void setupCpu() throws IOException {
        // workingDir = Path.of("", "tests/testcases");

        factory = new CpuFactory();
        // use a basic memory to avoid spawning process for testing
        factory.setMemory(new Memory());

        int seed = 42;
        factory.setSeed(seed);
        inputs = new Random(seed).ints(1, 101);

        PipedInputStream pipeIn = new PipedInputStream();
        in = new Scanner(pipeIn);
        factory.setOut(new PipedOutputStream(pipeIn));
    }

    private CpuFactory factory;
    private IntStream inputs;
    private Scanner in;

    public File getFile(String fileName) {
        URL url = this.getClass().getResource("/tests/resources/" + fileName);
        return new File(url.getFile());
    }

    @ParameterizedTest
    @ValueSource(strings = { "test1.txt", "1.txt" })
    public void testProgram(String fileName) throws FileNotFoundException, IOException {
        File file = getFile(fileName);
        List<Integer> program = Main.parseProgram(new FileInputStream(file));
        Cpu cpu = factory.makeCpu(program, Integer.MAX_VALUE);

        cpu.executeProgram();

        if (fileName.equals("test1.txt")) {
            assertEquals(inputs.limit(3).sum(), in.nextInt());
        }
    }
}