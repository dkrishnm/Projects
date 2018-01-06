package DiskSequentialRead;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Divya on 2/2/2016.
 * This class is the Runnable for Sequential Read Testing
 */
public class SequentialReadRunnable implements Runnable {
    static int blockSize = 1;
    public int getBlockSize() {
        return blockSize;
    }
    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }
    static File file =null;
    BufferedReader br = null;
    @Override
    public void run() {
        long startTime = System.nanoTime();
        try {
            file = new File("file.txt");
            long n = file.length()/blockSize;
            br = new BufferedReader(new FileReader(file));
            char[] buffer = new char[blockSize];
            int bytesRead =0;
            while ((bytesRead = br.read(buffer,0,blockSize)) != -1)
            {
               //System.out.println(bytesRead);
            }
            long endTime = System.nanoTime();
            timer(startTime, endTime);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Timer calculates the time elapsed and Throughput
     *
     * @param startTime
     * @param endTime
     * @throws IOException
     */
    private static void timer(long startTime, long endTime) throws IOException {
        long timeElapsed = (endTime - startTime);
        System.out.println("LATENCY IN MILLI SECONDS " + timeElapsed / 1000000);
        if (timeElapsed > 0) {
            long throughPut = (file.length() * 1000000000) / (timeElapsed * 1048576);
            System.out.println("Throughput in Mbytes/sec " + throughPut);
        }
    }
}