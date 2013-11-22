package insect.coverage.execution;

import insect.ConfigFile;
import insect.Debug;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides methods for instrumented programs to report coverage of basic block
 * at runtime.
 * 
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class CopyOfBlockMonitor {

	public static boolean initialized;

	// array of all basic blocks
	private static long[] blocks;

	// block coverage matrix
	private static ConcurrentHashMap<Integer, HashSet<String>> blockCoverage;

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

			blockCoverage = new ConcurrentHashMap<Integer, HashSet<String>>(insect.InsectValues.blockSize);

			/**
			 * Unnecessary to init monitor, since we don't need to save coverage
			 * files
			 */
			 if(execName == null){
			 InitMonitor.init();
			 }else{
			 InitMonitor.init(execName);
			 }

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
//			for (int i = 0; i < blocks.length; i++) {
//				if (blocks[i] > 0) {
//					HashSet<String> coveredTests = blockCoverage.get(i);
//					if (coveredTests == null)
//						coveredTests = new HashSet<String>();
//
//					coveredTests.add(insect.InsectValues.execDir);
//					blockCoverage.put(i, coveredTests);
//				}
//			}

			// Only serialize the block coverage matrix to a file. Unnecessary
			// to save each test case coverage
			// Read and merge existing coverage first
			String covDataFile = ConfigFile.getProperty("INSTRUMENTED")
					+ File.separator + insect.InsectValues.progName
					+ File.separator + "blockCoverage.dat";
			File file = new File(covDataFile);
			if(file.exists()){
				FileInputStream fileIn = new FileInputStream(covDataFile);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				ConcurrentHashMap<Integer, HashSet<String>> existedCov = (ConcurrentHashMap<Integer, HashSet<String>>) in
						.readObject();
				in.close();
				fileIn.close();
				
				if (existedCov != null) {
					Iterator<Entry<Integer, HashSet<String>>> it1 = existedCov
							.entrySet().iterator();
					while (it1.hasNext()) {
						Entry<Integer, HashSet<String>> entry = it1.next();
						HashSet<String> newTests = blockCoverage
								.get(entry.getKey());
						if (newTests == null) {
							blockCoverage.put(entry.getKey(), entry.getValue());
						} else {
							blockCoverage.put(entry.getKey(), mergeHashSets(entry.getValue(), newTests));
						}
					}
				}
			}
			
			
			// Serialize merged block coverage
			FileOutputStream fileOut = new FileOutputStream(covDataFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(blockCoverage);
			out.close();
			fileOut.close();

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

		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			insect.coverage.execution.CounterMonitor.counter.decrement();
			blocks = null;
			initialized = false;
			blockCoverage = null;
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
	 */
	public static synchronized void saveBlockCoverage(String execName) {
		if (blocks == null || blockCoverage == null)
			return;

		// try {
		// Only append the coverage data for the current test to the coverage
		// matrix
		HashSet<String> coveredTests;
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] > 0) {
				coveredTests = blockCoverage.get(i);
				if (coveredTests == null){
					coveredTests = new HashSet<String>(300);
					coveredTests.add(execName);
					blockCoverage.put(i, coveredTests);
				}else{
					coveredTests.add(execName);
				}
				
				coveredTests = null;
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
	
	public static HashSet<String> mergeHashSets(HashSet<String> set1, HashSet<String> set2){
		if(set1.size() < set2.size()){
			Iterator<String> it = set1.iterator();
			while(it.hasNext()){
				set2.add(it.next());
			}
			return set2;
		}else{
			Iterator<String> it = set2.iterator();
			while(it.hasNext()){
				set1.add(it.next());
			}
			return set1;
		}
	}

} // BlockMonitor
