package src.operatingsystem;

/**
 * Manages a cpu and memory process.
 */
public class OperatingSystem {
    public OperatingSystem(CpuInterface cpuInterface, Timer timer) {
        this.cpuInterface = cpuInterface;
        this.timer = timer;
    }

    public void exit() {
        cpuInterface.exit();
    }

    // public int fetchInstruction() {
    //     memoryManager.write(0, 10);
    //     return memoryManager.read(cpuManager.readRegister(Register.INSTRUCTION_REGISTER));
    // }


    private CpuInterface cpuInterface;
    private OperatingMode mode = OperatingMode.USER;
    private Timer timer;
}