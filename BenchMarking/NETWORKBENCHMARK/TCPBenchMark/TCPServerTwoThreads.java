package TCPBenchMark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;

/**
 * Created by Divya on 2/9/2016.
 * This class is the TCP server code for two thread TCP network benchmark.
 * The server accepts connection from the client at two ports 1234 & 1235.
 * Server sends the message received message back to the client.
 */
public class TCPServerTwoThreads {
    private static ServerSocket socket1;
    private static final int PORT1 = 1234;

    private static ServerSocket socket2;
    private static final int PORT2 = 1235;

    public static void main(String[] args)
    {
        try {
            TCPServerTwoThreads servers = new TCPServerTwoThreads();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TCPServerTwoThreads() throws IOException {
        socket1 = new ServerSocket(1234);
        socket2 = new ServerSocket(1235);
        Thread t1 = new Thread(new ServerThread(socket1));
        Thread t2 = new Thread(new ServerThread(socket2));

        t1.start();
        t2.start();
    }

    public static class ServerThread implements Runnable
    {

        private ServerSocket servSock;
        private Socket sock;
        ServerThread(ServerSocket serverSocket)
        {
            servSock =serverSocket;
        }
        @Override
        public void run() {
            System.out.println("Server Listening!.");
            try {
                while(true) {
                    sock = servSock.accept();
                     /*create a socket buffer reader*/
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                    int numMsgs = 0;
                    /*read from the data socket*/
                    String msg = in.readLine();
                    while (!msg.equals("BYE")) {
                        //System.out.println("Message received.");
                        numMsgs++;
                        out.println("Message " + numMsgs + ": " + msg);
                        msg = in.readLine();
                    }
                    out.println(numMsgs + " messages received.");
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                /*close the socket*/
                try {
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}