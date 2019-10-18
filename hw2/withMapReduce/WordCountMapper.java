import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper
    extends Mapper<LongWritable, Text, Text, IntWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    String line = value.toString();
    String delims = "[ ,;#-/:]+";
    String[] tokens = line.split(delims);
    for (int i = 0; i < tokens.length; i++) tokens[i] = tokens[i].toLowerCase();
    Set<String> set = new HashSet<>(Arrays.asList(tokens));

    String[] tobeSearched = {"hackathon", "Dec", "Chicago", "Java"};
    for (String s : tobeSearched) {
      if (set.contains(s.toLowerCase()))
        context.write(new Text(s), new IntWritable(1));
      else 
        context.write(new Text(s), new IntWritable(0));
    }
  }
}