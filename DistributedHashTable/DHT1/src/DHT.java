import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Divya on 10/18/2015.
 */
public class DHT {

    public static void main(String[] args)
    {
        System.out.println("Please enter 1 to start in server mode /2 start client operations");
        Scanner sc = new Scanner(System.in);
        int val = sc.nextInt();
        if(val==1)
        {
            MultiThreadServer dhtServer = new MultiThreadServer();
            try {
                dhtServer.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(val==2)
        {
            MultiThreadClient dhtclient = new MultiThreadClient();
            try {
                dhtclient.startClient();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
