package insect.coverage.instrumentation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.CHECKCAST;
import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MONITORENTER;
import org.apache.bcel.generic.MONITOREXIT;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;

/**
 * Instruments a class with probes to report the invocation of methods.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class CallProbeInserter {

	/*private instance variables*/
	private ConstantPoolGen cpgen;
	private ProbeInserter master;
	private boolean callCov;
	private boolean extCallCov;
	private HashMap optClasses;
	private int optLevel;

	/* CallMonitor methods */
	private int reportMono;
	private int reportPoly;

	/* VarStack methods */
	private int reset;
	private int pushI;
	private int pushS;
	private int pushL;
	private int pushB;
	private int pushC;
	private int pushZ;
	private int pushF;
	private int pushD;
	private int pushO;
	private int popI;
	private int popS;
	private int popL;
	private int popB;
	private int popC;
	private int popZ;
	private int popF;
	private int popD;
	private int popO;
	private int vstack;

	/**
	 * Initializes probe inserter for current class.
	 *
	 * @param cpgen constantpoolgen of class
	 * @param master reference to ProbeInserter object
	 * @param callCov whether or not system call coverage is enabled
	 * @param extCallCov whether or not library call coverage is enabled
	 */
	public void init(ConstantPoolGen cpgen, ProbeInserter master,
							boolean callCov, boolean extCallCov) {

		//add reference to method for probes to report to
		reportMono =
			cpgen.addMethodref(
				"insect.coverage.execution.CallMonitor",
				"reportMono",
				"(Ljava/lang/String;III)V");
		reportPoly =
			cpgen.addMethodref(
				"insect.coverage.execution.CallMonitor",
				"reportPoly",
				"(Ljava/lang/Object;III)V");

		reset =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"reset",
				"()V");
		pushI =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushI",
				"(I)V");
		pushS =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushS",
				"(S)V");
		pushL =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushL",
				"(J)V");
		pushB =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushB",
				"(B)V");
		pushC =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushC",
				"(C)V");
		pushZ =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushZ",
				"(Z)V");
		pushF =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushF",
				"(F)V");
		pushD =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushD",
				"(D)V");
		pushO =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"pushO",
				"(Ljava/lang/Object;)V");
		popI =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popI",
				"()I");
		popS =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popS",
				"()S");
		popL =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popL",
				"()J");
		popB =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popB",
				"()B");
		popC =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popC",
				"()C");
		popZ =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popZ",
				"()Z");
		popF =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popF",
				"()F");
		popD =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popD",
				"()D");
		popO =
			cpgen.addMethodref(
				"insect.coverage.execution.VarStack",
				"popO",
				"()Ljava/lang/Object;");
		vstack =
			cpgen.addFieldref(
				"insect.coverage.execution.VarStack",
				"stack",
				"Ljava/util/Stack;");

		this.cpgen = cpgen;
		this.master = master;
		this.callCov = callCov;
		this.extCallCov = extCallCov;

	}

	/**
	 * Method to load optimization information for use
	 * during probe insertion.  This information is assumed
	 * to be stored in the instrumentation directory of the
	 * program as 'calls.optimized'
	 *
	 * @param optFileDir a <code>String</code> value
	 */
	public void loadOptimizeInfo(int optLevel, String optFileDir) {

		BufferedReader inFile = null;
		optClasses = new HashMap();

		try {
			inFile =
				new BufferedReader(
					new FileReader(
						optFileDir + File.separator + "calls.optimized"));
			String curLine;
			HashMap curMap = null;
			TreeMap offsetMap = null;

			//parse file to create a hash table of class names
			//where each entry refers to a hash table of methods
			//where each entry refers to a tree map of offsets
			while ((curLine = inFile.readLine()) != null) {

				//add to class hash table
				if (curLine.startsWith("<C>")) {
					curMap = new HashMap();
					optClasses.put(curLine.substring(3), curMap);
				}
				//add to method hash table
				else if (curLine.startsWith("<M>")) {
					offsetMap = new TreeMap();
					curMap.put(
						curLine.substring(3, curLine.lastIndexOf(')')),
						offsetMap);
				} else {

					//add to offset tree map
					int spIndex = curLine.indexOf(":");
					offsetMap.put(
						new Integer(curLine.substring(0, spIndex)),
						curLine.substring(spIndex + 1));
				}
			}
			this.optLevel = optLevel;
		} catch (IOException ioe) {
			System.err.println("Error reading optimization file");
			optClasses = null;
		} finally {
			try {
				inFile.close();
			} catch (Exception e) {
			}
		}

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

		InstructionHandle curHandle = ilist.getStart();
		InstructionHandle insertedInstr;
		InstructionList probeInstrs;
		InstructionTargeter[] iTargs;
		LineNumberTable lineNums = mgen.getLineNumberTable(cpgen);
		String className = mgen.getClassName();
		Iterator optVirtuals = null;

		//if optimization information is available, prepare iterator
		if (optLevel > 0) {
			//System.out.println("method: " + methodName + "class: "+className);

			HashMap methMap = (HashMap) optClasses.get(className);
			TreeMap offsets =
				(TreeMap) methMap.get(
					methodName.substring(0, methodName.lastIndexOf(')') + 1));
			Collection vals = offsets.values();
			if (vals != null)
				optVirtuals = vals.iterator();
		}

		//traverse through instructions looking for method calls
		while (curHandle != null) {

			Instruction curInstr = curHandle.getInstruction();
			if (curInstr instanceof InvokeInstruction) {

				InvokeInstruction instr = (InvokeInstruction) curInstr;
				String calledClass = instr.getClassName(cpgen);
				String calledSig =
					instr.getMethodName(cpgen) + instr.getSignature(cpgen);

				if (master.isInstrumented(calledClass)) {
					//if normal call coverage is disabled, skip this call
					if (!callCov) {
						curHandle = curHandle.getNext();

						//move optimization iterator if necessary
						if ((optLevel > 0)
							&& (!(instr instanceof INVOKESTATIC))
							&& (calledSig.indexOf("<") != -1))
							optVirtuals.next();

						continue;
					}
				} else {
					//if external call coverage is disabled, skip this call
					if (!extCallCov) {
						curHandle = curHandle.getNext();

						//move optimization iterator if necessary
						if ((optLevel > 0)
							&& (!(instr instanceof INVOKESTATIC))
							&& (calledSig.indexOf("<") < 0))
							optVirtuals.next();

						continue;
					}
				}

				//if not optimizing, then the target method of every virtual call
				//is unknown and a ? id must be used for the called method
				Integer calledID = null;
				if ((optLevel == 0) && (!(instr instanceof INVOKESTATIC)))
					calledID = master.getClassID("?" + calledSig);
				else
					calledID = master.getClassID(calledClass + "." + calledSig);

				probeInstrs = new InstructionList();

				//if init call
				if (calledSig.indexOf("<") != -1) {
					probeInstrs.append(new PUSH(cpgen, calledClass));
					probeInstrs.append(
						new PUSH(
							cpgen,
							lineNums.getSourceLine(curHandle.getPosition())));
					probeInstrs.append(new PUSH(cpgen, index));
					probeInstrs.append(new PUSH(cpgen, calledID.intValue()));
					probeInstrs.append(new INVOKESTATIC(reportMono));

					//if optimizing, move iterator to account
					//for this init calledID
					if (optLevel > 0) {
						optVirtuals.next();
					}
				}
				//insert probe for static call
				else if (instr instanceof INVOKESTATIC) {
					probeInstrs.append(new PUSH(cpgen, calledClass));
					probeInstrs.append(
						new PUSH(
							cpgen,
							lineNums.getSourceLine(curHandle.getPosition())));
					probeInstrs.append(new PUSH(cpgen, index));
					probeInstrs.append(new PUSH(cpgen, calledID.intValue()));
					probeInstrs.append(new INVOKESTATIC(reportMono));
				}
				//insert probe for virtual call
				else {
					boolean unresolvedObj = true;

					//if optimizing, use any information about resolved
					//target method or resolved object type
					if (optLevel > 0) {
						String targAndObj = (String) optVirtuals.next();
						String targ =
							targAndObj.substring(0, targAndObj.indexOf(':'));
						String obj =
							targAndObj.substring(targAndObj.indexOf(':') + 1);

						//if target is unresolved, create ? id
						if (targ.equals("<unresolved>"))
							calledID = master.getClassID("?" + calledSig);
						else {
							calledID = master.getClassID(targ);

							//if optimizing for target method only, or
							//if object is resolved, go ahead and insert probe
							if ((optLevel == 2)
								|| ((optLevel == 1)
									&& (!obj.equals("<unresolved>")))) {
								unresolvedObj = false;
								probeInstrs.append(new PUSH(cpgen, obj));
								probeInstrs.append(
									new PUSH(
										cpgen,
										lineNums.getSourceLine(
											curHandle.getPosition())));
								probeInstrs.append(new PUSH(cpgen, index));
								probeInstrs.append(
									new PUSH(cpgen, calledID.intValue()));
								probeInstrs.append(
									new INVOKESTATIC(reportMono));
							}
						}
					}

					//unresolved calls require use of the VarStack
					//to determine the object type at runtime
					if (unresolvedObj) {

						Type[] argTypes = instr.getArgumentTypes(cpgen);

						if (argTypes.length > 0) {
							probeInstrs.append(new GETSTATIC(vstack));
							probeInstrs.append(new MONITORENTER());
							probeInstrs.append(new INVOKESTATIC(reset));
						}
						//push method args onto VarStack
						for (int i = argTypes.length - 1; i >= 0; i--) {
							switch (argTypes[i].getType()) {

								case Constants.T_INT :
									probeInstrs.append(new INVOKESTATIC(pushI));
									break;
								case Constants.T_SHORT :
									probeInstrs.append(new INVOKESTATIC(pushS));
									break;
								case Constants.T_LONG :
									probeInstrs.append(new INVOKESTATIC(pushL));
									break;
								case Constants.T_BYTE :
									probeInstrs.append(new INVOKESTATIC(pushB));
									break;
								case Constants.T_CHAR :
									probeInstrs.append(new INVOKESTATIC(pushC));
									break;
								case Constants.T_BOOLEAN :
									probeInstrs.append(new INVOKESTATIC(pushZ));
									break;
								case Constants.T_FLOAT :
									probeInstrs.append(new INVOKESTATIC(pushF));
									break;
								case Constants.T_DOUBLE :
									probeInstrs.append(new INVOKESTATIC(pushD));
									break;
								case Constants.T_OBJECT :
								case Constants.T_ARRAY :
									probeInstrs.append(new INVOKESTATIC(pushO));
									break;
								default :
									System.err.println(
										"Type unknown: " + argTypes[i]);

							}
						}

						//insert probe
						probeInstrs.append(new DUP());
						probeInstrs.append(
							new PUSH(
								cpgen,
								lineNums.getSourceLine(
									curHandle.getPosition())));
						probeInstrs.append(new PUSH(cpgen, index));
						probeInstrs.append(
							new PUSH(cpgen, calledID.intValue()));
						probeInstrs.append(new INVOKESTATIC(reportPoly));

						//pop method args back from VarStack
						for (int i = 0; i < argTypes.length; i++) {
							switch (argTypes[i].getType()) {
								case Constants.T_INT :
									probeInstrs.append(new INVOKESTATIC(popI));
									break;
								case Constants.T_SHORT :
									probeInstrs.append(new INVOKESTATIC(popS));
									break;
								case Constants.T_LONG :
									probeInstrs.append(new INVOKESTATIC(popL));
									break;
								case Constants.T_BYTE :
									probeInstrs.append(new INVOKESTATIC(popB));
									break;
								case Constants.T_CHAR :
									probeInstrs.append(new INVOKESTATIC(popC));
									break;
								case Constants.T_BOOLEAN :
									probeInstrs.append(new INVOKESTATIC(popZ));
									break;
								case Constants.T_FLOAT :
									probeInstrs.append(new INVOKESTATIC(popF));
									break;
								case Constants.T_DOUBLE :
									probeInstrs.append(new INVOKESTATIC(popD));
									break;
								case Constants.T_OBJECT :
									probeInstrs.append(new INVOKESTATIC(popO));
									probeInstrs.append(
										new CHECKCAST(
											cpgen.addClass(
												(ObjectType) argTypes[i])));
									break;
								case Constants.T_ARRAY :
									probeInstrs.append(new INVOKESTATIC(popO));
									probeInstrs.append(
										new CHECKCAST(
											cpgen.addArrayClass(
												(ArrayType) argTypes[i])));
									break;
								default :
									}
						}
						if (argTypes.length > 0) {
							probeInstrs.append(new GETSTATIC(vstack));
							probeInstrs.append(new MONITOREXIT());
						}
					}
				}

				//insert before methodcall
				insertedInstr = ilist.insert(instr, probeInstrs);
				iTargs = curHandle.getTargeters();

				//update the targeters
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

} // CallProbeInserter
