package src.cpu;

public class InstructionManager {
    public InstructionManager(Cpu cpu) {
        this.cpu = cpu;
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
        cpu.setRegister(Register.ACCUMULATOR, cpu.fetchNext());
    }

    private Cpu cpu;
}
