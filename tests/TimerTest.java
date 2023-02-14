package tests;

import org.junit.Test;
import org.junit.Assert;

import src.operatingsystem.Timer;

public class TimerTest {
    @Test
    public void test() {
        // timer interrupts after every two instructions (poll is called after instruction execution)
        Timer timer = new Timer(2);
        // execute one
        Assert.assertFalse(timer.poll());
        // execute two
        // interrupt
        Assert.assertTrue(timer.poll());
        // execute three
        Assert.assertFalse(timer.poll());
        // execute four
        // interrupt
        Assert.assertTrue(timer.poll());
    }
}
