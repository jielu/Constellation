package insect.coverage.execution;

import insect.Debug;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Provides methods for instrumented programs to report
 * the invocation of  methods during runtime.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class CallMonitor {

	public static boolean initialized;

	//set of all method calls
	private static Collection calls;

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

		//if this is the first class initializing this monitor
		if (initialized == false) {

			initialized = true;			
			insect.coverage.execution.CounterMonitor.counter.increment();
			if(execName == null){
				InitMonitor.init();
			}else{
				InitMonitor.init(execName);
			}

			Debug.println("*** CallMonitor init", 5);

			if (insect.InsectValues.profile)
				calls = new Bag();
			else
				calls = new HashSet();

			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					quit();
				}
			}));
		}
	}

	/**
	 * Method to report the invocation of a method whose 
	 * runtime object type is statically known.
	 *
	 * @param className class of object on which method was invoked
	 * @param lineNum source line number of method call
	 * @param callingID id of invoking method
	 * @param calledID id of invoked method
	*/
	public static void reportMono(String className,	int lineNum,
									int callingID, int calledID) {

		calls.add(lineNum + ":" + callingID + ":"
					 + calledID + ":" + className);
	}

	/**
	 * Method to report the invocation of a method whose
	 * runtime object type is determined dynamically.
	 *
	 * @param o object on which method was invoked
	 * @param lineNum source line number of method call
	 * @param callingID id of invoking method
	 * @param calledID id of invoked method
	*/
	public static void reportPoly(Object o, int lineNum,
								int callingID, int calledID) {
									
		calls.add(lineNum + ":"	+ callingID	+ ":"
					+ calledID + ":"+ o.getClass().getName());
	}

	/**
	 * Outputs the set of all method calls to a file named "insect.calls".
	 * If profiling is enabled, a file named "insect.pcalls" is also output.
	 * Invoked on VM shutdown.
	 *
	 */
	public static void quit() {

		if (calls == null)
			return;

		CounterMonitor.shutdown = true;

		Iterator it = calls.iterator();
		PrintWriter outFile;
		PrintWriter poutFile = null;
		boolean profile = insect.InsectValues.profile;
		try {
			outFile =
				new PrintWriter(
					new FileWriter(
						insect.InsectValues.execDir
							+ java.io.File.separator
							+ "insect.calls"));
			if (profile)
				poutFile =
					new PrintWriter(
						new FileWriter(
							insect.InsectValues.execDir
								+ java.io.File.separator
								+ "insect.pcalls"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			insect.coverage.execution.CounterMonitor.counter.decrement();
			return;
		}

		while (it.hasNext()) {
			Object next = it.next();
			outFile.println(next);
			if (profile)
				poutFile.println(next + " " + ((Bag) calls).countItem(next));
		}

		outFile.close();
		if (profile)
			poutFile.close();
		insect.coverage.execution.CounterMonitor.counter.decrement();
		calls = null;
		initialized = false;
		Debug.println("*** CallMonitor quit", 5);
	}
} // CallMonitor
