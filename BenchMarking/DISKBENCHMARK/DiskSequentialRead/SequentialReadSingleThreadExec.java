package DiskSequentialRead;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/8/2016.
 * This class handles the execution of Sequenatial Read Single thread for block sizes 1B,1KB,1MB
 */
public class SequentialReadSingleThreadExec {
    static SequentialReadRunnable sequentialReadRunnable = new SequentialReadRunnable();
    public static void main(String[] args) {

        System.out.println("STARTING READ SEQUENTIAL SINGLE THREAD EXECUTION - 1BYTE BLOCK SIZE");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        sequentialReadRunnable.setBlockSize(1);
        Future future = executor.submit(sequentialReadRunnable);
        boolean isDone = future.isDone();
        while (!isDone) {
            isDone = future.isDone();
        }
        if (isDone) {
            executor.shutdown();
            System.out.println("STARTING READ SEQUENTIAL SINGLE THREAD EXECUTION - 1KILO BYTE BLOCK SIZE");
            ExecutorService executor1 = Executors.newSingleThreadExecutor();
            sequentialReadRunnable.setBlockSize(1024);
            Future future1 = executor1.submit(sequentialReadRunnable);
            boolean isDone1 = future1.isDone();
            while (!isDone1) {
                isDone1 = future1.isDone();
            }
            if(isDone1)
            {
                executor1.shutdown();
                System.out.println("STARTING READ SEQUENTIAL SINGLE THREAD EXECUTION - 1MEGA BYTE BLOCK SIZE");
                ExecutorService executor2 = Executors.newSingleThreadExecutor();
                sequentialReadRunnable.setBlockSize(1048576);
                Future future2 = executor2.submit(sequentialReadRunnable);
                boolean isDone2 = future2.isDone();
                while (!isDone2) {
                    isDone2 = future2.isDone();
                }
                if(isDone1)
                {
                    executor2.shutdown();
                }
            }
        }
    }
    }
