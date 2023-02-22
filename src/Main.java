package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import src.command.CommandProcess;
import src.cpu.CpuManager;
import src.memory.MemoryManager;
import src.operatingsystem.CpuInterface;
import src.operatingsystem.OperatingSystem;
import src.operatingsystem.Timer;

class Main {
    public static void main(String[] args) throws IOException {
        OperatingSystem operatingSystem = startOperatingSystem(args);

        // System.out.println(operatingSystem.fetchInstruction());
    }

    public static OperatingSystem startOperatingSystem(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IOException("Expected two arguments.");
        }

        List<Integer> program;
        try {
            program = CpuInterface.parseProgram(new FileInputStream(args[0]));
        } catch (IOException e) {
            throw new IOException("Failed to parse program file.", e);
        }

        int timerIncrement;
        try {
            timerIncrement = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IOException("Failed to parse timer increment.", e);
        }

        ManagerFactory managerFactory = new ManagerFactory();
        CpuInterface cpuInterface = new CpuInterface(managerFactory.makeMemoryManager(), managerFactory.makeCpuManager());
        cpuInterface.loadProgram(program);
        return new OperatingSystem(cpuInterface, new Timer(timerIncrement));
    }
}
