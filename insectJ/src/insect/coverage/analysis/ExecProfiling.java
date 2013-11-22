/*
 * Created on Sep 7, 2003
 */
package insect.coverage.analysis;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Provides profiling for a specific execution of an instrumented program.
 *
 * <br>
 * Note: For methods requiring a method signature
 * as an argument, the method signature must
 * be in the following format:
 * <br>	 
 * <class name>.<method name><method descriptor>
 * <br>
 * where <class name> is the fully qualified
 * name of the class of the method, <method name>
 * is simply the name of the method, and
 * <method descriptor> is as described in the
 * Java Virtual Machine Specification at:
 * <br>
 * http://java.sun.com/docs/books/vmspec/2nd-edition/html/ClassFile.doc.html#7035
 * 
 * @author Anil Chawla
 */
public class ExecProfiling {

	private Analysis parent;
	private String execDirPath;

	// Id information
	private HashMap classIDs;
	private HashMap blockClasses;

	// Cached execution count of basic blocks
	private long[] blocks;
	
	// Cached execution count of branches
	private long[] branches;

	// Cached method calls
	private HashMap callClasses;
	
	/**
	 * Creates a new Profiling object for the program contained
	 * in the specified resource file.
	 * 
	 * @param parent parent Profiling instance.
	 * @param execDirPath filepath of resource file.
	 */
	public ExecProfiling(Analysis parent, String execDirPath) {

		if (execDirPath.charAt(execDirPath.length() - 1) != File.separatorChar)
			execDirPath += File.separatorChar;

		this.parent = parent;
		this.execDirPath = execDirPath;
		this.classIDs = parent.getClassIDs();
		this.blockClasses = parent.getBlockClasses();
		
		// Load stored profile information
		loadCallSites();
		loadHitFiles();
	}
	
	/**
	 * Caches coverage from hit-vector files for fast access.
	 *
	 */
	private void loadHitFiles() {
		
		File blockFile = new File(execDirPath + "insect.pblockhits");
		if (blockFile.exists()) {
			blocks = readHitFile(blockFile);
		}
		
		File branchFile = new File(execDirPath + "insect.pbranchhits");			
		if (branchFile.exists()) {
			branches = readHitFile(branchFile);
		}
	}

	/**
	 * Read long values stored in a hit-vector file and returns an
	 * array containing the values.
	 * 
	 * @param hitFile hit-vector file.
	 * @return array of values.
	 */
	private long[] readHitFile(File hitFile) {
		
		int longsRead = 0;
		int availBytes = 0;
		
		long[] values = new long[0];
		
		try {
			
			DataInputStream hitStream =
				new DataInputStream(new FileInputStream(hitFile));
					
			// Make sure to read in all data
			while ((availBytes = hitStream.available()) > 0) {			 
					
				// Resize array for additional available input
				long[] newValues = new long[values.length + availBytes / 8];
				System.arraycopy(values, 0, newValues, 0, values.length);
			
				for (int i = longsRead; i < newValues.length; i++) {
					newValues[i] = hitStream.readLong();
					longsRead++;
				}
				
				values = newValues;
			}				
			hitStream.close();
		
		} catch (IOException blockExcep) {
			System.err.println("Error loading file: " + hitFile.getPath());
			values = null;
		}
		
		return values;
	}
	
	/**
	 * Stores covered call site information for fast access.
	 *
	 */
	private void loadCallSites() {

		try {

			callClasses = new HashMap();

			String curLine;
			String callingSig, calledSig;
			String callingClass, callingMeth;
			int spIndex1, spIndex2;

			//read a line and parse information
			BufferedReader callFile =
				new BufferedReader(
					new FileReader(execDirPath + "insect.pcalls"));
			while ((curLine = callFile.readLine()) != null) {

				//parse source line number
				spIndex1 = curLine.indexOf(':');
				String callLine = curLine.substring(0, spIndex1);
				curLine = curLine.substring(spIndex1 + 1);

				//parse method IDs and get method names
				spIndex1 = curLine.indexOf(':');
				spIndex2 = curLine.lastIndexOf(':');
				callingSig =
					(String) classIDs.get(
						new Integer(curLine.substring(0, spIndex1)));
				calledSig =
					(String) classIDs.get(
						new Integer(curLine.substring(spIndex1 + 1, spIndex2)));

				spIndex1 = callingSig.lastIndexOf('.');
				callingClass = callingSig.substring(0, spIndex1);
				callingMeth = callingSig.substring(spIndex1 + 1);

				//store calls according to the class and method they are in
				HashMap callMethods = (HashMap) callClasses.get(callingClass);
				if (callMethods == null) {
					callMethods = new HashMap();
					callClasses.put(callingClass, callMethods);
				}
				ArrayList calls = (ArrayList) callMethods.get(callingMeth);
				if (calls == null) {
					calls = new ArrayList();
					callMethods.put(callingMeth, calls);
				}
				calls.add(
					callLine + ":" + calledSig + curLine.substring(spIndex2));
			}
			callFile.close();
		}
		catch (IOException blockExcep) {
			System.err.println("Error loading file: insect.pcalls");
			callClasses = null;
		}
	}

	/**
	 * Determines how many times the specified statement was executed.
	 * Use wasStatementCovered(String, String, int) for
	 * efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return number of times, or -1 on error
	 */
	public long timesStatementExecuted(String className, int srcLine) {

		if (blockClasses == null || blocks == null)
			return -1;

		//get methods in this class
		HashMap blockMethods = (HashMap) blockClasses.get(className);
		if (blockMethods == null)
			return -1;

		//for each method, check its basic blocks
		Iterator it = blockMethods.keySet().iterator();
		while (it.hasNext()) {

			ArrayList bblocks =
				(ArrayList) blockMethods.get((String) it.next());
			if (bblocks == null)
				continue;

			//use the source line number to determine the correct block
			int blen = bblocks.size();
			for (int i = 0; i < blen; i++) {
				BasicBlock curBB = (BasicBlock) bblocks.get(i);
				if (curBB.contains(srcLine))
					return blocks[curBB.getID()];
			}
		}

		return -1;
	}

	/**
	 * Determines the number of times the specified statement was executed.
	 *
	 * @param className fully qualified name of class.
	 * @param methodSig method signature (see class javadoc above).
	 * @param srcLine source line of statement.
	 * @return number of times, or -1 on error.
	 */
	public long timesStatementExecuted(String className, 
							String methodSig, int srcLine) {

		if (blockClasses == null || blocks == null)
			return -1;

		//retrieve the correct set of blocks
		HashMap blockMethods = (HashMap) blockClasses.get(className);
		if (blockMethods == null)
			return -1;
		ArrayList bblocks = (ArrayList) blockMethods.get(methodSig);
		if (bblocks == null)
			return -1;

		//use the source line number to determine the correct block
		int blen = bblocks.size();
		for (int i = 0; i < blen; i++) {
			BasicBlock curBB = (BasicBlock) bblocks.get(i);
			if (curBB.contains(srcLine))
				return blocks[curBB.getID()];
		}

		return -1;
	}
	
	/**
	 * Determines the number of times the specified method call was executed.
	 *
	 * @param sourceSig method signature (see class javadoc above)
	 * @param targetSig method signature (see class javadoc above)
	 * @param srcLine source line of method call
	 * @return number of times, or -1 on error.
	 */
	public long timesCallExecuted(String sourceSig,
							String targetSig, int srcLine) {

		if (callClasses == null)
			return -1;

		//get correct list of targets
		int split = sourceSig.lastIndexOf('.');
		String callingClass = sourceSig.substring(0, split);
		String callingMeth = sourceSig.substring(split + 1);
		HashMap callMethods = (HashMap) callClasses.get(callingClass);
		if (callMethods == null) {
			return -1;
		}
		ArrayList calls = (ArrayList) callMethods.get(callingMeth);
		if (calls == null) {
			return -1;
		}
		
		//traverse calls made from the source method
		int numCalls = calls.size();
		for (int i = 0; i < numCalls; i++) {

			int spIndex1, spIndex2;
			int callLine;
			String calledSig;
			String objName;
						
			//curLine has the format <src line>:<called sig>:<object> <times execed>
			String curLine = (String) calls.get(i);
	
			//parse source line number
			spIndex1 = curLine.indexOf(":");
			spIndex2 = curLine.lastIndexOf(":");
			callLine = Integer.parseInt(curLine.substring(0, spIndex1));

			if (callLine != srcLine)
				continue;

			//parse method and object name, and execution count
			calledSig = curLine.substring(spIndex1 + 1, spIndex2);
			spIndex1 = curLine.indexOf(' ');	
			objName = curLine.substring(spIndex2 + 1, spIndex1);
			
			//fix unknown target
			if (calledSig.charAt(0) == '?')
				calledSig = objName + "." + calledSig.substring(1);

			//if target method matches
			if (calledSig.equals(targetSig))
				return Long.parseLong(curLine.substring(spIndex1 + 1));
		}

		return -1;
	}

	/**
	 * Determines the number of times the specified method call was executed
	 * through the given object type.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param objectClass class name of object on which method was invoked.
	 * @return number of times, or -1 on error.
	 */
	public long timesCallExecuted(String sourceSig, String targetSig,
									int srcLine, String objectClass) {

		if (callClasses == null)
			return -1;

		//get correct list of targets
		int split = sourceSig.lastIndexOf('.');
		String callingClass = sourceSig.substring(0, split);
		String callingMeth = sourceSig.substring(split + 1);
		HashMap callMethods = (HashMap) callClasses.get(callingClass);
		if (callMethods == null) {
			return -1;
		}
		ArrayList calls = (ArrayList) callMethods.get(callingMeth);
		if (calls == null) {
			return -1;
		}
			

		//traverse calls made from the source method
		int numCalls = calls.size();
		for (int i = 0; i < numCalls; i++) {

			int spIndex1, spIndex2;
			int callLine;
			String calledSig;
			String objName;
					
			//curLine has the format <src line>:<called sig>:<object> <times execed>
			String curLine = (String) calls.get(i);

			//parse source line number
			spIndex1 = curLine.indexOf(":");
			spIndex2 = curLine.lastIndexOf(":");
			callLine = Integer.parseInt(curLine.substring(0, spIndex1));

			if (callLine != srcLine)
				continue;

			//parse method and object name, and execution count
			calledSig = curLine.substring(spIndex1 + 1, spIndex2);
			spIndex1 = curLine.indexOf(' ');	
			objName = curLine.substring(spIndex2 + 1, spIndex1);

			if (!objName.equals(objectClass))
				continue;

			//fix unknown target
			if (calledSig.charAt(0) == '?')
				calledSig = objName + "." + calledSig.substring(1);

			//if target method matches
			if (calledSig.equals(targetSig))
				return Long.parseLong(curLine.substring(spIndex1 + 1));
		}

		return -1;
	}
	
	/**
	 * Determines the number of times the specified branch label was taken.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param label branch label, i.e., "T" or "F" for an if statement.
	 * @return number of times.
	 */
	public long timesBranchExecuted(String className, 
								int srcLine, String label) {
									
		if (branches == null) {
			return -1;
		}

		try {

			BufferedReader branchFile =
				parent.getIDFileReader("insect.branchids");

			if (branchFile == null) {
				return -1;
			}

			String curLine;
			int spIndex;

			while ((curLine = branchFile.readLine()) != null) {

				spIndex = curLine.indexOf(" ");
				if (!(curLine.substring(0, spIndex).equals(className)))
					continue;
				curLine = curLine.substring(spIndex + 1);

				spIndex = curLine.indexOf(" ");
				if (Integer.parseInt(curLine.substring(0, spIndex)) != srcLine)
					continue;
				curLine = curLine.substring(spIndex + 1);

				spIndex = curLine.indexOf(" ");
				if (curLine.substring(0, spIndex).equals(label))
					return branches[Integer.parseInt(
							curLine.substring(spIndex + 1))];
			}
		}
		catch (IOException branchExcep) {
			System.err.println("Error accessing branchids file.");
		}

		return -1;
	}
	
}
