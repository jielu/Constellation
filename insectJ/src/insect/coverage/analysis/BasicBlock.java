package insect.coverage.analysis;

/**
 * BasicBlock.java
 *
 * Represents a BasicBlock.
 *
 * Created: Mon Nov 04 17:59:47 2002
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class BasicBlock {

	/**
	 * Attributes of the BasicBlock.
	 */
	private String className;
	private String methodSig;
	private int start, end;
	private int id;

	/**
	 * Creates a new BasicBlock with the specified properties.
	 *
	 * @param className class this block belongs to.
	 * @param methodSig method signature of containing method.
	 * @param start start source line.
	 * @param end end source line.
	 * @param id id of this block in the blockids file.
	 */
	public BasicBlock(String className,	String methodSig, 
								int start, int end,	int id) {
									
		this.className = className;
		this.methodSig = methodSig;
		this.start = start;
		this.end = end;
		this.id = id;
	}

	/**
	 * Creates a new BasicBlock with the specified properties.
	 *
	 * @param start start bytecode offset.
	 * @param end end bytecode offset.
	 * @param id id of this block in the blockids file.
	 */
	public BasicBlock(int start, int end, int id) {
		this.start = start;
		this.end = end;
		this.id = id;
	}

	/**
	 * @return the name of the class containing this block.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the method signature of the method containing this block.
	 */
	public String getMethodSig() {
		return methodSig;
	}

	/**
	 * Determines if a line number falls
	 * within this BasicBlock's range.
	 *
	 * @param lineNum a source line number.
	 * @return true if within this blocks range, false otherwise.
	 */
	public boolean contains(int lineNum) {
		//There may be a bug in getting the block ids, some blocks with start line number > end line number
		//Just use a walkaround here to check whether the block contain a specified line.
		//Jie Lu, 02/07/2013
		return (((lineNum >= start) && (lineNum <= end)) || ((lineNum >= end) && (lineNum <= start)));
	}

	/**
	 * @return the ID number of this block.
	 */
	public int getID() {
		return id;
	}
	
	
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public boolean equals(Object obj2){
		BasicBlock bb2 = (BasicBlock)obj2;
		return this.getClassName().equals(bb2.getClassName())
				&& this.getMethodSig().equals(bb2.getMethodSig())
				&& this.getStart() == bb2.getStart()
				&& this.getEnd() == bb2.getEnd();
	}

} // BasicBlock
