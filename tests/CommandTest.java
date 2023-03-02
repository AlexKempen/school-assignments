package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import src.command.CommandInvoker;
import src.command.CommandReceiver;
import src.command.CommandStream;
import src.command.ExitCommand;
import src.memory.Memory;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

public class CommandTest {
    @BeforeEach
    public void initializeCommandStreams(@TempDir Path folder) throws IOException {
        File sentCommands = folder.resolve("commands.txt").toFile();
        File commandResults = folder.resolve("results.txt").toFile();

        invokerStream = new CommandStream(new FileOutputStream(sentCommands));
        receiverStream = new CommandStream(new FileOutputStream(commandResults));

        invokerStream.addInputStream(new FileInputStream(commandResults));
        receiverStream.addInputStream(new FileInputStream(sentCommands));
    }

    public CommandStream invokerStream;
    public CommandStream receiverStream;

    @Test
    public void TestCommandInvoker() {
        Memory memory = new Memory();
        CommandInvoker<Memory> invoker = new CommandInvoker<>(memory, invokerStream);

        Integer expected = 10;
        int testAddress = 5;
        WriteCommand writeCommand = new WriteCommand(testAddress, expected);
        ReadCommand readCommand = new ReadCommand(testAddress);

        // read executor from stream
        assertNotNull((Memory) receiverStream.readObject());

        invoker.send(writeCommand);
        assertNotNull((WriteCommand) receiverStream.readObject());

        // write result before command
        receiverStream.writeObject(expected);
        Integer result = invoker.send(readCommand);
        assertEquals(result, expected);
        assertNotNull((ReadCommand) receiverStream.readObject());

        receiverStream.close();
    }

    @Test
    public void TestCommandReceiver() {
        invokerStream.writeObject(new Memory());

        CommandReceiver receiver = new CommandReceiver(receiverStream);

        Integer expected = 10;
        int testAddress = 5;
        invokerStream.writeObject(new WriteCommand(testAddress, expected));
        invokerStream.writeObject(new ReadCommand(testAddress));
        invokerStream.writeObject(new ExitCommand());

        receiver.processCommands();

        assertEquals(expected, invokerStream.readObject());

        invokerStream.close();
    }
}