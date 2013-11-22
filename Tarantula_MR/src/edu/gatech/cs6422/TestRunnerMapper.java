package edu.gatech.cs6422;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class TestRunnerMapper<K> extends MapReduceBase implements
		Mapper<K, Text, IntWritable, Text> {
	// Input: Value (Text): fully qualified test class name
	// Output: Key (IntWritable): basic block id
	// Output: Value (Text): covered test case(method) name and test result, e.g. test1.test1:P
	
	private String junit3Lib;
	private String junit4Lib;
	private String classPath;
	
	public void configure(JobConf job){
		junit3Lib = job.get("testRunnerMapper.junit3.lib");
		junit4Lib = job.get("testRunnerMapper.junit4.lib");
		classPath = job.get("testRunnerMapper.classpath");
	}

	@Override
	public void map(K key, Text value, OutputCollector<IntWritable, Text> output,
			Reporter reporter) throws IOException {
		// Add tested program, test cases, junit library to classpath
		URLClassLoader loader = new URLClassLoader(new URL[]
				{new URL(classPath), new URL(junit3Lib), new URL(junit4Lib)});
		try {
			// Load test case class
			Class testClass = Class.forName(value.toString(), true, loader);
			
			// Figure out junit3 or junit4
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
