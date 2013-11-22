package insect.coverage.instrumentation;

import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.LineNumberGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.PUSH;

/**
 * Instruments a class with probes to report caught exceptions.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class CatchProbeInserter {

	/*private instance variables*/
	private ConstantPoolGen cpgen;
	private int report;

	/**
	 * Initializes probe inserter for the current class.
	 *
	 * @param cpgen constantpoolgen of class
	 */
	public void init(ConstantPoolGen cpgen) {

		report =
			cpgen.addMethodref(
				"insect.coverage.execution.CatchMonitor",
				"report",
				"(Ljava/lang/Throwable;II)V");
		this.cpgen = cpgen;
	}

	/**
	 * Performs the actual insertion of probes into the bytecode.
	 *
	 * @param methodName name of method
	 * @param mgen methodgen of method
	 * @param index id number of current method
	 */
	public void instrumentMethod(
		String methodName,
		MethodGen mgen,
		int index) {

		InstructionList ilist = mgen.getInstructionList();
		if (ilist == null)
			return;

		CodeExceptionGen[] xhandlers = mgen.getExceptionHandlers();
		LineNumberGen[] lineNums = mgen.getLineNumbers();
		InstructionTargeter[] iTargs;
		String className = mgen.getClassName();

		//instrument each catch block
		for (int i = 0; i < xhandlers.length; i++) {

			if (xhandlers[i].getCatchType() == null)
				continue;

			InstructionHandle catchHandle = xhandlers[i].getHandlerPC();

			//Check if this catch handle now points to instrumentation.
			//This indicates that there are multiple entries in the
			//exception table to this catch block
			if (catchHandle.getPosition() < 0)
				continue;

			InstructionHandle insertedInstr;
			InstructionList probeInstrs = new InstructionList();

			probeInstrs.append(new DUP());
			probeInstrs.append(new PUSH(cpgen, index));
			probeInstrs.append(
				new PUSH(cpgen, getSourceLine(lineNums, catchHandle)));
			probeInstrs.append(new INVOKESTATIC(report));
			insertedInstr = ilist.insert(catchHandle, probeInstrs);

			//update targeters for this probe
			iTargs = catchHandle.getTargeters();
			if (iTargs != null) {
				for (int j = 0; j < iTargs.length; j++) {
					if (iTargs[j] instanceof CodeExceptionGen)
						iTargs[j].updateTarget(catchHandle, insertedInstr);
				}
			}
		}

		//apply changes
		ilist.setPositions();
		mgen.setMaxStack();
	}

	/**
	 * Determines the source line number of an instruction.
	 *
	 * @param lineNums array of linenumbergen for this method
	 * @param ih handle of instruction to find
	 * @return source line
	 */
	private int getSourceLine(LineNumberGen[] lineNums, InstructionHandle ih) {

		//traverse linenums until instructionhandle is found
		for (int i = 0; i < lineNums.length; i++) {
			if (lineNums[i].containsTarget(ih))
				return lineNums[i].getSourceLine();
		}
		return -1;
	}

} // CatchProbeInserter
