package DiskRandomWrite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Created by Divya on 2/2/2016.
 * This class is the Runnable for Random Write Disk Operations
 */
public class RandomWriteRunnable implements Runnable {

    static int fileSize = 10000000;
    static int blockSize = 1;

    StringBuffer sb = new StringBuffer();
    Random rand = new Random();

    public int getBlockSize() {
        return blockSize;
    }

    static RandomAccessFile writeFile;

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * Random Access File utilized for testing random write
     * randFile.seek(currentPos) changes the position each time write operation happens.
     */
    @Override
    public void run() {
        long startTime = System.nanoTime();
        try {
            long n = fileSize / blockSize;
            //System.out.println(n);
            RandomAccessFile writeFile = new RandomAccessFile("WriteTest.txt", "rw");
            for (int i = 0; i < blockSize; i++) {
                sb.append('a');
            }
            for (int i = 0; i < n; i++) {
                writeFile.seek(rand.nextInt(fileSize));
                writeFile.write(sb.toString().getBytes());
            }
            writeFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        timer(startTime, endTime);
    }

    /**
     * Timer calculates the time elapsed and Throughput
     *
     * @param startTime
     * @param endTime
     */

    private static void timer(long startTime, long endTime) {
        long timeElapsed = (endTime - startTime);
        System.out.println("LATENCY IN MILLI SECONDS " + timeElapsed / 1000000);
        if (timeElapsed > 0) {
           /* long throughPut = (fileSize * 1000000000) / (timeElapsed * 1048576);
            System.out.println("Throughput in Mbytes/sec " + throughPut);*/

            BigDecimal time = new BigDecimal(timeElapsed);
            BigDecimal div = new BigDecimal(1000000000);
            BigDecimal timeDiv = time.divide(div, 10, RoundingMode.HALF_UP);

            BigDecimal len = new BigDecimal(fileSize);
            BigDecimal bite = new BigDecimal(1048576);
            BigDecimal lenbite = len.divide(bite, 10, RoundingMode.HALF_UP);

            BigDecimal result = lenbite.divide(timeDiv, 10, RoundingMode.HALF_UP);

            System.out.println("Throughput in Mbytes/sec : " + result);
        }
    }
}
