package src.cpu.command;

import src.command.ResultCommand;
import src.cpu.Cpu;
import src.cpu.Register;

public class ReadRegisterCommand extends ResultCommand<Cpu, Integer> {
    public ReadRegisterCommand(Register register) {
        this.register = register;
    }

    @Override
    public Integer execute(Cpu cpu) {
        return cpu.registers.read(register);
    }

    private Register register;
}