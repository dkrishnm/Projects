import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Hashtable;

/**
 * Created by Divya on 10/17/15.
 */

/*single-threaded TCP server class - handles client communication*/
public class Server extends Thread {

    private int counter;
    private Hashtable<String, String> DHT = new Hashtable<String, String>();

    /*client data socket*/
    private static Socket sock;

    /*BufferReader used to read data from data socket*/
    private BufferedReader in;

    /*PrinterWriter used to write to the data socket*/
    private PrintWriter out;

    /*common delimiter*/
    private String delimiter;

    /*Constructor for the single threaded server*/
    public Server(Socket s, String delimiter) throws IOException {
        /*set the data socket*/
        sock = s;

        this.delimiter = delimiter;

        /*create the BufferReader from data socket*/
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        /*create the PrintWriter for data socket*/
        out = new PrintWriter(sock.getOutputStream(),true);

        /*If any of the above calls throws an exception,
        the caller will close the socket.
        Otherwise the thread will close it.*/

        /*call the run() method*/
        start();
    }

    /*run() method performs the actual task*/
    public void run()
    {
        try {
            int numMessages = 0;

            /*read message from the data socket (client)*/
            String msg = in.readLine();

            /*verify if the message is BYE*/
            while (!msg.equals("BYE"))
            {
                /*response from process()*/
                String rsp = "";

                /*count the number of messages received*/
                numMessages++;

                String[] split = msg.split(delimiter);
                if(split[0].equals("put"))
                    rsp = process(split[0], split[1], split[2]);
                else
                    rsp = process(split[0], split[1], "");

                /*send the reply message to the client*/
                out.println(rsp);

                /*read the next message*/
                msg = in.readLine();
            }

            /*at this point BYE has been received*/
            /*the server reports the number of received
            messages*/
            out.println(numMessages + " messages received.");
        } catch (SocketException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            try
            {
                System.out.println("\n Closing connection");

                /*close the data socket*/
                sock.close();
            }
            catch(IOException e)
            {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    } /*run()*/

    public synchronized String process(String oper, String key, String data){
        String reply = "";
        if(oper.equals("put")) {

            if(this.DHT.isEmpty()) {
                this.DHT.put(key, data);
                reply = "pass";
            }
            else
            {
                if(this.DHT.containsKey(key)) {
                    reply = "fail";
                }
                else {
                    this.DHT.put(key, data);
                    reply = "pass";
                }
            }
        }
        else if(oper.equals("get")) {

            if(this.DHT.isEmpty()) {
                reply = "fail";
            }
            else
            {
                if(this.DHT.containsKey(key)) {
                    reply = this.DHT.get(key);
                }
                else {
                    reply = "fail";
                }
            }
        }
        else {
            if(this.DHT.isEmpty()) {
                reply = "fail";
            }
            else {
                if(this.DHT.containsKey(key))
                {
                    this.DHT.remove(key);
                    reply = "pass";
				}
                else {
                    reply = "fail";
                }
            }
        }
        return reply;
    }
} /*Server*/