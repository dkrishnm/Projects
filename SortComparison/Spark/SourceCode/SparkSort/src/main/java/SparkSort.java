import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.util.Arrays;

/**
 * Created by Divya on 3/19/2016.
 */
public class SparkSort
{
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide the input file full path as argument");
            System.exit(0);
        }
        SparkConf conf = new SparkConf().setAppName("SparkTeraSort").setMaster("local");
        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<String> file = context.textFile(args[0]);
        JavaRDD<String> lines = file.flatMap(FILE_EXTRACTOR);
        JavaPairRDD<String, String> pairs = lines.mapToPair(SORT_MAPPER);
       /* JavaPairRDD<String, String> counter = pairs.reduceByKey(IdentityReducer);*/
        JavaPairRDD<String, String> sortFile = pairs.sortByKey();
        sortFile.keys().saveAsTextFile(args[1]);
        //sortFile.saveAsTextFile(args[1]);
    }
    private static final FlatMapFunction<String, String> FILE_EXTRACTOR =
            new FlatMapFunction<String, String>() {
                @Override
                public Iterable<String> call(String s) throws Exception {
                    return Arrays.asList(s);
                }
            };

    private static final PairFunction<String, String, String> SORT_MAPPER =
            new PairFunction<String, String, String>() {
                @Override
                public Tuple2<String, String> call(String s) throws Exception {
                    return new Tuple2<String, String>(s.concat(" "),null);
                }
            };


}