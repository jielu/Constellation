package insect.gui.infos;

import java.net.URL;
import java.util.HashMap;

/**
 * ClassExecInfo.java
 *
 *
 * Created: Sun Oct 06 21:30:17 2002
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ClassExecInfo {

	//attributes
	private byte[] lines;
	private HashMap callTargets;
	private HashMap excepTargets;
	private URL sourceView;

	/**
	 * Creates a new <code>ClassExecInfo</code> instance
	 * with the specified attributes.
	 *
	 * @param lines a <code>byte[]</code> value
	 * @param callTargets a <code>HashMap</code> value
	 * @param excepTargets a <code>HashMap</code> value
	 */
	public ClassExecInfo(byte[] lines,
						HashMap callTargets, HashMap excepTargets) {
		this.lines = lines;
		this.callTargets = callTargets;
		this.excepTargets = excepTargets;
	}

	/**
	 * @return value of sourceView.
	 */
	public URL getSourceView() {
		return sourceView;
	}

	/**
	 * Set the value of sourceFile.
	 * @param v  Value to assign to sourceFile.
	 */
	public void setSourceView(URL v) {
		this.sourceView = v;
	}

	/**
	 * @return a byte array describing the source lines.
	 */
	public byte[] getLines() {
		return lines;
	}

	/**
	 * @return returns a mapping of source lines to method calls.
	 */
	public HashMap getCallTargets() {
		return callTargets;
	}

	/**
	 * @return a mapping of source lines to exception constructs.
	 */
	public HashMap getExcepTargets() {
		return excepTargets;
	}

} // ClassExecInfo
