package insect.coverage.analysis.jaba;

import insect.Debug;
import insect.coverage.analysis.Analysis;
import insect.coverage.analysis.ExecCoverage;
import jaba.graph.Edge;
import jaba.graph.Node;
import jaba.graph.StatementNode;

/**
 * Computes coverage information for entities in a JABA GDG graph by mapping
 * its information to the InsECT interface.  This class is actually backed by
 * the ExecCoverage class.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class JABAExecCoverage {

	/**
	 * Reference to ExecCoverage backing.
	 */
	private ExecCoverage coverage;

	/**
	 * Creates a new JABAExecCoverage instance.
	 *
	 * @param parent parent Analysis object.
	 * @param execDirPath path of execution directory.
	 */
	public JABAExecCoverage(Analysis parent, String execDirPath) {
		coverage = new ExecCoverage(parent, execDirPath);
	}

	/**
	 * Determines if the specified Node was covered.
	 *
	 * @param node a Node.
	 * @return a Boolean specifying true or false, or null on error.
	 */
	public Boolean wasCovered(Node node) {
		// Currently only StatementNode's are instrumented
		 if (node instanceof StatementNode) {
			 return wasCovered((StatementNode)node);
		 }
		 return null;
	}


	/**
	 * Determines if the specified StatementNode was covered.
	 *
	 * @param node a StatementNode.
	 * @return a Boolean specifying true or false, or null on error.
	 */
	public Boolean wasCovered(StatementNode node) {

		// Extract information from node
		StatementNode stNode = (StatementNode) node;
		jaba.sym.Class nodeClass =
			(jaba.sym.Class) stNode.getContainingMethod().getContainingType();
		String className = nodeClass.getName().replaceAll("[/]", ".");
		jaba.sym.Method nodeMethod = stNode.getContainingMethod();
		String methodSig = nodeMethod.getName() + nodeMethod.getDescriptor();
		int lineNum = stNode.getSourceLineNumber();

		return coverage.wasStatementCovered(className, methodSig, lineNum);
	}

	/**
	 * Determines if the specified node was covered through the object with
	 * the given class name. This method currently only applies to throw nodes
	 * and simply invokes the wasThrowCovered() method.
	 *
	 * @param node a statement node
	 * @param objClassName class name of object.
	 * @return a Boolean specifying true or false, or null if not a throw node
	 */
	public Boolean wasCovered(Node node, String objClassName) {

		// Check if throw node
		if ((node instanceof StatementNode)
			&& (((StatementNode) node).getType()
				== StatementNode.THROW_NODE))
			return wasThrowCovered(node, objClassName);

		return null;
	}

	/**
	 * Determines the class names of the objects through which the 
	 * specified node was covered.
	 *
	 * @param node a Node.
	 * @return class names of covered objects.
	 */
	public String[] getCoveredObjs(Node node) {

		// Check if a throw statement 
		if (((StatementNode) node)
			.getAttributeOfType("jaba.graph.ThrowStatementAttribute")
			!= null)
			return getCoveredThrowObjs(node);

		return null;
	}

	/**
	 * Determines the class names of the objects through which the 
	 * specified throw node was covered.
	 *
	 * @param node a throw node.
	 * @return class names of covered objects.
	 */
	public String[] getCoveredThrowObjs(Node node) {

		// Check if a throw statement
		StatementNode throwNode;
		try {
			throwNode = (StatementNode)node;
			if (throwNode.getType() != StatementNode.THROW_NODE) {
				return null;
			}
		}
		catch(ClassCastException e) {
			return null;
		}

		String className = throwNode.getContainingMethod().getContainingType().
								getName().replaceAll("[/]", ".");
		return coverage.getCoveredThrowObjects(className,
							 throwNode.getSourceLineNumber());		
	}

	/**
	 * Determines if the specified throw node was covered.
	 *
	 * @param node a throw Node.
	 * @return Boolean specifying true or false, or null if not a throw node.
	 */
	public Boolean wasThrowCovered(Node node) {
		
		StatementNode throwNode = (StatementNode)node;

		String className = throwNode.getContainingMethod().getContainingType().
								getName().replaceAll("[/]", ".");
		return coverage.wasThrowCovered(className, 
							throwNode.getSourceLineNumber());
	}

	/**
	 * Determines if the specified throw node was covered through the object
	 * with the given class name.
	 *
	 * @param node a throw node.
	 * @param objClassName class name of object.
	 * @return Boolean specifying true or false, or null if not a throw node.
	 */
	public Boolean wasThrowCovered(Node node, String objClassName) {

		// Check if a throw statement
		StatementNode throwNode = (StatementNode)node;
				
		String className = throwNode.getContainingMethod().getContainingType().
								getName().replaceAll("[/]", ".");
		return coverage.wasThrowCovered(className, 
							throwNode.getSourceLineNumber(), objClassName);
	}

	/**
	 * Determines if the specified edge was covered based on its type.
	 *
	 * @param edge an edge in the graph
	 * @return a Boolean specifying true or false,
	 * 			 or null if could not be determined
	 */
	public Boolean wasCovered(Edge edge) {

		// Check if call edge
		if (edge.getAttributeOfType(
			"jaba.graph.MethodCallEdgeAttribute") != null) {
			return wasCallCovered(edge);
		}
		
		// Check if exception edge
		else if (edge.getAttributeOfType(
				"jaba.tools.graph.CaughtEdgeAttribute") != null) {
			return wasCatchCovered(edge);
		}
		
		else if (edge.getSource() instanceof StatementNode) {
		
				// Check if entry node; if so return coverage of sink
				if (((StatementNode) edge.getSource()).getType()
					== StatementNode.ENTRY_NODE) {
					return wasCovered(edge.getSink());
				}
				
				// Check if catch edge; if so return coverage 
				// of first statement in catch block
				else if (
					((StatementNode) edge.getSource()).getType()
						== StatementNode.CATCH_NODE) {
					return wasCovered(edge.getSink());
				}
				
				// Check if source and sink nodes both correspond to
				// statements in code; if so, return coverage for the
				// joining edge
				else if (
					(edge.getSink() instanceof StatementNode)
						&& (((StatementNode) edge.getSource())
							.getByteCodeOffset()
							>= 0)
						&& (((StatementNode) edge.getSink())
							.getByteCodeOffset()
							>= 0)) {
					return wasEdgeCovered(edge);
				}
		}
		return null;
	}

	/**
	 * Determines if the specified edge was covered through an object
	 * with the given class name. This method currently only applies to call 
	 * and catch edges.
	 *
	 * @param edge an edge.
	 * @param objClassName class name of object.
	 * @return a Boolean specifying true or false, or null on error.
	 */
	public Boolean wasCovered(Edge edge, String objClassName) {

		// Check if call edge
		if (edge.getAttributeOfType(
				"jaba.graph.MethodCallEdgeAttribute") != null) {
			return wasCallCovered(edge, objClassName);
		}
		else if (edge.getAttributeOfType(
				"jaba.tools.graph.CaughtEdgeAttribute")	!= null) {
			return wasCatchCovered(edge, objClassName);
		}

		return null;
	}

	/**
	 * Determines the class names of the objects through which the specified
	 * edge was covered.
	 *
	 * @param edge an Edge.
	 * @return class names of covered objects.
	 */
	public String[] getCoveredObjs(Edge edge) {

		//check if call edge
		if (edge.getAttributeOfType(
				"jaba.graph.MethodCallEdgeAttribute") != null) {
			return getCoveredCallObjects(edge);
		}
		else if (edge.getAttributeOfType(
				"jaba.tools.graph.CaughtEdgeAttribute") != null) {
			return getCoveredCatchObjs(edge);
		}

		return null;
	}

	/**
	 * Determines if the specified generic statement-to-statement
	 * edge was covered.  If the source statement is a predicate,
	 * branch coverage is taken into account. Otherwise, it is
	 * assumed that the edge is covered if both source and sink nodes
	 * are.
	 *
	 * @param edge statement-to-statement edge
	 * @return a Boolean specifying true or false, or null on error
	 */
	public Boolean wasEdgeCovered(Edge edge) {

		StatementNode source = (StatementNode) edge.getSource();

		// If not a predicate, edge coverage can be determined by checking if
		// its source and sink nodes were covered
		if (source.getType() != StatementNode.PREDICATE_NODE) {
			if (wasCovered(edge.getSink()) == null) {
				Debug.println(
				"Warning: no coverage information for the sink of edge\n\t"
					+ edge.toString(), 3);
				return null;
			}
			else if (wasCovered(source) == null) {
				Debug.println(
				"Warning: no coverage information for the source of edge\n\t"
					+ edge.toString(),	3);
				return null;
			}
			else {
				return Boolean.valueOf(wasCovered(source).booleanValue() 
						&& wasCovered(edge.getSink()).booleanValue());
			}
		}

		// Otherwise get the coverage for the branch label
		String className =
			source.getContainingMethod().
					getContainingType().getName().replaceAll(
					"[/]", ".");
		return coverage.wasBranchCovered(className,
										source.getSourceLineNumber(),
										edge.getLabel());
	}

	/**
	 * Determines the class names of the objects through which the specified
	 * catch edge was covered.
	 *
	 * @param  edge a call edge.
	 * @return class names of the covered objects.
	 */
	public String[] getCoveredCatchObjs(Edge edge) {

		// Get info about catch block
		StatementNode sink = (StatementNode)edge.getSink();
		String className = sink.getContainingMethod().
						 		getContainingType().getName().replaceAll(
						 		"[/]", ".");
		return coverage.
			getCoveredCatchObjects(className, sink.getSourceLineNumber());
	}

	/**
	 * Determines if the specified catch edge was covered.
	 *
	 * @param edge a catch edge.
	 * @return a Boolean specifying true or false, or null on error.
	 */
	public Boolean wasCatchCovered(Edge edge) {

		// Get info about catch block
		StatementNode sink = (StatementNode)edge.getSink();
		String className = sink.getContainingMethod().
								getContainingType().getName().replaceAll(
								"[/]", ".");
		return coverage.wasCatchCovered(className, sink.getSourceLineNumber());
	}

	/**
	 * Determines if the specified catch edge was covered through an object
	 * with the given class name.
	 *
	 * @param edge an exception edge.
	 * @param objClassName class name of object.
	 * @return a Boolean specifying true or false, or null on error.
	 */
	public Boolean wasCatchCovered(Edge edge, String objClassName) {

		// Get info about catch block
		StatementNode sink = (StatementNode)edge.getSink();
		String className = sink.getContainingMethod().
								getContainingType().getName().replaceAll(
								"[/]", ".");
		return coverage.wasCatchCovered(className, 
						sink.getSourceLineNumber(), objClassName);
	}

	/**
	 * Determines if the specified call edge was covered.
	 *
	 * @param  edge a call edge.
	 * @return a Boolean specifying true or false, or null on error.
	 */
	public Boolean wasCallCovered(Edge edge) {

		StatementNode source = (StatementNode)edge.getSource();
		StatementNode sink = (StatementNode)edge.getSink();
		
		// Determine signatures for each method
		jaba.sym.Method sourceMethod = source.getContainingMethod();
		String sourceSig =
			sourceMethod.getContainingType().getName().replaceAll("[/]", ".") 
			+ "." + sourceMethod.getName() + sourceMethod.getDescriptor();
		jaba.sym.Method sinkMethod = sink.getContainingMethod();
		String sinkSig =
			sinkMethod.getContainingType().getName().replaceAll("[/]", ".") 
			+ "." + sinkMethod.getName() + sinkMethod.getDescriptor();
		
		return coverage.wasCallCovered(sourceSig, sinkSig,
										source.getSourceLineNumber());	
	}

	/**
	 * Determines whether or not the specified call edge was
	 * covered through an object of the given class name.
	 *
	 * @param edge a call edge.
	 * @param objClassName class name of the object.
	 * @return a Boolean specifying true or false, or null on error.
	 */
	public Boolean wasCallCovered(Edge edge, String objClassName) {

		StatementNode source = (StatementNode)edge.getSource();
		StatementNode sink = (StatementNode)edge.getSink();
		
		// Determine signatures for each method
		jaba.sym.Method sourceMethod = source.getContainingMethod();
		String sourceSig =
			sourceMethod.getContainingType().getName().replaceAll("[/]", ".") 
			+ "." + sourceMethod.getName() + sourceMethod.getDescriptor();
		jaba.sym.Method sinkMethod = sink.getContainingMethod();
		String sinkSig =
			sinkMethod.getContainingType().getName().replaceAll("[/]", ".") 
			+ "." + sinkMethod.getName() + sinkMethod.getDescriptor();
		
		return coverage.wasCallCovered(sourceSig, sinkSig,
							source.getSourceLineNumber(), 
							objClassName);
	}

	/**
	 * Determines the class names of the objects through which the specified
	 * call edge was covered.
	 *
	 * @param edge a call edge.
	 * @return class names of the covered objects.
	 */
	public String[] getCoveredCallObjects(Edge edge) {

		StatementNode source = (StatementNode)edge.getSource();
		StatementNode sink = (StatementNode)edge.getSink();
		
		// Determine signatures for each method
		jaba.sym.Method sourceMethod = source.getContainingMethod();
		String sourceSig =
			sourceMethod.getContainingType().getName().replaceAll("[/]", ".") 
			+ "." + sourceMethod.getName() + sourceMethod.getDescriptor();
		jaba.sym.Method sinkMethod = sink.getContainingMethod();
		String sinkSig =
			sinkMethod.getContainingType().getName().replaceAll("[/]", ".") 
			+ "." + sinkMethod.getName() + sinkMethod.getDescriptor();
			
		return coverage.getCoveredCallObjects(sourceSig, sinkSig,
												source.getSourceLineNumber());
	}

} // JABAExecCoverage