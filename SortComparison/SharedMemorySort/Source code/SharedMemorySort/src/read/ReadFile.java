package read;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/**
 * This class deals with splitting the file based on the ascii characters
 * File is split based on the read limit mentioned .
 * Once the buffer size to hold the lines is attaines , it is passed on to the next stage for splitting based on
 * ascii character
 */
public class ReadFile {

    public static Boolean processFile(String inputFile, String outputDir, int readLimit, boolean isSplit) {

        Calendar calendar = Calendar.getInstance();
        System.out.println("Processing split file : " + calendar.getTime());
        //System.out.println("Free heap : " + Runtime.getRuntime().freeMemory());
        int totLineCount = 0;
        int currLineCount = 0;
        BufferedReader bufferedReader = null;
        try {

            String line;
            List<String> buffer = new ArrayList<String>();
            Boolean isSplitComplete = true;
            bufferedReader = new BufferedReader(new FileReader(inputFile));
            while ((line = bufferedReader.readLine()) != null && isSplitComplete) {
                currLineCount++;

                if(currLineCount>totLineCount)
                    buffer.add(line);

                if(buffer.size()==readLimit) {

                    bufferedReader.close();
                    totLineCount = totLineCount + buffer.size();

                    isSplitComplete = false;
                    if(!isSplit) {
                        isSplitComplete = SplitFile.arrange(buffer, outputDir);
                    }
                    else
                    {
                        isSplitComplete =ProcessSplits.arrange(buffer,outputDir,inputFile);
                    }
                    if (isSplitComplete) {
                        buffer.clear();
                        currLineCount = 0;
                        bufferedReader = new BufferedReader(new FileReader(inputFile));
                    } else
                        throw new Exception();
                }
                //System.out.println(line);
            }
            if(!buffer.isEmpty()) {
                if(!isSplit) {
                    isSplitComplete = SplitFile.arrange(buffer, outputDir);
                }
                else {
                    isSplitComplete =ProcessSplits.arrange(buffer,outputDir,inputFile);
                }
                if(isSplitComplete)
                    buffer.clear();
                else
                    throw new Exception();
            }
        } catch (IOException e) {

            System.err.println("Could not read input file");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Split file operation did not complete");
            return false;
        } finally {

            try {

                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {

                System.err.println("Cannot close bufferedReader instance");
                return false;
            }
        }
        calendar = Calendar.getInstance();
        System.out.println("Input file processed : " + calendar.getTime());
        return true;
    }
}
