import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * Created by Divya on 3/14/2016.
 * This is the driver class for Hadoop
 */
public class HadoopTeraSort {
    public static void main(String args[]) throws Exception {
        Configuration c = new Configuration();
        c.set("mapreduce.output.textoutputformat.separator"," ");
        Job j = Job.getInstance(c, "HadoopTeraSort");
        j.setJarByClass(HadoopTeraSort.class);
        j.setMapperClass(TeraSortMapper.class);
        j.setCombinerClass(TeraSortReducer.class);
        j.setReducerClass(TeraSortReducer.class);
        j.setOutputKeyClass(Text.class);
        j.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(j, new Path(args[0]));
        FileOutputFormat.setOutputPath(j, new Path(args[1]));
        System.exit(j.waitForCompletion(true) ? 0 : 1);
    }
}