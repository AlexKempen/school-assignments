package src.cpu;

import java.util.Random;

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
     * 
     * @return true if the program should terminate, and false if it should
     *         continue.
     */
    public boolean executeInstruction() {
        Instruction instruction = Instruction.getInstruction(registers.read(Register.INSTRUCTION_REGISTER));
        switch (instruction) {
            case LOAD_VALUE:
                loadValue();
                break;
            case LOAD_ADDRESS:
                loadAddress();
                break;
            case LOAD_INDEX_ADDRESS:
            loadIndexAddress();
            break;
            case GET:
                get();
            case ADD_X:
                add(Register.X);
                break;
            case ADD_Y:
                add(Register.Y);
                break;
            case SUB_X:
                sub(Register.X);
                break;
            case SUB_Y:
                sub(Register.Y);
                break;
            case EXIT:
                exit();
                return true;
            default:
                throw new AssertionError("The specified instruction is not implemented.");
        }
        return false;
    }

    private void loadValue() {
        registers.setAccumulator(fetchNext());
    }

    // todo: memory protection
    private void loadAddress() {
        registers.setAccumulator(memory.read(fetchNext()));
    }

    private void loadIndexAddress() {
        int address = memory.read(fetchNext());
        registers.setAccumulator(memory.read(address));
    }

    private void get() {
        Random random = new Random();
        registers.setAccumulator(random.nextInt(1, 101));
    }

    private void add(Register register) {
        registers.setAccumulator(registers.getAccumulator() + registers.read(register));
    }


    private void sub(Register register) {
        registers.setAccumulator(registers.getAccumulator() - registers.read(register));
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
