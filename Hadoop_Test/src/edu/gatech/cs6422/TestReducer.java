package edu.gatech.cs6422;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class TestReducer<K> extends MapReduceBase implements
Reducer<Text, Text, Text, Text>  {

	@Override
	public void reduce(Text arg0, Iterator<Text> arg1,
			OutputCollector<Text, Text> arg2, Reporter arg3)
			throws IOException {
		
		
		
	}

}
