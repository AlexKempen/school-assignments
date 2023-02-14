package tests;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import org.junit.Assert;
import src.operatingsystem.OperatingSystem;

public class OperatingSystemTest {
    @Test
    public void testParseProgram() throws IOException {
        String test = "25 Hello\n.10 Ahh\n.15";
        List<Integer> expected = Arrays.asList(25, 10, 15);

        InputStream inputStream = new ByteArrayInputStream(test.getBytes());
        List<Integer> result = OperatingSystem.parseProgram(inputStream);
        Assert.assertArrayEquals(expected.toArray(), result.toArray());
    }
}