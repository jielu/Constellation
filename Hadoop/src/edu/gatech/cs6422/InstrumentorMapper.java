package edu.gatech.cs6422;

import java.io.IOException;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class InstrumentorMapper<K> extends MapReduceBase implements
		Mapper<K, Text, Text, Text> {
	// Input: Value (Text): fully qualified test class name
	// Output: Key (IntWritable): basic block id
	// Output: Value (Text): covered test case(method) name and test result,
	// e.g. test1.test1:P, test1.test1:F
	private FileSystem fs;
	private BlockProbeInserter blockProbes;

	static enum MyCounters {
		PASSED, FAILED
	}

	public void configure(JobConf job) {
		try {
			fs = FileSystem.get(job);
			blockProbes = new BlockProbeInserter();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Instrument each class
	public void instrument(String classFile) {
		JavaClass jclass;
		try {
			jclass = (new ClassParser(classFile)).parse();
			String className;
			Integer classID;
			ConstantPoolGen cpgen = null;
			org.apache.bcel.classfile.Method[] methods = null;
			int clinitIndex = -1;

			// get class name and id
			className = jclass.getClassName();

			// get constantpool and methods
			cpgen = new ConstantPoolGen(jclass.getConstantPool());
			methods = jclass.getMethods();

			// initialize probe inserters for the current class
			blockProbes.init(cpgen, methods, className);

			// instrument each method
			for (int j = 0; j < methods.length; j++) {
				// skip abstract methods
				if (methods[j].isAbstract())
					continue;

				MethodGen mgen = new MethodGen(methods[j], className, cpgen);
				String methodName = mgen.getName() + mgen.getSignature();

				if (methodName.equals("<clinit>()V"))
					clinitIndex = j;

				// instrument method with all probe types
				blockProbes.instrumentMethod(methodName, mgen, j);

				methods[j] = mgen.getMethod();
			}

			// set constant pool and write new class file
			jclass.setConstantPool(cpgen.getFinalConstantPool());
			FSDataOutputStream out;
			try {
				// Create output directory first if it doesn't exist
				classFile = classFile.replace("insect_input", "insect_output");
				classFile = "/insect/" + classFile;
				out = fs.create(new Path(classFile));
				jclass.dump(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

	}

	@Override
	public void map(K key, Text value, OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException {
		instrument(value.toString());
	}

}