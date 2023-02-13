package src.cpu.command;

import src.command.Command;
import src.cpu.Cpu;
import src.cpu.Register;

public class WriteRegisterCommand extends Command<Cpu> {
    public WriteRegisterCommand(Register register, int data) {
        this.register = register;
        this.data = data;
    }

    @Override
    public void execute(Cpu cpu) {
        cpu.registers.write(register, data);
    }

    private Register register;
    private int data;
}