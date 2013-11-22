package insect.gui.infos;

import insect.coverage.analysis.jaba.JABACoverage;
import insect.gui.InsectGUI;
import insect.gui.frames.ProgramTreeFrame;
import jaba.graph.Edge;
import jaba.graph.MethodCallEdgeAttribute;
import jaba.graph.Node;
import jaba.graph.StatementNode;
import jaba.graph.ThrowStatementAttribute;
import jaba.sym.Method;
import jaba.tools.graph.GDG;
import jaba.tools.local.Factory;

import java.util.HashMap;
import java.util.Iterator;

/**
 * MethodInfo.java
 *
 *
 * Created: Wed Sep 25 18:37:31 2002
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class MethodInfo extends InsectGUIInfo {

	//attributes
	private String name;
	private Method method;
	private ClassInfo classInfo;
	private JABACoverage cov;
	private byte[] lines;
	protected int srcStart, srcEnd;

	/**
	 * Constructor to build a MethodInfo based on a
	 * JABA method object.
	 *
	 * @param method a <code>Method</code> value
	 * @param classInfo a <code>ClassInfo</code> value
	 * @param cov program coverage object
	 */
	public MethodInfo(Method method, ClassInfo classInfo, JABACoverage cov) {

		this.method = method;
		this.classInfo = classInfo;
		this.cov = cov;
		name = method.getSignature();
	}

	public void computeAllExecs(boolean recompute) {

		//total counts
		int tnumCalls, tnumSt, tnumThrows, tnumCaught;
		int tcovCalls, tcovSt, tcovThrows, tcovCaught;
		tnumCalls = tnumSt = tnumThrows = tnumCaught = 0;
		tcovCalls = tcovSt = tcovThrows = tcovCaught = 0;

		//reset counts
		numCalls = numSt = numThrows = numCaught = 0;
		covCalls = covSt = covThrows = covCaught = 0;

		String execIDs[] = cov.getExecIDs();
		int numexecs = execIDs.length;

		// compute coverage for all executions
		for (int i = 0; i < numexecs; i++) {
			compute(execIDs[i], false);

			//sum coverage stats
			tnumSt += numSt;
			tnumCalls += numCalls;
			tnumThrows += numThrows;
			tnumCaught += numCaught;
			tcovSt += covSt;
			tcovCalls += covCalls;
			tcovThrows += covThrows;
			tcovCaught += covCaught;
		}

		setExec(
			ProgramTreeFrame.ALLEXECS,
			tnumCalls,
			tnumSt,
			tnumThrows,
			tnumCaught,
			tcovCalls,
			tcovSt,
			tcovThrows,
			tcovCaught);

		//set totals as current values
		numSt = tnumSt;
		numCalls = tnumCalls;
		numThrows = tnumThrows;
		numCaught = tnumCaught;
		covSt = tcovSt;
		covCalls = tcovCalls;
		covThrows = tcovThrows;
		covCaught = tcovCaught;
	}

	/**
	 * Computes coverage for this class.
	 *
	 * @param execID execution to compute coverage for
	 * @param recompute if coverage should be recomputed
	 */
	public void compute(String execID, boolean recompute) {

		if (method.isAbstract()) {
			lines = new byte[0];
			return;
		}

		//load correct execution
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

		//for all executions, send null as execid
		//so that coverage object will by default
		//return coverage across all executions
		if (execID.equals(ProgramTreeFrame.ALLEXECS))
			execID = null;

		//load DIG for method
		GDG graph = null;
		try {
			graph = Factory.getGDG(method);
		} catch (Exception e) {
			e.printStackTrace();
			lines = new byte[0];
			return;
		}

		Node[] allNodes = graph.getNodes();
		Edge[] allEdges = graph.getEdges();

		//mapping for source line # to coverage
		HashMap lhash = new HashMap();
		srcStart = Integer.MAX_VALUE;
		srcEnd = 0;

		//reset counts
		numCalls = numSt = numThrows = numCaught = 0;
		covCalls = covSt = covThrows = covCaught = 0;

		//traverse nodes
		for (int i = 0; i < allNodes.length; i++) {
			if (allNodes[i] instanceof StatementNode) {
				StatementNode stNode = (StatementNode) allNodes[i];
				int srcLine = stNode.getSourceLineNumber();

				if (srcLine > 0) {

					//determine start/end of method
					if (srcLine < srcStart)
						srcStart = srcLine;
					if (srcLine > srcEnd)
						srcEnd = srcLine;

					//record statement coverage
					numSt++;
					Integer srcInt = new Integer(srcLine);
					Byte stVal = (Byte) lhash.get(srcInt);
					Boolean result = cov.wasCoveredIn(stNode, execID);
					if ((result != null) && (result.booleanValue())) {
						covSt++;
						if (stVal == null)
							stVal = new Byte(InsectGUI.COVERED);
						else
							stVal =
								new Byte(
									(byte) (InsectGUI.COVERED
										| (stVal.byteValue())));
					} else {
						if (stVal == null)
							stVal = new Byte(InsectGUI.NOTCOVERED);
						else
							stVal =
								new Byte(
									(byte) (InsectGUI.NOTCOVERED
										| (stVal.byteValue())));
					}
					lhash.put(srcInt, stVal);

					//check if a throw node
					ThrowStatementAttribute throwAttrib;
					if ((throwAttrib =
						(ThrowStatementAttribute) stNode.getAttributeOfType(
							"jaba.graph.ThrowStatementAttribute"))
						!= null) {

						numThrows++;

						//get coverage
						String[] objNames = cov.getCoveredObjects(stNode);
						if (objNames.length > 0) {

							//build string of object names
							String objList = "";
							for (int j = 0; j < objNames.length; j++) {
								if (!objList.equals(""))
									objList += ", ";
								objList	+= objNames[j].replaceAll("[/]", ".");
							}

							Byte callb =
								new Byte(
									(byte) (stVal.byteValue()
										| InsectGUI.EXCEPTIONSITE));
							lhash.put(srcInt, callb);
							classInfo.addExcepTarget(
								srcInt,
								"throw=" + objList);
						}
					}

				}
			}
		}

		//traverse edges
		try {
			for (int i = 0; i < allEdges.length; i++) {

				//if calledge
				if ((MethodCallEdgeAttribute) allEdges[i]
					.getAttributeOfType("jaba.graph.MethodCallEdgeAttribute")
					!= null) {

					numCalls++;

					String[] objNames = cov.getCoveredObjectsIn(allEdges[i], execID);

					if ((objNames != null) && (objNames.length > 0)) {
						covCalls++;

						//determine list of objects traversed
						String objList = "";
						for (int j = 0; j < objNames.length; j++) {
							if (!objList.equals(""))
								objList += ", ";
							objList	+= objNames[j].replaceAll("/", ".");
						}

						//record call coverage for the source line
						Integer srcLine =
							new Integer(
								((StatementNode) allEdges[i].getSource())
									.getSourceLineNumber());
						Byte callb =
							new Byte(
								(byte) (((Byte) lhash.get(srcLine)).byteValue()
									| InsectGUI.CALLSITE));
						lhash.put(srcLine, callb);

						//notify parent classinfo of callsite
						Method callTarget =
							(Method) (((StatementNode) allEdges[i].getSink())
								.getContainingMethod());
						jaba.sym.Class callClass =
							(jaba.sym.Class) callTarget.getContainingType();

						//if external call append * before method signature
						if (callClass.isSummarized())
							classInfo.addCallTarget(
								srcLine,
								callTarget.getName()
									+ "@*"
									+ callClass.getName()
									+ "."
									+ callTarget.getSignature()
									+ "="
									+ objList
									+ "|");
						else
							classInfo.addCallTarget(
								srcLine,
								callTarget.getName()
									+ "@"
									+ callClass.getName()
									+ "."
									+ callTarget.getSignature()
									+ "="
									+ objList
									+ "|");
					}
				} else if (
					allEdges[i].getAttributeOfType(
						"jaba.tools.graph.CaughtEdgeAttribute")
						!= null) {

					numCaught++;

					String[] objNames = cov.getCoveredObjectsIn(allEdges[i], execID);

					if ((objNames != null) && (objNames.length > 0)) {

						//determine list of objects traversed
						String objList = "";
						for (int j = 0; j < objNames.length; j++) {
							if (!objList.equals(""))
								objList += ", ";
							objList	+= objNames[j].replaceAll("/", ".");
						}

						covCaught++;
						//record call coverage for the source line
						Integer srcLine =
							new Integer(
								((StatementNode) allEdges[i].getSource())
									.getSourceLineNumber());
						Byte callb =
							new Byte(
								(byte) (((Byte) lhash.get(srcLine)).byteValue()
									| InsectGUI.EXCEPTIONSITE));
						lhash.put(srcLine, callb);

						classInfo.addExcepTarget(srcLine, "catch=" + objList);
					}
				}
			}
		} catch (java.lang.IllegalArgumentException iae) {
			iae.printStackTrace();
		}

		//create line table for setting attributes
		lines = new byte[srcEnd - srcStart + 1];

		//copy results of analysis into array representing the source lines
		Iterator it = lhash.keySet().iterator();
		while (it.hasNext()) {
			Integer key = (Integer) it.next();
			int index = key.intValue();
			if ((index >= srcStart) && (index <= srcEnd))
				lines[index - srcStart] = ((Byte) lhash.get(key)).byteValue();
		}

		//could only be null if passed in value was ALLEXECS
		if (execID == null)
			execID = ProgramTreeFrame.ALLEXECS;

		//save values for execution
		setExec(
			execID,
			numCalls,
			numSt,
			numThrows,
			numCaught,
			covCalls,
			covSt,
			covThrows,
			covCaught);
	}

	/**
	 * Returns the name of this method.
	 *
	 * @return a <code>String</code> value
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the ClassInfo for the class that
	 * this method belongs to.
	 *
	 * @return a <code>ClassInfo</code> value
	 */
	public ClassInfo getClassInfo() {
		return classInfo;
	}

	/**
	 * Returns a byte array describing the coverage
	 * of this method.
	 *
	 * @return a <code>byte[]</code> value
	 */
	protected byte[] getLines() {
		return lines;
	}

	/**
	 * Returns the source line for the beginning
	 * of this method.
	 *
	 * @return an <code>int</code> value
	 */
	protected int getSourceStart() {
		return srcStart;
	}

	/**
	 * Returns the source line for the end
	 * of this method.
	 *
	 * @return an <code>int</code> value
	 */
	protected int getSourceEnd() {
		return srcEnd;
	}

	/**
	 * Returns the name of this method.
	 *
	 * @return a <code>String</code> value
	 */
	public String toString() {
		return name;
	}

} // MethodInfo
