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
 * Provides coverage for a specific execution  of an instrumented program.
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
 * @author <a href="mailto:cc@resnet.gatech.edu">Anil Chawla</a>
 */

public class ExecCoverage {

	/**
	 * Reference to parent Analysis object.
	 */
	private Analysis parent;
	
	/**
	 * Path of execution directory.
	 */
	private String execDirPath;

	/**
	 * Maps class ID numbers to class names.
	 */
	private HashMap classIDs;
	
	/**
	 * Maintains the first level of a multi-level data structure
	 * for storing basic block information. The structure is as
	 * follows: Class name table -> method name table -> basic
	 * block table.
	 */
	private HashMap blockClasses;

	// Status of basic blocks for statement coverage
	private boolean[] blocks;

	// Status of branches for edge coverage
	private boolean[] branches;

	// Storage of callsites for call coverage
	private HashMap callClasses;

	// Specifies whether throw/catch info is available
	private boolean thrown;
	private boolean caught;

	/**
	 * Creates a new <code>ExecCoverage</code> instance.
	 *
	 * @param parent parent Analysis object.
	 * @param execDirPath path of execution directory.
	 */
	public ExecCoverage(Analysis parent, String execDirPath) {

		if (execDirPath.charAt(execDirPath.length() - 1) != File.separatorChar)
			execDirPath += File.separatorChar;

		this.parent = parent;
		this.execDirPath = execDirPath;
		this.classIDs = parent.getClassIDs();
		this.blockClasses = parent.getBlockClasses();

		//store covered call sites for this execution
		loadCallSites();

		//determine if throw and catch info is available
		if ((new File(execDirPath + "insect.thrown")).exists())
			thrown = true;
		if ((new File(execDirPath + "insect.caught")).exists())
			caught = true;

		loadHitFiles();
	}

	/**
	 * Caches coverage from hit-vector files for fast access.
	 *
	 */
	private void loadHitFiles() {
		
		File blockFile = new File(execDirPath + "insect.blockhits");
		if (blockFile.exists()) {
			blocks = readHitFile(blockFile);
		}
		
		File branchFile = new File(execDirPath + "insect.branchhits");			
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
	private boolean[] readHitFile(File hitFile) {
		
		int boolsRead = 0;
		int availBytes = 0;
		
		boolean[] values = new boolean[0];
		
		try {
			
			DataInputStream hitStream =
				new DataInputStream(new FileInputStream(hitFile));
					
			// Make sure to read in all data
			while ((availBytes = hitStream.available()) > 0) {			 
					
				// Resize array for additional available input
				boolean[] newValues = 
					new boolean[values.length + availBytes];
				System.arraycopy(values, 0, newValues, 0, values.length);
			
				for (int i = boolsRead; i < newValues.length; i++) {
					newValues[i] = hitStream.readBoolean();
					boolsRead++;
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
					new FileReader(execDirPath + "insect.calls"));
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
			callClasses = null;
		}
	}

	/**
	 * Determines whether the specified statement was covered.
	 * Use wasStatementCovered(String, String, int) for
	 * efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class
	 * @param srcLine source line number
	 * @return true, false, or null on error
	 */
	public Boolean wasStatementCovered(String className, int srcLine) {

		if (blockClasses == null)
			return null;

		//get methods in this class
		HashMap blockMethods = (HashMap) blockClasses.get(className);
		if (blockMethods == null)
			return null;

		//for each method, check its basic blocks
		for (Iterator it = blockMethods.keySet().iterator(); it.hasNext();) {
		
			ArrayList bblocks =
				(ArrayList) blockMethods.get((String) it.next());
			if (bblocks == null)
				continue;

			//use the source line number to determine the correct block
			int blen = bblocks.size();
			for (int i = 0; i < blen; i++) {
				BasicBlock curBB = (BasicBlock) bblocks.get(i);
				if (curBB.contains(srcLine))
					return Boolean.valueOf(blocks[curBB.getID()]);
			}
		}

		return null;
	}

	/**
	 * Determines whether the specified statement was covered.
	 *
	 * @param className fully qualified name of class
	 * @param methodSig method signature (see class javadoc above)
	 * @param srcLine source line of statement
	 * @return true, false, or null on error
	 */
	public Boolean wasStatementCovered(String className, 
							String methodSig, int srcLine) {

		if (blockClasses == null)
			return null;

		//retrieve the correct set of blocks
		HashMap blockMethods = (HashMap) blockClasses.get(className);
		if (blockMethods == null)
			return null;
		ArrayList bblocks = (ArrayList) blockMethods.get(methodSig);
		if (bblocks == null)
			return null;

		//use the source line number to determine the correct block
		int blen = bblocks.size();
		for (int i = 0; i < blen; i++) {
			BasicBlock curBB = (BasicBlock) bblocks.get(i);
			if (curBB.contains(srcLine))
				return Boolean.valueOf(blocks[curBB.getID()]);
		}

		return null;
	}

	/**
	 * Determines if the specified branch was covered.
	 *
	 * @param className fully qualified name of class
	 * @param srcLine source line number
	 * @param label branch label, i.e., "T" or "F" for an if statement
	 * @return a <code>Boolean</code> value
	 */
	public Boolean wasBranchCovered(String className,
							int srcLine, String label) {

		try {

			BufferedReader branchFile =
				parent.getIDFileReader("insect.branchids");

			if (branchFile == null)
				return null;

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
					return Boolean.valueOf(
						branches[Integer.parseInt(
							curLine.substring(spIndex + 1))]);
			}
		}
		catch (IOException branchExcep) {
			System.err.println("Error accessing branchids");
		}

		return null;
	}

	/**
	 * Determines which labels were branched to
	 * from the specified branch.
	 *
	 * @param className fully qualified name of class
	 * @param srcLine source line number
	 * @return array of labels
	 */
	public String[] getCoveredBranches(String className, int srcLine) {

		ArrayList branchLabels = null;

		try {

			BufferedReader branchFile =
				parent.getIDFileReader("insect.branchids");

			if (branchFile == null)
				return null;

			String curLine;
			int spIndex;

			branchLabels = new ArrayList();

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

				//if this branch id was covered, add its label to the list
				if (branches[Integer.parseInt(curLine.substring(spIndex + 1))])
					branchLabels.add(curLine.substring(0, spIndex));
			}
		}
		catch (IOException branchExcep) {
			System.err.println("Error accessing branchids");
			return null;
		}

		return (String[]) branchLabels.toArray(new String[0]);
	}

	/**
	 * Determines if the specified method call was covered.
	 *
	 * @param sourceSig method signature (see class javadoc above)
	 * @param targetSig method signature (see class javadoc above)
	 * @param srcLine source line of method call
	 * @return true, false, or null on error
	 */
	public Boolean wasCallCovered(String sourceSig,
						String targetSig, int srcLine) {

		if (callClasses == null)
			return null;

		//get correct list of targets
		int split = sourceSig.lastIndexOf('.');
		String callingClass = sourceSig.substring(0, split);
		String callingMeth = sourceSig.substring(split + 1);
		HashMap callMethods = (HashMap) callClasses.get(callingClass);
		if (callMethods == null)
			return null;
		ArrayList calls = (ArrayList) callMethods.get(callingMeth);
		if (calls == null)
			return null;

		//traverse calls made from the source method
		int numCalls = calls.size();
		for (int i = 0; i < numCalls; i++) {

			int spIndex;
			int callLine;
			String calledSig;
			String objName;
			String curLine = (String) calls.get(i);

			//parse source line number
			spIndex = curLine.indexOf(":");
			callLine = Integer.parseInt(curLine.substring(0, spIndex));

			if (callLine != srcLine)
				continue;

			//parse method and object name
			curLine = curLine.substring(spIndex + 1);
			spIndex = curLine.indexOf(":");
			calledSig = curLine.substring(0, spIndex);
			objName = curLine.substring(spIndex + 1);

			//fix unknown target
			if (calledSig.charAt(0) == '?')
				calledSig = objName + "." + calledSig.substring(1);

			//if target method matches
			if (calledSig.equals(targetSig))
				return Boolean.valueOf(true);
		}

		return Boolean.valueOf(false);
	}

	/**
	 * Determines if the specified method call was covered
	 * through the specified object type.
	 *
	 * @param sourceSig method signature (see class javadoc above)
	 * @param targetSig method signature (see class javadoc above)
	 * @param srcLine source line of method call
	 * @param objectClass class name of object on which method was invoked
	 * @return true, false, or null on error
	 */
	public Boolean wasCallCovered(String sourceSig, String targetSig,
									int srcLine, String objectClass) {

		if (callClasses == null)
			return null;

		//get correct list of targets
		int split = sourceSig.lastIndexOf('.');
		String callingClass = sourceSig.substring(0, split);
		String callingMeth = sourceSig.substring(split + 1);
		HashMap callMethods = (HashMap) callClasses.get(callingClass);
		if (callMethods == null)
			return null;
		ArrayList calls = (ArrayList) callMethods.get(callingMeth);
		if (calls == null)
			return null;

		//traverse calls made from the source method
		int numCalls = calls.size();
		for (int i = 0; i < numCalls; i++) {

			int spIndex;
			int callLine;
			String calledSig;
			String objName;
			String curLine = (String) calls.get(i);

			//parse source line number
			spIndex = curLine.indexOf(":");
			callLine = Integer.parseInt(curLine.substring(0, spIndex));

			if (callLine != srcLine)
				continue;

			//parse method and object name
			curLine = curLine.substring(spIndex + 1);
			spIndex = curLine.indexOf(":");
			calledSig = curLine.substring(0, spIndex);
			objName = curLine.substring(spIndex + 1);

			if (!objName.equals(objectClass))
				continue;

			//fix unknown target
			if (calledSig.charAt(0) == '?')
				calledSig = objectClass + "." + calledSig.substring(1);

			//if target method matches
			if (calledSig.equals(targetSig))
				return Boolean.valueOf(true);
		}

		return Boolean.valueOf(false);
	}

	/**
	 * Determines the object types through which the
	 * specified call was covered.
	 *
	 * @param sourceSig method signature (see class javadoc above)
	 * @param targetSig method signature (see class javadoc above)
	 * @param srcLine source line of method call
	 * @return array of class names, or null on error
	 */
	public String[] getCoveredCallObjects(String sourceSig,
								String targetSig, int srcLine) {

		if (callClasses == null) {
			return null;
		}

		//get correct list of targets
		int split = sourceSig.lastIndexOf('.');
		String callingClass = sourceSig.substring(0, split);
		String callingMeth = sourceSig.substring(split + 1);
		HashMap callMethods = (HashMap) callClasses.get(callingClass);
		if (callMethods == null) {
			return null;
		}
		ArrayList calls = (ArrayList) callMethods.get(callingMeth);
		if (calls == null) {
			return null;
		}

		ArrayList callObjs = new ArrayList();

		//traverse calls made from the source method
		int numCalls = calls.size();
		for (int i = 0; i < numCalls; i++) {

			int spIndex;
			int callLine;
			String calledSig;
			String objName;
			String curLine = (String) calls.get(i);

			//parse source line number
			spIndex = curLine.indexOf(":");
			callLine = Integer.parseInt(curLine.substring(0, spIndex));

			if (callLine != srcLine)
				continue;

			//parse method and object name
			curLine = curLine.substring(spIndex + 1);
			spIndex = curLine.indexOf(":");
			calledSig = curLine.substring(0, spIndex);
			objName = curLine.substring(spIndex + 1);

			//fix unknown target
			if (calledSig.charAt(0) == '?') {
				calledSig = objName + "." + calledSig.substring(1);
			}

			//if target method matches
			if (calledSig.equals(targetSig)) {
				callObjs.add(objName);
			}
		}

		return (String[]) callObjs.toArray(new String[0]);
	}

	/**
	 * Determines if the throw at the specified
	 * source line was covered.
	 *
	 * @param className fully qualified class name
	 * @param srcLine source line number
	 * @return true, false, or null on error
	 */
	public Boolean wasThrowCovered(String className, int srcLine) {

		if (!thrown)
			return null;

		//attempt to locate throw from this line in output file
		try {

			String curLine;
			String classStr;
			int lineNum;
			int spIndex1, spIndex2;

			//read a line and parse information
			BufferedReader throwFile =
				new BufferedReader(
					new FileReader(execDirPath + "insect.thrown"));

			while ((curLine = throwFile.readLine()) != null) {

				spIndex1 = curLine.indexOf(":");
				spIndex2 = curLine.lastIndexOf(":");

				lineNum = Integer.parseInt(curLine.substring(0, spIndex1));
				classStr = (String) classIDs.get(
					new Integer(curLine.substring(spIndex1 + 1, spIndex2)));

				//if class and source line match, return true
				if ((lineNum == srcLine) && (className.equals(classStr)))
					break;
			}

			//if curLine == null, that indicates that the throw was not found
			if (curLine != null) {
				return Boolean.valueOf(true);
			}

		}
		catch (IOException ioe) {
			System.err.println("Error accessing thrown");
			return null;
		}

		return Boolean.valueOf(false);
	}

	/**
	 * Determines if the throw at the specified
	 * source line was covered through the specified
	 * object type.
	 *
	 * @param className fully qualified class name
	 * @param srcLine source line number
	 * @param objectClass class name of thrown object
	 * @return true, false, or null on error
	 */
	public Boolean wasThrowCovered(String className,
									int srcLine, String objectClass) {

		if (!thrown)
			return null;

		//attempt to locate throw in output file
		try {

			String curLine;
			String classStr;
			int lineNum;
			int spIndex1, spIndex2;

			//read a line and parse information
			BufferedReader throwFile =
				new BufferedReader(
					new FileReader(execDirPath + "insect.thrown"));

			while ((curLine = throwFile.readLine()) != null) {

				spIndex1 = curLine.indexOf(":");
				spIndex2 = curLine.lastIndexOf(":");

				lineNum = Integer.parseInt(curLine.substring(0, spIndex1));
				classStr =
					(String) classIDs.get(
						new Integer(curLine.substring(spIndex1 + 1, spIndex2)));

				//if class and source line  match, check if exception type matches as well
				if ((lineNum == srcLine)
					&& (className.equals(classStr))
					&& (curLine.substring(spIndex2 + 1).equals(objectClass)))
					break;
			}

			//if curLine == null, that indicates that the throw was not found
			if (curLine != null) {
				return Boolean.valueOf(true);
			}

		}
		catch (IOException ioe) {
			System.err.println("Error accessing thrown");
			return null;
		}

		return Boolean.valueOf(false);
	}

	/**
	 * Determines which object types where thrown
	 * at the specified throw.
	 *
	 * @param className fully qualified class name
	 * @param srcLine source line number
	 * @return array of class names
	 */
	public String[] getCoveredThrowObjects(String className, int srcLine) {

		if (!thrown)
			return null;

		ArrayList throwObjs = null;

		//locate all matching throws in output file
		try {

			String curLine;
			String classStr;
			int lineNum;
			int spIndex1, spIndex2;

			throwObjs = new ArrayList();

			//read a line and parse information
			BufferedReader throwFile =
				new BufferedReader(
					new FileReader(execDirPath + "insect.thrown"));

			while ((curLine = throwFile.readLine()) != null) {

				spIndex1 = curLine.indexOf(":");
				spIndex2 = curLine.lastIndexOf(":");

				lineNum = Integer.parseInt(curLine.substring(0, spIndex1));
				classStr = (String) classIDs.get(
					new Integer(curLine.substring(spIndex1 + 1, spIndex2)));

				//if class and source line match, add object type to list
				if ((lineNum == srcLine) && (className.equals(classStr)))
					throwObjs.add(curLine.substring(spIndex2 + 1));
			}
		}
		catch (IOException ioe) {
			System.err.println("Error accessing thrown");
			return null;
		}

		return (String[]) throwObjs.toArray(new String[0]);
	}

	/**
	 * Determines if the catch at the specified
	 * source line was covered.
	 *
	 * @param className fully qualified class name
	 * @param srcLine source line number
	 * @return true, false, or null on error
	 */
	public Boolean wasCatchCovered(String className, int srcLine) {

		if (!caught)
			return null;

		//attempt to locate catch from this line in output file
		try {

			String curLine;
			String classStr;
			int lineNum;
			int spIndex1, spIndex2;

			//read a line and parse information
			BufferedReader catchFile =
				new BufferedReader(
					new FileReader(execDirPath + "insect.caught"));

			while ((curLine = catchFile.readLine()) != null) {

				spIndex1 = curLine.indexOf(":");
				spIndex2 = curLine.lastIndexOf(":");

				lineNum = Integer.parseInt(curLine.substring(0, spIndex1));
				classStr = (String) classIDs.get(
					new Integer(curLine.substring(spIndex1 + 1, spIndex2)));

				//if class and source line match, return true
				if ((lineNum == srcLine) && (className.equals(classStr)))
					break;
			}

			//if curLine == null, that indicates that the catch was not found
			if (curLine != null) {
				return Boolean.valueOf(true);
			}

		}
		catch (IOException ioe) {
			System.err.println("Error accessing caught");
			return null;
		}

		return Boolean.valueOf(false);
	}

	/**
	 * Determines if the catch at the specified
	 * source line was covered through the specified
	 * object type.
	 *
	 * @param className fully qualified class name
	 * @param srcLine source line number
	 * @param objectClass class name of caught object
	 * @return true, false, or null on error
	 */
	public Boolean wasCatchCovered(String className,
								int srcLine, String objectClass) {

		if (!caught)
			return null;

		// attempt to locate catch in output file
		try {

			String curLine;
			String classStr;
			int lineNum;
			int spIndex1, spIndex2;

			// read a line and parse information
			BufferedReader catchFile =
				new BufferedReader(
					new FileReader(execDirPath + "insect.caught"));

			while ((curLine = catchFile.readLine()) != null) {

				spIndex1 = curLine.indexOf(":");
				spIndex2 = curLine.lastIndexOf(":");

				lineNum = Integer.parseInt(curLine.substring(0, spIndex1));
				classStr = (String) classIDs.get(
					new Integer(curLine.substring(spIndex1 + 1, spIndex2)));

				// if class and source line  match,
				// check if exception type matches as well
				if ((lineNum == srcLine)
					&& (className.equals(classStr))
					&& (curLine.substring(spIndex2 + 1).equals(objectClass)))
					break;
			}

			// if curLine == null, that indicates that the catch was not found
			if (curLine != null) {
				return Boolean.valueOf(true);
			}

		} catch (IOException ioe) {
			System.err.println("Error accessing caught");
			return null;
		}

		return Boolean.valueOf(false);
	}

	/**
	 * Determines which object types where caught
	 * at the specified catch.
	 *
	 * @param className fully qualified class name
	 * @param srcLine source line number
	 * @return array of class names
	 */
	public String[] getCoveredCatchObjects(String className, int srcLine) {

		if (!caught)
			return null;

		ArrayList catchObjs = null;

		//locate all matching catches in output file
		try {

			String curLine;
			String classStr;
			int lineNum;
			int spIndex1, spIndex2;

			catchObjs = new ArrayList();

			//read a line and parse information
			BufferedReader catchFile =
				new BufferedReader(
					new FileReader(execDirPath + "insect.caught"));

			while ((curLine = catchFile.readLine()) != null) {

				spIndex1 = curLine.indexOf(":");
				spIndex2 = curLine.lastIndexOf(":");

				lineNum = Integer.parseInt(curLine.substring(0, spIndex1));
				classStr = (String) classIDs.get(
					new Integer(curLine.substring(spIndex1 + 1, spIndex2)));

				//if class and source line match, add object type to list
				if ((lineNum == srcLine) && (className.equals(classStr)))
					catchObjs.add(curLine.substring(spIndex2 + 1));
			}
		} catch (IOException ioe) {
			System.err.println("Error accessing caught");
			return null;
		}

		return (String[]) catchObjs.toArray(new String[0]);
	}

} // ExecCoverage