package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.memory.MemoryManager;

public class MemoryTest {
    @Test
    public void testParseProgram() throws IOException {
        String test = "25 // Some comment\n" +
                "10 (Comment #5)\n" +
                ".15\n" +
                "30\n" +
                "32";
        List<Integer> expected = new ArrayList<>(Collections.nCopies(2000, 0));
        expected.set(0, 25);
        expected.set(1, 10);
        expected.set(15, 30);
        expected.set(16, 32);

        InputStream inputStream = new ByteArrayInputStream(test.getBytes());
        List<Integer> result = MemoryManager.parseProgram(inputStream);
        assertArrayEquals(expected.toArray(), result.toArray());
    }
}
