package src.cpu;

import src.operatingsystem.Manager;

public class CpuManager extends Manager<Cpu> {
    public CpuManager() {
        super(new Cpu());
    }


}