package src.cpu;

import src.command.CommandInvoker;
import src.command.CommandProcess;
import src.cpu.command.IncrementProgramCounterCommand;
import src.cpu.command.ReadRegisterCommand;
import src.cpu.command.WriteRegisterCommand;
import src.operatingsystem.Manager;

public class CpuManager extends Manager<Cpu> {
    public CpuManager(CommandInvoker<Cpu> invoker, CommandProcess commandProcess) {
        super(invoker, commandProcess);
    }

    public int readRegister(Register register) {
        return invoker.send(new ReadRegisterCommand(register));
    }

    public void writeRegister(Register register, int data) {
        invoker.send(new WriteRegisterCommand(register, data));
    }

    public void incrementProgramCounter() {
        invoker.send(new IncrementProgramCounterCommand());
    }
}