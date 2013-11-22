package insect.coverage.execution;

import insect.ConfigFile;
import insect.Debug;
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

/**
 * Provides methods for instrumented programs to report values evaluated in
 * conditional branches during runtime. These methods simulate the evaluation of
 * each condition in order to record branch coverage.
 * 
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class BranchMonitor {

	// array of all branch outcomes
	private static boolean[] branches;

	public static boolean initialized;

	// branch coverage matrix
	private static HashMap<Integer, HashSet<String>> branchCoverage;

	private static String covDataFile;
	private static String covSeqFile;

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
			branchCoverage = new HashMap<Integer, HashSet<String>>(
					insect.InsectValues.branchSize);
			
			String execDir = ConfigFile.getProperty("INSTRUMENTED")
					+ File.separator + insect.InsectValues.progName
					+ File.separator;
			covDataFile = execDir + "branchCoverage.dat";
			covSeqFile = execDir + "branchCoverage.seq";
		

//			if (execName == null) {
//				InitMonitor.init();
//			} else {
//				InitMonitor.init(execName);
//			}

			branches = new boolean[insect.InsectValues.branchSize];

			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					quit();
				}
			}));
		}
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param a1
	 *            an <code>Object</code> value
	 * @param a2
	 *            an <code>Object</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_acmpeq(Object a1, Object a2, int t) {
		if (a1 == a2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param a1
	 *            an <code>Object</code> value
	 * @param a2
	 *            an <code>Object</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_acmpne(Object a1, Object a2, int t) {
		if (a1 != a2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i1
	 *            an <code>int</code> value
	 * @param i2
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_icmpeq(int i1, int i2, int t) {
		if (i1 == i2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i1
	 *            an <code>int</code> value
	 * @param i2
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_icmpne(int i1, int i2, int t) {
		if (i1 != i2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i1
	 *            an <code>int</code> value
	 * @param i2
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_icmplt(int i1, int i2, int t) {
		if (i1 < i2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i1
	 *            an <code>int</code> value
	 * @param i2
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_icmpge(int i1, int i2, int t) {
		if (i1 >= i2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i1
	 *            an <code>int</code> value
	 * @param i2
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_icmpgt(int i1, int i2, int t) {
		if (i1 > i2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i1
	 *            an <code>int</code> value
	 * @param i2
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void if_icmple(int i1, int i2, int t) {
		if (i1 <= i2)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void ifeq(int i, int t) {
		if (i == 0)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void ifne(int i, int t) {
		if (i != 0)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void iflt(int i, int t) {
		if (i < 0)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void ifge(int i, int t) {
		if (i >= 0)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void ifgt(int i, int t) {
		if (i > 0)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void ifle(int i, int t) {
		if (i <= 0)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void ifnonnull(Object a, int t) {
		if (a != null)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a specific IF comparison.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @param t
	 *            id number of the true branch
	 */
	public static void ifnull(Object a, int t) {
		if (a == null)
			branches[t] = true;
		else
			branches[t + 1] = true;
	}

	/**
	 * Method to report a switch predicate.
	 * 
	 * @param s
	 *            an <code>int</code> value
	 * @param cases
	 *            String describing index to branch id pairing
	 */
	public static void select(int s, String cases) {

		int case_pos = cases.indexOf("(" + s + ":");
		if (case_pos < 0)
			case_pos = cases.indexOf("(D:");
		int id_pos = cases.indexOf(':', case_pos) + 1;
		String id = cases.substring(id_pos, cases.indexOf(')', id_pos));

		branches[Integer.parseInt(id)] = true;
	}

	/**
	 * Outputs branch outcome information to a file named "insect.branchhits".
	 * Invoked on VM shutdown.
	 * 
	 */
	public static synchronized void quit() {
		if (branches == null || branchCoverage == null)
			return;

		try {
			serializeBranchCoverage();
	
			


			// DataOutputStream hitFile =
			// new DataOutputStream(
			// new FileOutputStream(
			// insect.InsectValues.execDir
			// + java.io.File.separator
			// + "insect.branchhits"));
			// for (int i = 0; i < branches.length; i++) {
			// if (branches[i])
			// hitFile.writeBoolean(true);
			// else
			// hitFile.writeBoolean(false);
			// }
			//
			// hitFile.close();
		}  finally {
			insect.coverage.execution.CounterMonitor.counter.decrement();
			branches = null;
			initialized = false;
			branchCoverage = null;
			covDataFile = null;
			covSeqFile = null;
			Debug.println("*** BranchMonitor quit", 5);
		}
	}

	/**
	 * Reset the branch coverage data
	 */
	public static synchronized void resetBranchCoverage() {
		branches = new boolean[insect.InsectValues.branchSize];
	}

	/**
	 * Only save branch coverage data to the specified execution directory
	 * 
	 * @param execPath
	 *            the execution directory name
	 */
	public static synchronized void saveBranchCoverage(String execName) {
		if (branches == null || branchCoverage == null)
			return;
	
		if ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime()
				.totalMemory()) < 80000000) {
			serializeBranchCoverage();
		}

		for (int i = 0; i < branches.length; i++) {
			if (branches[i]) {
				HashSet<String> coveredTests = branchCoverage.get(i);
				if (coveredTests == null) {
					coveredTests = new HashSet<String>();
					branchCoverage.put(i, coveredTests);
				}
				coveredTests.add(execName);

			}
		}

		resetBranchCoverage();
		// try {
		// DataOutputStream hitFile =
		// new DataOutputStream(
		// new FileOutputStream(
		// execPath
		// + java.io.File.separator
		// + "insect.branchhits"));
		// for (int i = 0; i < branches.length; i++) {
		// if (branches[i])
		// hitFile.writeBoolean(true);
		// else
		// hitFile.writeBoolean(false);
		// }
		//
		// hitFile.close();
		// resetBranchCoverage();
		// }
		// catch (IOException ioe) {
		// ioe.printStackTrace();
		// return;
		// }
	}

	public static synchronized void serializeBranchCoverage() {
		int val = 1;
		try {
			DataInputStream inStream = new DataInputStream(new FileInputStream(
					covSeqFile));
			val = inStream.readInt();
			inStream.close();
		} catch (IOException ioe1) {
		}

		try {
			DataOutputStream outStream = new DataOutputStream(
					new FileOutputStream(covSeqFile));
			outStream.writeInt(val + 1);
			outStream.close();
		} catch (IOException ioe2) {
		}

		try {
			BinIO.storeObject(branchCoverage, covDataFile + val);

			Object[] values =  branchCoverage.values()
					.toArray();
			for (int i = 0; i < values.length; i++)
				values[i] = null;

			branchCoverage.clear();
			Runtime.getRuntime().gc();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

} // BranchMonitor
