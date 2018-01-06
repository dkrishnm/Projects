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

/**
 * Created by Divya on 2/9/2016.
 * This class holds the client code for TCP TWO threads Network BenchMark
 * Client sends message and receives the send message back from the server.
 * Calculates the time elpased for the byte sizes 1B,1KB,64KB
 * One concurrent thread connects to one port at the server
 * and the other thread connects to the second port available at the  server.
 * Here the ports used are 1234 and 1235
 * The user is expected to enter the correct IPAdrress of the server.
 *
 */
public class TCPClientTwoThreads {
        private static String host;
        /*server port*/
        private static final int PORT1 = 1234;
        private static final int PORT2 = 1235;
        private static int totalSize = 64*1024;
        private static int byteSize =1;
       public static void main(String[] args)
        {
            System.out.println("Opening port\n");
            try
            {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the Host Address");
                host=sc.next();
            }
            catch(Exception e) {
                System.out.println("Host not found!");
                System.exit(1);
            }
            System.out.println("TCP NETWORK BENCHMARK TWO THREADS 64KB");
            byteSize =64*1024;
            Thread t1 = new Thread(new ClientThread());
            t1.setName("Thread1");
            t1.start();
            Thread t2 = new Thread(new ClientThread());
            t2.setName("Thread2");
            t2.start();
            try
            {
               /* t1.join();*/
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("TCP NETWORK BENCHMARK TWO THREADS 1KB");
            byteSize =1024;
            Thread t3 = new Thread(new ClientThread());
            t3.setName("Thread3");
            t3.start();
            Thread t4 = new Thread(new ClientThread());
            t4.setName("Thread4");
            t4.start();
            try
            {
                t3.join();
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("TCP NETWORK BENCHMARK TWO THREADS 1B");
            byteSize =1;
            Thread t5 = new Thread(new ClientThread());
            t5.setName("Thread5");
            t5.start();
            Thread t6 = new Thread(new ClientThread());
            t6.setName("Thread6");
            t6.start();

        }
        private static class ClientThread implements Runnable
        {
            int numIterations = totalSize/byteSize;
            @Override
            public void run() {
                System.out.println("Client Started");
                Socket sock = null;
                try {
                    if(Thread.currentThread().getName().equalsIgnoreCase("Thread1")||
                            Thread.currentThread().getName().equalsIgnoreCase("Thread3")||
                            Thread.currentThread().getName().equalsIgnoreCase("Thread5")) {
                        sock = new Socket(host, PORT1);
                    }
                    else if(Thread.currentThread().getName().equalsIgnoreCase("Thread2")||
                            Thread.currentThread().getName().equalsIgnoreCase("Thread4")||
                            Thread.currentThread().getName().equalsIgnoreCase("Thread6"))
                    {
                        sock = new Socket(host,PORT2);
                    }
                    /*create socket reader and writer*/
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                    /*Set up stream for user entry*/
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    /*storage for message and response message*/
                    String msgOut, msgIn;
                    Random rand = new Random();
                    long startTime = System.nanoTime();
                    for(int i=0;i<numIterations;i++)
                    {
                        // System.out.print("Enter message: ");
                        byte[] bytes = new byte[1];
                        rand.nextBytes(bytes);
                        msgOut=bytes.toString();
                        out.println(msgOut);
                        msgIn = in.readLine();
                        // System.out.println("SERVER> " + msgIn);
                    }
                    msgOut="BYE";
                    out.println(msgOut);
                    long endTime = System.nanoTime();
                    /*System.out.println("Byte Size = " + byteSize);
                    System.out.println("Total File Size = " + totalSize);
                    double latency = (double) (endTime - startTime) / 1000000;
                    System.out.println("Latency in ms for "+totalSize*2+" bytes is "+latency);
                    System.out.println("ThroughPut in MBytes/Sec " + (double) (totalSize * 2 * 1000/ (latency*1000000)));*/
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
                }
                finally
                {
                    try {
                        sock.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

