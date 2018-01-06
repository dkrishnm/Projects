package DiskRandomRead;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * Created by Divya on 2/2/2016.
 * This class is the Runnable for Random Read Disk Operations
 */
public class RandomReadRunnable implements Runnable {

    private long currenPos;
    static int blockSize=1;
    public static int getBlockSize() {
        return blockSize;
    }
    public static void setBlockSize(int blockSize) {

        RandomReadRunnable.blockSize = blockSize;
    }
    static RandomAccessFile readFile;

    /**
     * Random Access File utilized for testing random read
     * randFile.seek(currentPos) changes the position each time read operation happens.
     */
    @Override
    public void run()
    {
        long startTime = System.nanoTime();
        try {
            readFile = new RandomAccessFile("file.txt", "rw");
            FileChannel inChannel = readFile.getChannel();
            currenPos = readFile.length()-blockSize;
            ByteBuffer buffer = ByteBuffer.allocate(blockSize);
            System.out.println("Reading files in block size of " + blockSize + " bytes");

            while (inChannel.read(buffer) > 0 && currenPos>0) {
                readFile.seek(currenPos);
                buffer.flip();
                currenPos = currenPos-blockSize;
                buffer.clear();
            }
            long endTime = System.nanoTime();
            timer(startTime,endTime);
            /*System.out.println("Latency in milli seconds " + (endTime - startTime));
            System.out.println("Throughput in bytes/sec "+ Math.floor(readFile.length()/((endTime - startTime))));*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Timer is used to calculating the time elapsed
     * @param startTime
     * @param endTime
     * @throws IOException
     */
    private static void timer(long startTime, long endTime) throws IOException {
        DecimalFormat df = new DecimalFormat("#.####");
        long timeElapsed = (endTime - startTime);
        System.out.println("LATENCY IN MILLI SECONDS " + timeElapsed / 1000000);
        if (timeElapsed > 0) {
            System.out.println("Throughput in bytes/sec "+(readFile.length() * 1000000000) / (timeElapsed));
            BigDecimal time = new BigDecimal(timeElapsed);
            BigDecimal div = new BigDecimal(1000000000);
            BigDecimal timeDiv = time.divide(div, 10, RoundingMode.HALF_UP);

            BigDecimal len = new BigDecimal(readFile.length());
            BigDecimal bite = new BigDecimal(1048576);
            BigDecimal lenbite = len.divide(bite, 10, RoundingMode.HALF_UP);

            BigDecimal result = lenbite.divide(timeDiv, 10, RoundingMode.HALF_UP);

            System.out.println("Throughput in Mbytes/sec : " + result);
        }
    }
}
