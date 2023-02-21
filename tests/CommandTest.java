package tests;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;

import src.command.CommandInvoker;
import src.command.CommandStream;
import src.memory.Memory;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

public class CommandTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void TestCommandInvoker() throws ClassNotFoundException, IOException {
        File sentCommands = folder.newFile("commands.txt");
        File commandResults = folder.newFile("results.txt");

        CommandStream receiverStream = new CommandStream();
        CommandStream stream = new CommandStream();

        stream.addOutputStream(new FileOutputStream(sentCommands));
        receiverStream.addOutputStream(new FileOutputStream(commandResults));

        stream.addInputStream(new FileInputStream(commandResults));
        receiverStream.addInputStream(new FileInputStream(sentCommands));

        Memory memory = new Memory();
        CommandInvoker<Memory> invoker = new CommandInvoker<>(memory, stream);

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

        stream.close();
        receiverStream.close();
    }

    // @Test
    // public void TestCommandReciever() {

    // }
}
