package CPUIOPSBenchMark;

/**
 * Created by Divya on 2/7/2016.
 * This is the Runnable class which does Integer operations
 */
public class IOPSSamplerRunnable implements Runnable {
    static int i=0;
    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }

    /**This method runs for a period of 10 minutes**/
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime+ 10*60*1000;
        while(System.currentTimeMillis()<endTime)
        {
            i= i+1;
        }
    }
}
