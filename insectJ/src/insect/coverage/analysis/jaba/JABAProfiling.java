/*
 * Created on Sep 11, 2003
 */
package insect.coverage.analysis.jaba;

import insect.coverage.analysis.Analysis;
import jaba.graph.Edge;
import jaba.graph.Node;

import java.util.HashMap;

/**
 * Provides profiling information regarding nodes and edges in a program graph.
 * Coverage is determined by mapping information from the executions of an
 * instrumented program to the particular graph type.  This mapping is done
 * using the JABAJABAExecProfiling class.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class JABAProfiling extends Analysis {

	// Profilers for each individual execution.
	private JABAExecProfiling[] execProfs;
	
	/**
	 * Creates a new Profiling object for the program contained in the given
	 * resource file.
	 * 
	 * @param rcPath filepath of resource file.
	 */
	public JABAProfiling(String rcPath) {
		
		super(rcPath);
		
		// Determine executions and create analyzers for each execution
		execIDs = (String[]) getExecDirs(instrDir, "").toArray(new String[0]);
		execProfs = new JABAExecProfiling[execIDs.length];
		execIndex = new HashMap();
		for (int i = 0; i < execIDs.length; i++) {
			execProfs[i] =	new JABAExecProfiling(this,
									instrDir.getPath() + dirSep + execIDs[i]);
			execIDs[i] = execIDs[i].substring(0, execIDs[i].length() - 3);
			execIndex.put(execIDs[i], new Integer(i));
		}
	}

	/**
	 * Determines the total number of times the specified node was executed
	 * across all executions.
	 *
	 * @param node Node in the graph.
	 * @return number of times, or -1 on error.
	 */
	public long timesExecuted(Node node) {

		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(node, execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;
	}

	/**
	 * Determines the average number of times the specified node was
	 * executed across all executions.
	 *
	 * @param node Node in the graph.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesExecuted(Node node) {
		
		long total = timesExecuted(node);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}

	/**
	 * Determines the execution in which the specified node was
	 * least executed.
	 *
	 * @param node Node in the graph.
	 * @return execution identifer, or null on error.
	 */
	public String execInLeastExecuted(Node node) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(node, execIDs[i]);
			if (times < 0) {
				return null;
			}
			else if (times < min) {
				min = times;
				exec = execIDs[i];
			}
		}
		
		return exec;	
	}

	/**
	 * Determines the execution in which the specified node was
	 * most executed.
	 *
	 * @param node Node in the graph.
	 * @return execution identifier, or null on error.
	 */
	public String execInMostExecuted(Node node) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(node, execIDs[i]);
			if (times < 0) {
				return null;
			}
			else if (times > max) {
				max = times;
				exec = execIDs[i];
			}
		}
		
		return exec;	
	}

	/**
	 * Determines the number of times the specified node was executed
	 * in the specified execution.
	 *
	 * @param node Node in the graph.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error.
	 */
	public long timesExecutedIn(Node node, String execID) {

		if (execID == null) {
			return timesExecuted(node);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		JABAExecProfiling ep = execProfs[index.intValue()];
		return ep.timesExecuted(node);
	}
	
	/**
	 * Determines the total number of times the specified edge was executed
	 * across all executions.
	 *
	 * @param edge Edge in the graph.
	 * @return number of times, or -1 on error.
	 */
	public long timesExecuted(Edge edge) {

		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(edge, execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;
	}

	/**
	 * Determines the average number of times the specified edge was
	 * executed across all executions.
	 *
	 * @param edge Edge in the graph.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesExecuted(Edge edge) {
		
		long total = timesExecuted(edge);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}

	/**
	 * Determines the execution in which the specified edge was
	 * least executed.
	 *
	 * @param edge Edge in the graph.
	 * @return execution identifer, or null on error.
	 */
	public String execInLeastExecuted(Edge edge) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(edge, execIDs[i]);
			if (times < 0) {
				return null;
			}
			else if (times < min) {
				min = times;
				exec = execIDs[i];
			}
		}
		
		return exec;	
	}

	/**
	 * Determines the execution in which the specified edge was
	 * most executed.
	 *
	 * @param edge Edge in the graph.
	 * @return execution identifier, or null on error.
	 */
	public String execInMostExecuted(Edge edge) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(edge, execIDs[i]);
			if (times < 0) {
				return null;
			}
			else if (times > max) {
				max = times;
				exec = execIDs[i];
			}
		}
		
		return exec;	
	}
	
	/**
	 * Determines the number of times the specified Edge was executed
	 * in the specified execution.
	 *
	 * @param edge Edge in the graph.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error.
	 */
	public long timesExecutedIn(Edge edge, String execID) {

		if (execID == null) {
			return timesExecuted(edge);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		JABAExecProfiling ep = execProfs[index.intValue()];
		return ep.timesExecuted(edge);
	}

	/**
	 * Determines the total number of times the specified edge was executed
	 * through an object of the given class name across all executions.
	 *
	 * @param edge Edge in the graph.
	 * @param objClassName class name of object.
	 * @return number of times, or -1 on error.
	 */
	public long timesExecuted(Edge edge, String objClassName) {

		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(edge, objClassName, execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;
	}

	/**
	 * Determines the average number of times the specified edge was
	 * executed through an object of the given class name across all
	 * executions.
	 *
	 * @param edge Edge in the graph.
	 * @param objClassName class name of object.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesExecuted(Edge edge, String objClassName) {
		
		long total = timesExecuted(edge, objClassName);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}

	/**
	 * Determines the execution in which the specified edge was
	 * executed through an object of the given class name least.
	 *
	 * @param edge Edge in the graph.
	 * @param objClassName class name of object.
	 * @return execution identifer, or null on error.
	 */
	public String execInLeastExecuted(Edge edge, String objClassName) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(edge, objClassName, execIDs[i]);
			if (times < 0) {
				return null;
			}
			else if (times < min) {
				min = times;
				exec = execIDs[i];
			}
		}
		
		return exec;	
	}

	/**
	 * Determines the execution in which the specified edge was
	 * executed through an object of the given class name most.
	 *
	 * @param edge Edge in the graph.
	 * @param objClassName class name of object.
	 * @return execution identifier, or null on error.
	 */
	public String execInMostExecuted(Edge edge, String objClassName) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesExecutedIn(edge, objClassName, execIDs[i]);
			if (times < 0) {
				return null;
			}
			else if (times > max) {
				max = times;
				exec = execIDs[i];
			}
		}
		
		return exec;	
	}
	
	/**
	 * Determines the number of times the specified Edge was executed
	 * through an object of the given class name in the specified execution.
	 *
	 * @param edge Edge in the graph.
	 * @param objClassName class name of object.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error.
	 */
	public long timesExecutedIn(Edge edge, String objClassName,
								String execID) {

		if (execID == null) {
			return timesExecuted(edge, objClassName);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		JABAExecProfiling ep = execProfs[index.intValue()];
		return ep.timesExecuted(edge, objClassName);
	}

}