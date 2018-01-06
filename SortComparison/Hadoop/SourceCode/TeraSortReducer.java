import org.apache.hadoop.io.Text;
import java.io.IOException;

/**
 * Created by Divya on 3/14/2016.
 */
public class TeraSortReducer extends
        org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text>
{
    private Text res = new Text();
    public void reduce(Text key, Iterable<Text> values,
                       Context context) throws IOException, InterruptedException {
        res.set(values.iterator().next());
        context.write(key, res);
    }
   /*public void reduce(Text key, Text value,
                       Context context) throws IOException, InterruptedException {
        context.write(key, value);
    }*/
}

