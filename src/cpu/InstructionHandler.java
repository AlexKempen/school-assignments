package src.cpu;

import src.memory.MemoryManager;

public class InstructionHandler {
    public InstructionHandler(Registers registers, MemoryManager memory) {
        this.registers = registers;
        this.memory = memory;
    }

    public void fetchInstruction() {
        registers.write(Register.INSTRUCTION_REGISTER, fetchNext());
    }

    private int fetchNext() {
        return memory.read(registers.incrementProgramCounter());
    }

    /**
     * Executes the instruction in the INSTRUCTION_REGISTER.
     * @return true if the program should terminate, and false if it should continue.
     */
    public boolean executeInstruction() {
        Instruction instruction = Instruction.getInstruction(registers.read(Register.INSTRUCTION_REGISTER));
        switch (instruction) {
            case LOAD_ADDRESS:
                loadAddress();
                break;
            case EXIT:
                exit();
                return true;
            default:
                throw new AssertionError("The specified instruction is not implemented.");
        }
        return false;
    }

    private void loadAddress() {
        registers.write(Register.ACCUMULATOR, fetchNext());
    }

    private void exit() {
        // terminate memory process
        memory.exit();
    }

    public void interrupt() {

    }

    private Registers registers;
    private MemoryManager memory;
}
