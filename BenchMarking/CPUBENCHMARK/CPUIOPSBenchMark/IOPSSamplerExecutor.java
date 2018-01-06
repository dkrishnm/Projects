package CPUIOPSBenchMark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/7/2016.
 * This Class IOPSSamplerExecutor starts four threads required to
 * calculate IOPS for a period of 10 minutes
 */
public class IOPSSamplerExecutor {
    static IOPSSamplerRunnable iOPSSamplerRunnable = new IOPSSamplerRunnable();
    static int oldCount= 0;
    static Timer timer = new Timer();
    static File file = new File("IOPSSampler.txt");
    static PrintWriter pw = null;
    public static void main (String[] args)
    {
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        oldCount = iOPSSamplerRunnable.getI();
        Thread t1 = new Thread(new IOPSSamplerRunnable());
        t1.start();
        Thread t2 = new Thread(new IOPSSamplerRunnable());
        t2.start();
        Thread t3 = new Thread(new IOPSSamplerRunnable());
        t3.start();
        Thread t4= new Thread(new IOPSSamplerRunnable());
        t4.start();
        timer.schedule(new getIOPSSample(),0,1000);
        try
        {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("IOPS Sampling completed , please open the file IOPSSampler.txt to see the results");

    }
    /**getIOPSSample is the Timer Task which triggers every second and calculates the IOPS executed
    * in that second and writes the result to a file
    */
    public static class getIOPSSample extends TimerTask {
        @Override
        public void run() {
            int  newCount =iOPSSamplerRunnable.getI();
            if((newCount-oldCount)>0) {
                /*System.out.println("IOPS Per Second " + String.valueOf(newCount - oldCount));*/
                pw.println("IOPS Per Second " + String.valueOf(newCount - oldCount));
                pw.flush();
                oldCount = newCount;
            }
        }
    }
}
