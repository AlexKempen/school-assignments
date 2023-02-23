package src.cpu;
public class Timer {
    public Timer(int increment) {
        this.increment = increment;
    }

    /**
     * Polls the timer. 
     * @return : true if the timer has triggered, and false otherwise.
     */
    public boolean poll() {
        current = (++current) % increment;
        return current == 0;
    }

    private final int increment;
    private int current = 0;
}
