package CPUFLOPSBenchMark;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Divya on 2/8/2016.
 * This Class FLOPSSamplerExecutor starts four threads required to
 * calculate FLOPS for a period of 10 minutes
 */
public class FLOPSSamplerExecutor {
    static FLOPSSamplerRunnable flopsSamplerRunnable = new FLOPSSamplerRunnable();
    static int oldCount= 0;
    static File file = new File("FLOPSSampler.txt");
    static PrintWriter pw = null;
    public static void main (String[] args)
    {
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer();
        oldCount = flopsSamplerRunnable.getOps();
        Thread t1 = new Thread(new FLOPSSamplerRunnable());
        t1.start();
        Thread t2 = new Thread(new FLOPSSamplerRunnable());
        t2.start();
        Thread t3 = new Thread(new FLOPSSamplerRunnable());
        t3.start();
        Thread t4= new Thread(new FLOPSSamplerRunnable());
        t4.start();
        timer.schedule(new getFLOPSSample(),0,1000);
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
        System.out.println("FLOPS Sampling completed , please open the file FLOPSSampler.txt to see the results");
    }

    /**
     * getFLOPSSample is the Timer Task which triggers every second and calculates the FLOPS executed
     * in that second and writes the result to a file
     */
    public static class getFLOPSSample extends TimerTask {
        @Override
        public void run() {
            int  newCount =flopsSamplerRunnable.getOps();
            pw.println("FLOPS Per Second " + String.valueOf(newCount - oldCount));
            pw.flush();
            oldCount =newCount;
        }
    }
}
