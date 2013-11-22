package insect.jaba;

import insect.ConfigFile;
import jaba.du.DefUse;
import jaba.du.NewInstance;
import jaba.du.RecursiveDefUse;
import jaba.du.ReferenceDefUse;
import jaba.graph.MethodCallAttribute;
import jaba.graph.StatementNode;
import jaba.main.JABADriver;
import jaba.main.Options;
import jaba.main.ResourceFile;
import jaba.sym.Method;
import jaba.sym.NamedReferenceType;
import jaba.sym.Program;
import jaba.tools.local.Factory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * JABAOptimizer.java
 *
 * Utilizes JABA to determine optimizations for the
 * instrumentation done by InsECT.  Currently only
 * performs virtual call resolution
 *
 * Created: Mon Feb 17 18:08:39 2003
 *
 * @author <a href="mailto:anil@resnet.gatech.edu">Anil Chawla</a>
 */
public class JABAOptimizer extends JABADriver {

//	//InsECT properties
//	private File instrDir;
//	private Program program;
//
//	/**
//	 * Initializes JABA and loads the specified program.
//	 * The program's resource file is expected as the
//	 * first argument.
//	 *
//	 * @param argv arguments
//	 */
//	public void init(String[] argv) {
//
//		Options jabaOptions = Factory.getOptions();
//		jabaOptions.setCreateLVT(true);
//
//		if (argv.length < 1) {
//			System.err.println("Incorrect Usage: Please specify resource file");
//			System.exit(1);
//		}
//
//		//get program & directory information
//		ResourceFile rf = new ResourceFile(argv[0]);
//		instrDir =
//			new File(
//				ConfigFile.getProperty("INSTRUMENTED")
//					+ File.separator
//					+ rf.getProgramName());
//
//		program = Factory.getProgram(rf, jabaOptions);
//	}
//
//	/**
//	 * Attempts to resolve virtual method calls as monomorphic
//	 * and outputs the results to the 'calls.optimized' file
//	 * in the program's instrumented directory.
//	 *
//	 */
//	public void resolveVirtualCalls() {
//
//		try {
//			System.out.println("Resolving virtual method calls:");
//
//			Rta rta = new Rta(program);
//			PrintWriter outFile = null;
//			int numCalls = 0;
//			int targsResolved = 0;
//			int objsResolved = 0;
//
//			jaba.sym.Class[] classes = program.getClasses();
//			jaba.sym.Interface[] intfs = program.getInterfaces();
//			NamedReferenceType[] allClasses =
//				new NamedReferenceType[classes.length + intfs.length];
//
//			System.arraycopy(classes, 0, allClasses, 0, classes.length);
//			System.arraycopy(
//				intfs,
//				0,
//				allClasses,
//				classes.length,
//				intfs.length);
//
//			//create output file
//			try {
//				File f =
//					new File(
//						instrDir.getPath()
//							+ File.separator
//							+ "calls.optimized");
//				if (!f.getParentFile().exists()) {
//					f.getParentFile().mkdirs();
//				}
//				FileWriter fw = new FileWriter(f);
//				outFile = new PrintWriter(fw);
//			} catch (IOException ioe) {
//				System.err.println("Could not create output file");
//				ioe.printStackTrace();
//				return;
//			}
//
//			//traverse through classes
//			for (int i = 0; i < allClasses.length; i++) {
//
//				String className =
//					allClasses[i].getName().replaceAll("[/]", ".");
//				outFile.println("<C>" + className);
//				Method[] methods = allClasses[i].getMethods();
//
//				//traverse through methods
//				for (int j = 0; j < methods.length; j++) {
//
//					if (methods[j].isAbstract())
//						continue;
//
//					outFile.println("<M>" + methods[j].getSignature());
//					StatementNode[] callNodes = methods[j].getCallSiteNodes();
//
//					//output information about virtual calls
//					for (int k = 0; k < callNodes.length; k++) {
//
//						StatementNode stNode = callNodes[k];
//
//						if (stNode.getType()
//							!= StatementNode.VIRTUAL_METHOD_CALL_NODE)
//							continue;
//
//						numCalls++;
//						outFile.print(stNode.getByteCodeOffset() + ":");
//
//						//determine possible targets of this method call
//						//if there is only one, it is monomorphic
//						HashSet targets = rta.getTargetSet(stNode);
//						if (targets.size() == 1) {
//							Method m = (Method) targets.iterator().next();
//							NamedReferenceType mClass = m.getContainingType();
//							String name =
//								mClass.getName().replaceAll("/", ".")
//									+ "."
//									+ m.getSignature();
//							outFile.print(name + ":");
//							targsResolved++;
//						} else
//							outFile.print("<unresolved>:");
//
//						//determine dynamic object type this method is invoked on
//						HashSet objs =
//							rta.getReferenceSet(
//								getReceiverDefUse(stNode),
//								stNode);
//						if (objs.size() == 1) {
//							NewInstance obj =
//								(NewInstance) objs.iterator().next();
//							outFile.println(
//								obj.getType().getName().replaceAll("/", "."));
//							objsResolved++;
//						} else
//							outFile.println("<unresolved>");
//
//					}
//				}
//			}
//
//			outFile.close();
//
//			System.out.println("\tvirtual calls - " + numCalls);
//			System.out.println("\ttargets resolved - " + targsResolved);
//			System.out.println("\tobjects resolved - " + objsResolved);
//			System.out.println("Done");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Returns the DefUse corresponding to the specified
//	 * StatementNode in relation to the method call at that
//	 * node.  Credit to Maikel Pennings.
//	 *
//	 * @param sn a <code>StatementNode</code> value
//	 * @return a <code>DefUse</code> value
//	 */
//	private DefUse getReceiverDefUse(StatementNode sn) {
//		// assuming sn is a call node
//
//		MethodCallAttribute ma =
//			(MethodCallAttribute) sn.getAttributeOfType(
//				"jaba.graph.MethodCallAttribute");
//
//		DefUse[][] actuals = ma.getActualParameters();
//		for (int i = 0; i < actuals[0].length; i++) {
//			if (actuals[0][i] instanceof ReferenceDefUse)
//				return actuals[0][i];
//			if (actuals[0][i] instanceof RecursiveDefUse
//				&& ((RecursiveDefUse) actuals[0][i]).getLeafElement()
//					instanceof ReferenceDefUse)
//				return actuals[0][i];
//		}
//		// assuming it is a virtual call, this point cannot be reached
//		return null;
//	}
//
//	/**
//	 * Performs the optimization routines.
//	 *
//	 */
//	public void run() {
//
//		resolveVirtualCalls();
//
//	}

} // JABAOptimizer
