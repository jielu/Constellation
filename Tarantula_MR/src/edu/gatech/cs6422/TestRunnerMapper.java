package edu.gatech.cs6422;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

public class TestRunnerMapper<K> extends MapReduceBase implements
		Mapper<K, Text, IntWritable, Text> {
	// Input: Value (Text): fully qualified test class name
	// Output: Key (IntWritable): basic block id
	// Output: Value (Text): covered test case(method) name and test result,
	// e.g. test1.test1:P, test1.test1:F

	private String junit3Lib;
	private String junit4Lib;
	private String classPath;
	private URLClassLoader loader;

	private JUnitCore junitCore;

	public void configure(JobConf job) {
		
		junit3Lib = job.get("testRunnerMapper.junit3.lib");
		junit4Lib = job.get("testRunnerMapper.junit4.lib");
		classPath = job.get("testRunnerMapper.classpath");

		try {
			String[] cps = classPath.split(";");
//			URL[] urls = new URL[cps.length + 2];
//			urls[0] = new URL(junit3Lib);
//			urls[1] = new URL(junit4Lib);
//			for(int i=0; i<cps.length; i++){
//				System.out.println("#####" + cps[i]);
//				urls[i+2] = new URL(cps[i]);
//			}
			URL[] url = new URL[]{ new URL("file:///Users/jielu/Dropbox/workspace2013F/ReTest/deliverables/.coverage_data/test/"),
					new URL("file:///Users/jielu/Dropbox/insect.jar"),
					new URL("file:///Users/jielu/Dropbox/workspace2013F/cs6422/hadoop-1.2.1/example/testcase/")
			};
			loader = new URLClassLoader(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void map(K key, Text value,
			OutputCollector<IntWritable, Text> output, Reporter reporter)
			throws IOException {
		
		try {
			URL[] url = new URL[]{ new URL("file:///Users/jielu/Dropbox/workspace2013F/ReTest/deliverables/.coverage_data/test/"),
					new URL("file:///Users/jielu/Dropbox/insect.jar"),
					new URL("file:///Users/jielu/Dropbox/workspace2013F/cs6422/hadoop-1.2.1/example/testcase/")
			};
			URLClassLoader loader = new URLClassLoader(url);
			Class testClass = Class.forName("god.oil.v587.TestEntry", true, loader);
			
			Method[] methods = testClass.getMethods();
			for(int i=0; i<methods.length; i++){
				Method method = methods[i];
				String methodName = method.getName();
				
				Annotation annotation = method.getAnnotation(org.junit.Test.class);
//				if(annotation != null){
					Request request = Request.method(testClass, methodName);
					Result testResult = new JUnitCore().run(request);
					
					System.out.println("###" + testResult.wasSuccessful());
					
					if(testResult.getFailureCount() > 0){
						System.out.println("###"+ methodName);
						System.out.println(testResult.getFailures().get(0).getTrace());
					}
				
//				}
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		junitCore = new JUnitCore();
//
//		try {
//			// Load test case class
//			
//			Class testClass = Class.forName(value.toString(), true, loader);
//			boolean isJunit3 = testClass.getSuperclass().getName().equals("junit.framework.TestCase");
//
//			Method[] methods = testClass.getMethods();
//			for(int i=0; i<methods.length; i++){
//				Method method = methods[i];
//				String methodName = method.getName();
//				if(isJunit3){
//					if(method.getModifiers() == Modifier.PUBLIC && methodName.startsWith("test")){
//						runTestMethod(testClass, methodName, output);
//					}
//				}else{
//					Annotation annotation = method.getAnnotation(org.junit.Test.class);
//					
//					//if (annotation != null) {
//						runTestMethod(testClass, methodName, output);
//					//}
//				}
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		}

	}

	// Run test method and get coverage data
	private void runTestMethod(Class testClass, String methodName,
			OutputCollector<IntWritable, Text> output) {
		Request request = Request.method(testClass, methodName);
		Result testResult = junitCore.run(request);
		
		if(testResult.getFailureCount() > 0){
			System.out.println("###"+ methodName);
			System.out.println(testResult.getFailures().get(0).getTrace());
		}

		try {
			Class bm = Class.forName("insect.coverage.execution.BlockMonitor",
					true, loader);

			Field f = (Field) bm.getDeclaredField("blocks");
			f.setAccessible(true);
			long[] coverage = (long[]) f.get(null);

			for (int j = 0; j < coverage.length; j++) {
				if (coverage[j] > 0) {
					StringBuilder test = new StringBuilder();
					test.append(testClass.getName());
					test.append(".");
					test.append(methodName);
					test.append(":");
					test.append(testResult.wasSuccessful() == true ? "P" : "F");
					output.collect(new IntWritable(j),
							new Text(test.toString()));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
