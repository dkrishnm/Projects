package DiskSequentialWrite;

import java.io.*;

/**
 * Created by Divya on 2/2/2016.
 * This class is the Runnable for testing Sequential Write
 */
public class SequentialWriteRunnable implements Runnable {

    static int fileSize =1000000000;
    static int blockSize=1;

    public int getBlockSize() {
        return blockSize;
    }

    public  void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }
    static File writeFile = null;

    @Override
    public void run(){
        int n = (fileSize/blockSize);
        writeFile = new File("writeTest.txt");
        StringBuffer sb =new StringBuffer();
        for(int i=0;i<blockSize;i++) {
            sb.append('a');
        }
        long startTime =System.nanoTime();
        try {
            PrintWriter pw = new PrintWriter(writeFile);
            for(int i=0;i<n;i++)
            {
                 pw.write(sb.toString());
            }
            pw.flush();
            pw.close();
            //writeFile.delete();
            long endTime =System.nanoTime();
            timer(startTime,endTime);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Timer calculates the time elapsed and Throughput
     * @param startTime
     * @param endTime
     * @throws IOException
     */
   /* private static void timer(long startTime, long endTime) throws IOException {
        double latency = (endTime-startTime)/1000000000;
        System.out.println("Latency in milli seconds "+ latency *1000);
        if(latency>0) {
            System.out.println("Throughput in Mbytes/sec " + (double) (fileSize/ (latency* 1048576)));
        }
    }*/

    private static void timer(long startTime, long endTime) {
        long timeElapsed = (endTime-startTime);
        System.out.println("LATENCY IN MILLI SECONDS "+timeElapsed/1000000);
        if(timeElapsed>0) {
             long throughPut = (writeFile.length()*1000000000)/(timeElapsed * 1048576);
            System.out.println("Throughput in Mbytes/sec "+throughPut);
        }


    }

}
