import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Divya on 10/17/15.
 */

/*single-threaded TCP Client class*/
public class Client extends Thread {

    /*socket holder set by thread class*/
    Socket sock = null;

    /*hashmap to maintain*/
    HashMap<String, String> block = new HashMap<String, String>();

    /*common delimiter*/
    String delimiter;

    public Client (Socket sock, HashMap<String ,String> block, String delimiter) throws IOException
    {
        /*setting socket from the thread handler*/
        this.sock = sock;

        /*setting the data block assigned by the thread handler*/
        this.block = block;

        /*setting common delimiter*/
        this.delimiter = delimiter;

        /*calling run() method to start communcating with the server*/
        start();
    }

    public void run()
    {
        try
        {
            /*create socket reader and writer*/
            BufferedReader in = new BufferedReader(new InputStreamReader (sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            /*Set up stream for user entry*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            /*storage for message and response message*/
            String msgOut, msgIn;
            int pass, fail;
            long timeIn, timeOut, blockTime, totTime, totFail;

            Set<String> keySet= block.keySet();
            Iterator<String> keyIter = keySet.iterator();

            timeIn = 0;
            timeOut = 0;
            blockTime = 0;
            pass = 0;
            fail = 0;
            totTime = 0;
            totFail = 0;
            while (keyIter.hasNext()) {

                String key = keyIter.next();

                msgOut = "put" + delimiter + key + delimiter + block.get(key);

                /*send the message*/
                out.println(msgOut);
                timeIn = System.currentTimeMillis();

                /*read the response*/
                msgIn = in.readLine();
                timeOut = System.currentTimeMillis();

                /*reponse category*/
                if(msgIn.equals("pass"))
                    pass++;
                else
                    fail++;

                /*processing time per operation*/
                blockTime += (timeOut - timeIn);

            }
            int var1 = block.size();
            //System.out.println("\n===================================");
            //System.out.println("\t\t\tPUT COMPLETE");
            //System.out.println("===================================");
            //System.out.println("\t TOTAL OPER   : " + var1);
            //System.out.println("\t SUCCESS OPER : " + pass);
            //System.out.println("\t FAIL OPER    : " + fail);
            //System.out.println("\t TOTAL TIME   : " + blockTime + " msec");
            if(blockTime!=0 && var1!=0) {
                //BigDecimal avgTime = new BigDecimal(blockTime).divide(new BigDecimal(var1));
                //System.out.println("\t AVG TIME     : " + avgTime.setScale(2, RoundingMode.HALF_UP) + " msec/oper");
            }

            keyIter = keySet.iterator();
            totTime += blockTime;
            totFail += fail;
            timeIn = 0;
            timeOut = 0;
            blockTime = 0;
            pass = 0;
            fail = 0;
            while (keyIter.hasNext()) {

                String key = keyIter.next();

                msgOut = "get" + delimiter + key;

                /*send the message*/
                out.println(msgOut);
                timeIn = System.currentTimeMillis();

                /*read the response*/
                msgIn = in.readLine();
                timeOut = System.currentTimeMillis();

                /*reponse category*/
                if(!msgIn.equals("fail"))
                    pass++;
                else
                    fail++;

                /*processing time per operation*/
                blockTime += (timeOut - timeIn);

            }
            var1 = block.size();
            //System.out.println("\n===================================");
            //System.out.println("\t\t\tGET COMPLETE");
            //System.out.println("===================================");
            //System.out.println("\t TOTAL OPER   : " + var1);
           // System.out.println("\t SUCCESS OPER : " + pass);
            //System.out.println("\t FAIL OPER    : " + fail);
            //System.out.println("\t TOTAL TIME   : " + blockTime + " msec");
            if(blockTime!=0 && var1!=0) {
                //BigDecimal avgTime = new BigDecimal(blockTime).divide(new BigDecimal(var1));
                //System.out.println("\t AVG TIME     : " + avgTime.setScale(2, RoundingMode.HALF_UP) + " msec/oper");
            }

            keyIter = keySet.iterator();
            totTime += blockTime;
            totFail += fail;
            timeIn = 0;
            timeOut = 0;
            blockTime = 0;
            pass = 0;
            fail = 0;
            while (keyIter.hasNext()) {

                String key = keyIter.next();

                msgOut = "rem" + delimiter + key;

                /*send the message*/
                out.println(msgOut);
                timeIn = System.currentTimeMillis();

                /*read the response*/
                msgIn = in.readLine();
                timeOut = System.currentTimeMillis();

                /*reponse category*/
                if(msgIn.equals("pass"))
                    pass++;
                else
                    fail++;

                /*processing time per operation*/
                blockTime += (timeOut - timeIn);

            }
            var1 = block.size();
            //System.out.println("\n===================================");
            //System.out.println("\t\t REMOVE COMPLETE");
            //System.out.println("===================================");
            //System.out.println("\t TOTAL OPER   : " + var1);
            //System.out.println("\t SUCCESS OPER : " + pass);
            //System.out.println("\t FAIL OPER    : " + fail);
            //System.out.println("\t TOTAL TIME   : " + blockTime + " msec");
            if(blockTime!=0 && var1!=0) {
                //BigDecimal avgTime = new BigDecimal(blockTime).divide(new BigDecimal(var1));
                //System.out.println("\t AVG TIME     : " + avgTime.setScale(2, RoundingMode.HALF_UP) + " msec/oper");
            }
            totTime += blockTime;
            totFail += fail;

            System.out.println("\n===================================");
            System.out.println("\t\t\t  SUMMARY");
            System.out.println("===================================");
            System.out.println("\t TOTAL OPER   : " + (var1*3));
            System.out.println("\t SUCCESS OPER : " + ((var1*3)-totFail));
            System.out.println("\t FAIL OPER    : " + totFail);
            System.out.println("\t TOTAL TIME   : " + totTime + " msec");
            if(blockTime!=0 && var1!=0) {
                MathContext mathCtxt = new MathContext(2, RoundingMode.HALF_UP);
                BigDecimal avgTime = new BigDecimal(totTime).divide(new BigDecimal(var1*3), mathCtxt);
                System.out.println("\t AVG TIME     : " + avgTime + " msec/oper");
            }

            System.out.print("\nEnter BYE to exit: ");

            /*read user message*/
            msgOut =  reader.readLine();

            /*send the message*/
            out.println(msgOut);

            /*read the response*/
            msgIn = in.readLine();

            System.out.println("SERVER> " + msgIn);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}