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

	// block coverage matrix
	private static HashMap<Integer, HashSet<String>> blockCoverage;

	// record whole coverage
	private static HashMap<String, TestResult> totalTests;
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

			blockCoverage = new HashMap<Integer, HashSet<String>>(
					insect.InsectValues.blockSize);
			String execDir = ConfigFile.getProperty("INSTRUMENTED")
					+ File.separator + insect.InsectValues.progName
					+ File.separator;
			covDataFile = execDir + "blockCoverage.dat";
			covSeqFile = execDir + "blockCoverage.seq";

			totalTests = new  HashMap<String, TestResult>();

			/**
			 * Unnecessary to init monitor, since we don't need to save coverage
			 * files
			 */
			// if(execName == null){
			// InitMonitor.init();
			// }else{
			// InitMonitor.init(execName);
			// }

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

		if (blocks == null || blockCoverage == null)
			return;

		CounterMonitor.shutdown = true;

		try {
			serializeBlockCoverage();

			File totalTestsFile = new File(ConfigFile.getProperty("INSTRUMENTED")
					+ File.separator + insect.InsectValues.progName
					+ File.separator + "totalTests.dat");
			if(totalTestsFile.exists()){
				Object object = BinIO.loadObject(totalTestsFile);
				if(object != null){
					HashMap<String, TestResult> oldTests = (HashMap<String, TestResult>) object;
					Iterator<Entry<String, TestResult>> it = oldTests.entrySet().iterator();
					while(it.hasNext()){
						Entry<String, TestResult> entry = it.next();
						totalTests.put(entry.getKey(), entry.getValue());
					}
					BinIO.storeObject(totalTests, totalTestsFile);
				}
			}else{
				BinIO.storeObject(totalTests, totalTestsFile);
			}
		
			

			// output "blockhits" file
			// DataOutputStream hitFile =
			// new DataOutputStream(
			// new FileOutputStream(
			// insect.InsectValues.execDir
			// + java.io.File.separator
			// + "insect.blockhits"));
			// for (int i = 0; i < blocks.length; i++) {
			// if (blocks[i] > 0)
			// hitFile.writeBoolean(true);
			// else
			// hitFile.writeBoolean(false);
			// }
			// hitFile.close();
			//
			// //if profiling is requested, output "pblockhits" as well
			// if (insect.InsectValues.profile) {
			// hitFile =
			// new DataOutputStream(
			// new FileOutputStream(
			// insect.InsectValues.execDir
			// + java.io.File.separator
			// + "insect.pblockhits"));
			// for (int i = 0; i < blocks.length; i++)
			// hitFile.writeLong(blocks[i]);
			// }
			// hitFile.close();

		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			insect.coverage.execution.CounterMonitor.counter.decrement();
			blocks = null;
			initialized = false;
			blockCoverage = null;
			covDataFile = null;
			covSeqFile = null;
			totalTests = null;
			Debug.println("*** BlockMonitor quit", 5);
		}
	}

	/**
	 * Reset the block coverage data
	 */
	public static synchronized void resetBlockCoverage() {
		blocks = new long[insect.InsectValues.blockSize];
	}

	/**
	 * Only save block coverage data to the specified execution directory
	 * 
	 * @param execPath
	 *            the execution directory name
	 * @time running time for test execName (ms)
	 */
	public static synchronized void saveBlockCoverage(String execName, TestResult result) {
		if (blocks == null || blockCoverage == null)
			return;
		
		totalTests.put(execName, result);

		if ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime()
				.totalMemory()) < 80000000) {
			serializeBlockCoverage();
		}

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] > 0) {
				HashSet<String> coveredTests = blockCoverage.get(i);
				if (coveredTests == null) {
					coveredTests = new HashSet<String>();
					blockCoverage.put(i, coveredTests);
				}
				coveredTests.add(execName);

			}
		}

		resetBlockCoverage();

		// //output "blockhits" file
		// DataOutputStream hitFile =
		// new DataOutputStream(
		// new FileOutputStream(
		// execPath
		// + java.io.File.separator
		// + "insect.blockhits"));
		// for (int i = 0; i < blocks.length; i++) {
		// if (blocks[i] > 0)
		// hitFile.writeBoolean(true);
		// else
		// hitFile.writeBoolean(false);
		// }
		// hitFile.close();
		//
		// //if profiling is requested, output "pblockhits" as well
		// if (insect.InsectValues.profile) {
		// hitFile =
		// new DataOutputStream(
		// new FileOutputStream(
		// execPath
		// + java.io.File.separator
		// + "insect.pblockhits"));
		// for (int i = 0; i < blocks.length; i++)
		// hitFile.writeLong(blocks[i]);
		// }
		// hitFile.close();
		//
		// resetBlockCoverage();
		// }
		// catch (IOException ioe) {
		// ioe.printStackTrace();
		// return;
		// }
	}

	public static synchronized void serializeBlockCoverage() {
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
			BinIO.storeObject(blockCoverage, covDataFile + val);

			Object[] values =  blockCoverage.values()
					.toArray();
			for (int i = 0; i < values.length; i++)
				values[i] = null;
			

			blockCoverage.clear();
			Runtime.getRuntime().gc();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

} // BlockMonitor
