package read;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This is the main driver class
 * Deals with passing the input file to different stages split, sort and merge files
 *
 */
public class Control {

    static int readLimit = 5000000;
    static String inputFile = "/home/ubuntu/10gb.txt";
    static String outputDir = "/home/ubuntu/sortedout";
    static int numThreads = 1;
    static Calendar calendar = Calendar.getInstance();
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the number of threads");
        numThreads =sc.nextInt();
        System.out.println("File Process start time : " + calendar.getTime());
        Boolean isFileProcessed = ReadFile.processFile(inputFile, outputDir, readLimit, false);
        if (isFileProcessed) {

            System.out.println("File processed");
            System.out.println("Available heap space : " + Runtime.getRuntime().freeMemory());
            System.gc();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("System GC clean up interrupted");
            }
            System.out.println("Available heap space : " + Runtime.getRuntime().freeMemory());
            for (int i = 32; i < 128; i++) {
                String path = i + ".txt";
                Boolean isSplitProcessed = ReadFile.processFile(outputDir + File.separator + path, outputDir, readLimit, true);
                if (isSplitProcessed) {
                    Boolean isSorted = sortSplits();
                    if(isSorted) {
                        Boolean isTempMerged = MergeSplits.mergeSplits(outputDir, path, readLimit);
                        if (isTempMerged) {
                            File inFile = new File(outputDir + File.separator + path);
                            inFile.delete();
                        }
                    }

                } else {
                    System.out.println("No Split File to process");
                }
            }

        }
    }

    private static Boolean sortSplits() {
        Thread[] threads = new Thread[numThreads];
        int k =0;
        for (int i = 32; i < 128; i++) {
            for(int j=32;j<128;j++) {
                String path = outputDir + File.separator + i+".txt"+ "_" + j + "temp.txt";
                Thread sortThread = new Thread(new SortThread(path));
                sortThread.run();
                threads[k]=sortThread;
                k++;
                if(k==numThreads)
                {
                    for(int l=0;l<numThreads;l++)
                    {
                        try {
                            threads[l].join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    k=0;
                }
            }
        }
        return true;
    }
}
