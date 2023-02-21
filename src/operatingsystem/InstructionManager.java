package src.operatingsystem;

import src.cpu.Register;

public class InstructionManager {
    public InstructionManager(CpuInterface cpuInterface) {
        this.cpuInterface = cpuInterface;
    }

    public void executeInstruction(Instruction instruction) {
        switch (instruction) {
            case LOAD_ADDRESS:
                loadAddress();
            default:
                throw new AssertionError("The specified instruction is not implemented.");
        }
    }

    private void loadAddress() {
        cpuInterface.setRegister(Register.ACCUMULATOR, cpuInterface.fetchNext());
    }

    private CpuInterface cpuInterface;
}
