package src.memory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import src.command.CommandInvoker;
import src.memory.command.BatchWriteCommand;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

/**
 * A class which manages a Memory inside a CommandProcess.
 */
public class MemoryManager implements MemoryInterface {
    public MemoryManager(CommandInvoker<Memory> invoker) {
        this.invoker = invoker;
    }

    public static List<Integer> parseProgram(InputStream in) throws IOException {
        List<Integer> memory = new ArrayList<>(Collections.nCopies(2000, 0));
        try (Scanner scanner = new Scanner(in)) {
            List<MatchResult> matches = scanner.findAll(INSTRUCTION_REGEX).toList();
            int index = 0;
            for (MatchResult match : matches) {
                int value = Integer.parseInt(match.group(2));
                if (match.group(1).equals(".")) {
                    index = value;
                } else {
                    memory.set(index++, value);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return memory;
    }

    private static final Pattern INSTRUCTION_REGEX = Pattern.compile("^(\\.?)(\\d+)", Pattern.MULTILINE);

    public void loadProgram(List<Integer> program) {
        batchWrite(0, program);
    }

    private void batchWrite(int address, List<Integer> data) {
        invoker.send(new BatchWriteCommand(address, data));
    }

    @Override
    public void write(int address, int data) {
        invoker.send(new WriteCommand(address, data));
    }

    @Override
    public int read(int address) {
        return invoker.send(new ReadCommand(address));
    }

    public CommandInvoker<Memory> invoker;
}