package DiskSequentialRead;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/8/2016.
 * This class handles the execution of Sequenatial Read Two threads for block sizes 1B,1KB,1MB
 */
public class SequentialReadTwoThreadExec {
    static SequentialReadRunnable sequentialReadRunnable = new SequentialReadRunnable();
    public static void main(String[] args) {

        System.out.println("STARTING SEQUENTIAL READ TWO THREADS EXECUTION - 1BYTE BLOCK SIZE");
        sequentialReadRunnable.setBlockSize(1);
        Thread t1 = new Thread(sequentialReadRunnable);
        t1.start();
        Thread t2 = new Thread(sequentialReadRunnable);
        t2.start();
        try
        {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING SEQUENTIAL READ TWO THREADS EXECUTION - 1KILO BYTE BLOCK SIZE");
        sequentialReadRunnable.setBlockSize(1024);
        Thread t3 = new Thread(sequentialReadRunnable);
        t3.start();
        Thread t4 = new Thread(sequentialReadRunnable);
        t4.start();
        try
        {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING SEQUENTIAL READ TWO THREADS EXECUTION - 1MEGA BYTE BLOCK SIZE");
        sequentialReadRunnable.setBlockSize(1048576);

        Thread t5= new Thread(sequentialReadRunnable);
        t5.start();
        Thread t6 = new Thread(sequentialReadRunnable);
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
