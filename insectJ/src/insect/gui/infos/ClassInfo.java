package insect.gui.infos;

import insect.coverage.analysis.jaba.JABACoverage;
import insect.gui.InsectGUI;
import jaba.sym.Method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

/**
 * ClassInfo.java
 *
 *
 * Created: Sun Sep 29 20:41:03 2002
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */
public class ClassInfo extends InsectGUIInfo {

	//attributes
	private String name;
	private MethodInfo[] methods;
	private JABACoverage cov;
	private HashMap execs;
	private File sourceFile;

	//info to build source view
	private TreeMap methodPos;
	private HashMap callTargets;
	private HashMap excepTargets;

	/**
	 * Constructor to build a ClassInfo based on
	 * JABA class object.
	 *
	 * @param jClass a <code>jaba.sym.Class</code> value
	 * @param cov program coverage object
	 */
	public ClassInfo(jaba.sym.Class jClass, JABACoverage cov) {

		name = jClass.getName();
		execs = new HashMap();
		this.cov = cov;

		//build MethodInfos for all methods in this class
		Method[] allMethods = jClass.getMethods();
		ArrayList m = new ArrayList();
		for (int i = 0; i < allMethods.length; i++)
			m.add(new MethodInfo(allMethods[i], this, cov));

		methods = (MethodInfo[]) m.toArray(new MethodInfo[m.size()]);

	}

	/**
	 * Computes coverage for this class.
	 *
	 * @param execID execution to compute coverage for
	 * @param recompute if coverage should be recomputed
	 */
	public void compute(String execID, boolean recompute) {

		ExecInfo ei = getExec(execID);
		if ((ei != null) && (!recompute)) {
			numSt = ei.numSt;
			numCalls = ei.numCalls;
			numThrows = ei.numThrows;
			numCaught = ei.numCaught;
			covSt = ei.covSt;
			covCalls = ei.covCalls;
			covThrows = ei.covThrows;
			covCaught = ei.covCaught;
			return;
		}

		Vector linesTemp = new Vector();
		methodPos = new TreeMap();
		callTargets = new HashMap();
		excepTargets = new HashMap();

		//reset counts
		numCalls = numSt = numThrows = numCaught = 0;
		covCalls = covSt = covThrows = covCaught = 0;

		//compute coverage for every method and combine
		int srcLen = 0;
		for (int i = 0; i < methods.length; i++) {

			//get method coverage
			methods[i].compute(execID, recompute);
			byte[] mlines = methods[i].getLines();

			//copy coverage stats
			numSt += methods[i].numSt;
			numCalls += methods[i].numCalls;
			numThrows += methods[i].numThrows;
			numCaught += methods[i].numCaught;
			covSt += methods[i].covSt;
			covCalls += methods[i].covCalls;
			covThrows += methods[i].covThrows;
			covCaught += methods[i].covCaught;

			//determine start of method
			int base = methods[i].getSourceStart();
			methodPos.put(new Integer(base), methods[i].getName());

			//adjust length of source if necessary
			if (methods[i].getSourceEnd() > srcLen)
				srcLen = methods[i].getSourceEnd();
			if (srcLen > (linesTemp.size() - 1))
				linesTemp.setSize(srcLen + 1);

			//copy coverage from method
			for (int j = 0; j < mlines.length; j++)
				linesTemp.set(base + j, new Byte(mlines[j]));

		}

		//copy the coverage into a single array for fast access
		byte[] lines = new byte[srcLen + 1];
		int listSize = linesTemp.size();
		for (int i = 0; i < listSize; i++) {
			if (linesTemp.get(i) != null)
				lines[i] = ((Byte) linesTemp.get(i)).byteValue();
		}

		execs.put(execID, new ClassExecInfo(lines, callTargets, excepTargets));
		//save values for execution
		setExec(execID, numCalls, numSt, numThrows,	numCaught,
					covCalls, covSt, covThrows, covCaught);
	}

	/**
	 * Appropriately modifies a source line containing a
	 * covered call site with HTML tags.
	 *
	 * @param line source line
	 * @param methodName name of method invoked
	 * @param anchor description of MethodInfo to link to
	 * @return modified source line
	 */
	private String notateCallSite(
		String line,
		String methodName,
		String anchor) {

		//if init method
		if (methodName.indexOf('<') > -1)
			methodName = "new ";

		int start = line.indexOf(methodName);

		if (start == -1)
			return line;

		//determine indices of call site
		int cur = line.indexOf('(', start) + 1;
		int openParen = 1;
		while (openParen > 0) {
			if (line.charAt(cur) == '(')
				openParen++;
			else if (line.charAt(cur) == ')')
				openParen--;
			cur++;
			if (cur >= line.length())
				break;
		}

		//if external call, use formatting
		if (anchor.charAt(0) == '*') {
			line =
				line.substring(0, start)
					+ "<a class=\"xcall\" href=\"##"
					+ anchor
					+ "\">"
					+ line.substring(start, cur)
					+ "</a>"
					+ notateCallSite(line.substring(cur), methodName, anchor);
		}
		//otherwise make callsite an actual hyperlink
		else {
			line =
				line.substring(0, start)
					+ "<a class=\"call\" href=\""
					+ anchor
					+ "\">"
					+ line.substring(start, cur)
					+ "</a>"
					+ notateCallSite(line.substring(cur), methodName, anchor);
		}

		return line;
	}

	/**
	 * Appropriately modifies a source line containing a
	 * covered throw/catch site with HTML tags.
	 *
	 * @param line source line
	 * @param match text to match
	 * @return modified source line
	 */
	private String notateExcepSite(String line, String match, String objs) {

		//determine start and end of expression
		int start = line.indexOf(match);
		int end;

		if (start <= 0) {
			return line;
		}

		//determine indices of text to match
		if (match.equals("catch"))
			end = line.lastIndexOf(')', start);
		else
			end = line.indexOf(';', start);
		end = (end >= 0) ? end : line.length() - 1;

		//add HTML formatting
		line =
			line.substring(0, start)
				+ "<a class=\"excep\" href=\"##="
				+ objs
				+ "\">"
				+ line.substring(start, end)
				+ "</a>"
				+ line.substring(end);

		return line;
	}

	/**
	 * Gets the source view for this class.
	 *
	 * @param execID name of execution to display
	 * @return a URL for HTML source view.
	 */
	public URL getSourceView(String execID) {

		ClassExecInfo execInfo = (ClassExecInfo) execs.get(execID);

		//compute coverage if necessary
		if (execInfo == null) {
			compute(execID, true);
			execInfo = (ClassExecInfo) execs.get(execID);
		}

		return createSourceView(execInfo);
	}

	/**
	 * Creates the source view for this class.
	 *
	 * @param execInfo ClassExecInfo containing coverage info
	 * @return URL for HTML source view
	 */
	private URL createSourceView(ClassExecInfo execInfo) {

		//sources not loaded
		if (sourceFile == null)
			return null;

		//get source view if already created
		URL sourceView = execInfo.getSourceView();
		if (sourceView != null)
			return sourceView;

		HashMap callTargets = execInfo.getCallTargets();
		HashMap excepTargets = execInfo.getExcepTargets();
		String nextMethod = null;

		//get first method in class
		int nextPos = -1;
		if (methodPos.size() > 0) {
			Integer key = (Integer) methodPos.firstKey();
			nextMethod = (String) methodPos.get(key);
			nextPos = key.intValue();
			methodPos.remove(key);
		}

		byte[] lines = execInfo.getLines();

		try {

			//use temporary file to store HTML
			String fname = name.replaceAll("/", "").replaceAll(";", "");
			if (fname.length() < 3)
				fname += "xxx";
			File viewFile = File.createTempFile(fname, ".tmp");
			viewFile.deleteOnExit();

			BufferedReader inFile =
				new BufferedReader(new FileReader(sourceFile));
			PrintWriter outFile = new PrintWriter(new FileWriter(viewFile));

			outFile.println("<html>\n<body>\n");

			//traverse through source annotating lines with tags
			String curLine;
			int count = 1;
			while ((curLine = inFile.readLine()) != null) {
				curLine = curLine.replaceAll("<", "&lt;");

				//insert anchor if beginning of a method
				if (count == nextPos) {
					outFile.println("<a name=\"" + nextMethod + "\"></a>");
					if (methodPos.size() > 0) {
						Integer key = (Integer) methodPos.firstKey();
						nextMethod = (String) methodPos.get(key);
						nextPos = key.intValue();
						methodPos.remove(key);
					}
				}

				//for all instrumented lines in source
				if (count < lines.length) {

					//call site
					if ((lines[count] & InsectGUI.CALLSITE) != 0) {

						int split, end;
						String targs =
							(String) callTargets.get(new Integer(count));

						while (targs.charAt(0) != '|') {
							split = targs.indexOf('@');
							end = targs.indexOf('|');
							curLine =
								notateCallSite(
									curLine,
									targs.substring(0, split),
									targs.substring(split + 1, end));
							if (end != targs.length() - 1)
								targs = targs.substring(end + 1);
							else
								targs = targs.substring(end);
						}
					}

					//throw or catch site
					if ((lines[count] & InsectGUI.EXCEPTIONSITE) != 0) {

						String targs =
							(String) excepTargets.get(new Integer(count));
						int split = targs.indexOf('=');
						curLine =
							notateExcepSite(
								curLine,
								targs.substring(0, split),
								targs.substring(split + 1));
					}

					//statement with covered and noncovered elements
					if ((lines[count]
						& (InsectGUI.COVERED | InsectGUI.NOTCOVERED))
						== (InsectGUI.COVERED | InsectGUI.NOTCOVERED)) {
						outFile.println(
							"<pre class=\"mst\">" + curLine + "</pre>");
					}
					//covered statement
					else if ((lines[count] & InsectGUI.COVERED) != 0)
						outFile.println(
							"<pre class=\"cst\">" + curLine + "</pre>");
					//noncovered statement
					else if ((lines[count] & InsectGUI.NOTCOVERED) != 0)
						outFile.println(
							"<pre class=\"ust\">" + curLine + "</pre>");
					//not instrumented
					else
						outFile.println(
							"<pre class=\"ni\">" + curLine + "</pre>");
				}
				count++;
			}

			outFile.println("</body></html>");

			inFile.close();
			outFile.close();

			//store at URL and return
			sourceView = viewFile.toURL();
			execInfo.setSourceView(sourceView);
			return sourceView;

		} catch (IOException ie) {
			System.err.println("Error loading source for class " + name);
			return null;
		}
	}

	/**
	 * Allows a method in this class to add a call target
	 * when it computes its coverage.
	 *
	 * @param srcLine line number
	 * @param target target description
	 */
	protected void addCallTarget(Integer srcLine, String target) {

		if (callTargets == null)
			return;

		String prevTargs = (String) callTargets.get(srcLine);
		if (prevTargs == null)
			prevTargs = "";

		callTargets.put(srcLine, prevTargs + target);
	}

	/**
	 * Allows a method in this class to add a throw or
	 * catch target when it computes its coverage.
	 *
	 * @param srcLine line number
	 * @param target target description
	 */
	protected void addExcepTarget(Integer srcLine, String target) {
		if (excepTargets != null)
			excepTargets.put(srcLine, target);
	}

	/**
	 * @return the name of this class.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return a <code>MethodInfo[]</code> value.
	 */
	public MethodInfo[] getMethodInfos() {
		return methods;
	}

	/**
	 * @param sourceFile a <code>File</code> value.
	 */
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	/**
	 * Returns the immediate name of this class as
	 * a string representation.
	 *
	 * @return a <code>String</code> value
	 */
	public String toString() {
		return name.substring(name.lastIndexOf("/") + 1);
	}

} // ClassInfo
