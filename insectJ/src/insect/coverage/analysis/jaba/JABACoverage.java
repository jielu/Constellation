package insect.coverage.analysis.jaba;

import insect.coverage.analysis.Analysis;
import jaba.graph.Edge;
import jaba.graph.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Provides coverage information regarding nodes and edges in a program graph.
 * Coverage is determined by mapping information from the executions of an
 * instrumented program to the particular graph type.  This mapping is done
 * using the JABAExecCoverage class.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class JABACoverage extends Analysis {


	private JABAExecCoverage[] execCovs;

	/**
	 * Creates a JABACoverage object from a JABA resource file.
	 *
	 * @param rcFile pathname of JABA resource file
	 */
	public JABACoverage(String rcFile) {

		super(rcFile);

		// Determine executions and create analyzers for each execution
		execIDs = (String[]) getExecDirs(instrDir, "").toArray(new String[0]);
		execCovs = new JABAExecCoverage[execIDs.length];
		execIndex = new HashMap();
		for (int i = 0; i < execIDs.length; i++) {
			execCovs[i] =	new JABAExecCoverage(this,
									instrDir.getPath() + dirSep + execIDs[i]);
			execIDs[i] = execIDs[i].substring(0, execIDs[i].length() - 3);
			execIndex.put(execIDs[i], new Integer(i));
		}
	}

	/**
	 * Determines if the specified node was covered during any
	 * of the program executions.
	 *
	 * @param aNode node in the graph.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage couldnot be determined.
	 */
	public Boolean wasCovered(Node aNode) {

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(aNode, execIDs[i]);
			
			if (b == null)
				return null;
			else if (b.booleanValue() == true)
				return b;
		}
		return new Boolean(false);

	}

	/**
	 * Determines if the specified node was covered through an object
	 * of the given class name.
	 *
	 * @param aNode node in the graph.
	 * @param objClassName class name of object.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage could not be determined.
	 */
	public Boolean wasCovered(Node aNode, String objClassName) {

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(aNode, objClassName, execIDs[i]);
			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}
		return new Boolean(false);

	}

	/**
	 * Determines the class names of objects that
	 * are traversed when the specified node
	 * is covered during any execution.
	 *
	 * @param aNode a Node.
	 * @return class names of objects.
	 */
	public String[] getCoveredObjects(Node aNode) {

		HashSet covSet = new HashSet();

		for (int i = 0; i < execIDs.length; i++) {
			String[] objs = getCoveredObjectsIn(aNode, execIDs[i]);
			if (objs == null)
				return null;
			else
				covSet.addAll(Arrays.asList(objs));
		}

		return (String[])covSet.toArray(new String[0]);
	}

	/**
	 * Determines the class names of objects that
	 * are traversed when the specified node
	 * is covered during the specified execution.
	 *
	 * @param aNode a Node.
	 * @param execID an execution.
	 * @return class names of covered objects.
	 */
	public String[] getCoveredObjectsIn(Node aNode, String execID) {

		if (execID == null)
			return getCoveredObjects(aNode);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		JABAExecCoverage coverage = execCovs[index.intValue()];
		return coverage.getCoveredObjs(aNode);
	}

	/**
	 * Determines the program executions in which the
	 * specified node was covered.
	 *
	 * @param aNode node in the graph.
	 * @return the names of all executions in which the node was covered.
	 */
	public String[] getCoveredExecs(Node aNode) {

		List execs = new ArrayList();

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(aNode, execIDs[i]);
			if (b == null)
				return null;
			else if (b.booleanValue() == true)
				execs.add(execIDs[i]);
		}
		return (String [])execs.toArray(new String[0]);

	}

	/**
	 * Determines the program executions in which the
	 * specified node was covered through an object of the given
	 * class name.
	 *
	 * @param aNode node in the graph.
	 * @param objClassName class name of object.
	 * @return names of all executions in which the node was covered.
	 */
	public String[] getCoveredExecs(Node aNode, String objClassName) {

		List execs = new ArrayList();

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(aNode, objClassName, execIDs[i]);
			if (b == null)
				return null;
			else if (b.booleanValue())
				execs.add(execIDs[i]);
		}
		return (String [])execs.toArray(new String[0]);

	}

	/**	
	 * Determines if the specified node was covered during the
	 * specified program execution.
	 *
	 * @param aNode node in the graph.
	 * @param execID name of execution.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage could not be determined.
	 */
	public Boolean wasCoveredIn(Node aNode, String execID) {

		if (execID == null)
			return wasCovered(aNode);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return new Boolean(false);

		JABAExecCoverage coverage = execCovs[index.intValue()];

		return coverage.wasCovered(aNode);

	}

	/**	
	 * Determines if the specified node was covered through an object of the
	 * given class name.
	 *
	 * @param aNode node in the graph.
	 * @param objClassName class name of object.
	 * @param execID name of execution.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage could not be determined.
	 */
	public Boolean wasCoveredIn(
						Node aNode,
						String objClassName,
						String execID) {

		if (execID == null)
			return wasCovered(aNode, objClassName);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return new Boolean(false);

		JABAExecCoverage coverage = execCovs[index.intValue()];

		return coverage.wasCovered(aNode, objClassName);

	}

	/**
	 * Determines if the specified edge was covered during any
	 * of the program executions.
	 *
	 * @param anEdge edge in the graph.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage could not be determined.
	 */
	public Boolean wasCovered(Edge anEdge) {

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(anEdge, execIDs[i]);
			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}
		return new Boolean(false);

	}

	/**
	 * Determines if the specified edge was covered through an object
	 * of the given class name.
	 *
	 * @param anEdge edge in the graph
	 * @param objClassName class name of object.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage could not be determined.
	 */
	public Boolean wasCovered(Edge anEdge, String objClassName) {

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(anEdge, objClassName, execIDs[i]);
			if (b == null)
				return null;
			else if (b.booleanValue())
				return b;
		}
		
		return new Boolean(false);
	}

	/**
	 * Returns the class names of the objects that
	 * are traversed when the specified edge
	 * is covered during any execution.
	 *
	 * @param anEdge an Edge.
	 * @return class names of objects.
	 */
	public String[] getCoveredObjects(Edge anEdge) {

		HashSet covSet = new HashSet();

		for (int i = 0; i < execIDs.length; i++) {
			String[] objs = getCoveredObjectsIn(anEdge, execIDs[i]);
			if (objs == null)
				return null;
			else
				covSet.addAll(Arrays.asList(objs));
		}

		return (String[])covSet.toArray(new String[0]);
	}

	/**
	 * Returns the class names of the objects that
	 * are traversed when the given edge
	 * is covered during the specifed execution.
	 *
	 * @param anEdge an Edge.
	 * @param execID execution identifier.
	 * @return class names of covered objects.
	 */
	public String[] getCoveredObjectsIn(Edge anEdge, String execID) {

		if (execID == null)
			return getCoveredObjects(anEdge);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return null;

		JABAExecCoverage coverage = execCovs[index.intValue()];

		return coverage.getCoveredObjs(anEdge);
	}

	/**
	 * Determines the program executions in which the
	 * specified edge was covered.
	 *
	 * @param anEdge edge in the graph.
	 * @return the names of all	executions in which the edge was covered.
	 */
	public String[] getCoveredExecs(Edge anEdge) {

		List execs = new ArrayList();

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(anEdge, execIDs[i]);
			if (b == null)
				return null;
			else if (b.booleanValue())
				execs.add(execIDs[i]);
		}
		
		return (String [])execs.toArray(new String[0]);
	}

	/**
	 * Determines the program executions in which the
	 * specified edge was covered through an object of the given
	 * class name.
	 *
	 * @param anEdge edge in the graph.
	 * @param objClassName class name of object.
	 * @return the names of all executions in which the edge was covered.
	 */
	public String[] getCoveredExecs(Edge anEdge, String objClassName) {

		List execs = new ArrayList();

		for (int i = 0; i < execIDs.length; i++) {
			Boolean b = wasCoveredIn(anEdge, objClassName, execIDs[i]);
			if (b == null)
				return null;
			else if (b.booleanValue())
				execs.add(execIDs[i]);
		}
		
		return (String[])execs.toArray(new String[0]);
	}

	/**	
	 * Determines if the specified edge was covered during the
	 * specified program execution.
	 *
	 * @param anEdge edge in the graph.
	 * @param execID name of execution.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage could not be determined.
	 */
	public Boolean wasCoveredIn(Edge anEdge, String execID) {

		if (execID == null)
			return wasCovered(anEdge);

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return new Boolean(false);

		JABAExecCoverage coverage = execCovs[index.intValue()];

		return coverage.wasCovered(anEdge);

	}

	/**	
	 * Determines if the specified edge was covered through an object of the
	 * given class name in the specified execution.
	 *
	 * @param anEdge edge in the graph.
	 * @param objClassName class name of object.
	 * @param execID name of execution.
	 * @return a Boolean object specifying true or false,
	 * 			or null if coverage could not be determined.
	 */
	public Boolean wasCoveredIn(
		Edge anEdge,
		String objClassName,
		String execID) {
	
		if (execID == null)
			return wasCovered(anEdge, objClassName);
	
		Integer index = (Integer) execIndex.get(execID);
	
		if (index == null)
			return new Boolean(false);
	
		JABAExecCoverage coverage = execCovs[index.intValue()];
	
		return coverage.wasCovered(anEdge, objClassName);
	
	}

	/**
	 * Checks for new executions and adds them along
	 * with their coverage to this JABACoverage object.
	 *
	 * @return the names of the new executions.
	 */
	public String[] refresh() {

		List newExecNames = new ArrayList();

		//determine all execution IDs and store them into an array
		String[] execDirs = 
			(String[])getExecDirs(instrDir, "").toArray(new String[0]);
		String[] newExecIDs = new String[execDirs.length];
		JABAExecCoverage[] newMappers = new JABAExecCoverage[execDirs.length];

		//copy old exec dirs and execCovs to temp arrays
		for (int i = 0; i < execIDs.length; i++) {
			newExecIDs[i] = execIDs[i];
			newMappers[i] = execCovs[i];
		}

		//add any new execs
		int curIndex = execIDs.length;
		String curExecID;
		for (int i = 0; i < execDirs.length; i++) {
			
			curExecID =	execDirs[i].substring(0, execDirs[i].length() - 3);
			if (execIndex.get(curExecID) != null)
				continue;

			newExecIDs[curIndex] = curExecID;
			newMappers[curIndex] =
				new JABAExecCoverage(this,
					instrDir.getPath() + dirSep + curExecID + ".xd");
			execIndex.put(curExecID, new Integer(curIndex));
			curIndex++;

			newExecNames.add(curExecID);
		}

		//set execCovs and execs to new values
		execCovs = newMappers;
		execIDs = newExecIDs;

		return (String[])newExecNames.toArray(new String[0]);
	}

	/**
	 * Reloads available coverage information.
	 *
	 * @return Array of execution names.
	 */
	public String[] reset() {

		reloadIDs();

		String[] execDirs = 
			(String[])getExecDirs(instrDir, "").toArray(new String[0]);
		execIDs = new String[execDirs.length];
		execCovs = new JABAExecCoverage[execDirs.length];
		execIndex = new HashMap();
		for (int i = 0; i < execDirs.length; i++) {
			execIDs[i] = execDirs[i].substring(0, execDirs[i].length() - 3);
			execCovs[i] = new JABAExecCoverage(this, instrDir.getPath() + dirSep 
													+ execIDs[i] + ".xd");
			execIndex.put(execIDs[i], new Integer(i));
		}

		return execIDs;
	}
} // JABACoverage
