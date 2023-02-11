package src.cpu;

import src.Manager;
import src.ProcessUtils;
import src.operating_system.Subsystem;

public class CpuManager extends Manager {
    public CpuManager(Process process) {
        super(process);
    }

    public static CpuManager startCpuManager() {
        return new CpuManager(ProcessUtils.startSubsystemProcess(Subsystem.CPU));
    }
}