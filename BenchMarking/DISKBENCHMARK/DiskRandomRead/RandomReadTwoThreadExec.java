package DiskRandomRead;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/8/2016.
 * This class handles the RandomRead Execution for two threads with varying block sizes
 * of 1B,1KB , 1MB
 */
public class RandomReadTwoThreadExec {
    static RandomReadRunnable randomReadRunnable = new RandomReadRunnable();
    static int fileSize=1000000000;
    public static void main(String[] args) {

        System.out.println("STARTING RANDOM READ TWO THREADS EXECUTION - 1BYTE BLOCK SIZE");
        randomReadRunnable.setBlockSize(1);
        Thread t1 = new Thread(randomReadRunnable);
        t1.start();
        Thread t2 = new Thread(randomReadRunnable);
        t2.start();
        try
        {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING RANDOM READ TWO THREADS EXECUTION - 1KILO BYTE BLOCK SIZE");
        randomReadRunnable.setBlockSize(1024);
        Thread t3 = new Thread(randomReadRunnable);
        t3.start();
        Thread t4 = new Thread(randomReadRunnable);
        t4.start();
        try
        {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING RANDOM READ TWO THREADS EXECUTION - 1MEGA BYTE BLOCK SIZE");
        randomReadRunnable.setBlockSize(1048576);

        Thread t5= new Thread(randomReadRunnable);
        t5.start();
        Thread t6 = new Thread(randomReadRunnable);
        t6.start();
        try
        {
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
