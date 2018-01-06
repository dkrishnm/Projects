package DiskRandomRead;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/8/2016.
 * This class handles the Random Read Single thread Execution for different block sizes of 1B,1KB,1MB
 */
public class RandomReadSingleThreadExec {
    static RandomReadRunnable randomReadRunnable = new RandomReadRunnable();
       public static void main(String[] args) {

        System.out.println("STARTING READ RANDOM SINGLE THREAD EXECUTION - 1BYTE BLOCK SIZE");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        randomReadRunnable.setBlockSize(1);
           Future future = executor.submit(randomReadRunnable);
        boolean isDone = future.isDone();
        while (!isDone) {
            isDone = future.isDone();
        }
        if (isDone) {
            executor.shutdown();
            System.out.println("STARTING READ RANODM SINGLE THREAD EXECUTION - 1KILO BYTE BLOCK SIZE");
            ExecutorService executor1 = Executors.newSingleThreadExecutor();
            randomReadRunnable.setBlockSize(1024);
            Future future1 = executor1.submit(randomReadRunnable);
            boolean isDone1 = future1.isDone();
            while (!isDone1) {
                isDone1 = future1.isDone();
            }
            if(isDone1)
            {
                executor1.shutdown();
                System.out.println("STARTING READ RANDOM SINGLE THREAD EXECUTION - 1MEGA BYTE BLOCK SIZE");
                ExecutorService executor2 = Executors.newSingleThreadExecutor();
                randomReadRunnable.setBlockSize(1048576);
                Future future2 = executor2.submit(randomReadRunnable);
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
