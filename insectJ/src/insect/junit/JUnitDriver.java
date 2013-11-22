package insect.junit;

import insect.ConfigFile;
import insect.coverage.execution.ExecClassLoader;
import insect.coverage.instrumentation.Instrumentor;
import jaba.constants.AccessLevel;
import jaba.main.JABADriver;
import jaba.main.Options;
import jaba.main.ResourceFile;
//import jaba.sym.Class;
import jaba.sym.Interface;
import jaba.sym.Method;
import jaba.sym.Program;
import jaba.tools.local.Factory;

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
import java.util.Enumeration;
import java.util.HashSet;
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
public class JUnitDriver extends JABADriver {

	// stored here for easy access
	private static final String dirSep = File.separator;

	private String classListPath;
	private File rcFile;
	private File instrDir;
	private String[] classPathNames;
	private jaba.sym.Class[] testClasses;
	private Program p;

	private Options opt = null;

	/**
	 * Empty default constructor.
	 * 
	 */
	public JUnitDriver() {
	}

	/**
	 * Creates a new JUnitDriver from the provided JABA resource file.
	 * 
	 * @param rcPath
	 *            path of a JABA resource file
	 * @param classListPath
	 *            path of optional classlist
	 */
	public JUnitDriver(String rcPath, String classListPath) {

		this.classListPath = classListPath;
		processSystem(rcPath);
	}

	/**
	 * JABADriver init method.
	 * 
	 * @param argv
	 *            [] a <code>String</code> value
	 */
	public void init(String argv[]) {

		if ((argv.length < 1) || (argv.length > 2)) {
			System.err
					.println("Usage: insect.junit.JUnitDriver <resource file> [<classlist>]");
			System.exit(1);
		}

		String rcPath = argv[0];

		if (argv.length == 2) {
			this.classListPath = argv[1];
		}

		processSystem(rcPath);
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
	 * Analyses the system to determine system classes and JUnit test case
	 * classes.
	 * 
	 * @param rcPath
	 *            a <code>String</code> value
	 */
	private void processSystem(String rcPath) {

		opt = Factory.getOptions();
		opt.setCreateLVT(true);

		// load names from classlist
		HashSet selectClasses = null;
		if (classListPath != null) {
			selectClasses = new HashSet();
			String curClass;
			try {
				BufferedReader clist = new BufferedReader(new FileReader(
						classListPath));
				while ((curClass = clist.readLine()) != null)
					selectClasses.add(curClass.trim());
				clist.close();
			} catch (IOException ioe) {
				System.err.println("Error reading class list");
				return;
			}
		}

		// determine TestCase classes
		ResourceFile rf = new ResourceFile(rcPath);
		p = Factory.getProgram(rf, opt);
		testClasses = getTestClasses(p, selectClasses);

		// put class names into hashset for quick lookup
		HashSet testClassSet = new HashSet();
		for (int i = 0; i < testClasses.length; i++)
			testClassSet.add(testClasses[i].getName().replace('/', '.'));

		// create a new temporary resource file containing all classes
		// that are not test cases

		String name = rf.getProgramName();
		classPathNames = rf.getClassPaths();
		String[] files = rf.getClassFiles();
		String pathString = "";

		for (int i = 0; i < classPathNames.length; i++) {
			pathString += classPathNames[i];
			if (i < (classPathNames.length - 1))
				pathString += File.pathSeparator;
		}

		try {
			rcFile = File.createTempFile("tmp", "jrf");
			rcFile.deleteOnExit();
			PrintWriter outFile = new PrintWriter(new FileWriter(rcFile));
			outFile.println("ProgramName = " + name);
			outFile.println("ClassPath = " + pathString);
			outFile.println("ClassFiles = \\");
			for (int i = 0; i < files.length; i++) {
				if (!(testClassSet.contains(files[i]))) {
					outFile.print(files[i]);
					if (i < files.length - 1)
						outFile.println(" \\");
				}
			}
			outFile.close();
		} catch (IOException ie) {
			System.out.println("Error creating temporary resource file");
		}

		instrDir = new File(ConfigFile.getProperty("INSTRUMENTED") + dirSep
				+ name);

	}

	/**
	 * Instruments the program classes and executes all TestCases.
	 * 
	 * @return all resulting TestFailures (failures and errors) from the
	 *         executed tests
	 */
	public Vector instrumentAndTest() {
		instrumentNonTestClasses();
		return executeTests();
	}

	/**
	 * Instruments the program classes.
	 * 
	 */
	public void instrumentNonTestClasses() {
		Instrumentor ins = new Instrumentor(rcFile.getPath(), classListPath);
		ins.instrument();
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
		URL[] classPaths = classPaths = new URL[classPathNames.length + 1];
		try {

			classPaths[0] = instrDir.toURL();

			for (int i = 0; i < classPathNames.length; i++)
				classPaths[i + 1] = (new File(classPathNames[i])).toURL();
		} catch (MalformedURLException mue) {
			System.err.println("Unable to load classpaths");
			return null;
		}
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

		// for all TestCase classes
		for (int i = 0; i < testClasses.length; i++) {

			String className = testClasses[i].getName();
			Class testClass = null;
			Object testObj = null;

			// use reflection to load methods
			java.lang.reflect.Method nameMethod = null;
			java.lang.reflect.Method runMethod = null;
			try {
				testClass = classLoader.loadClass(className);
				Constructor testConstructor = testClass.getConstructor(null);
				testObj = testConstructor.newInstance(null);
				nameMethod = testClass.getMethod("setName",
						new Class[] { String.class });
				runMethod = testClass.getMethod("run", null);
			} catch (ClassNotFoundException cnfe) {
				System.err.println("Class not found: " + className);
				continue;
			} catch (InstantiationException ie) {
				System.out.println("Unable to instantiate class: " + className);
				continue;
			} catch (IllegalAccessException ie) {
				System.out.println("Unable to instantiate class: " + className);
				continue;
			} catch (InvocationTargetException ie) {
				System.out.println("Unable to instantiate class: " + className);
				continue;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			// for each test method in the class
			Method[] allMethods = testClasses[i].getMethods();
			for (int j = 0; j < allMethods.length; j++) {
				if (allMethods[j].getName().startsWith("test")
						&& allMethods[j].getAccessLevel() == AccessLevel.A_PUBLIC) {

					// stop any previously running monitors
					insect.coverage.execution.CallMonitor.quit();
					insect.coverage.execution.BlockMonitor.quit();
					insect.coverage.execution.BranchMonitor.quit();
					insect.coverage.execution.ThrowMonitor.quit();
					insect.coverage.execution.CatchMonitor.quit();

					String methodName = allMethods[j].getName();

					// create execution directory
					String execPath = instrDir.getPath() + dirSep + className
							+ " - " + methodName + ".xd";
					File execDir = new File(execPath);
					execDir.mkdirs();

					// set execution directory in InsectValues classes
					try {

						Field f = valClass.getDeclaredField("execDir");
						f.set(null, execPath);
						insect.InsectValues.execDir = execPath;
					} catch (Exception e) {
						System.err.println("Error accessing InsectValues");
						return null;
					}

					// actually run test
					Object[] nameArg = new Object[] { methodName };
					try {

						// set name of method to run
						nameMethod.invoke(testObj, nameArg);
						// run the method and save result
						Object result = runMethod.invoke(testObj, null);

						// store all errors/failures in error vector
						Enumeration errs = (Enumeration) errorMethod.invoke(
								result, null);
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
							System.out
									.println("Exception in executing test case");
							t.printStackTrace();
						} else
							ite.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return errVector;
	}

	/**
	 * Determines which of the classes specified in the resource file contain
	 * test cases. A class is determined to be a test class if it implement
	 * junit.framework.Test.
	 * 
	 * @param prog
	 *            a JABA Program
	 * @param selectClasses
	 *            HashSet containing class list
	 * @return JABA Classes containing test cases
	 */
	private jaba.sym.Class[] getTestClasses(Program prog, HashSet selectClasses) {

		Vector testClasses = new Vector();
		jaba.sym.Class[] allClasses = prog.getClasses();

		for (int i = 0; i < allClasses.length; i++) {
			if (implementsInterface(allClasses[i].getSuperClass(),
					"junit.framework.Test")) {
				if (selectClasses != null) {
					if (selectClasses.contains(allClasses[i].getName().replace(
							'/', '.')))
						testClasses.add(allClasses[i]);
				} else
					testClasses.add(allClasses[i]);
			}
		}

		return (jaba.sym.Class[]) testClasses.toArray(new jaba.sym.Class[0]);
	}

	/**
	 * Determines the test cases within the specified class.
	 * 
	 * @param junitClass
	 *            a JABA Class
	 * @return array of test case JABA Methods
	 */
	private Method[] getTestMethods(jaba.sym.Class junitClass) {
		Method[] allMethods = junitClass.getMethods();
		Vector v = new Vector();
		for (int i = 0; i < allMethods.length; i++) {
			if (allMethods[i].getName().startsWith("test")
					&& allMethods[i].getAccessLevel() == AccessLevel.A_PUBLIC) {
				v.add(allMethods[i]);
			}
		}
		return (Method[]) v.toArray(new Method[0]);
	}

	/**
	 * Determines if a class or any of its superclass implements the specified
	 * interface.
	 * 
	 * @param cls
	 *            a JABA class
	 * @param infaceName
	 *            interface name
	 * @return true if the class implements the interface, otherwise false
	 */
	private boolean implementsInterface(jaba.sym.Class cls, String infaceName) {
		if (cls == null) {
			return false;
		}

		Interface[] infaces = cls.getInterfaces();
		for (int i = 0; i < infaces.length; i++) {
			// System.out.println(infaces[i].getName());
			if (infaces[i].getName().replace('/', '.').equals(infaceName)) {
				return true;
			}
		}

		jaba.sym.Class superClass = cls.getSuperClass();
		if (superClass == null) {
			return false;
		} else if (superClass.getName().replace('/', '.')
				.equals("java.lang.Object")) {
			return false;
		} else {
			return implementsInterface(superClass, infaceName);
		}
	}

	public void instrumentAndGetTests() {
		this.instrumentNonTestClasses();
		getJunitTests(p);

	}

	public Method[] getJunitTests(Program prog) {
		Vector junitTestMethods = new Vector();
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(
					rcFile.getAbsolutePath() + ".tests"));
			jaba.sym.Class[] allOldClasses = prog.getClasses();

			for (int i = 0; i < allOldClasses.length; i++) {
				// If a class implements junit.framework.Test,
				// it's a TestCase or a TestSuite (or a TestDecorator)
				if (implementsInterface(allOldClasses[i].getSuperClass(),
						"junit.framework.Test")) {
					Method[] allMethods = allOldClasses[i].getMethods();
					for (int j = 0; j < allMethods.length; j++) {
						if (allMethods[j].getName().startsWith("test")
								&& allMethods[j].getAccessLevel() == AccessLevel.A_PUBLIC) {
							pw.println(allMethods[j].getContainingType()
									.getName()
									+ "."
									+ allMethods[j].getSignature()
											.replace("(", "").replace(")", ""));
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Method[]) junitTestMethods.toArray(new Method[0]);
	}

	/*
	 * Main method to instrument a program and execute its JUnit test cases.
	 * 
	 * @param args[] path of a JABA resource file
	 * 
	 * 
	 * public static void main(String args[]) {
	 * 
	 * if ((args.length < 1)||(args.length > 2)) { System.err.println(
	 * "Usage: insect.junit.JUnitDriver <resource file> [<classlist>]");
	 * System.exit(1); }
	 * 
	 * 
	 * JUnitDriver d = null; if (args.length == 1){ d = new JUnitDriver(args[0],
	 * null); } classlist specified else if (args.length == 2){ d = new
	 * JUnitDriver(args[0], args[1]); }
	 * 
	 * Vector v = d.instrumentAndTest();
	 * 
	 * print all errors and failures if ((v != null) && (v.size() > 0)) {
	 * System.out.println("Errors/Failures In Test Cases:"); int vs = v.size();
	 * for (int i = 0; i < vs; i++) System.out.println(v.elementAt(i)); } }
	 */
} // JUnitDriver
