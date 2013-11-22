/*
 * Created on Sep 11, 2003
 */
package insect.coverage.analysis.jaba;

import insect.coverage.analysis.Analysis;
import insect.coverage.analysis.ExecProfiling;
import jaba.graph.StatementNode;

/**
 * Computes profiling information for entities in a JABA GDG graph by mapping
 * its information to the InsECT interface.  This class is actually backed by
 * the ExecProfiling class.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class JABAExecProfiling {

	/**
	 * Reference to Execprofiling backing.
	 */
	private ExecProfiling profiling;

	/**
	 * Creates a new JABAExecProfiling instance.
	 *
	 * @param parent parent Analysis object.
	 * @param execDirPath path of execution directory.
	 */
	public JABAExecProfiling(Analysis parent, String execDirPath) {
		profiling = new ExecProfiling(parent, execDirPath);
	}

	/**
	 * Determines the number of times the specified Node was executed.
	 * 
	 * @param node node in the graph.
	 * @return number of times the node was executed, or -1 on error.
	 */
	public long timesExecuted(jaba.graph.Node node) {
		if (node instanceof StatementNode) {
			return timesExecuted((StatementNode)node);
		}
		return -1;
	}

	/**
	 * Determines the number of times the specified StatementNode was
	 * executed.
	 * 
	 * @param stNode StatementNode in the graph.
	 * @return number of times the node was executed, or -1 on error.
	 */
	public long timesExecuted(jaba.graph.StatementNode stNode) {
	
		// Extract information from node
		jaba.sym.Class nodeClass =
			(jaba.sym.Class) stNode.getContainingMethod().getContainingType();
		String className = nodeClass.getName().replaceAll("[/]", ".");
		jaba.sym.Method nodeMethod = stNode.getContainingMethod();
		String methodSig = nodeMethod.getName() + nodeMethod.getDescriptor();
		int lineNum = stNode.getSourceLineNumber();

		return profiling.timesStatementExecuted(className, methodSig, lineNum);	
	}
	
	/**
	 * Determines the number of times the specified Edge was executed.
	 * 
	 * @param edge edge in the graph.
	 * @return number of times the edge was executed, or -1 on error.
	 */
	public long timesExecuted(jaba.graph.Edge edge) {
		
		// Check if call edge
		if (edge.getAttributeOfType(
			"jaba.graph.MethodCallEdgeAttribute") != null) {
			return timesCallExecuted(edge);
		} else {
			
			// Return branch profiling if the edge source is a 
			// branch predicate
			if ((edge.getSource() instanceof StatementNode)
				&& (((StatementNode)edge.getSource()).getType() 
					== StatementNode.PREDICATE_NODE)) {
				return timesBranchExecuted(edge);			
			}
					
		}
		return -1;
	}
	
	/**
	 * Determines the number of times the specified Edge was executed
	 * through an object of the given class name.
	 * 
	 * @param edge edge in the graph.
	 * @param objClassName class name of object.
	 * @return number of times the edge was executed, or -1 on error.
	 */
	public long timesExecuted(jaba.graph.Edge edge, String objClassName) {
		
		// Check if call edge
		if (edge.getAttributeOfType(
			"jaba.graph.MethodCallEdgeAttribute") != null) {
			return timesCallExecuted(edge, objClassName);
		}
		return -1;
	}

	/**
	 * Determines the number of times the specified method call Edge was
	 * executed.
	 * 
	 * @param edge method call edge in the graph.
	 * @return number of times the edge was executed, or -1 on error.
	 */
	public long timesCallExecuted(jaba.graph.Edge edge){
		
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
		
		return profiling.timesCallExecuted(sourceSig, sinkSig,
											source.getSourceLineNumber());
	}

	/**
	 * Determines the number of times the specified method call Edge was
	 * executed through an object of the given class name.
	 * 
	 * @param edge method call edge in the graph.
	 * @param objClassName class name of object.
	 * @return number of times the edge was executed, or -1 on error.
	 */
	public long timesCallExecuted(jaba.graph.Edge edge,
										String objClassName) {
		
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
		
		return profiling.timesCallExecuted(sourceSig, sinkSig,
					source.getSourceLineNumber(), objClassName);
	}
	
	/**
	 * Determines the number of times the specified branch edge
	 * was executed.
	 * 
	 * @param edge Edge in the graph.
	 * @return number of times the edge was executed, or -1 on error.
	 */
	public long timesBranchExecuted(jaba.graph.Edge edge) {
		
		StatementNode source = (StatementNode)edge.getSource();
		
		String className =
			source.getContainingMethod().
					getContainingType().getName().replaceAll(
					"[/]", ".");
		return profiling.timesBranchExecuted(className,
										source.getSourceLineNumber(),
										edge.getLabel());		
	}
}