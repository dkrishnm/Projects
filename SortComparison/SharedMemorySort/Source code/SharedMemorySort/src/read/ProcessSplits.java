package read;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Divya on 3/29/2016.
 * The first level split files are split again on the second ascii character on each line.
 * These temp files generated are individually sorted and merged on to the output file
 */
public class ProcessSplits {

    public static Boolean arrange(List<String> lines, String outputDirPath, String inputFile) {

        int linesProcessed = 0;
        Integer key;
        File outputDir = new File(outputDirPath);
        String outputFilePath = "";
        File outputFile = null;
        Boolean fileExists = false;
        Boolean dirExists = outputDir.exists();
        File inFile = new File(inputFile);
        if (dirExists) {
            for (String line : lines) {
                key = Integer.valueOf(line.charAt(1));
                outputFilePath = outputDirPath+File.separator+inFile.getName()+"_"+key +"temp.txt";
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
