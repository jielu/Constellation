package insect.coverage.instrumentation;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;

/**
 * Instruments a classfile with probes to report coverage of basic blocks.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class BlockProbeInserter implements org.apache.bcel.Constants {

	/*private instance variables*/
	private ConstantPoolGen cpgen;
	private PrintWriter blockFile;
	private int numBlocks = 0;
	private int blockCount = 0;
	private int report;

	//for easy access
	private static char dirSep = java.io.File.separatorChar;

	/**
	 * Initializes probe inserter for current class.  Outputs basic block information.
	 *
	 * @param cpgen constantpoolgen of class
	 * @param methods array of methods in class
	 * @param className name of class
	 * @param blockFile to output basic blocks
	 */
	public void init(ConstantPoolGen cpgen, Method[] methods,
				String className, PrintWriter blockFile) {

		//add reference to method for probes to report to
		report =
			cpgen.addMethodref(
				"insect.coverage.execution.BlockMonitor",
				"report",
				"(I)V");
		this.cpgen = cpgen;
		this.blockFile = blockFile;
	}

	/**
	 * Completes probe insertion and  modifies
	 * InsectValues class with appropriate size for hits file.
	 *
	 * @param progDir directory path of instrumented program
	 */
	public void finish(String progDir) {

		try {
			JavaClass jclass =
				(new ClassParser(progDir
					+ dirSep
					+ "insect"
					+ dirSep
					+ "InsectValues.class"))
					.parse();

			ClassGen cg = new ClassGen(jclass);
			ConstantPoolGen cpgen = cg.getConstantPool();
			FieldGen blockSizeFG =
				new FieldGen(
					ACC_PUBLIC | ACC_STATIC | ACC_FINAL,
					Type.INT,
					"blockSize",
					cpgen);
			blockSizeFG.setInitValue(blockCount);
			cg.addField(blockSizeFG.getField());

			cg.getJavaClass().dump(
				progDir + dirSep + "insect" + dirSep + "InsectValues.class");
		}
		catch (IOException loadExcep) {
			System.err.println("Error modifying InsectValues");
			return;
		}
	}

	/**
	 * Performs the actual insertion of probes into the bytecode.
	 *
	 * @param methodName name of method
	 * @param mgen methodgen of method
	 * @param index index of method in order of instrumentation
	 */
	public void instrumentMethod(String methodName,
							MethodGen mgen,	int index) {

		InstructionList ilist = mgen.getInstructionList();
		if (ilist == null)
			return;

		InstructionHandle curHandle = ilist.getStart();
		InstructionHandle insertedInstr;
		InstructionList probeInstrs;
		InstructionTargeter[] iTargs;

		LineNumberTable lineNums = mgen.getLineNumberTable(cpgen);
		Method m = mgen.getMethod();
		String className = mgen.getClassName();

		//get BasicBlocks
		BasicBlock[] methodBBs = CodeAnalysis.getBasicBlocks(ilist, cpgen);

		//instrument each basic block
		for (int i = 0; i < methodBBs.length; i++) {

			BasicBlock bb = methodBBs[i];
			int startPos = bb.getStartHandle().getPosition();
			int endPos = bb.getEndHandle().getPosition();

			//print out block information
			blockFile.println(
				className + " "	+ m.getName() 
					+ m.getSignature()
					+ " " + lineNums.getSourceLine(startPos)
					+ "-" + lineNums.getSourceLine(endPos)
					+ " " + blockCount);

			InstructionHandle lastInstr = bb.getEndHandle();
			
			//insert probe before last instruction
			probeInstrs = new InstructionList();
			probeInstrs.append(new PUSH(cpgen, blockCount));
			probeInstrs.append(new INVOKESTATIC(report));
			insertedInstr = ilist.insert(lastInstr, probeInstrs);
			iTargs = lastInstr.getTargeters();

			//retarget all but exception handlers to include probe
			if (iTargs != null) {
				for (int j = 0; j < iTargs.length; j++) {
					if (!(iTargs[j] instanceof CodeExceptionGen))
						iTargs[j].updateTarget(lastInstr, insertedInstr);
				}
			}

			blockCount++;
		}

		//apply changes
		ilist.setPositions();
		mgen.setMaxStack();
	}

} // BranchProbeInserter
