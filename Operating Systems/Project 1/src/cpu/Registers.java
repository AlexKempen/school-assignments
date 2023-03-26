package src.cpu;

/**
 * A class defining several basic registers.
 */
public class Registers {
    public int read(Register register) {
        switch (register) {
            case PROGRAM_COUNTER:
                return programCounter;
            case STACK_POINTER:
                return stackPointer;
            case INSTRUCTION_REGISTER:
                return instructionRegister;
            case ACCUMULATOR:
                return accumulator;
            case X:
                return x;
            case Y:
                return y;
            default:
                throw new AssertionError("Failed to find register.");
        }
    }

    public void write(Register register, int value) {
        switch (register) {
            case PROGRAM_COUNTER:
                programCounter = value;
                break;
            case STACK_POINTER:
                stackPointer = value;
                break;
            case INSTRUCTION_REGISTER:
                instructionRegister = value;
                break;
            case ACCUMULATOR:
                accumulator = value;
                break;
            case X:
                x = value;
                break;
            case Y:
                y = value;
                break;
            default:
                throw new AssertionError("Failed to find register.");
        }
    }

    public void setAccumulator(int value) {
        accumulator = value;
    }

    public int getAccumulator() {
        return accumulator;
    }

    /**
     * Increments a register by a given amount.
     * @return The value of the register prior to incrementing.
     */
    public int increment(Register register, int amount) {
        int value = read(register);
        write(register, value + amount);
        return value;
    }

    private int programCounter;
    private int stackPointer;
    private int instructionRegister;
    private int accumulator;
    private int x;
    private int y;
}