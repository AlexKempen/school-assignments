package tests;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

import src.command.CommandInvoker;
import src.command.CommandReceiver;
import src.command.CommandStream;
import src.command.ExitCommand;
import src.memory.Memory;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

public class CommandTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void initializeCommandStreams() throws IOException {
        File sentCommands = folder.newFile("commands.txt");
        File commandResults = folder.newFile("results.txt");

        receiverStream = new CommandStream();
        invokerStream = new CommandStream();

        invokerStream.addOutputStream(new FileOutputStream(sentCommands));
        receiverStream.addOutputStream(new FileOutputStream(commandResults));

        invokerStream.addInputStream(new FileInputStream(commandResults));
        receiverStream.addInputStream(new FileInputStream(sentCommands));
    }

    @After
    public void closeCommandStreams() {
        invokerStream.close();
        receiverStream.close();
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
        Assert.assertNotNull((Memory)receiverStream.readObject());

        invoker.send(writeCommand);
        Assert.assertNotNull((WriteCommand) receiverStream.readObject());

        // write result before command
        receiverStream.writeObject(expected);
        Integer result = invoker.send(readCommand);
        Assert.assertEquals(result, expected);
        Assert.assertNotNull((ReadCommand) receiverStream.readObject());
    }

    @Test
    public void TestCommandReceiver() {
        invokerStream.writeObject(new Memory());

        CommandReceiver receiver = new CommandReceiver(receiverStream);

        Integer expected = 10;
        int testAddress = 5;
        invokerStream.writeObject( new WriteCommand(testAddress, expected));
        invokerStream.writeObject(new ReadCommand(testAddress));
        invokerStream.writeObject(new ExitCommand());
        
        receiver.processCommands();

        Assert.assertEquals(expected, invokerStream.readObject());
    }
}