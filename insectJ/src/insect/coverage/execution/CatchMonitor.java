package insect.coverage.execution;

import insect.Debug;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Provides methods for instrumented programs to report
 * exceptions caught during runtime.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class CatchMonitor {

	//file to output catch information
	private static PrintWriter outFile;

	public static boolean initialized;

	// Run init when class is loaded
	static {
		initialized = false;
		init();
	}

	/**
	 * Initializes this monitoring class if not already initialized.
	 *
	 */
	public static synchronized void init() {
		//Use default sequence or date as test case name
		init(null);
	}
	
	/**
	 * Initializes this monitoring class if not already initialized.
	 * @param execName specified test case name
	 */
	public static synchronized void init(String execName) {

		if (initialized == false) {
			
			initialized = true;
			insect.coverage.execution.CounterMonitor.counter.increment();
			if(execName == null){
				InitMonitor.init();
			}else{
				InitMonitor.init(execName);
			}

			try {
				outFile =
					new PrintWriter(
						new FileWriter(
							insect.InsectValues.execDir
								+ java.io.File.separator
								+ "insect.caught"));
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			Debug.println("*** CatchMonitor init", 5);

			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					quit();
				}
			}));
		}
	}

	/**
	 * Method to report an exception being caught.
	 *
	 * @param e the exception being caught
	 * @param index id of class
	 * @param lineNumber source line number of catch block
	 */
	public static void report(Throwable t, int index, int lineNumber) {
		outFile.println(
			lineNumber + ":" + index + ":" + t.getClass().getName());
	}

	/**
	 * Stops monitoring.
	 *
	 */
	public static synchronized void quit() {
		if (outFile == null) {
			return;
		}

		CounterMonitor.shutdown = true;

		outFile.close();
		insect.coverage.execution.CounterMonitor.counter.decrement();
		outFile = null;
		initialized = false;
		Debug.println("*** CatchMonitor quit", 5);
	}

} // CatchMonitor
