import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCount {

    // Mapper Class
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);  // Value for each word occurrence
        private Text word = new Text();  // Variable to store words

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString()); // Tokenize each word from input line
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken()); // Extract next word
                context.write(word, one); // Emit (word, 1) as key-value pair
            }
        }
    }

    // Reducer Class
    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0; // Initialize sum for each word
            for (IntWritable val : values) {
                sum += val.get(); // Add all occurrences of the word
            }
            result.set(sum); // Store the final word count
            context.write(key, result); // Emit (word, count)
        }
    }

    // Driver Program (Main Method)
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration(); // Load Hadoop configuration
        Job job = Job.getInstance(conf, "word count example"); // Create job instance

        job.setJarByClass(WordCount.class); // Set the main class
        job.setMapperClass(TokenizerMapper.class); // Set Mapper class
        job.setCombinerClass(IntSumReducer.class); // Set Combiner class (optional)
        job.setReducerClass(IntSumReducer.class); // Set Reducer class

        job.setMapOutputKeyClass(Text.class); // Output key type from Mapper
        job.setMapOutputValueClass(IntWritable.class); // Output value type from Mapper

        job.setOutputKeyClass(Text.class); // Final output key type
        job.setOutputValueClass(IntWritable.class); // Final output value type

        job.setInputFormatClass(TextInputFormat.class); // Input format
        job.setOutputFormatClass(TextOutputFormat.class); // Output format

        FileInputFormat.addInputPath(job, new Path(args[0])); // Input file path
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output file path

        System.exit(job.waitForCompletion(true) ? 0 : 1); // Execute the job
    }
}

