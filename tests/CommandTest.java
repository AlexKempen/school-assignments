package tests;

import org.junit.Test;
import org.junit.Assert;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.memory.Memory;
import src.memory.command.ReadCommand;
import src.memory.command.WriteCommand;

public class CommandTest {
    @Test
    public void TestMemoryCommands() {
        // invoker spawns process which handles commands
        Process commandProcess = CommandProcess.startCommandProcess();
        CommandInvoker<Memory> invoker = new CommandInvoker<>(new Memory(), commandProcess);
        Assert.assertTrue(commandProcess.isAlive());

        invoker.send(new WriteCommand(5, 10));

        Integer integer = 10;
        Assert.assertEquals(invoker.send(new ReadCommand(5)), integer);
        invoker.exit();
        Assert.assertFalse(commandProcess.isAlive());
    }
}
