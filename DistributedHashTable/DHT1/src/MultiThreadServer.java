import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by Divya on 10/17/15.
 */

/*Class implementing the multi-threaded echo server.*/
/*This server receives a message from the clients
 *and replies with the same message back.
 */
public class MultiThreadServer {

    /*The server socket is defined as a class member.*/
    private static ServerSocket servSock;

    /*The port number is defined as a member.*/
    /*The server will listen on this port.*/
    private static int PORT;

    /*Data socket is defined as a member.*/
    /*Socket to be used for */
    /*communication with the client.*/
    Socket sock = null;

    /*Common delimiter*/
    private static String delimiter;

    /*Constructor.*/
    public MultiThreadServer() {

    }

    /*The main function to be run
     *when the server application stars.*/
    public static void startServer() throws IOException
    {
        MultiThreadServer multiThreadServer = new MultiThreadServer();
        multiThreadServer.setProperties();

        System.out.println("Opening port\n");
        try {
            /*Create the server socket to listen on PORT*/
            servSock = new ServerSocket(PORT);
        }
        catch (IOException e)
        {
            /*Handles potential exceptions
             *thrown while the server socket is created.*/
            /*Most common exception is triggered
             *when the chosen port is already used*/
            System.out.println("Port error!");
            System.exit(1);
        }

        /*At this point the server socket
        *was successfully created.*/
        try {
            /*main server loop.*/
            do
            {
                /*Server accepts connections from client.*/
                /*The Accept method blocks
                *until a connection occurs.*/
                Socket socket = servSock.accept();
                try
                {
                    /*Create a single-threaded server.*/
                    /*This will handle the client.*/
                    new Server(socket, delimiter);
                }
                catch(IOException e)
                {
                    /*Handle potential exceptions.*/
                    /*As the creation of the
                     *single-threaded server failed,
                     *communication with the client can not start,
                     *so the data socket is closed.
                     */
                    socket.close();
                }
            } while (true);
        }
        finally {
            /*When the server end its operation,
             *the server socket is closed.
             */
            servSock.close();
        }
    }

    public void setProperties()
    {
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("resources/config.properties");

            if (fileInputStream != null) {
                properties.load(fileInputStream);
            } else {
                throw new FileNotFoundException("Property file not found in path");
            }

            /*set port number specific to the sever in the arrangement*/
            this.PORT =  Integer.parseInt(properties.getProperty("port1"));

            /*initialize delimiter*/
            this.delimiter = properties.getProperty("delimiter");

            if(this.delimiter.equals("|"))
                this.delimiter = "\\" + properties.getProperty("delimiter");


            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}