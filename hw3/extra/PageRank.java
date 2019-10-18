import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRank {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: PageRank <input path> <iteration>");
			System.exit(-1);
		}
		Configuration conf = new Configuration();
		conf.set("mapreduce.output.textoutputformat.separator", " ");
		FileSystem fs = FileSystem.get(conf);	

		for (int i = 0; i < Integer.parseInt(args[1]); i++) {
			Job job = Job.getInstance(conf);
			job.setJobName("PageRank");
			job.setNumReduceTasks(1);	

			job.setJarByClass(PageRank.class);
			job.setMapperClass(PageRankMapper.class);
			job.setReducerClass(PageRankReducer.class);

			Path in = new Path(args[0] + "input" + i + "/");
   			Path out = new Path(args[0] + "input" + (i + 1));
			if (fs.exists(out)) fs.delete(out, true);
			FileInputFormat.addInputPath(job, in);
			FileOutputFormat.setOutputPath(job, out);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			job.waitForCompletion(true);
		}

		System.exit(0);
	}
}