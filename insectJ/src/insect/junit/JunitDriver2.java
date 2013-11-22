package insect.junit;

import insect.coverage.execution.BlockMonitor;
import insect.coverage.execution.BranchMonitor;
import insect.coverage.execution.CallMonitor;
import insect.coverage.execution.CatchMonitor;
import insect.coverage.execution.ExecClassLoader;
import insect.coverage.execution.ThrowMonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

/**
 * JUnitDriver.java
 * 
 * 
 * Provided a JABA resource file consisting of program classes as well as JUnit
 * TestCase classes for that program, this driver utiltizes InsECT to instrument
 * the program classes and execute the JUnit Test Cases. Classes that implement
 * junit.framework.Test are assumed to be the test case classes.
 * 
 * Created: Sat Nov 09 14:58:11 2002
 * 
 * @author <a href="mailto:anil@resnet.gatech.edu">Anil Chawla</a>
 */
public class JunitDriver2 {

	// stored here for easy access
	private static final String dirSep = File.separator;
	private String tsFilePath;
	private String tsPath;
	private String instrProgramPath;
	private String execPath;
	private String classPathStr;

	/**
	 * Empty default constructor.
	 * 
	 */
	public JunitDriver2() {
	}

	/**
	 * Creates a new JUnitDriver from the provided JABA resource file.
	 * 
	 * @param rcPath
	 *            path of a JABA resource file
	 * @param classListPath
	 *            path of optional classlist
	 */
	public JunitDriver2(String tsFilePath, String tsPath, String instProgPath,
			String classPaths) {
		this.tsFilePath = tsFilePath;
		this.tsPath = tsPath;
		this.instrProgramPath = instProgPath.trim();
		this.classPathStr = classPaths;
	}

	/**
	 * Instruments and executes the system. Reports any errors or failures.
	 * 
	 */
	public void run() {
		// Vector v = instrumentAndTest();
		Vector v = executeTests();

		// print all errors and failures
		if ((v != null) && (v.size() > 0)) {
			System.out.println("Errors/Failures In Test Cases:");
			int vs = v.size();
			for (int i = 0; i < vs; i++)
				System.out.println(v.elementAt(i));
		}
	}

	/**
	 * Executes all test cases.
	 * 
	 * @return all resulting TestFailures (failures and errors) from the
	 *         executed tests
	 */
	public Vector executeTests() {

		Vector errVector = new Vector();

		// create classloader with all necessary classpaths
		String[] results = null;
		if (classPathStr != null) {
			results = classPathStr.split(":");
		}
		URL[] classPaths = null;
		if (results != null) {
			classPaths = new URL[results.length + 2];
		} else {
			classPaths = new URL[2];
		}

		try {
			classPaths[0] = (new File(this.tsPath)).toURL();
			classPaths[1] = (new File(this.instrProgramPath)).toURL();
			if (results != null) {
				for (int i = 0; i < results.length; i++) {
					classPaths[i + 2] = (new File(results[i])).toURL();
				}
			}

		} catch (MalformedURLException mue) {
			System.err.println("Unable to load classpaths");
			return null;
		}

		// for all TestCase classes and methods
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					this.tsFilePath));
			String curLine;
			long totalTime = 0;
			int totalTsNumber = 0;
			while ((curLine = reader.readLine()) != null) {
				if(curLine.trim().startsWith("#")){
					continue;
				}
				totalTsNumber ++;
				ExecClassLoader classLoader = new ExecClassLoader(classPaths);

				// set field values of InsectValues classes
				Class valClass;
				try {
					Field f;
					valClass = classLoader.loadClass("insect.InsectValues");
					f = valClass.getDeclaredField("blockSize");
					insect.InsectValues.blockSize = f.getInt(null);
					f = valClass.getDeclaredField("branchSize");
					insect.InsectValues.branchSize = f.getInt(null);
					f = valClass.getDeclaredField("serial");
					insect.InsectValues.serial = (String) f.get(null);
				} catch (Exception e) {
					System.err.println("Error accessing InsectValues");
					return null;
				}

				// load TestResult methods
				java.lang.reflect.Method errorMethod = null;
				java.lang.reflect.Method failureMethod = null;
				try {
					Class resultClass = classLoader
							.loadClass("junit.framework.TestResult");
					errorMethod = resultClass.getMethod("errors", null);
					failureMethod = resultClass.getMethod("failures", null);
				} catch (ClassNotFoundException cnfe) {
					System.err.println("Could not load TestResult");
					return null;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}

				String className = curLine.substring(0,
						curLine.lastIndexOf(".")).replace("/", ".");
				String methodName = curLine
						.substring(curLine.lastIndexOf(".") + 1);

				Class testClass = null;
				Object testObj = null;

				// use reflection to load methods
				java.lang.reflect.Method nameMethod = null;
				java.lang.reflect.Method runMethod = null;
				try {
					System.out.println("# Run tests: " + curLine);
					testClass = classLoader.loadClass(className);
					Constructor testConstructor = testClass
							.getConstructor(null);
					testObj = testConstructor.newInstance(null);
					nameMethod = testClass.getMethod("setName",
							new Class[] { String.class });
					runMethod = testClass.getMethod("run", null);
				} catch (ClassNotFoundException cnfe) {
					System.err.println("Class not found: " + className);
					continue;
				} catch (InstantiationException ie) {
					System.out.println("Unable to instantiate class: "
							+ className);
					continue;
				} catch (IllegalAccessException ie) {
					System.out.println("Unable to instantiate class: "
							+ className);
					continue;
				} catch (InvocationTargetException ie) {
					System.out.println("Unable to instantiate class: "
							+ className);
					continue;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}

				

				// create execution directory
				String execPath = this.instrProgramPath.trim() + dirSep + className
						+ "." + methodName + ".xd";
				File execDir = new File(execPath);
				execDir.mkdirs();
				this.execPath = execPath;

				// set execution directory in InsectValues classes
				try {
					Field f = valClass.getDeclaredField("execDir");
					f.set(null, execPath);
					insect.InsectValues.execDir = execPath;
				} catch (Exception e) {
					System.err.println("Error accessing InsectValues");
					return null;
				}
				
				initMonitor();

				// actually run test
				Object[] nameArg = new Object[] { methodName };
				try {
					SimpleDateFormat dfs = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss.SSS");
					java.util.Date begin = dfs.parse(dfs.format(new Date())
							.toString());

					// set name of method to run
					nameMethod.invoke(testObj, nameArg);
					// run the method and save result
					Object result = runMethod.invoke(testObj, null);

					java.util.Date end = dfs.parse(dfs.format(new Date())
							.toString());
					long between = end.getTime() - begin.getTime();
					totalTime += between;
					// Write runtime to each test execution
					printRuntime(between);

					// store all errors/failures in error vector
					Enumeration errs = (Enumeration) errorMethod.invoke(result,
							null);
					Enumeration fails = (Enumeration) failureMethod.invoke(
							result, null);
					while (errs.hasMoreElements())
						errVector.add(errs.nextElement());
					while (fails.hasMoreElements())
						errVector.add(fails.nextElement());
				}

				// if JUnit doesnt catch exception
				catch (InvocationTargetException ite) {
					Throwable t = ite.getCause();
					if (t != null) {
						System.out.println("Exception in executing test case");
						t.printStackTrace();
					} else
						ite.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				exitMonitor();

			}
			
			//Print total time and total test cases number
			printTotalInfo(totalTime, totalTsNumber);
			System.out.println("\n ============ Completed ================");
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return errVector;
	}

	private void initMonitor() {
		BlockMonitor.init();
		BranchMonitor.init();
		CallMonitor.init();
		ThrowMonitor.init();
		CatchMonitor.init();
	}

	private void exitMonitor() {
		BlockMonitor.quit();
		BranchMonitor.quit();
		CallMonitor.quit();
		ThrowMonitor.quit();
		CatchMonitor.quit();
	}

	private void printRuntime(long time) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(this.execPath + this.dirSep
					+ "insect.runtime"));
			pw.println(time + "ms");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printTotalInfo(long time, int totalNum){
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(this.instrProgramPath + File.separator + "insect.total"));
			pw.println(time + "ms");
			pw.println(totalNum);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out
					.println("JunitDriver2 <tsListFile> <tsPath> <instProgramPath> (<classPath>)");
			System.exit(1);
		}
		String classPath = null;
		if (args.length > 3) {
			classPath = args[3];
		}

		JunitDriver2 driver = new JunitDriver2(args[0], args[1], args[2],
				classPath);
//		JunitDriver2 driver = new JunitDriver2("/Users/jielu/Dropbox/workspace/ARG/ReTest/dejavoo/1.8.4.jrc.tests", 
//				"/Users/jielu/Dropbox/workspace/ARG/subjects/apache-ant-1.8.4/build/testcases/", 
//				"/Users/jielu/Dropbox/workspace/ARG/ReTest/dejavoo/.coverage_data/ant_1.8.4/", null);
		driver.run();
	}

}
