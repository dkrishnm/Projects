package read;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Divya on 3/30/2016.
 * This class reads the sorted input files and merges on to the sortedfile.txt
 */
public class MergeSplits {
    static Calendar calendar = Calendar.getInstance();
    public static boolean mergeSplits(String outputDir, String path, int readLimit) {
        System.out.println("Merging splits "+path);
        File sortedFile = new File(outputDir + File.separator + "sortedfile.txt");
        if(!sortedFile.exists())
        {
            try {
                sortedFile.createNewFile();
                sortedFile.setExecutable(true);
                sortedFile.setReadable(true);
                sortedFile.setWritable(true);
                sortedFile.exists();
            } catch (IOException e) {

                System.err.println("Cannot create new file : " + sortedFile);
            }
        }
        if(sortedFile.exists()) {
            try {
                Charset charset = Charset.forName("ISO-8859-1");
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(sortedFile, true));
                    for (int i = 32; i <= 128; i++) {
                        Path fpath = Paths.get(outputDir, path + "_" + i + "temp.txt");
                        if (Files.exists(fpath)) {
                            List<String> lines = Files.readAllLines(fpath, charset);
                            for (String line : lines) {
                                bw.write(line + "\r\n");
                            }
                            bw.flush();
                        }
                        Files.deleteIfExists(fpath);
                    }
                    System.out.println("File merge complete time for "+ path + ": " +calendar.getTime());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bw.close();
                System.gc();
            } catch (IOException e) {
                System.out.println("Merge temp files failed");
                return false;
            }
        }
        return true;
    }
    }
