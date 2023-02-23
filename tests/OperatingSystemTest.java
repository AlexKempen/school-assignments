package tests;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import src.cpu.Cpu;

import org.junit.Assert;

public class OperatingSystemTest {
    @Test
    public void testParseProgram() throws IOException {
        String test = "25 // Some comment\n.10 Comment #5\n.15";
        List<Integer> expected = Arrays.asList(25, 10, 15);

        InputStream inputStream = new ByteArrayInputStream(test.getBytes());
        List<Integer> result = Cpu.parseProgram(inputStream);
        Assert.assertArrayEquals(expected.toArray(), result.toArray());
    }
}