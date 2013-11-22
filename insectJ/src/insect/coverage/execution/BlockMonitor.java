package insect.coverage.execution;

import insect.ConfigFile;
import insect.Debug;
import insect.ant.TestResult;
import it.unimi.dsi.fastutil.io.BinIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Provides methods for instrumented programs to report coverage of basic block
 * at runtime.
 * 
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class BlockMonitor {

	public static boolean initialized;

	// array of all basic blocks
	private static long[] blocks;

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
		// Use default sequence or date as test case name
		init(null);
	}

	/**
	 * Initializes this monitoring class if not already initialized.
	 * 
	 * @param execName
	 *            specified test case name
	 */
	public static synchronized void init(String execName) {
		if (initialized == false) {
			initialized = true;
			insect.coverage.execution.CounterMonitor.counter.increment();

			blocks = new long[insect.InsectValues.blockSize];

			Debug.println("*** BlockMonitor init", 5);

			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					quit();
				}
			}));
		}
	}

	/**
	 * Method for a basic block probe to report coverage.
	 * 
	 * @param index
	 *            index of basic block
	 */
	public static void report(int index) {
		blocks[index]++;
	}

	/**
	 * Outputs basic block coverage information to a file named
	 * "insect.blockhits". If profiling is enabled, a file named
	 * "insect.pblockhits" is also output. Invoked on VM shutdown.
	 * 
	 */
	public static synchronized void quit() {

		if (blocks == null)
			return;

		CounterMonitor.shutdown = true;

		insect.coverage.execution.CounterMonitor.counter.decrement();
		blocks = null;
		initialized = false;
		Debug.println("*** BlockMonitor quit", 5);

	}

	/**
	 * Reset the block coverage data
	 */
	public static synchronized void resetBlockCoverage() {
		blocks = new long[insect.InsectValues.blockSize];
	}
} // BlockMonitor
