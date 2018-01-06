package CPUFLOPSBenchMark;

/**
 * Created by Divya on 2/8/2016.
 * This is the Runnable class which does floating point operation
 */
public class FLOPSSamplerRunnable implements Runnable {

    static float i=0.0f;
    static int ops=0;

    public static int getOps() {
        return ops;
    }

    public static void setOps(int ops) {
        FLOPSSamplerRunnable.ops = ops;
    }

    /**
     * The run method runs for a period of 10 minutes
     */
    @Override
    public void run() {
        System.out.println("Thread id "+Thread.currentThread().getId());
        /*long totalTime =0;
        while(totalTime<1*60*1000)
        {
            long startTime = System.currentTimeMillis();
            i=i+1.0f;
            long endTime = System.currentTimeMillis();
            ops++;
            totalTime+=(endTime-startTime);
        }

    */
        long startTime = System.currentTimeMillis();
        long endTime = startTime+ 10*60*1000;
        while(System.currentTimeMillis()<endTime)
        {
            i= i+1.0f;
            ops++;
        }
    }
}
