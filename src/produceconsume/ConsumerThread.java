package produceconsume;

import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerThread extends Thread {

    ArrayBlockingQueue s2;
    public boolean interrupted = false;
    private long num;
    private long sum = 0;

    public ConsumerThread(ArrayBlockingQueue s2) {
        this.s2 = s2;
    }
    
    @Override
    public void run() {
        while (!interrupted) {
            try {
                num = (long) s2.take();
                sum += num;
                System.out.println("S2: Take " + num);
                sleep(1000);
            } catch (InterruptedException ex) {
                System.err.println("ConsumerThread Interrupted!");
                break;
            }
        }
    }
    
    public void stopThread() {
        interrupted = true;
    }
    
    public long getSum() {
        return sum;
    }

}