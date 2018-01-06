package TCPBenchMark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Divya on 2/9/2016.
 * This class holds the client code for TCP Single thread Network BenchMark
 * Client sends message and receives the send message of back .
 * Timer calculates the time elpased for the byte sizes 1B,1KB,64KB
 */
public class TCPClientSingleThread {
    /*server IP*/
    private static String host;
    /*server port*/
    private static final int PORT = 1234;
    private static int totalSize = 64*1024;
    private static int byteSize = 1;

    public static void main(String[] args) {
        System.out.println("Opening port\n");
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the Host Address");
            host=sc.next();
        } catch (Exception e) {
            System.out.println("Host not found!");
            System.exit(1);
        }

        System.out.println("TCP NETWORK BENCHMARK SINGLE THREAD 64KB");
        ExecutorService exec = Executors.newSingleThreadExecutor();
        byteSize =64*1024;
        Future future = exec.submit(new SingleThreadRunnable()) ;
        boolean isDone = future.isDone();
        while(!isDone)
        {
            isDone = future.isDone();
        }
        if(isDone)
        {
            exec.shutdown();
            System.out.println("TCP NETWORK BENCHMARK SINGLE THREAD 1KB");
            ExecutorService exec1 = Executors.newSingleThreadExecutor();
            byteSize =1024;
            Future future1 = exec1.submit(new SingleThreadRunnable()) ;
            boolean isDone1 = future1.isDone();
            while(!isDone1)
            {
                isDone1 = future1.isDone();
            }
            if(isDone1)
            {
                exec1.shutdown();
                System.out.println("TCP NETWORK BENCHMARK SINGLE THREAD 1B");
                ExecutorService exec2 = Executors.newSingleThreadExecutor();
                byteSize =1;
                Future future2 = exec2.submit(new SingleThreadRunnable()) ;
                boolean isDone2 = future2.isDone();
                while(!isDone2)
                {
                    isDone2 = future2.isDone();
                }
                if(isDone2)
                {
                    exec2.shutdown();
                }

            }

        }

    }

    private static class SingleThreadRunnable implements Runnable {
        @Override
        public void run() {
            Socket sock = null;
            try {
                int numIterations = totalSize / byteSize;
            /*create data socket*/
                sock = new Socket(host, PORT);
            /*create socket reader and writer*/
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            /*Set up stream for user entry*/
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            /*storage for message and response message*/
                String msgOut, msgIn;
                Random rand = new Random();
                long startTime = System.nanoTime();
                for (int i = 0; i < numIterations; i++) {
                    // System.out.print("Enter message: ");
                    byte[] bytes = new byte[byteSize];
                    rand.nextBytes(bytes);
                    msgOut = bytes.toString();
                    out.println(msgOut);
                    msgIn = in.readLine();
                    //System.out.println("SERVER> " + msgIn);
                }
                msgOut = "BYE";
                out.println(msgOut);
                long endTime = System.nanoTime();
                /*System.out.println("Byte Size = " + byteSize);
                System.out.println("Total File Size = " + totalSize);
                double latency = (double) (endTime - startTime) / 1000000;
                System.out.println("Latency in ms for "+totalSize*2+" bytes is "+latency);
                System.out.println("ThroughPut in MBytes/Sec " + (double) (totalSize * 2 * 1000/ (latency*1048576)));*/

                long timeElapsed = endTime-startTime;

                BigDecimal time = new BigDecimal(timeElapsed);
                BigDecimal div = new BigDecimal(1000000000);
                BigDecimal timeDiv = time.divide(div, 10, RoundingMode.HALF_UP);

                BigDecimal len = new BigDecimal(totalSize*2);
                BigDecimal bite = new BigDecimal(1048576);
                BigDecimal lenbite = len.divide(bite, 10, RoundingMode.HALF_UP);

                BigDecimal result = lenbite.divide(timeDiv, 10, RoundingMode.HALF_UP);

                System.out.println("Latency in ms for "+totalSize*2+" bytes is "+timeDiv.multiply(new BigDecimal(1000)));
                System.out.println("ThroughPut in MBytes/Sec " + result);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

