package insect.coverage.instrumentation;

import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.PUSH;

/**
 * Instruments a class with probes to report thrown Exceptions.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ThrowProbeInserter {

	/*private instance variables*/
	private ConstantPoolGen cpgen;
	private int report;

	/**
	 * Initializes probe inserter for current class.
	 *
	 * @param cpgen constantpoolgen of class
	 */
	public void init(ConstantPoolGen cpgen) {

		//add reference to method for probes to report to
		report =
			cpgen.addMethodref(
				"insect.coverage.execution.ThrowMonitor",
				"report",
				"(Ljava/lang/Throwable;II)V");
		this.cpgen = cpgen;
	}

	/**
	 * Performs the actual insertion of probes into the bytecode.
	 *
	 * @param methodName name of method
	 * @param mgen methodgen of method
	 * @param index id number of current class
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
		String className = mgen.getClassName();

		//traverse through instructions looking for throws
		while (curHandle != null) {

			if (curHandle.getInstruction() instanceof ATHROW) {

				probeInstrs = new InstructionList();

				//create probe and insert before throw
				probeInstrs.append(new DUP());
				probeInstrs.append(new PUSH(cpgen, index));
				probeInstrs.append(
					new PUSH(
						cpgen,
						lineNums.getSourceLine(curHandle.getPosition())));
				probeInstrs.append(new INVOKESTATIC(report));
				insertedInstr = ilist.insert(curHandle, probeInstrs);

			}

			curHandle = curHandle.getNext();
		}

		//apply changes
		ilist.setPositions();
		mgen.setMaxStack();
	}

} // ThrowProbeInserter
