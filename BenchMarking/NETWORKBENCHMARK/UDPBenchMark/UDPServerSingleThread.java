package UDPBenchMark;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/**
 * Created by Divya on 2/10/2016.
 * This class is the TCP server code for single thread TCP network benchmark
 */
public class UDPServerSingleThread
    {
        private static final int PORT = 1234;
        private static DatagramSocket dgramSocket;
        private static DatagramPacket inPkt, outPkt;
        private static byte[] buffer;
        public static void main(String[] args)
        {
            System.out.println("Opening port...\n");
            try
            {
            dgramSocket = new DatagramSocket(PORT);
            }
        catch(SocketException e)
        {
            System.out.println("Error attach port!");
            System.exit(1);
        }
        run();
    }
        private static void run() {
        try {
            String msgIn,msgOut;
            int numMsgs = 0;
            do {

                buffer = new byte[256];
                inPkt = new DatagramPacket(buffer,buffer.length);
                dgramSocket.receive(inPkt);
                InetAddress cliAddress = inPkt.getAddress();
                int cliPort = inPkt.getPort();
                msgIn = new String(inPkt.getData(),0,inPkt.getLength());
                //System.out.println("Message received.");
                numMsgs++;
                msgOut = msgIn;
                outPkt = new DatagramPacket(msgOut.getBytes(), msgOut.length(),cliAddress,cliPort);
                dgramSocket.send(outPkt);
        } while(true);
    }
    catch(IOException e) {
        e.printStackTrace();
    }
    finally
        {
            dgramSocket.close();
        }
}
}
