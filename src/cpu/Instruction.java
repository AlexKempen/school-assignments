package src.cpu;

public enum Instruction {
    LOAD_VALUE(1),
    LOAD_ADDRESS(2),
    LOAD_INDEX_ADDRESS(3),
    // Load address + X into AC
    LOAD_INDEX_X(4),
    LOAD_INDEX_Y(5),
    LOAD_STACK_POINTER_X(6),
    STORE_ADDRESS(7),
    GET(8),
    PUT(9),
    ADD_X(10),
    ADD_Y(11),
    SUB_X(12),
    SUB_Y(13),
    // Load address + Y into AC
    LOAD_ADDRESS_INDEX_TO_Y(5),

    EXIT(50);

    private Instruction(int value) {
        this.value = value;
    }

    private int value;

    public static Instruction getInstruction(int value) {
        Instruction[] instructions = Instruction.values();
        for (Instruction instruction : instructions) {
            if (instruction.value == value) {
                return instruction;
            }
        }
        throw new AssertionError("Failed to cast value to Instruction.");
    }
}
