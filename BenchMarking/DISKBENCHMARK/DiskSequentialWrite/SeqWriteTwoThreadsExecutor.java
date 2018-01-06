package DiskSequentialWrite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/8/2016.
 * This class handles the execution of Sequenatial Write TWO threads for block sizes 1B,1KB,1MB
 */
public class SeqWriteTwoThreadsExecutor {
    static SequentialWriteRunnable sequentialWriteRunnable = new SequentialWriteRunnable();
    public static void main(String[] args) {

        System.out.println("STARTING SEQUENTIAL WRITE TWO THREADS EXECUTION - 1BYTE BLOCK SIZE");
        sequentialWriteRunnable.setBlockSize(1);
        Thread t1 = new Thread(sequentialWriteRunnable);
        t1.start();
        Thread t2 = new Thread(sequentialWriteRunnable);
        t2.start();
        try
        {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING SEQUENTIAL WRITE TWO THREADS EXECUTION - 1KILO BYTE BLOCK SIZE");
        sequentialWriteRunnable.setBlockSize(1024);
        Thread t3 = new Thread(sequentialWriteRunnable);
        t3.start();
        Thread t4 = new Thread(sequentialWriteRunnable);
        t4.start();
        try
        {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING SEQUENTIAL WRITE TWO THREADS EXECUTION - 1MEGA BYTE BLOCK SIZE");
        sequentialWriteRunnable.setBlockSize(1048576);

        Thread t5= new Thread(sequentialWriteRunnable);
        t5.start();
        Thread t6 = new Thread(sequentialWriteRunnable);
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
