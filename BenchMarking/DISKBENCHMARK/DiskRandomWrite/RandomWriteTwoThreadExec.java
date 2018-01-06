package DiskRandomWrite;

import DiskRandomWrite.RandomWriteRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/8/2016.
 */
public class RandomWriteTwoThreadExec {
    static RandomWriteRunnable randomWriteRunnable = new RandomWriteRunnable();
    public static void main(String[] args) {

        System.out.println("STARTING RANDOM WRITE TWO THREADS EXECUTION - 1BYTE BLOCK SIZE");
        randomWriteRunnable.setBlockSize(1);
        Thread t1 = new Thread(randomWriteRunnable);
        t1.start();
        Thread t2 = new Thread(randomWriteRunnable);
        t2.start();
        try
        {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING RANDOM WRITE TWO THREADS EXECUTION - 1KILO BYTE BLOCK SIZE");
        randomWriteRunnable.setBlockSize(1024);
        Thread t3 = new Thread(randomWriteRunnable);
        t3.start();
        Thread t4 = new Thread(randomWriteRunnable);
        t4.start();
        try
        {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING RANDOM READ TWO THREADS EXECUTION - 1MEGA BYTE BLOCK SIZE");
        randomWriteRunnable.setBlockSize(1048576);

        Thread t5= new Thread(randomWriteRunnable);
        t5.start();
        Thread t6 = new Thread(randomWriteRunnable);
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
