package src.operatingsystem;

public enum Instruction {
    LOAD_VALUE(1),
    LOAD_ADDRESS(2),
    LOAD_ADDRESS_FROM(3),
    // Load address + X into AC
    LOAD_ADDRESS_INDEX_TO_X(4),
    // Load address + Y into AC
    LOAD_ADDRESS_INDEX_TO_Y(5);

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
