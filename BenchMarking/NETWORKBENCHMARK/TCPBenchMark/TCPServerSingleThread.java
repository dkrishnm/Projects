package TCPBenchMark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Divya on 2/9/2016.
 * This class is the TCP server code for single thread TCP network benchmark
 */
public class TCPServerSingleThread {
    private static ServerSocket servSock;
    private static final int PORT = 1234;

    public TCPServerSingleThread(Socket socket) {
    }

    public static void main(String[] args) {
        System.out.println("Opening port\n");
        try {
            servSock = new ServerSocket(PORT);
        } catch (SocketException e) {
            System.out.println("Error attach port!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error create socket!");
            System.exit(1);
        }
        do {
            run();
        }
        while (true);
    }

    private static void run() {
        Socket sock = null;
        try
        {
                sock = servSock.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
                int numMsgs = 0;
                /*read from the data socket*/
                String msg = in.readLine();
                while (!msg.equals("BYE"))
                {
                    //System.out.println("Message received.");
                    numMsgs++;
                    out.println(msg);
                    msg = in.readLine();
                }
                out.println(numMsgs + " messages received.");
            }
            catch(IOException e) {
                e.printStackTrace(); }
            finally{
                /*close the socket*/
                try {
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}
/*create the socket writer*/


