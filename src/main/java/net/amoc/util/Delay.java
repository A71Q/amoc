package net.amoc.util;

public class Delay extends Thread {
    private long delay;

    public Delay(long delay) {
        this.delay = delay;
    }

    public void run() {
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}