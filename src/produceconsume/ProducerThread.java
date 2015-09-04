package ProduceConsume;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerThread extends Thread {

    ArrayBlockingQueue s1, s2;
 

    public ProducerThread(ArrayBlockingQueue s1, ArrayBlockingQueue s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public void run() {      
       while (!s1.isEmpty()) {
           
           int n = (int) s1.poll();
           System.out.println("S1:  Poll " + n);
           
           try {
           
               long fibNo = fib(n);
               System.out.println("FIB:  " + n + " = " + fibNo);
               
               
               s2.put(fibNo);
               System.out.println("S2:  Put " + fibNo);
               
               
           } catch (InterruptedException ex) {
               System.err.println("ProducerThread Interrupted");
           }    
       }  
    }



    private long fib(long n) {
        if ((n == 0) || (n == 1)) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

}