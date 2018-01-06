package CPUIOPSBenchMark;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Divya on 2/7/2016.
 * This is the Threads Runnable class which runs a total 1000000000 iterations of 10 Integer
 * operations
 */
public  class IOPSRunnable implements Runnable {
    Random rand = new Random();
    static long totalOps = 1000000000;
    int k =0;
    @Override
    public void run() {
        long startTime = System.nanoTime();
        for(int i=0;i<totalOps;i++)
        {
            k=i+1;
            k=i+2;
            k=i+3;
            k=i+4;
            k=i+5;
            k=i+6;
            k=i+7;
            k=i+8;
            k=i+9;
            k=i+10;

       }
        long endTime = System.nanoTime();
        timer(startTime,endTime);
    }
    /**
     * This calculates the Time elapsed and GIOPS per thread
     * @param startTime
     * @param endTime
     */
    private static void timer(long startTime, long endTime) {
        long timeElapsed = (endTime-startTime);
        System.out.println("TOTAL TIME ELAPSED IN NANO SECONDS"+timeElapsed);
        System.out.println("TOTAL OPERATIONS " +totalOps*10);
        long GIOPS = (totalOps*10/(timeElapsed));
        System.out.println("GIOPS "+ GIOPS);
    }
}
