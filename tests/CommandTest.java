package tests;

import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Assert;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.command.ExitCommand;
import src.memory.Memory;
import src.memory.MemoryManager;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

public class CommandTest {
    @Test
    public void TestExitCommand() throws IOException, InterruptedException {
        CommandProcess commandProcess = CommandProcess.startCommandProcess();
        Process process = commandProcess.getProcess();
        Assert.assertTrue(process.isAlive());

        ObjectOutputStream out = new ObjectOutputStream(process.getOutputStream());
        // write arbitrary executor
        out.writeObject(new Memory());
        // write exit command
        out.writeObject(new ExitCommand());
        process.waitFor();

        Assert.assertFalse(process.isAlive());
    }

    // @Test
    // public void TestMemoryManager() {
    //     MemoryManager manager = new MemoryManager();

    //     int expected = 10;
    //     manager.write(5, expected);
    //     Assert.assertEquals(manager.read(5), expected);

    //     manager.exit();
    // }

    // @Test
    // public void TestCommandInvoker() {
    //     // invoker spawns process which handles commands
    //     Process process = CommandProcess.startCommandProcess();

    //     CommandInvoker<Memory> invoker = new CommandInvoker<>(new Memory(), process);

    //     Integer expected = 10;
    //     invoker.send(new WriteCommand(5, expected));
    //     Assert.assertEquals(invoker.send(new ReadCommand(5)), expected);

    //     // test exit
    //     invoker.exit();
    //     Assert.assertFalse(process.isAlive());
    // }
}
