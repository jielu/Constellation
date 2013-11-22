package insect.coverage.instrumentation;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.DUP2;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.IFEQ;
import org.apache.bcel.generic.IFGE;
import org.apache.bcel.generic.IFGT;
import org.apache.bcel.generic.IFLE;
import org.apache.bcel.generic.IFLT;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.IFNONNULL;
import org.apache.bcel.generic.IFNULL;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.bcel.generic.IF_ACMPNE;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPGE;
import org.apache.bcel.generic.IF_ICMPGT;
import org.apache.bcel.generic.IF_ICMPLE;
import org.apache.bcel.generic.IF_ICMPLT;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Select;
import org.apache.bcel.generic.Type;

/**
 * Instruments a class with probes to report the outcome of branch statements.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class BranchProbeInserter implements org.apache.bcel.Constants {

	/* private instance variables*/
	private ConstantPoolGen cpgen;
	private PrintWriter branchFile;

	private int branchCount = 0;

	/* BranchMonitor methods */
	private int if_acmpeq;
	private int if_acmpne;
	private int if_icmpeq;
	private int if_icmpne;
	private int if_icmplt;
	private int if_icmpge;
	private int if_icmpgt;
	private int if_icmple;
	private int ifeq;
	private int ifne;
	private int iflt;
	private int ifge;
	private int ifgt;
	private int ifle;
	private int ifnonnull;
	private int ifnull;
	private int select;

	//for easy access
	private static char dirSep = java.io.File.separatorChar;

	/**
	 * Initializes probe inserter for current class.
	 *
	 * @param cpgen constantpoolgen of class
	 * @param branchFile to output branch ids
	 */
	public void init(ConstantPoolGen cpgen, PrintWriter branchFile) {

		//add reference to methods for probes
		if_acmpeq =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_acmpeq",
				"(Ljava/lang/Object;Ljava/lang/Object;I)V");
		if_acmpne =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_acmpne",
				"(Ljava/lang/Object;Ljava/lang/Object;I)V");
		if_icmpeq =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_icmpeq",
				"(III)V");
		if_icmpne =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_icmpne",
				"(III)V");
		if_icmplt =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_icmplt",
				"(III)V");
		if_icmpge =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_icmpge",
				"(III)V");
		if_icmpgt =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_icmpgt",
				"(III)V");
		if_icmple =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"if_icmple",
				"(III)V");
		ifeq =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"ifeq",
				"(II)V");
		ifne =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"ifne",
				"(II)V");
		iflt =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"iflt",
				"(II)V");
		ifge =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"ifge",
				"(II)V");
		ifgt =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"ifgt",
				"(II)V");
		ifle =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"ifle",
				"(II)V");
		ifnonnull =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"ifnonnull",
				"(Ljava/lang/Object;I)V");
		ifnull =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"ifnull",
				"(Ljava/lang/Object;I)V");
		select =
			cpgen.addMethodref(
				"insect.coverage.execution.BranchMonitor",
				"select",
				"(ILjava/lang/String;)V");

		this.cpgen = cpgen;
		this.branchFile = branchFile;
	}

	/**
	 * Completes probe insertion and modifies
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
			FieldGen branchSizeFG =
				new FieldGen(
					ACC_PUBLIC | ACC_STATIC | ACC_FINAL,
					Type.INT,
					"branchSize",
					cpgen);
			branchSizeFG.setInitValue(branchCount);
			cg.addField(branchSizeFG.getField());

			cg.getJavaClass().dump(
				progDir + dirSep + "insect" + dirSep + "InsectValues.class");

		} catch (IOException loadExcep) {
			System.err.println("Error modifying InsectValues");
			return;
		}
	}

	/**
	* Performs the actual insertion of probes into the bytecode.
	*
	* @param methodName name of method
	* @param mgen methodgen of method
	*/
	public void instrumentMethod(String methodName, MethodGen mgen) {

		InstructionList ilist = mgen.getInstructionList();
		if (ilist == null)
			return;

		InstructionHandle curHandle = ilist.getStart();
		InstructionHandle insertedInstr;
		InstructionList probeInstrs;
		InstructionTargeter[] iTargs;
		String className = mgen.getClassName();
		LineNumberTable lineNums = mgen.getLineNumberTable(cpgen);

		//traverse through instructions looking for predicates
		while (curHandle != null) {

			Instruction curInstr = curHandle.getInstruction();

			//if IF instruction
			if (curInstr instanceof IfInstruction) {

				IfInstruction instr = (IfInstruction) curInstr;
				int sourceNum = lineNums.getSourceLine(curHandle.getPosition());

				//write branch ids
				branchFile.println(
					className + " " + sourceNum + " " + "T " + branchCount);
				branchFile.println(
					className
						+ " "
						+ sourceNum
						+ " "
						+ "F "
						+ (branchCount + 1));

				//create probe and insert before predicate
				probeInstrs = new InstructionList();

				//create probe based on IF type
				if (instr instanceof IF_ACMPEQ) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_acmpeq));
				} else if (instr instanceof IF_ACMPNE) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_acmpne));
				} else if (instr instanceof IF_ICMPEQ) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_icmpeq));
				} else if (instr instanceof IF_ICMPNE) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_icmpne));
				} else if (instr instanceof IF_ICMPLT) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_icmplt));
				} else if (instr instanceof IF_ICMPGE) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_icmpge));
				} else if (instr instanceof IF_ICMPGT) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_icmpgt));
				} else if (instr instanceof IF_ICMPLE) {
					probeInstrs.append(new DUP2());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(if_icmple));
				} else if (instr instanceof IFEQ) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(ifeq));
				} else if (instr instanceof IFNE) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(ifne));
				} else if (instr instanceof IFLT) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(iflt));
				} else if (instr instanceof IFGE) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(ifge));
				} else if (instr instanceof IFGT) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(ifgt));
				} else if (instr instanceof IFLE) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(ifle));
				} else if (instr instanceof IFNONNULL) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(ifnonnull));
				} else if (instr instanceof IFNULL) {
					probeInstrs.append(new DUP());
					probeInstrs.append(new PUSH(cpgen, branchCount));
					probeInstrs.append(new INVOKESTATIC(ifnull));
				}

				insertedInstr = ilist.insert(instr, probeInstrs);

				//update targeters for this probe
				iTargs = curHandle.getTargeters();
				if (iTargs != null) {
					for (int j = 0; j < iTargs.length; j++) {
						if (!(iTargs[j] instanceof CodeExceptionGen))
							iTargs[j].updateTarget(curHandle, insertedInstr);
					}
				}

				branchCount += 2;
			} else if (curInstr instanceof Select) {

				Select instr = (Select) curInstr;
				int sourceNum = lineNums.getSourceLine(curHandle.getPosition());
				String caseStr = "";

				//get cases, output branch ids, and formulate case string
				int[] cases = instr.getMatchs();
				for (int i = 0; i < cases.length; i++) {
					branchFile.println(
						className
							+ " "
							+ sourceNum
							+ " "
							+ cases[i]
							+ " "
							+ (branchCount + i));
					caseStr += "(" + cases[i] + ":" + (branchCount + i) + ")";
				}
				branchCount += cases.length;
				caseStr += "(D:" + branchCount + ")";
				branchFile.println(
					className + " " + sourceNum + " default " + branchCount++);

				//create probe and insert before predicate
				probeInstrs = new InstructionList();
				probeInstrs.append(new DUP());
				probeInstrs.append(new PUSH(cpgen, caseStr));
				probeInstrs.append(new INVOKESTATIC(select));
				insertedInstr = ilist.insert(curInstr, probeInstrs);

				//update targeters for this probe
				iTargs = curHandle.getTargeters();
				if (iTargs != null) {
					for (int j = 0; j < iTargs.length; j++) {
						if (!(iTargs[j] instanceof CodeExceptionGen))
							iTargs[j].updateTarget(curHandle, insertedInstr);
					}
				}

			}

			curHandle = curHandle.getNext();
		}

		//apply changes
		ilist.setPositions();
		mgen.setMaxStack();
	}

} // BranchProbeInserter
