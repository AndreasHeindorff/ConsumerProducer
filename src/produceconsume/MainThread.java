package ProduceConsume;

import produceconsume.ConsumerThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainThread {

    static ArrayBlockingQueue s1, s2;
    static List<ProducerThread> producers = new ArrayList<>();
    static ExecutorService pool;
    static ConsumerThread c1;
    static int[] numbers = {4, 5, 8, 12, 21, 22, 34, 35};
    static long total, end, start;

    public static void run(int n) {
        try {
            
            
            
            
            initBlockingQueues();
            fillArray();
            long start = System.nanoTime();
            initProducers(n);
            runProducers();
            long end = System.nanoTime();
            total = end - start;
            startConsumer();
            pool.awaitTermination(10000, TimeUnit.MILLISECONDS);
            stopConsumer();
            System.out.println("TIME ELAPSED: " + total);

        } catch (InterruptedException ex) {
            System.err.println("Main Thread Interrupted");
        }
    }

    public static void fillArray() {
        for (int i : numbers) {
            s1.add(i);
        }
    }

    public static void initProducers(int n) {
        pool = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            producers.add(new ProducerThread(s1, s2));
        }
    }

    public static void initBlockingQueues() {
        s1 = new ArrayBlockingQueue(numbers.length);
        s2 = new ArrayBlockingQueue(numbers.length);
    }

    public static void runProducers() {
        for (ProducerThread p : producers) {
            pool.execute(p);
            System.out.println("Thread started!");
        }
    }

    public static void startConsumer() {
        c1 = new ConsumerThread(s2);
        pool.execute(c1);
    }

    public static void stopConsumer() {
        pool.shutdownNow();
        System.out.println("SUM: " + c1.getSum());
    }
}