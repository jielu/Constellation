package insect.gui.infos;

import java.util.HashMap;

/**
 * InsectGUIInfo.java
 *
 *
 * Created: Tue Mar 18 17:42:45 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */
public abstract class InsectGUIInfo {

	/**
	 * ExecInfos for this item.
	 *
	 */
	private HashMap execInfos;

	/**
	 * Contains number of occurrences of this code aspect.
	 *
	 */
	public int numCalls, numSt, numThrows, numCaught;

	/**
	 * Contains number of covered occurrences of this code aspect.
	 *
	 */
	public int covCalls, covSt, covThrows, covCaught;

	/**
	 * Creates a new <code>InsectGUIInfo</code> instance.
	 *
	 */
	public InsectGUIInfo() {
		execInfos = new HashMap();
	}

	/**
	 * Returns the ExecInfo for the given execution.
	 *
	 * @param execID a <code>String</code> value
	 * @return an <code>ExecInfo</code> value
	 */
	protected ExecInfo getExec(String execID) {
		return (ExecInfo) execInfos.get(execID);
	}

	/**
	 * Sets covered values for the given execution.
	 *
	 * @param execID a <code>String</code> value
	 * @param numCalls an <code>int</code> value
	 * @param numSt an <code>int</code> value
	 * @param numThrows an <code>int</code> value
	 * @param numCaught an <code>int</code> value
	 * @param covCalls an <code>int</code> value
	 * @param covSt an <code>int</code> value
	 * @param covThrows an <code>int</code> value
	 * @param covCaught an <code>int</code> value
	 */
	protected void setExec(String execID, int numCalls, int numSt,
							int numThrows, int numCaught, int covCalls,
							int covSt, int covThrows, int covCaught) {

		ExecInfo ei = (ExecInfo) execInfos.get(execID);
		if (ei == null)
			ei = new ExecInfo();
		ei.numSt = numSt;
		ei.numCalls = numCalls;
		ei.numThrows = numThrows;
		ei.numCaught = numCaught;
		ei.covSt = covSt;
		ei.covCalls = covCalls;
		ei.covThrows = covThrows;
		ei.covCaught = covCaught;
	}

	/**
	 * Computes coverage for this package.
	 *
	 * @param execID execution to compute coverage for
	 * @param recompute if coverage should be recomputed
	 */
	public abstract void compute(String execID, boolean recompute);

	/**
	 * Returns the name of this method.
	 *
	 * @return a <code>String</code> value
	 */
	public abstract String getName();

} // InsectGUIInfo
