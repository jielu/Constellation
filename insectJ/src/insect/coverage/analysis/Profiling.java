/*
 * Created on Sep 7, 2003
 */
package insect.coverage.analysis;

import java.util.HashMap;

/**
 * Provides profile information regarding executions of an instrumented
 * program.
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
public class Profiling extends Analysis {

	// Profilers for each individual execution.
	private ExecProfiling[] execProfs;
	
	/**
	 * Creates a new Profiling object for the program contained in the given
	 * resource file.
	 * 
	 * @param rcPath filepath of resource file.
	 */
	public Profiling(String rcPath) {
		
		super(rcPath);
		
		// Determine executions and create analyzers for each execution
		execIDs = (String[]) getExecDirs(instrDir, "").toArray(new String[0]);
		execProfs = new ExecProfiling[execIDs.length];
		execIndex = new HashMap();
		for (int i = 0; i < execIDs.length; i++) {
			execProfs[i] =	new ExecProfiling(this,
									instrDir.getPath() + dirSep + execIDs[i]);
			execIDs[i] = execIDs[i].substring(0, execIDs[i].length() - 3);
			execIndex.put(execIDs[i], new Integer(i));
		}
	}

	/**
	 * Determines the total number of times the specified statement was
	 * executed across all executions.
	 * 
	 * Use timesStatemedExecuted(String, String, int)
	 * for efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return number of times, or -1 on error.
	 */
	public long timesStatementExecuted(String className, int srcLine) {
		
		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesStatementExecutedIn(className, srcLine, execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;	
	}
	
	/**
	 * Determines the average number of times the specified statement was
	 * executed across all executions.
	 * 
	 * Use avgTimesStatemedExecuted(String, String, int)
	 * for efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesStatementExecuted(String className, int srcLine) {
		
		long total = timesStatementExecuted(className, srcLine);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}
	
	/**
	 * Determines the execution in which the specified statement was
	 * least executed.
	 * 
	 * Use execInStatemedLeastExecuted(String, String, int)
	 * for efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return execution identifier, or null on error.
	 */
	public String execInStatementLeastExecuted(String className, int srcLine) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesStatementExecutedIn(className, srcLine, execIDs[i]);
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
	 * Determines the execution in which the specified statement was
	 * most executed.
	 * 
	 * Use execInStatemedLeastExecuted(String, String, int)
	 * for efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return execution identifier, or null on error.
	 */
	public String execInStatementMostExecuted(String className, int srcLine) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesStatementExecutedIn(className, srcLine, execIDs[i]);
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
	 * Determines the number of times the specified statement was executed
	 * in the specified execution.
	 *
	 * Use timesStatemedExecutedIn(String, String, int, String)
	 * for efficiency if the containing method is known.
	 *
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error.
	 */
	public long timesStatementExecutedIn(String className,
										int srcLine, String execID) {

		if (execID == null) {
			return timesStatementExecuted(className, srcLine);
		}
		
		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		ExecProfiling ep = execProfs[index.intValue()];
		return ep.timesStatementExecuted(className, srcLine);
	}
	
	/**
	 * Determines the total number of times the specified statement was
	 * executed.
	 * 
	 * @param className fully qualified name of class.
	 * @param methodSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @return number of times, or -1 on error.
	 */
	public long timesStatementExecuted(String className, String methodSig,
										int srcLine) {
		
		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesStatementExecutedIn(className, methodSig,
					 srcLine, execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;	
	}

	/**
	 * Determines the average number of times the specified statement was
	 * executed across all executions.
	 * 
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesStatementExecuted(String className, String methodSig, 
																int srcLine) {
		
		long total = timesStatementExecuted(className, methodSig, srcLine);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}

	/**
	 * Determines the execution in which the specified statement was
	 * least executed.
	 * 
	 * @param className fully qualified name of class.
	 * @param methodSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @return execution identifier, or null on error.
	 */
	public String execInStatementLeastExecuted(String className,
									String methodSig, int srcLine) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesStatementExecutedIn(className, methodSig,
											srcLine, execIDs[i]);
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
	 * Determines the execution in which the specified statement was
	 * most executed.
	 * 
	 * @param className fully qualified name of class.
	 * @param methodSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @return execution identifier, or null on error.
	 */
	public String execInStatementMostExecuted(String className,
									String methodSig, int srcLine) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesStatementExecutedIn(className, methodSig,
											srcLine, execIDs[i]);
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
	 * Determines the number of times the specified statement was executed
	 * in the specified execution.
	 *
	 * @param className fully qualified name of class.
	 * @param methodSig method signature (see class javadoc above).
	 * @param srcLine source line of statement.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error.
	 */
	public long timesStatementExecutedIn(String className, String methodSig,
												int srcLine, String execID) {

		if (execID == null) {
			return timesStatementExecuted(className, methodSig, srcLine);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		ExecProfiling ep = execProfs[index.intValue()];
		return ep.timesStatementExecuted(className, methodSig, srcLine);
	}

	/**
	 * Determines the total number of times the specified method call was
	 * executed across all executions.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @return number of times, or -1 on error.
	 */
	public long timesCallExecuted(String sourceSig, String targetSig,
									int srcLine) {
		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = timesCallExecutedIn(sourceSig, targetSig,
											srcLine, execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;	
	}

	/**
	 * Determines the average number of times the specified method call was
	 * executed across all executions.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesCallExecuted(String sourceSig, String targetSig, 
										int srcLine) {
		
		long total = timesCallExecuted(sourceSig, targetSig, srcLine);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}

	/**
	 * Determines the execution in which the specified call was
	 * least executed.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @return execution identifier, or null on error.
	 */
	public String execInCallLeastExecuted(String sourceSig, String targetSig,
																int srcLine) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesCallExecutedIn(sourceSig, targetSig,
									srcLine, execIDs[i]);
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
	 * Determines the execution in which the specified call was
	 * most executed.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @return execution identifier, or null on error.
	 */
	public String execInCallMostExecuted(String sourceSig, String targetSig,
																int srcLine) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesCallExecutedIn(sourceSig, targetSig,
									srcLine, execIDs[i]);
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
	 * Determines the number of times the specified method call was executed
	 * in the specified execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error
	 */
	public long timesCallExecutedIn(String sourceSig, String targetSig,
											int srcLine, String execID) {

		if (execID == null) {
			return timesCallExecuted(sourceSig, targetSig, srcLine);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		ExecProfiling ep = execProfs[index.intValue()];
		return ep.timesCallExecuted(sourceSig, targetSig, srcLine);
	}
	
	/**
	 * Determines the total number of times the specified method call was
	 * executed across all executions.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @param objectClass class name of object on which method was invoked.
	 * @return number of times, or -1 on error.
	 */
	public long timesCallExecuted(String sourceSig, String targetSig,
									int srcLine, String objectClass) {
		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = timesCallExecutedIn(sourceSig, targetSig,
									srcLine, objectClass,execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;	
	}

	/**
	 * Determines the average number of times the specified method call was
	 * executed across all executions.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @param objectClass class name of object on which method was invoked.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesCallExecuted(String sourceSig, String targetSig, 
										int srcLine, String objectClass) {
		
		long total = timesCallExecuted(sourceSig, targetSig,
										srcLine, objectClass);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}

	/**
	 * Determines the execution in which the specified call was
	 * least executed.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @param objectClass class name of object on which method was invoked.
	 * @return execution identifier, or null on error.
	 */
	public String execInCallLeastExecuted(String sourceSig, String targetSig,
										int srcLine, String objectClass) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesCallExecutedIn(sourceSig, targetSig,
									srcLine, objectClass, execIDs[i]);
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
	 * Determines the execution in which the specified call was
	 * most executed.
	 * 
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line number.
	 * @param objectClass class name of object on which method was invoked.
	 * @return execution identifier, or null on error.
	 */
	public String execInCallMostExecuted(String sourceSig, String targetSig,
											int srcLine, String objectClass) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesCallExecutedIn(sourceSig, targetSig,
									srcLine, objectClass, execIDs[i]);
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
	 * Determines the number of times the specified method call was covered
	 * through the specified object type in the given execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param objectClass class name of object on which method was invoked.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error
	 */
	public long timesCallExecutedIn(String sourceSig, String targetSig,
							int srcLine, String objectClass, String execID) {

		if (execID == null) {
			return timesCallExecuted(sourceSig, targetSig, srcLine, objectClass);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		ExecProfiling ep = execProfs[index.intValue()];
		return ep.timesCallExecuted(sourceSig, targetSig, srcLine, objectClass);
	}
	
	/**
	 * Determines the total number of times the specified method call was
	 * executed across all executions.
	 * 
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param label branch label, i.e., "T" or "F" for an if statement.
	 * @return number of times, or -1 on error.
	 */
	public long timesBranchExecuted(String className,
									int srcLine, String label) {
		long total = 0;		
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesBranchExecutedIn(className, srcLine,label, execIDs[i]);
			if (times < 0) {
				return -1;
			}
			total += times;
		}
		
		return total;	
	}

	/**
	 * Determines the average number of times the specified branch label was
	 * taken across all executions.
	 * 
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param label branch label, i.e., "T" or "F" for an if statement.
	 * @return number of times, or -1 on error.
	 */
	public long avgTimesBranchExecuted(String className, 
										int srcLine, String label) {
		
		long total = timesBranchExecuted(className, srcLine, label);
		if (total < 0) {
			return -1;		
		}
		else {
			return total / execIDs.length;
		}		
	}

	/**
	 * Determines the execution in which the specified branch label was
	 * taken least.
	 * 
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param label branch label, i.e., "T" or "F" for an if statement.
	 * @return execution identifier, or null on error.
	 */
	public String execInBranchLeastExecuted(String className,
											int srcLine, String label) {
		
		long min = Long.MAX_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesBranchExecutedIn(className, srcLine, label, execIDs[i]);
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
	 * Determines the execution in which the specified branch label was
	 * taken most.
	 * 
	 * @param className fully qualified name of class.
	 * @param srcLine source line number.
	 * @param label branch label, i.e., "T" or "F" for an if statement.
	 * @return execution identifier, or null on error.
	 */
	public String execInBranchMostExecuted(String className,
											int srcLine, String label) {
		
		long max = Long.MIN_VALUE;
		String exec = null;
				
		for (int i = 0; i < execIDs.length; i++) {
			long times = 
				timesBranchExecutedIn(className, srcLine, label, execIDs[i]);
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
	 * Determines the number of times the specified method call was executed
	 * in the specified execution.
	 *
	 * @param sourceSig method signature (see class javadoc above).
	 * @param targetSig method signature (see class javadoc above).
	 * @param srcLine source line of method call.
	 * @param execID identifier for execution.
	 * @return number of times, or -1 on error
	 */
	public long timesBranchExecutedIn(String className, int srcLine,
										String label, String execID) {

		if (execID == null) {
			return timesBranchExecuted(className, srcLine, label);
		}

		Integer index = (Integer) execIndex.get(execID);

		if (index == null)
			return -1;

		ExecProfiling ep = execProfs[index.intValue()];
		return ep.timesBranchExecuted(className, srcLine, label);
	}
	
}