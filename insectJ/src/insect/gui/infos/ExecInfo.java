package insect.gui.infos;

/**
 * ExecInfo.java
 *
 *
 * Created: Fri Mar 21 16:38:23 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */

public class ExecInfo {

	/**
	 * Contains number of occurrences of this code aspect
	 * in this execution.
	 *
	 */
	public int numCalls, numSt, numThrows, numCaught;

	/**
	 * Contains number of covered occurrences of this code aspect
	 * in this execution.
	 *
	 */
	public int covCalls, covSt, covThrows, covCaught;

} // ExecInfo
