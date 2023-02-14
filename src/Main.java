package src;

import java.io.IOException;

import src.operatingsystem.OperatingSystem;

class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        OperatingSystem operatingSystem = OperatingSystem.startOperatingSystem(args);
        // operatingSystem.fetchInstruction();
    }
}
