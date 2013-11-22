package insect.coverage.instrumentation;

import insect.ConfigFile;
import insect.Debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;

/**
 * Responsible for performing the actual instrumentation of a set of classes.
 * Coordinates the probe inserters by initializing files, retrieving bytecode
 * information, etc and then instructing each probe inserter to instrument on
 * a per method basis.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ProbeInserter {

	//probe inserter types
	private CallProbeInserter callProbes;
	private CatchProbeInserter catchProbes;
	private BranchProbeInserter branchProbes;
	private BlockProbeInserter blockProbes;
	private ThrowProbeInserter throwProbes;

	private String[] classFiles;
	private HashMap idMapping;
	private int nextID;

	/**
	 * Constructor to initialize probe inserters.
	 *
	 */
	private ProbeInserter() {

		nextID = 0;

		idMapping = new HashMap();
		callProbes = new CallProbeInserter();
		catchProbes = new CatchProbeInserter();
		branchProbes = new BranchProbeInserter();
		blockProbes = new BlockProbeInserter();
		throwProbes = new ThrowProbeInserter();
	}

	/**
	 * Constructor to create a ProbeInserter from a string of filenames.
	 *
	 * @param fileString space delimited filenames
	 */
	public ProbeInserter(String fileString) {

		this();

		StringTokenizer st = new StringTokenizer(fileString);
		classFiles = new String[st.countTokens()];
		for (int i = 0; i < classFiles.length; i++)
			classFiles[i] = st.nextToken();

	}

	/**
	 * Constructor to create a ProbeInserter from an array of filenames.
	 *
	 * @param classFiles a <code>String[]</code> value
	 */
	public ProbeInserter(String[] classFiles) {

		this();

		this.classFiles = classFiles;

	}

	/**
	 * Instruments the classes by coordinating all of the probe inserters.
	 *
	 * @param outFileDir filepath to place id files
	 * @return true if successful, otherwise false
	 */
	public boolean instrument(String outFileDir) {

		boolean catchCoverage = false;
		boolean callCoverage = false;
		boolean extCallCoverage = false;
		boolean branchCoverage = false;
		boolean blockCoverage = false;
		boolean throwCoverage = false;
		int optimize = 0;
		String prop;

		//check config file for which types of coverage are enabled
		if (((prop = ConfigFile.getProperty("CALLCOVERAGE")) != null)
			&& (prop.toUpperCase().equals("TRUE"))) {
			callCoverage = true;
			Debug.println("Instrumenting for internal call coverage", 3);
		}

		if (((prop = ConfigFile.getProperty("EXTCALLCOVERAGE")) != null)
			&& (prop.toUpperCase().equals("TRUE"))) {
			extCallCoverage = true;
			Debug.println("Instrumenting for external call coverage", 3);
		}

		if (((prop = ConfigFile.getProperty("BLOCKCOVERAGE")) != null)
			&& (prop.toUpperCase().equals("TRUE"))) {
			blockCoverage = true;
			Debug.println("Instrumenting for block coverage", 3);
		}

		if (((prop = ConfigFile.getProperty("BRANCHCOVERAGE")) != null)
			&& (prop.toUpperCase().equals("TRUE"))) {
			branchCoverage = true;
			Debug.println("Instrumenting for branch coverage", 3);
		}

		if (((prop = ConfigFile.getProperty("CATCHCOVERAGE")) != null)
			&& (prop.toUpperCase().equals("TRUE"))) {
			catchCoverage = true;
			Debug.println("Instrumenting for catch coverage", 3);
		}

		if (((prop = ConfigFile.getProperty("THROWCOVERAGE")) != null)
			&& (prop.toUpperCase().equals("TRUE"))) {
			throwCoverage = true;
			Debug.println("Instrumenting for throw coverage", 3);
		}

		if (((prop = ConfigFile.getProperty("PROFILE")) != null)
			&& (prop.toUpperCase().equals("TRUE"))) {
			Debug.println("Instrumenting for profiling", 3);
		}

		if (((prop = ConfigFile.getProperty("OPTIMIZE")) != null)
			&& ((prop.trim().equals("1")) || (prop.trim().equals("2")))) {
			optimize = Integer.parseInt(prop.trim());
			Debug.println(
				"Optimizing instrumentation (level = " + optimize + ")",
				3);
			callProbes.loadOptimizeInfo(optimize, outFileDir);
		}

		//open file to write basic blocks    
		PrintWriter blockFile = null;
		if (blockCoverage) {
			try {
				blockFile =
					new PrintWriter(
						new FileWriter(
							outFileDir + File.separator + "insect.blockids"));
			} catch (IOException blockExcep) {
				System.err.println("Unable to create block file");
				return false;
			}
		}

		//open file to write branch ids
		PrintWriter branchFile = null;
		if (branchCoverage) {
			try {
				branchFile =
					new PrintWriter(
						new FileWriter(
							outFileDir + File.separator + "insect.branchids"));
			} catch (IOException branchExcep) {
				System.err.println("Unable to create branch id file");
				return false;
			}
		}

		/*Add class ids to id mapping. This is done separately so that
		  all class ids will be available when instrumenting.*/
		JavaClass[] jclasses = new JavaClass[classFiles.length];
		for (int i = 0; i < classFiles.length; i++) {

			//load current class in BCEL
			try {
				jclasses[i] = (new ClassParser(classFiles[i])).parse();
			} catch (IOException loadExcep) {
				System.err.println("Error loading file " + classFiles[i]);
				return false;
			}
			if (idMapping.get(jclasses[i].getClassName()) == null)
				idMapping.put(
					jclasses[i].getClassName(),
					new Integer(nextID++));
			else
				System.out.println("Warning: Duplicate class - " 
									+ jclasses[i].getClassName());

		}

		//instrument each class
		for (int i = 0; i < classFiles.length; i++) {
			String className;
			Integer classID;
			ConstantPoolGen cpgen = null;
			Method[] methods = null;
			int clinitIndex = -1;

			//get class name and id
			className = jclasses[i].getClassName();
			classID = (Integer) idMapping.get(className);

			//get constantpool and methods
			cpgen = new ConstantPoolGen(jclasses[i].getConstantPool());
			methods = jclasses[i].getMethods();

			//initialize probe inserters for the current class
			if ((callCoverage) || (extCallCoverage)) {
				callProbes.init(cpgen, this, callCoverage, extCallCoverage);
			}
			if (blockCoverage) {
				blockProbes.init(cpgen, methods, className, blockFile);
			}
			if (branchCoverage) {
				branchProbes.init(cpgen, branchFile);
			}
			if (catchCoverage) {
				catchProbes.init(cpgen);
			}
			if (throwCoverage) {
				throwProbes.init(cpgen);
			}

			//instrument each method
			for (int j = 0; j < methods.length; j++) {
				//skip abstract methods
				if (methods[j].isAbstract())
					continue;

				MethodGen mgen = new MethodGen(methods[j], className, cpgen);
				String methodName = mgen.getName() + mgen.getSignature();
				
				if (methodName.equals("<clinit>()V"))
					clinitIndex = j;

				//add method-id mapping
				Integer methodID =
					(Integer) idMapping.get(className + "." + methodName);
				if (methodID == null) {
					methodID = new Integer(nextID++);
					idMapping.put(className + "." + methodName, methodID);
				}

				//instrument method with all probe types
				if ((callCoverage) || (extCallCoverage))
					callProbes.instrumentMethod(
						methodName,
						mgen,
						methodID.intValue());
				if (blockCoverage)
					blockProbes.instrumentMethod(methodName, mgen, j);
				if (branchCoverage)
					branchProbes.instrumentMethod(methodName, mgen);
				if (catchCoverage)
					catchProbes.instrumentMethod(
						methodName,
						mgen,
						classID.intValue());
				if (throwCoverage)
					throwProbes.instrumentMethod(
						methodName,
						mgen,
						classID.intValue());
				methods[j] = mgen.getMethod();
			}

			//set constant pool and write new class file
			jclasses[i].setConstantPool(cpgen.getFinalConstantPool());
			try {
				jclasses[i].dump(classFiles[i]);
			} catch (IOException dumpExcep) {
				System.err.println("Error writing file " + classFiles[i]);
				return false;
			}

		}

		//finish block/branch instrumentation
		if (blockCoverage) {
			blockFile.close();
			blockProbes.finish(outFileDir);
		}
		if (branchCoverage) {
			branchFile.close();
			branchProbes.finish(outFileDir);
		}

		//open file to output ids
		try {
			PrintWriter idFile =
				new PrintWriter(
					new FileWriter(
						outFileDir + File.separator + "insect.classids"));
			Iterator it = idMapping.keySet().iterator();

			for (int j = 0; j < nextID; j++) {
				Object nextObj = it.next();
				idFile.println(idMapping.get(nextObj) + ":" + nextObj);
			}

			idFile.close();
		} catch (IOException idExcep) {
			System.err.println("Unable to create class ids file");
			return false;
		}

		return true;
	}

	/**
	 * Returns the insect class-level id of the
	 * specified class or method
	 * 
	 * @param name class name or method signature
	 * @return Integer containing the id
	 */
	public Integer getClassID(String name) {
		Integer id = (Integer) idMapping.get(name);
		if (id == null)
			idMapping.put(name, (id = new Integer(nextID++)));
		return id;
	}

	/**
	 * Returns whether or not the class or method
	 * with the specified name is (in the process
	 * of being) instrumented.
	 *
	 * @param name class name or method signature
	 * @return true or false
	 */
	public boolean isInstrumented(String name) {
		if (idMapping.get(name) == null)
			return false;
		else
			return true;
	}

	/**
	 * For testing purposes. Instruments the specified classes and places them
	 * in the current directory.
	 *
	 * @param args[] filenames of classes to instrument
	 */
	public static void main(String args[]) {

		ProbeInserter p = new ProbeInserter(args);
		p.instrument(System.getProperty("user.dir"));
	}

} // ProbeInserter
