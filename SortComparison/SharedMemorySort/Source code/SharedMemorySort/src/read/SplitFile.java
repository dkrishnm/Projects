package read;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

/**
 * This file splits the input lines based on the first ascii character and generates first level spllt files
 * in th outputpath
 */
public class SplitFile {
    Calendar calendar = Calendar.getInstance();
    public static Boolean arrange(List<String> lines, String outputDirPath) {

        int linesProcessed = 0;

        Integer key;
        File outputDir = new File(outputDirPath);
        String outputFilePath = "";
        File outputFile = null;
        Boolean fileExists = false;
        Boolean dirExists = outputDir.exists();
        if (dirExists) {

            for (String line : lines) {

                key = Integer.valueOf(line.charAt(0));
                outputFilePath = outputDirPath + File.separator + key + ".txt";
                outputFile = new File(outputFilePath);
                fileExists = outputFile.exists();

                if (!fileExists) {
                    try {
                        outputFile.createNewFile();
                        outputFile.setExecutable(true);
                        outputFile.setReadable(true);
                        outputFile.setWritable(true);

                        fileExists = outputFile.exists();
                    } catch (IOException e) {

                        System.err.println("Cannot create new file : " + outputFilePath);
                    }
                }

                if (fileExists) {
                    try
                    {
                        FileWriter fileWriter = new FileWriter(outputFilePath,true);
                        fileWriter.write(line + System.getProperty("line.separator"));
                        fileWriter.close();
                    }
                    catch(IOException ioe)
                    {
                        System.err.println("Cannot write in file : " + outputFilePath);
                    }
                }

                linesProcessed++;
                outputFile = null;
            }
        }

        if(lines.size()==linesProcessed) {
            lines.clear();
            return true;
        }
        else
            return false;
    }
}
