package src.cpu;

public enum Instruction {
    LOAD_VALUE(1),
    LOAD_ADDRESS(2),
    LOAD_INDEX(3),
    LOAD_INDEX_X(4),
    LOAD_INDEX_Y(5),
    LOAD_STACK_POINTER_X(6),
    STORE(7),
    GET(8),
    PUT(9),
    ADD_X(10),
    ADD_Y(11),
    SUB_X(12),
    SUB_Y(13),
    COPY_TO_X(14),
    COPY_FROM_X(15),
    COPY_TO_Y(16),
    COPY_FROM_Y(17),
    COPY_TO_STACK_POINTER(18),
    COPY_FROM_STACK_POINTER(19),
    JUMP(20),
    JUMP_IF_EQUAL(21),
    JUMP_IF_NOT_EQUAL(22),
    CALL(23),
    RETURN(24),
    INCREMENT_X(25),
    DECREMENT_X(26),
    PUSH(27),
    POP(28),
    INTERRUPT(29),
    INTERRUPT_RETURN(30),
    EXIT(50);

    /**
     * Converts a value to an Instruction.
     */
    public static Instruction getInstruction(int value) {
        Instruction[] instructions = Instruction.values();
        for (Instruction instruction : instructions) {
            if (instruction.value == value) {
                return instruction;
            }
        }
        throw new AssertionError("Failed to cast value to Instruction: " + value);
    }

    // singleton constructor
    private Instruction(int value) {
        this.value = value;
    }

    private int value;
}
