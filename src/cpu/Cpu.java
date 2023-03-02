package src.cpu;

/**
 * A class defining a basic Cpu capable of executing a program.
 */
public class Cpu {
    public Cpu(InstructionHandler handler, Timer timer) {
        this.handler = handler;
        this.timer = timer;
    }

    public void executeProgram() throws IllegalAccessException {
        while (true) {
            handler.fetchInstruction();
            if (handler.executeInstruction()) {
                break;
            }

            // timer.poll() is first to always keep incrementing
            if (timer.poll() && handler.canInterrupt()) {
                handler.interrupt(1000);
            }
        }
    }

    private Timer timer;
    private InstructionHandler handler;
}