package edu.gatech.cs6422;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class TarantulaMapper extends MapReduceBase implements
	Mapper<IntWritable, Text, LongWritable, Text> {

	static double totalFail;
	static double totalPass;
	
	// Tarantula:
	// http://www.st.ewi.tudelft.nl/~peterz/papers/AZG_PRDC06.pdf
	// %failed(s) / (%passed(s) + %failed(s))
	// %failed(s) : the number of failed test cases executed s, to total number
	// of failed test cases
	// %passed(s) : the number of passed test cases executed s, to total number
	// of passed test cases
	
	public void configure(JobConf job) {
		totalFail = (double) Long.parseLong(job.get("totalFail"));
		totalPass = (double) Long.parseLong(job.get("totalPass"));
	}
	
	@Override
	public void map(IntWritable blockId, Text testResult,
			OutputCollector<LongWritable, Text> output, Reporter reporter)
			throws IOException {
		System.out.println(blockId + " " + testResult.toString());
		String[] strs = testResult.toString().split(":");
		try {
			int passed = Integer.parseInt(strs[0]);
			int failed = Integer.parseInt(strs[1]);
			
			double passedRate = passed / totalPass;
			double failedRate = failed / totalFail;
			
			double suspiciousRate = failedRate / (failedRate + passedRate);
			
			output.collect(new LongWritable((long) (suspiciousRate * 10000)), 
					new Text(String.valueOf(blockId.get())));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
