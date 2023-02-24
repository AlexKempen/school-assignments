package src.cpu;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import src.memory.MemoryInterface;

public class InstructionHandler {
    /**
     * @param input : An iterator pointing at a stream of random integers ranging from 1 to 100.
     */
    public InstructionHandler(Registers registers, MemoryInterface memory, Iterator<Integer> input, OutputStream out) {
        this.registers = registers;
        this.memory = memory;
        this.input = input;
        this.out = new PrintWriter(out, true);
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
    public boolean executeInstruction() {
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
                jump();
                break;
            case JUMP_IF_EQUAL:
                conditionalJump(registers.getAccumulator() == 0);
                break;
            case JUMP_IF_NOT_EQUAL:
                conditionalJump(registers.getAccumulator() != 0);
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
            case SYSTEM_CALL:
                systemCall();
            case SYSTEM_CALL_RETURN:
                systemCallReturn();
            case EXIT:
                return true;
            default:
                throw new AssertionError("The specified instruction is not implemented.");
        }
        return false;
    }

    private void load(int value) {
        registers.setAccumulator(value);
    }

    private void loadAddress(int address) {
        load(memory.read(address));
    }

    private void loadIndex(int value, Register register) {
        loadAddress(value + memory.read(registers.read(register)));
    }

    private void get() {
        registers.setAccumulator(input.next());
    }

    private void put() {
        int port = fetchNext();
        int value = registers.getAccumulator();
        if (port == 1) {
            out.println(value);
        } else if (port == 2) {
            out.println((char) value);
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

    private void jump() {
        registers.write(Register.PROGRAM_COUNTER, fetchNext());
    }

    private void conditionalJump(boolean condition) {
        // cannot be ternary due to void
        if (condition) {
            jump();
        } else {
            // always fetch and discard address
            fetchNext();
        }
    }

    private void call() {

    }

    private void ret() {

    }

    private void push() {

    }

    private void pop() {

    }

    private void systemCall() {

    }

    private void systemCallReturn() {

    }

    public void interrupt() {

    }

    private OperatingMode mode = OperatingMode.USER;
    private Registers registers;
    private MemoryInterface memory;

    private Iterator<Integer> input;
    private PrintWriter out;
}
