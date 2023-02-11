package src.cpu;

import src.operatingsystem.Manager;
import src.operatingsystem.ProcessUtils;
import src.operatingsystem.Subsystem;

public class CpuManager extends Manager {
    public CpuManager(Process process) {
        super(process);
    }

    public static CpuManager startCpuManager() {
        return new CpuManager(ProcessUtils.startSubsystemProcess(Subsystem.CPU));
    }
}