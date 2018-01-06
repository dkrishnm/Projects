package DiskSequentialWrite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/2/2016.
 * This class handles the execution of Sequenatial Write Single thread for block sizes 1B,1KB,1MB
 */
public class SeqWriteSingleThreadExecutor {
    static SequentialWriteRunnable writeSequentialSingleThread = new SequentialWriteRunnable();
      public static void main(String[] args) {

          System.out.println("STARTING WRITE SEQUENTIAL SINGLE THREAD EXECUTION - 1BYTE BLOCK SIZE");
          ExecutorService executor = Executors.newSingleThreadExecutor();
          writeSequentialSingleThread.setBlockSize(1);
          Future future = executor.submit(writeSequentialSingleThread);
          boolean isDone = future.isDone();
          while (!isDone) {
              isDone = future.isDone();
          }
          if (isDone) {
              executor.shutdown();
              System.out.println(" System.out.println(\"STARTING WRITE SEQUENTIAL SINGLE THREAD EXECUTION - 1KILO BYTE BLOCK SIZE\");");
              ExecutorService executor1 = Executors.newSingleThreadExecutor();
              writeSequentialSingleThread.setBlockSize(1024);
              long startTime1 = System.nanoTime();
              Future future1 = executor1.submit(writeSequentialSingleThread);
              boolean isDone1 = future1.isDone();
              while (!isDone1) {
                  isDone1 = future1.isDone();
              }
              if(isDone1)
              {
                  executor1.shutdown();
                  System.out.println(" System.out.println(\"STARTING WRITE SEQUENTIAL SINGLE THREAD EXECUTION - 1MEGA BYTE BLOCK SIZE\");");
                  ExecutorService executor2 = Executors.newSingleThreadExecutor();
                  writeSequentialSingleThread.setBlockSize(1048576);
                  Future future2 = executor2.submit(writeSequentialSingleThread);
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
