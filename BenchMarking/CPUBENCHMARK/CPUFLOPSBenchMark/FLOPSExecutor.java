package CPUFLOPSBenchMark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/7/2016.
 * This class handles starting the threads for calculating FLOPS in single thread,
 * 2 thread and 4 thread scenarios
 */

public class FLOPSExecutor {
    public static void main(String[] args )
    {
        /**
         * Starts single thread execution
         */
        System.out.println("********SINGLE THREAD EXECUTION BEGINS********");
        Thread t1 = new Thread(new FLOPSRunnable());
        t1.start();
        try
        {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * Starts two threads concurrent execution
         */
        System.out.println("********TWO THREADS EXECUTION BEGINS********");
        Thread t2 = new Thread(new FLOPSRunnable());
        t2.start();
        Thread t3 = new Thread(new FLOPSRunnable());
        t3.start();
        try
        {
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * Starts four threads concurrent execution
         */
        System.out.println("********FOUR THREADS EXECUTION BEGINS********");
        Thread t4 = new Thread(new FLOPSRunnable());
        t4.start();
        Thread t5 = new Thread(new FLOPSRunnable());
        t5.start();
        Thread t6 = new Thread(new FLOPSRunnable());
        t6.start();
        Thread t7 = new Thread(new FLOPSRunnable());
        t7.start();

    }
}