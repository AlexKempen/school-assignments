package src.cpu;

import src.cpu.command.IncrementProgramCounterCommand;
import src.cpu.command.ReadRegisterCommand;
import src.cpu.command.WriteRegisterCommand;
import src.operatingsystem.Manager;

public class CpuManager extends Manager<Cpu> {
    public CpuManager() {
        super(new Cpu());
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