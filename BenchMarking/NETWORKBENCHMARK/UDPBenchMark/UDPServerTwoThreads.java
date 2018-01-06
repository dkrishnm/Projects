package UDPBenchMark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Divya on 2/10/2016.
 * his class is the UDP server code for two thread UDP network benchmark.
 * The server accepts connection from the client at two ports 1234 & 1235.
 * Server sends the message received message back to the client.
 */
public class UDPServerTwoThreads {
    private static DatagramSocket socket1;
    private static final int PORT1 = 1234;

    private static DatagramSocket socket2;
    private static final int PORT2 = 1235;

    public static void main(String[] args)
    {
        try {
            UDPServerTwoThreads servers = new UDPServerTwoThreads();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public UDPServerTwoThreads() throws IOException {
        socket1 = new DatagramSocket(1234);
        socket2 = new DatagramSocket(1235);
        Thread t1 = new Thread(new ServerThread(socket1));
        Thread t2 = new Thread(new ServerThread(socket2));
        t1.start();
        t2.start();
    }

    public static class ServerThread implements Runnable {
        private DatagramSocket dgramSocket;
        private Socket sock;

        ServerThread(DatagramSocket serverSocket) {
            dgramSocket = serverSocket;
        }

        @Override
        public void run() {
            System.out.println("Server Listening!.");
            DatagramPacket inPkt, outPkt;
            byte[] buffer;
            try {
                String msgIn, msgOut;
                int numMsgs = 0;
                do {
                    buffer = new byte[256];
                    inPkt = new DatagramPacket(buffer, buffer.length);
                    dgramSocket.receive(inPkt);
                    InetAddress cliAddress = inPkt.getAddress();
                    int cliPort = inPkt.getPort();
                    msgIn = new String(inPkt.getData(), 0, inPkt.getLength());
                    //System.out.println("Message received.");
                    numMsgs++;
                    msgOut = msgIn;
                    outPkt = new DatagramPacket(msgOut.getBytes(), msgOut.length(), cliAddress, cliPort);
                    dgramSocket.send(outPkt);
                } while (true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                dgramSocket.close();
            }
        }
    }
}
