package tests;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        File sendFile = folder.newFile("commands.txt");
        File receiveFile = folder.newFile("results.txt");

        FileInputStream receiveStream = new FileInputStream(receiveFile);
        FileOutputStream resultInStream = new FileOutputStream(receiveFile);

        FileOutputStream sendStream = new FileOutputStream(sendFile);
        FileInputStream commandOutStream = new FileInputStream(sendFile);

        ObjectOutputStream resultIn = new ObjectOutputStream(resultInStream);
        CommandStream stream = new CommandStream(receiveStream, sendStream);
        ObjectInputStream commandOut = new ObjectInputStream(commandOutStream);

        Memory memory = new Memory();
        CommandInvoker<Memory> invoker = new CommandInvoker<>(memory, stream);

        Integer expected = 10;
        int testAddress = 5;
        WriteCommand writeCommand = new WriteCommand(testAddress, expected);
        ReadCommand readCommand = new ReadCommand(testAddress);

        // read executor from stream
        Assert.assertNotNull((Memory) commandOut.readObject());

        invoker.send(writeCommand);
        Assert.assertNotNull((WriteCommand) commandOut.readObject());

        // write result before command
        resultIn.writeObject(expected);
        Integer result = invoker.send(readCommand);
        Assert.assertEquals(result, expected);
        Assert.assertNotNull((ReadCommand) commandOut.readObject());

        stream.close();
        commandOut.close();
        resultIn.close();
    }

    // @Test
    // public void TestCommandReciever() {

    // }
}
