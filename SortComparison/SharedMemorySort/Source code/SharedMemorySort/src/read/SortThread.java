package read;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the sort thread implementation .
 * Runnable class handles the sorting using the merge sort algorithm
 *
 */
public class SortThread implements Runnable{
    String path = null;
    public SortThread(String filepath)
    {
        path=filepath;
    }

    @Override
    public void run() {
        //System.out.println("Sorting "+path);
        BufferedReader bufferedReader = null;
        File filetoSort = new File(path);
        try {
            if(filetoSort.exists()) {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filetoSort)));
                List<String> dataList = new ArrayList<String>();
                String readLine = null;
                while ((readLine = bufferedReader.readLine()) != null) {
                    dataList.add(readLine);
                }
                List<String> sortedKeys = mergeSort(dataList);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filetoSort));

                for (String s : sortedKeys) {
                    bufferedWriter.write(s + "\r\n");
                }
                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private List<String> mergeSort(List<String> keys) {
        if(keys.size()<=1)
            return keys;
        List<String> leftKeys = new ArrayList<String>();
        List<String> rightKeys = new ArrayList<String>();
        int middle=keys.size()/2;
        for(int i=0;i<middle;i++)
        {
            leftKeys.add(keys.get(i));
        }
        for(int j=middle;j<keys.size();j++)
        {
            rightKeys.add(keys.get(j));
        }
        mergeSort(leftKeys);
        mergeSort(rightKeys);
        return merge(leftKeys,rightKeys,keys);
    }

    private List<String> merge(List<String> leftKeys, List<String> rightKeys,List<String> keys) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < keys.size(); k++) {
            if (j >= rightKeys.size() ||
                    (i < leftKeys.size() && leftKeys.get(i).compareTo(rightKeys.get(j)) < 0))
            {
                keys.set(k, leftKeys.get(i));
                i++;
            } else {
                keys.set(k, rightKeys.get(j));
                j++;
            }
        }
        return keys;
    }
}
