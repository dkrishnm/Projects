package CPUFLOPSBenchMark;

import java.text.DecimalFormat;

/**
 * Created by Divya on 2/8/2016.
 * This is the Threads Runnable class which runs a total 1000000000 iterations of 10 FLoating point
 * operations
 */
public class FLOPSRunnable implements  Runnable {
    static int totalOps = 1000000000;
    float k =0;
    @Override
    public void run() {
        long startTime = System.nanoTime();
        for(int i =0;i<totalOps;i++)
        {
            k=3.3f+4.4f;
            k=5.5f+4.4f;
            k=6.3f+4.4f;
            k=9.3f+4.4f;
            k=10.3f+4.4f;
            k=14.3f+4.4f;
            k=15.3f+4.4f;
            k=16.3f+4.4f;
            k=17.3f+18.4f;
            k=19.3f+4.4f;

        }
        long endTime = System.nanoTime();
        timer(startTime,endTime);
    }

    /**
     * This calculates the Time elapsed and GFLOPS per thread
     * @param startTime
     * @param endTime
     */
    private static void timer(long startTime, long endTime) {
        long timeElapsed = (endTime-startTime);
        System.out.println("TOTAL TIME ELAPSED IN NANO SECONDS"+timeElapsed);
        System.out.println("TOTAL OPERATIONS " +totalOps*10);
        long GFLOPS = (totalOps*10/(timeElapsed));
        System.out.println("GFLOPS "+ GFLOPS);
    }
}
