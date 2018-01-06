package DiskRandomWrite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/8/2016.
 */
public class RandomWriteSingleThreadExec {
    static RandomWriteRunnable writeRandomRunnable = new RandomWriteRunnable();
    public static void main(String[] args) {
        System.out.println("STARTING WRITE RANDOM SINGLE THREAD EXECUTION - 1BYTE BLOCK SIZE");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        writeRandomRunnable.setBlockSize(1);
        Future future = executor.submit(writeRandomRunnable);
        boolean isDone = future.isDone();
        while (!isDone) {
            isDone = future.isDone();
        }
        if (isDone) {
            executor.shutdown();
            System.out.println("STARTING WRITE RANDOM SINGLE THREAD EXECUTION - 1KILO BYTE BLOCK SIZE");
            ExecutorService executor1 = Executors.newSingleThreadExecutor();
            writeRandomRunnable.setBlockSize(1024);
            Future future1 = executor1.submit(writeRandomRunnable);
            boolean isDone1 = future1.isDone();
            while (!isDone1) {
                isDone1 = future1.isDone();
            }
            if(isDone1)
            {
                executor1.shutdown();
                System.out.println("STARTING WRITE RANDOM SINGLE THREAD EXECUTION - 1MEGA BYTE BLOCK SIZE");
                ExecutorService executor2 = Executors.newSingleThreadExecutor();
                writeRandomRunnable.setBlockSize(1048576);
                Future future2 = executor2.submit(writeRandomRunnable);
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
