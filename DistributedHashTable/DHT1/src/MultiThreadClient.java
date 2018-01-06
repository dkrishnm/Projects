import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

/**
 * Created by Divya on 10/17/15.
 */

public class MultiThreadClient {

    /*number of shared servers*/
    private static int numServers;

    /*server IP*/
    private static InetAddress[] host;

    /*server port*/
    private static int[] PORT;

    /*Data socket is defined as a member.*/
    /*Socket to be used for */
    /*communication with the server.*/
    public static Socket[] sock = null;

    /*total number of client requests*/
    public static int operCount;

    /*common delimiter*/
    public static String delimiter;

    /*hashmap block to be sent to each thread*/
    public static List<HashMap<String, String>> listOfBlocks = new ArrayList<HashMap<String, String>>();

    /**/
    public static final int DATA_SIZE = 1024;

    /*Constructor.*/
    public MultiThreadClient() {
    }

    /*The main function to be run
     *when the server application stars.*/
    public static void startClient() throws IOException
    {
        MultiThreadClient multiThreadClient = new MultiThreadClient();
        multiThreadClient.setProperties();

        System.out.println("Building data\n");
        for(int var1=0; var1<operCount; var1++) {

            /*Create random string of acceptable size as data*/
            String data = RandomStringUtils.randomAlphabetic(DATA_SIZE);
            int intKey = data.hashCode();

            /*decides the server to pass the data to - ergo the hashmap index*/
            int toId = Math.abs(intKey%numServers);

            /*assigns a key specific to the data and server*/
            String key = String.valueOf(toId) + String.valueOf(Math.abs(intKey));

            if(!listOfBlocks.get(toId).isEmpty()) {
                if(listOfBlocks.get(toId).containsKey(key)) {
                   if(var1>0)
                       var1--;
                }
                else
                    listOfBlocks.get(toId).put(key, data);
            }
            else {
                listOfBlocks.get(toId).put(key, data);
            }
        }


        System.out.println("Opening port\n");
        try {
            for(int var1=0; var1<numServers; var1++) {

                /*Create the server socket to listen on PORT*/
                sock[var1] = new Socket(host[var1], PORT[var1]);
            }
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

        /*main client loop.*/
        for(int var1=0; var1<numServers; var1++) {
            try
            {
                /*Create a single-threaded client.*/
                /*This will handle the data transfer.*/
                //new Client(sock[var1], blocks[var1], delimiter);
                new Client(sock[var1], listOfBlocks.get(var1), delimiter);
            }
            catch(IOException e)
            {
                /*Handle potential exceptions.*/
                /*As the creation of the
                 *single-threaded client failed,
                 *communication with the server can not start,
                 *so all data sockets are closed from this client.
                 */
                sock[var1].close();
            }
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

            /*set number of shared servers*/
            this.numServers = Integer.parseInt(properties.getProperty("servers"));

            /*initialize servers ip list*/
            this.host = new InetAddress[this.numServers];

            /*initialize servers PORT list*/
            this.PORT = new int[this.numServers];

            /*initialize servers sockets list*/
            this.sock = new Socket[this.numServers];

            /*initialize operations count*/
            this.operCount = Integer.parseInt(properties.getProperty("oper"));

            /*initialize delimiter*/
            this.delimiter = properties.getProperty("delimiter");

            for(int var1=0; var1<this.numServers; var1++) {

                /*initialize data block for all servers*/
                this.listOfBlocks.add(new HashMap<String, String>());

                int var2 = var1 + 1;
                try {
                    this.host[var1] = InetAddress.getByName(properties.getProperty("ip" + var2));
                    this.PORT[var1] = Integer.parseInt(properties.getProperty("port" + var2));
                }
                catch (IOException e)
                {

                }
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
