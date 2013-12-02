/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.gatech.cs6422;

import java.net.URI;
import java.util.Date;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/* Extracts matching regexs from input files and counts them. */
public class Test_Common extends Configured implements Tool {
	private Test_Common() {
	} // singleton

	public int run(String[] args) throws Exception {
//		 if (args.length < 4) {
//		 System.out.println("Usage: bin/hadoop jar test.jar <input_path> <junit4_lib> <junit3_lib> <classpath>");
//		 ToolRunner.printGenericCommandUsage(System.out);
//		 return -1;
//		 }

		JobConf grepJob = new JobConf(getConf(), Test_Common.class);
		
		String junit4_lib = "/common/lib/junit4.jar";
		String junit3_lib = "/common/lib/junit3.jar";
		String insect_lib = "/common/lib/insect.jar";
		
		String instrumented_classes_path = "/common/instrumented-src-class/";
		String testcases_path = "/common/test-src-class/";

		String input_path = "/common/testClass";
		String output_path = "/common/output";
		
		String data_path = "/common/data#data";
		
		// Add classpath
		DistributedCache.addFileToClassPath(new Path(junit4_lib), grepJob);
		DistributedCache.addFileToClassPath(new Path(junit3_lib), grepJob);

		DistributedCache.addFileToClassPath(new Path(instrumented_classes_path), grepJob);
		DistributedCache.addFileToClassPath(new Path(testcases_path), grepJob);
		DistributedCache.addFileToClassPath(new Path(insect_lib), grepJob);
		
		// Add resources
		DistributedCache.addCacheFile(new URI(data_path), grepJob);
		DistributedCache.createSymlink(grepJob);
		
		try {

			grepJob.setJobName("grep-search");
			grepJob.setInputFormat(NLineInputFormat.class);

			NLineInputFormat.setInputPaths(grepJob,
					input_path);
		
			
			grepJob.setMapperClass(TestRunnerMapper.class);

			// grepJob.setCombinerClass(LongSumReducer.class);
			grepJob.setReducerClass(TestReducer.class);
			//
			FileOutputFormat.setOutputPath(grepJob, new Path(output_path));
			//grepJob.setOutputFormat(org.apache.hadoop.mapred.TextOutputFormat.class);
			grepJob.setOutputKeyClass(Text.class);
			grepJob.setOutputValueClass(Text.class);

			RunningJob job = JobClient.runJob(grepJob);
			

			// JobConf sortJob = new JobConf(getConf(), Test.class);
			// sortJob.setJobName("grep-sort");
			//
			// FileInputFormat.setInputPaths(sortJob, tempDir);
			// sortJob.setInputFormat(SequenceFileInputFormat.class);
			//
			// sortJob.setMapperClass(InverseMapper.class);
			//
			// sortJob.setNumReduceTasks(1); // write a single file
			// FileOutputFormat.setOutputPath(sortJob, new Path(args[1]));
			// sortJob.setOutputKeyComparatorClass // sort by decreasing freq
			// (LongWritable.DecreasingComparator.class);
			//
			// JobClient.runJob(sortJob);
		} finally {
			//FileSystem.get(grepJob).delete(tempDir, true);
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new Test_Common(), args);
		System.exit(res);
	}

}
