/**
 * Created by Divya on 3/14/2016.
 * This is the mapper class for Hadoop
 */
import java.io.IOException;
import org.apache.hadoop.io.Text;

public class TeraSortMapper extends
        org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, Text> {

    private Text in = new Text();
    private Text out = new Text();
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException
   {
       in.set(value);
       out.set("");
       context.write(in,out);
   }
}
