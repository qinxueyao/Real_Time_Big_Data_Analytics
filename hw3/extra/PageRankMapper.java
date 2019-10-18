import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMapper 
		extends Mapper<LongWritable, Text, Text, Text> {
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] relations = value.toString().split(" ");
		int numOfOutlinks = relations.length - 2;
		double pr = Double.parseDouble(relations[relations.length - 1]) / numOfOutlinks;
		String outlinks = "";
		for (int i = 1; i <= numOfOutlinks; i++) {
			context.write(new Text(relations[i]), new Text(relations[0] + ", " + String.valueOf(pr)));
			outlinks += relations[i] + " ";
		}
		context.write(new Text(relations[0]), new Text(outlinks));
	}
}