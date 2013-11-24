package edu.gatech.cs6422;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class TestRunnerReducer<K> extends MapReduceBase
	implements Reducer<IntWritable, Text, IntWritable, Text>{

	static enum MyCounters { PASSED, FAILED }
	
	// Tarantula:
	// http://www.st.ewi.tudelft.nl/~peterz/papers/AZG_PRDC06.pdf
	// %failed(s) / (%passed(s) + %failed(s))
	// %failed(s) : the number of failed test cases executed s, to total number of failed test cases
	// %passed(s) : the number of passed test cases executed s, to total number of passed test cases
	
	@Override
	public void reduce(IntWritable key, Iterator<Text> values,
			OutputCollector<IntWritable, Text> output, Reporter reporter)
			throws IOException {
		int failed = 0, passed = 0;
		
		while(values.hasNext()) {
			Text text = values.next();
			char res = (char) text.charAt(text.getLength() - 1);
			if(res == 'P') {
				passed ++;
			} else if(res == 'F') {
				failed ++;
			} else {
				//throw new Exception();
			}
		}
		
		if(key.get() == -1) {
			reporter.incrCounter(MyCounters.PASSED, passed);
			reporter.incrCounter(MyCounters.FAILED, failed);
		} else {
			output.collect(key, new Text(passed + ":" + failed));
		}
	}

}
