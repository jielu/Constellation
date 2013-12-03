package edu.gatech.cs6422;

import org.apache.bcel.generic.InstructionHandle;
/**
 * BasicBlock.java
 *
 * Represents a BasicBlock by maintaining references
 * to the start and end InstructionHandles
 *
 * Created: Thu Jan 23 18:33:59 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */

public class BasicBlock {

	private InstructionHandle startHandle;
	private InstructionHandle endHandle;

	/**
	 * Creates a new <code>BasicBlock</code> instance.
	 *
	 * @param startHandle an <code>InstructionHandle</code> value
	 * @param endHandle an <code>InstructionHandle</code> value
	 */
	public BasicBlock(
		InstructionHandle startHandle,
		InstructionHandle endHandle) {
		this.startHandle = startHandle;
		this.endHandle = endHandle;
	}

	/**
	 * Returns the InstructionHandle referring to the
	 * start of the basic block.
	 *
	 * @return an <code>InstructionHandle</code> value
	 */
	public InstructionHandle getStartHandle() {
		return startHandle;
	}

	/**
	 * Returns the InstructionHandle referring to the
	 * end of the basic block.
	 *
	 * @return an <code>InstructionHandle</code> value
	 */
	public InstructionHandle getEndHandle() {
		return endHandle;
	}

} // BasicBlock
