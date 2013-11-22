package insect.coverage.instrumentation;

import java.util.Vector;

import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.RET;
import org.apache.bcel.generic.ReturnInstruction;
/**
 * CodeAnalysis.java
 *
 * Provides methods performing specific analysis on code segments.
 *
 * Created: Thu Jan 23 18:19:09 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class CodeAnalysis {

	/**
	 * Determines the BasicBlocks constituting a sequence
	 * of instructions.
	 *
	 * @param ilist an <code>InstructionList</code> value
	 * @param cpgen a <code>ConstantPoolGen</code> value
	 * @return the BasicBlocks of the InstructionList
	 */
	public static BasicBlock[] getBasicBlocks(
		InstructionList ilist,
		ConstantPoolGen cpgen) {

		if (ilist == null)
			return new BasicBlock[0];
		InstructionHandle blockStart = ilist.getStart();
		if (blockStart == null)
			return new BasicBlock[0];

		InstructionHandle curHandle = blockStart;
		Vector bblocks = new Vector();

		//traverse instruction list
		while (curHandle != null) {

			Instruction nextInstr = curHandle.getInstruction();

			//if end of basic block
			if ((nextInstr instanceof BranchInstruction)
				|| (nextInstr instanceof RET)
				|| (nextInstr instanceof ReturnInstruction)
				|| (nextInstr instanceof ATHROW)
				|| ((nextInstr instanceof InvokeInstruction)
					&& (!((InvokeInstruction) nextInstr)
						.getClassName(cpgen)
						.startsWith("insect.")))) {
				bblocks.add(new BasicBlock(blockStart, curHandle));
				blockStart = curHandle.getNext();
			}

			//if end of method
			if ((curHandle.getNext() == null) && (blockStart != null))
				bblocks.add(new BasicBlock(blockStart, curHandle));

			curHandle = curHandle.getNext();
		}

		return (BasicBlock[]) bblocks.toArray(new BasicBlock[0]);
	}

} // CodeAnalysis
