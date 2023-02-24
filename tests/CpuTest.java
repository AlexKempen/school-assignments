package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import src.Main;

public class CpuTest {
    @Test
    public void testParseProgram() throws IOException {
        String test = "25 // Some comment\n.10 Comment #5\n.15";
        List<Integer> expected = Arrays.asList(25, 10, 15);

        InputStream inputStream = new ByteArrayInputStream(test.getBytes());
        List<Integer> result = Main.parseProgram(inputStream);
        assertArrayEquals(expected.toArray(), result.toArray());
    }
}
