package CPUIOPSBenchMark;

/**
 * Created by Divya on 2/7/2016.
 * This class handles starting the threads for calculating IOPS in single thread,
 * 2 thread and 4 thread scenarios
 */
public class IOPSExecutor {
    public static void main(String[] args ) {
        System.out.println("********SINGLE THREAD EXECUTION BEGINS********");
        Thread t1 = new Thread(new IOPSRunnable());
        t1.start();
        try
        {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("********TWO THREADS EXECUTION BEGINS********");
        Thread t2 = new Thread(new IOPSRunnable());
        t2.start();
        Thread t3 = new Thread(new IOPSRunnable());
        t3.start();
        try
        {
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("********FOUR THREADS EXECUTION BEGINS********");
        Thread t4 = new Thread(new IOPSRunnable());
        t4.start();
        Thread t5 = new Thread(new IOPSRunnable());
        t5.start();
        Thread t6 = new Thread(new IOPSRunnable());
        t6.start();
        Thread t7 = new Thread(new IOPSRunnable());
        t7.start();

    }

}
