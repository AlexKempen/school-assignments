package src.cpu;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import src.memory.MemoryInterface;

public class InstructionHandler {
    /**
     * @param input : An iterator pointing at a stream of random integers ranging
     *              from 1 to 100.
     */
    public InstructionHandler(Registers registers, MemoryInterface memory, Iterator<Integer> input, OutputStream out) {
        this.registers = registers;
        this.memory = memory;
        this.input = input;
        this.out = new PrintWriter(out, true);

        // init stack location
        registers.write(Register.STACK_POINTER, 1000);
    }

    public void fetchInstruction() {
        registers.write(Register.INSTRUCTION_REGISTER, fetchNext());
    }

    private int fetchNext() {
        return memory.read(registers.increment(Register.PROGRAM_COUNTER, 1));
    }

    /**
     * Executes the instruction in the INSTRUCTION_REGISTER.
     * 
     * @return true if the program should terminate, and false if it should
     *         continue.
     */
    public boolean executeInstruction() throws IllegalAccessException {
        Instruction instruction = Instruction.getInstruction(registers.read(Register.INSTRUCTION_REGISTER));
        switch (instruction) {
            case LOAD_VALUE:
                load(fetchNext());
                break;
            case LOAD_ADDRESS:
                loadAddress(fetchNext());
                break;
            case LOAD_INDEX:
                loadAddress(memory.read(fetchNext()));
                break;
            case LOAD_INDEX_X:
                loadIndex(fetchNext(), Register.X);
                break;
            case LOAD_INDEX_Y:
                loadIndex(fetchNext(), Register.Y);
                break;
            case LOAD_STACK_POINTER_X:
                loadIndex(registers.read(Register.STACK_POINTER), Register.X);
                break;
            case STORE:
                memory.write(fetchNext(), registers.getAccumulator());
                break;
            case GET:
                get();
                break;
            case PUT:
                put();
                break;
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
            case COPY_TO_X:
                registers.write(Register.X, registers.getAccumulator());
                break;
            case COPY_FROM_X:
                registers.setAccumulator(registers.read(Register.X));
                break;
            case COPY_TO_Y:
                registers.write(Register.Y, registers.getAccumulator());
                break;
            case COPY_FROM_Y:
                registers.setAccumulator(registers.read(Register.Y));
                break;
            case COPY_TO_STACK_POINTER:
                registers.write(Register.STACK_POINTER, registers.getAccumulator());
                break;
            case COPY_FROM_STACK_POINTER:
                registers.setAccumulator(registers.read(Register.STACK_POINTER));
                break;
            case JUMP:
                jump(fetchNext());
                break;
            case JUMP_IF_EQUAL:
                conditionalJump(registers.getAccumulator() == 0, fetchNext());
                break;
            case JUMP_IF_NOT_EQUAL:
                conditionalJump(registers.getAccumulator() != 0, fetchNext());
                break;
            case CALL:
                call();
                break;
            case RETURN:
                ret();
                break;
            case INCREMENT_X:
                registers.increment(Register.X, 1);
            case DECREMENT_X:
                registers.increment(Register.X, -1);
            case PUSH:
                push();
            case POP:
                pop();
            case INTERRUPT:
                // execute at 1500
                interrupt(1500);
            case INTERRUPT_RETURN:
                interruptReturn();
            case EXIT:
                out.close();
                return true;
            default:
                throw new AssertionError("The specified instruction is not implemented.");
        }
        return false;
    }

    private void load(int value) {
        registers.setAccumulator(value);
    }

    private void loadAddress(int address) throws IllegalAccessException {
        checkMemoryAccess(address);
        load(memory.read(address));
    }

    private void loadIndex(int value, Register register) throws IllegalAccessException {
        checkMemoryAccess(registers.read(register));
        loadAddress(value + memory.read(registers.read(register)));
    }

    private void get() {
        registers.setAccumulator(input.next());
    }

    private void put() {
        int port = fetchNext();
        int value = registers.getAccumulator();
        if (port == 1) {
            out.print(value);
        } else if (port == 2) {
            out.print((char) value);
        } else {
            throw new AssertionError("Invalid port number - expected 1 or 2.");
        }
    }

    private void add(Register register) {
        registers.increment(Register.ACCUMULATOR, registers.read(register));
    }

    private void sub(Register register) {
        registers.increment(Register.ACCUMULATOR, -registers.read(register));
    }

    private void jump(int address) throws IllegalAccessException {
        checkMemoryAccess(address);
        registers.write(Register.PROGRAM_COUNTER, address);
    }

    private void conditionalJump(boolean condition, int address) throws IllegalAccessException {
        if (condition) {
            jump(address);
        }
    }

    private void call() throws IllegalAccessException {
        pushStack(registers.read(Register.PROGRAM_COUNTER));
        jump(fetchNext());
    }

    private void ret() throws IllegalAccessException {
        jump(popStack());
    }

    private void push() {
        pushStack(registers.getAccumulator());
    }

    private void pop() {
        registers.setAccumulator(popStack());
    }

    private void pushStack(int value) {
        // change stack location before pushing
        registers.increment(Register.STACK_POINTER, -1);
        memory.write(registers.read(Register.STACK_POINTER), value);
    }

    private int popStack() {
        // fetch value from location pointed to by stack
        return memory.read(registers.increment(Register.STACK_POINTER, 1));
    }

    private void systemCall() {

    }

    private void systemCallReturn() {

    }

    /**
     * @return true if the program can be interrupted, and false otherwise.
     */
    public boolean canInterrupt() {
        return interruptsEnabled;
    }

    /**
     * Interrupts the processor. Throws if `canInterrupt()` is false.
     * @throws IllegalAccessException
     */
    public void interrupt(int address) throws IllegalAccessException {
        if (!canInterrupt()) {
            throw new IllegalAccessException("The program cannot be interrupted at this time.");
        }
        interruptsEnabled = false;
        mode = OperatingMode.KERNEL;

        pushStack(registers.read(Register.STACK_POINTER));
        pushStack(registers.read(Register.PROGRAM_COUNTER));
        jump(address);
    }

    private void interruptReturn() throws IllegalAccessException {
        // stack is LIFO
        registers.write(Register.PROGRAM_COUNTER, popStack());
        registers.write(Register.STACK_POINTER, popStack());
        // resume normal execution
        jump(registers.read(Register.PROGRAM_COUNTER));

        interruptsEnabled = true;
        mode = OperatingMode.USER;
    }

    /**
     * @throws IllegalAccessException if the address cannot be legally accessed.
     */
    private void checkMemoryAccess(int address) throws IllegalAccessException {
        if (address < 0 || address >= 2000) {
            throw new IllegalAccessException("The specified address is out of bounds.");
        }
        else if (mode == OperatingMode.USER && address >= 1000) {
            throw new IllegalAccessException("Cannot access the specified address in user mode.");
        }
    }

    // private OperatingMode mode = OperatingMode.USER;
    private Registers registers;
    private MemoryInterface memory;

    private Iterator<Integer> input;
    private PrintWriter out;
    private OperatingMode mode = OperatingMode.USER;
    private boolean interruptsEnabled = true;
}
