package tests;

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

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import src.CpuFactory;
import src.Main;
import src.cpu.Cpu;
import src.memory.Memory;

public class MainTest {
    @Before
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

    @Test(timeout = 500)
    public void testProgram() throws FileNotFoundException, IOException {
        URL url = this.getClass().getResource("/tests/resources/test1.txt");
        File file = new File(url.getFile());

        List<Integer> program = Main.parseProgram(new FileInputStream(file));
        Cpu cpu = factory.makeCpu(program, Integer.MAX_VALUE);
        cpu.executeProgram();
        Assert.assertEquals(inputs.limit(3).sum(), in.nextInt());
    }
}