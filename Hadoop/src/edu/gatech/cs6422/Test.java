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

import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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
public class Test extends Configured implements Tool {
	private Test() {
	} // singleton

	public int run(String[] args) throws Exception {
		// if (args.length < 3) {
		// System.out.println("Test");
		// ToolRunner.printGenericCommandUsage(System.out);
		// return -1;
		// }

		Path tempDir = new Path("grep-temp-"
				+ Integer.toString(new Random().nextInt(Integer.MAX_VALUE)));

		JobConf grepJob = new JobConf(getConf(), Test.class);

		try {

			grepJob.setJobName("grep-search");

			FileInputFormat.setInputPaths(grepJob,
					"/home/yjy/workspace/Hadoop/testClass");

			grepJob.setMapperClass(TestRunnerMapper.class);
			grepJob.set(
					"testRunnerMapper.junit4.lib",
					"file:///home/yjy/eclipse-standard-kepler-R-linux-gtk-x86_64/eclipse/plugins/org.junit.source_4.11.0.v201303080030.jar");
			grepJob.set(
					"testRunnerMapper.junit3.lib",
					"file:///Users/jielu/Dropbox/workspace2013F/cs6422/hadoop-1.2.1/example/lib/junit3.jar");
			grepJob.set(
					"testRunnerMapper.classpath",
					"file:////home/yjy/workspace/deliverables/deliverables/.coverage_data/godoil/;file:///home/yjy/workspace/Hadoop/class/;file:///home/yjy/workspace/Constellation/insectJ/lib/insect.jar");

			// grepJob.setCombinerClass(LongSumReducer.class);
			grepJob.setReducerClass(TestRunnerReducer.class);
			//
			FileOutputFormat.setOutputPath(grepJob, tempDir);
			grepJob.setOutputFormat(SequenceFileOutputFormat.class);
			grepJob.setOutputKeyClass(IntWritable.class);
			grepJob.setOutputValueClass(Text.class);

			RunningJob job = JobClient.runJob(grepJob);
			Counters counters = job.getCounters();
			System.out.println("Passed test cases: "
					+ counters.getCounter(TestRunnerMapper.MyCounters.PASSED));
			System.out.println("Failed test cases: "
					+ counters.getCounter(TestRunnerMapper.MyCounters.FAILED));

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
			FileSystem.get(grepJob).delete(tempDir, true);
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new Test(), args);
		System.exit(res);
	}

}
