package src.cpu;

public class Cpu {
    public Cpu(InstructionHandler handler, Timer timer) {
        this.handler = handler;
        this.timer = timer;
    }

    public void executeProgram() {
        while (true) {
            handler.fetchInstruction();
            if (handler.executeInstruction()) {
                break;
            }

            if (timer.poll()) {
                // interrupt at address 1000
                handler.interrupt(1000);
            }
        }
    }

    private Timer timer;
    private InstructionHandler handler;
}