package src.cpu.command;

import src.command.Command;
import src.cpu.Cpu;

public class IncrementProgramCounterCommand extends Command<Cpu> {
    @Override
    public void execute(Cpu cpu) {
        // cpu.registers.write(Register.PROGRAM_COUNTER, cpu.registers.read(Register.PROGRAM_COUNTER) + 1);
    }
}