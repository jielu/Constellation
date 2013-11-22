package insect.coverage.execution;

import insect.ConfigFile;
import jaba.main.ResourceFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Executes an instrumented program with the given arguments within a single
 * instance of the VM.
 * 
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class Executor {

	// stored here for easy access
	private static String dirSep = File.separator;
	private static String pathSep = File.pathSeparator;

	// insect properties
	private File instrDir;

	// classpaths specified in resource file
	private String[] rfClassPathNames;

	/**
	 * Creates a new Executor for the program specified in the given resource
	 * file.
	 * 
	 * @param rcFile
	 *            file path of resource file
	 */
	public Executor(String rcFile) {

		ResourceFile rf = new ResourceFile(rcFile);

		rfClassPathNames = rf.getClassPaths();

		// fix relative classpaths
		String rfDir = (new File(rcFile)).getParent();
		for (int i = 0; i < rfClassPathNames.length; i++) {
			if (!(new File(rfClassPathNames[i])).isAbsolute())
				rfClassPathNames[i] = rfDir + rfClassPathNames[i];
		}

		// get directory information from config file
		instrDir = new File(ConfigFile.getProperty("INSTRUMENTED") + dirSep
				+ rf.getProgramName());

		if (!instrDir.exists())
			System.err.println("Error: Could not locate instrumented program.");
	}

	/**
	 * Method to actually execute the instrumented program.
	 * 
	 * @param execID
	 *            identifier for this execution
	 * @param classPathNames
	 *            array of additional classpath names
	 * @param main
	 *            fully qualified name of main class
	 * @param args
	 *            arguments to the main class
	 * @return true if execution seemed to have succeeded, otherwise false
	 */
	public boolean execute(String execID, String[] classPathNames, String main,
			String args[]) {

		URL[] classPaths;
		int numCPs = 1;

		if (classPathNames != null)
			numCPs += classPathNames.length;

		// if (rfClassPathNames != null)
		// numCPs += rfClassPathNames.length;

		classPaths = new URL[numCPs];

		// create classloader with all necessary classpaths
		try {
			int i = 0, j;

			classPaths[0] = instrDir.toURL();

			if (classPathNames != null) {
				for (i = 0; i < classPathNames.length; i++)
					classPaths[i + 1] = (new File(classPathNames[i])).toURL();
			}
			j = i;

			// Modified by Jie Lu, 2/1/2013
			// Only added the instrumented program path to class path.Do not add
			// the class path defined in rc file, in order for conflict
			// if (rfClassPathNames != null) {
			// for (i = 0; i < rfClassPathNames.length; i++, j++)
			// classPaths[j + 1] = (new File(rfClassPathNames[i])).toURL();
			// }

		} catch (MalformedURLException mue) {
			System.err.println("Unable to load classpaths");
			return false;
		}

		ExecClassLoader classLoader = new ExecClassLoader(classPaths);
		// load main class and set parameter to execute main method
		Class mainClass;
		try {
			mainClass = Class.forName(main, false, classLoader);
		} catch (ClassNotFoundException cnfe) {
			System.err.println("Could not locate class " + main);
			return false;
		}

		// build argument array
		Object[] mainArgs = new Object[1];
		if (args == null)
			mainArgs[0] = new String[0];
		else {
			mainArgs[0] = new String[args.length];
			for (int i = 0; i < args.length; i++)
				((String[]) mainArgs[0])[i] = args[i];
		}

		// create execution directory
		String execPath = instrDir.getPath() + dirSep
				+ execID.replaceAll(".xd" + dirSep, "") + ".xd";
		File execDir = new File(execPath);
		execDir.mkdirs();

		// set field values of InsectValues classes
		try {

			Class valClass = classLoader.loadClass("insect.InsectValues");
			Field f = valClass.getDeclaredField("execDir");
			f.set(null, execPath);
			insect.InsectValues.execDir = execPath;

			f = valClass.getDeclaredField("serial");
			insect.InsectValues.serial = (String) f.get(null);

			String prop;
			if (((prop = ConfigFile.getProperty("BLOCKCOVERAGE")) != null)
					&& (prop.toUpperCase().equals("TRUE"))) {
				f = valClass.getDeclaredField("blockSize");
				insect.InsectValues.blockSize = f.getInt(null);
			}
			if (((prop = ConfigFile.getProperty("BLOCKCOVERAGE")) != null)
					&& (prop.toUpperCase().equals("TRUE"))) {
				f = valClass.getDeclaredField("branchSize");
				insect.InsectValues.branchSize = f.getInt(null);
			}
			f = valClass.getDeclaredField("profile");
			insect.InsectValues.profile = f.getBoolean(null);

		} catch (ClassNotFoundException cnfe) {
			System.err.println("Error accessing InsectValues");
		} catch (NoSuchFieldException nsfe) {
			System.err.println("Error accessing InsectValues");
		} catch (IllegalAccessException iae) {
			System.err.println("Error accessing InsectValues");
		}

		/*
		 * Execute through the ExecThread. This is needed so that the
		 * SecurityException indicating an attempt to exit the VM can be caught
		 * if it occurs in a multithreaded application
		 */
		Thread x = new Thread(new ExecThreadGroup("InsECT"), new ExecThread(
				mainClass, mainArgs));

		initMonitors();
		
		// Record runtime for each test execution
		try {
			SimpleDateFormat dfs = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");
			java.util.Date begin = dfs.parse(dfs.format(new Date()).toString());

			x.start();
			try {
				x.join();
			} catch (InterruptedException ie) {
			}

			java.util.Date end = dfs.parse(dfs.format(new Date()).toString());
			long between = end.getTime() - begin.getTime();
			
			quitMonitors();
			
			//Write the runtime to the file insect.runtime
			this.printRuntime(execPath, between);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Manually inits all monitors.
	 * 
	 */
	public void initMonitors() {
		BlockMonitor.init();
		BranchMonitor.init();
		CallMonitor.init();
		ThrowMonitor.init();
		CatchMonitor.init();
	}

	/**
	 * Manually quits all monitors.
	 * 
	 */
	public void quitMonitors() {
		BlockMonitor.quit();
		BranchMonitor.quit();
		CallMonitor.quit();
		ThrowMonitor.quit();
		CatchMonitor.quit();
	}

	/**
	 * Wrapper to the System.exit(0) method. In multithreaded applications (such
	 * as those with Swing/AWT) the created threads will remain alive even after
	 * execution is complete, and the "main" thread dies. Therefore, this method
	 * must be called when the invoking program is ready to exit the VM.
	 * 
	 */
	public void exitVM() {
		System.exit(0);
	}

	/**
	 * Main method for command line usage.
	 * 
	 * @param args
	 *            [] command line arguments
	 */
	public static void main(String args[]) {

		if (args.length < 3)
			System.err
					.println("Usage: java Executor <resource file> <exec id> "
							+ "[-cp:<classpaths>] <main class> [<args>]");
		else {
			Executor ex = new Executor(args[0]);
			String cp[] = null;
			String a[] = null;
			int argIndex = 3;

			if ((args[2].length() > 4)
					&& (args[2].substring(0, 4).equals("-cp:"))) {

				StringTokenizer st = new StringTokenizer(args[2].substring(4),
						":");
				int numCPs = st.countTokens();
				cp = new String[numCPs];
				for (int i = 0; i < numCPs; i++)
					cp[i] = st.nextToken();
				argIndex++;
			}
			if (args.length > argIndex) {
				int numArgs = args.length - argIndex;
				a = new String[numArgs];
				for (int i = 0; i < numArgs; i++)
					a[i] = args[argIndex + i];
			}

			ex.execute(args[1], cp, args[argIndex - 1], a);
			ex.exitVM();
		}
	}

	private void printRuntime(String execPath, long time) {
		PrintWriter pw;
		try {
			double seconds = (double) time;
			seconds = seconds / 1000;
			DecimalFormat df = new DecimalFormat("0.000");
			pw = new PrintWriter(new FileWriter(execPath + this.dirSep
					+ "insect.runtime"));
			pw.println(df.format(seconds) + "s");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

} // Executor
