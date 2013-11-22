package insect.coverage.analysis;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Provides coverage information regarding
 * executions of an instrumented program.
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
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class Coverage extends Analysis {

	/**
	 * Coverage analyzers for each individual execution.
	 */
	private ExecCoverage[] execCovs;

	/**
	 * Creates a new Coverage object for the program
	 * in the specified resource file.
	 *
	 * @param rcPath path of resource file.
	 */
	public Coverage(String rcPath) {

		super(rcPath);

		// Determine executions and create analyzers for each execution
		execIDs = (String[]) getExecDirs(instrDir, "").toArray(new String[0]);
		execCovs = new ExecCoverage[execIDs.length];
		execIndex = new HashMap();
		for (int i = 0; i < execIDs.length; i++) {
			execCovs[i] =	new ExecCoverage(this,
									instrDir.getPath() + dirSep + execIDs[i]);
			execIDs[i] = execIDs[i].substring(0, execIDs[i].length() - 3);
			execIndex.put(execIDs[i], new Integer(i));
		}
	}

	/**
	 * Determines whether the specified statement was covered
	 * in any execution.
	 *
	 * Use wasStatementCovered(String, String, int) for
	 * efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return true, false, or null on error.
	 */
	public Boolean wasStatementCovered(String className, int srcLine) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b = wasStatementCoveredIn(className, srcLine, execIDs[i]);

			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}

		return new Boolean(false);
	}

	/**
	 * Determines whether the specified statement was covered
	 * in the specified execution.
	 *
	 * Use wasStatementCoveredIn(String, String, int, String)
	 * for efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasStatementCoveredIn(String className,
											int srcLine, String execID) {

		if (execID == null)
			return wasStatementCovered(className, srcLine);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasStatementCovered(className, srcLine);

	}

	/**
	 * Determines whether the specified statement was covered
	 * in any execution.
	 *
	 * @param className fully qualified name of class.
	 * @param methodSig method signature (see class javadoc above).
	 * @param srcLine source line of statement.
	 * @return true, false, or null on error.
	 */
	public Boolean wasStatementCovered(String className,
										String methodSig, int srcLine) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b =
				wasStatementCoveredIn(
					className,
					methodSig,
					srcLine,
					execIDs[i]);

			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}

		return new Boolean(false);
	}

	/**
	 * Determines whether the specified statement was covered
	 * in the specified execution.
	 *
	 * @param className fully qualified name of class.
	 * @param methodSig method signature (see class javadoc above).
	 * @param srcLine source line of statement.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasStatementCoveredIn(
		String className,
		String methodSig,
		int srcLine,
		String execID) {

		if (execID == null)
			return wasStatementCovered(className, methodSig, srcLine);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasStatementCovered(className, methodSig, srcLine);

	}

	/**
	 * Determines if the specified branch was covered
	 * in any execution.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param label branch label, i.e., "T" or "F" for an if statement.
	 * @return true, false, or null on error.
	 */
	public Boolean wasBranchCovered(String className,
										int srcLine, String label) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b =
				wasBranchCoveredIn(className, srcLine, label, execIDs[i]);

			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}

		return new Boolean(false);
	}

	/**
	 * Determines if the specified branch was covered
	 * in the specified execution.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param label branch label, i.e., "T" or "F" for an if statement.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasBranchCoveredIn(String className, int srcLine,
										String label, String execID) {

		if (execID == null)
			return wasBranchCovered(className, srcLine, label);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasBranchCovered(className, srcLine, label);
	}

	/**
	 * Determines which labels were branched to
	 * from the specified branch in the any execution.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return array of labels.
	 */
	public String[] getCoveredBranches(String className, int srcLine) {

		HashSet covLabs = new HashSet();

		for (int i = 0; i < execIDs.length; i++) {

			String[] labs =
				getCoveredBranchesFrom(className, srcLine, execIDs[i]);

			if (labs == null)
				return null;
			else {
				for (int j = 0; j < labs.length; j++)
					covLabs.add(labs[j]);
			}

		}

		return (String[]) covLabs.toArray(new String[0]);
	}

	/**
	 * Determines which labels were branched to
	 * from the specified branch in the specified
	 * execution.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param execID identifier for execution.
	 * @return array of labels.
	 */
	public String[] getCoveredBranchesFrom(String className,
											int srcLine, String execID) {

		if (execID == null)
			return getCoveredBranches(className, srcLine);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.getCoveredBranches(className, srcLine);
	}

	/**
	 * Determines if the specified method call was covered
	 * in any execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCallCovered(String sourceSig,
									String targetSig, int srcLine) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b =
				wasCallCoveredIn(sourceSig, targetSig, srcLine, execIDs[i]);

			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}

		return new Boolean(false);
	}

	/**
	 * Determines if the specified method call was covered
	 * in the specified execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCallCoveredIn(String sourceSig, String targetSig,
											int srcLine, String execID) {

		if (execID == null)
			return wasCallCovered(sourceSig, targetSig, srcLine);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasCallCovered(sourceSig, targetSig, srcLine);
	}

	/**
	 * Determines if the specified method call was covered
	 * through the specified object type in any execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param objectClass class name of object on which method was invoked.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCallCovered(String sourceSig,	String targetSig,
									int srcLine, String objectClass) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b = wasCallCoveredIn(sourceSig,	targetSig,
								srcLine, objectClass, execIDs[i]);

			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}

		return new Boolean(false);
	}

	/**
	 * Determines if the specified method call was covered
	 * through the specified object type in the specified
	 * execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param objectClass class name of object on which method was invoked.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCallCoveredIn(String sourceSig, String targetSig,
							int srcLine, String objectClass, String execID) {

		if (execID == null)
			return wasCallCovered(sourceSig, targetSig, srcLine, objectClass);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasCallCovered(sourceSig, targetSig, srcLine, objectClass);
	}

	/**
	 * Determines the object types through which the
	 * specified call was covered in any execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @return array of class names, or null on error.
	 */
	public String[] getCoveredCallObjects(String sourceSig,
								String targetSig, int srcLine) {

		HashSet covObjs = new HashSet();

		for (int i = 0; i < execIDs.length; i++) {

			String[] objs =
				getCoveredCallObjectsFrom(
					sourceSig,
					targetSig,
					srcLine,
					execIDs[i]);

			if (objs == null) {
				return null;
			}
			else {
				for (int j = 0; j < objs.length; j++)
					covObjs.add(objs[j]);
			}

		}

		return (String[]) covObjs.toArray(new String[0]);
	}

	/**
	 * Determines the object types through which the
	 * specified call was covered in the specified execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param execID identifier for execution.
	 * @return array of class names, or null on error.
	 */
	public String[] getCoveredCallObjectsFrom(String sourceSig, String targetSig,
												int srcLine, String execID) {

		if (execID == null) {
			return getCoveredCallObjects(sourceSig, targetSig, srcLine);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null) {
			return null;
		}

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.getCoveredCallObjects(sourceSig, targetSig, srcLine);
	}

	/**
	 * Determines if the throw at the specified
	 * source line was covered in any execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @return true, false, or null on error.
	 */
	public Boolean wasThrowCovered(String className, int srcLine) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b = wasThrowCoveredIn(className, srcLine, execIDs[i]);

			if (b == null) {
				return null;
			}
			else if (b.booleanValue()) {
				return b;
			}
		}

		return new Boolean(false);
	}

	/**
	 * Determines if the throw at the specified source
	 * line was covered in the specified execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasThrowCoveredIn(String className,
										 int srcLine, String execID) {

		if (execID == null) {
			return wasThrowCovered(className, srcLine);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null) {
			return null;
		}

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasThrowCovered(className, srcLine);
	}

	/**
	 * Determines if the throw at the specified
	 * source line was covered through the specified
	 * object type in any execution.
	 *
	 * @param className fully qualified class name
	 * @param srcLine source line number
	 * @param objectClass class name of thrown object
	 * @return true, false, or null on error
	 */
	public Boolean wasThrowCovered(String className,
								int srcLine, String objectClass) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b =
				wasThrowCoveredIn(className, srcLine, objectClass, execIDs[i]);

			if (b == null) {
				return null;
			}
			else if (b.booleanValue()) {
				return b;
			}
		}

		return new Boolean(false);
	}

	/**
	 * Determines if the throw at the specified
	 * source line was covered through the specified
	 * object type in the specified execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @param objectClass class name of thrown object.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasThrowCoveredIn(String className, int srcLine,
									String objectClass, String execID) {

		if (execID == null) {
			return wasThrowCoveredIn(className, srcLine, objectClass);
		}
		
		Integer index = (Integer) execIndex.get(execID);

		if (index == null) {
			return null;
		}

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasThrowCovered(className, srcLine, objectClass);
	}

	/**	
	 * Determines which object types where thrown
	 * at the specified throw in any execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @return array of class names.
	 */
	public String[] getCoveredThrowObjects(String className, int srcLine) {

		HashSet covObjs = new HashSet();

		for (int i = 0; i < execIDs.length; i++) {

			String[] objs =
				getCoveredThrowObjectsFrom(className, srcLine, execIDs[i]);

			if (objs == null) {
				return null;
			}
			else {
				for (int j = 0; j < objs.length; j++)
					covObjs.add(objs[j]);
			}

		}

		return (String[]) covObjs.toArray(new String[0]);
	}

	/**
	 * Determines which object types where thrown
	 * at the specified throw in the specified execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @param execID identifier for execution.
	 * @return array of class names.
	 */
	public String[] getCoveredThrowObjectsFrom(String className,
										int srcLine, String execID) {
		if (execID == null) {
			return getCoveredThrowObjects(className, srcLine);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null) {
			return null;
		}

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.getCoveredThrowObjects(className, srcLine);
	}

	/**
	 * Determines if the catch at the specified
	 * source line was covered in any execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCatchCovered(String className, int srcLine) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b = wasCatchCoveredIn(className, srcLine, execIDs[i]);

			if (b == null) {
				return null;
			}
			else if (b.booleanValue()) {
				return b;
			}
		}

		return new Boolean(false);
	}

	/**
	 * Determines if the catch at the specified
	 * source line was covered in the specified execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCatchCoveredIn(String className,
								int srcLine, String execID) {

		if (execID == null) {
			return wasCatchCovered(className, srcLine);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null) {
			return null;
		}

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasCatchCovered(className, srcLine);
	}

	/**
	 * Determines if the catch at the specified
	 * source line was covered through the specified
	 * object type in any execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @param objectClass class name of caught object.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCatchCovered(String className,
							int srcLine, String objectClass) {

		for (int i = 0; i < execIDs.length; i++) {

			Boolean b =
				wasCatchCoveredIn(className, srcLine, objectClass, execIDs[i]);

			if (b == null) {
				return null;
			}
			else if (b.booleanValue()) {
				return b;
			}
		}

		return new Boolean(false);
	}

	/**
	 * Determines if the catch at the specified
	 * source line was covered through the specified
	 * object type in the specified execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @param objectClass class name of caught object.
	 * @param execID identifier for execution.
	 * @return true, false, or null on error.
	 */
	public Boolean wasCatchCoveredIn(String className, int srcLine,
								String objectClass,	String execID) {

		if (execID == null) {
			return wasCatchCovered(className, srcLine, objectClass);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null) {
			return null;
		}

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.wasCatchCovered(className, srcLine, objectClass);
	}

	/**
	 * Determines which object types where caught
	 * at the specified catch in any execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @return array of class names.
	 */
	public String[] getCoveredCatchObjects(String className, int srcLine) {

		HashSet covObjs = new HashSet();

		for (int i = 0; i < execIDs.length; i++) {

			String[] objs =
				getCoveredCatchObjectsFrom(className, srcLine, execIDs[i]);

			if (objs == null) {
				return null;
			}
			else {
				for (int j = 0; j < objs.length; j++)
					covObjs.add(objs[j]);
			}

		}

		return (String[]) covObjs.toArray(new String[0]);
	}

	/**
	 * Determines which object types where caught
	 * at the specified catch in the specified execution.
	 *
	 * @param className fully qualified class name.
	 * @param srcLine source line number.
	 * @param execID identifier for execution.
	 * @return array of class names.
	 */
	public String[] getCoveredCatchObjectsFrom(String className,
										int srcLine, String execID) {

		if (execID == null) {
			return getCoveredCatchObjects(className, srcLine);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null) {
			return null;
		}

		ExecCoverage coverage = execCovs[index.intValue()];
		return coverage.getCoveredCatchObjects(className, srcLine);
	}

} // Coverage