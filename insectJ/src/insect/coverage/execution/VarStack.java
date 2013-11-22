package insect.coverage.execution;

import java.util.Stack;

/**
 * VarStack.java
 *
 * Serves as a "secondary" stack for the instrumentation
 * probes to use during execution.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */
public class VarStack {

	/**
	 * Backing for this stack.
	 */
	public static Stack stack;

	// Run init when class is loaded
	static {
		init();
	}

	/**
	 * Ensures that the backing stack has been initialized.
	 *
	 */
	public static void init() {
		if (stack == null)
			stack = new Stack();
	}

	/**
	 * Resets the stack.
	 *
	 */
	public static void reset() {
		stack.clear();
	}

	/**
	 * Push int primitive onto the stack.
	 *
	 * @param i an <code>int</code> value
	 */
	public static void pushI(int i) {
		stack.push(new Integer(i));
	}

	/**
	 * Pop int primitive off the stack.
	 *
	 * @return an <code>int</code> value
	 */
	public static int popI() {
		return ((Integer) stack.pop()).intValue();
	}

	/**
	 * Push short primitive onto the stack.
	 *
	 * @param s a <code>short</code> value
	 */
	public static void pushS(short s) {
		stack.push(new Short(s));
	}

	/**
	 * Pop short primitive off the stack.
	 *
	 * @return a <code>short</code> value
	 */
	public static short popS() {
		return ((Short) stack.pop()).shortValue();
	}

	/**
	 * Push long primitive onto the stack.
	 *
	 * @param l a <code>long</code> value
	 */
	public static void pushL(long l) {
		stack.push(new Long(l));
	}

	/**
	 * Pop long primitive off the stack.
	 *
	 * @return a <code>long</code> value
	 */
	public static long popL() {
		return ((Long) stack.pop()).longValue();
	}

	/**
	 * Push byte primitive onto the stack.
	 *
	 * @param i a <code>byte</code> value
	 */
	public static void pushB(byte b) {
		stack.push(new Byte(b));
	}

	/**
	 * Pop byte primitive off the stack.
	 *
	 * @return a <code>byte</code> value
	 */
	public static byte popB() {
		return ((Byte) stack.pop()).byteValue();
	}

	/**
	 * Push char primitive onto the stack.
	 *
	 * @param c a <code>char</code> value
	 */
	public static void pushC(char c) {
		stack.push(new Character(c));
	}

	/**
	 * Pop char primitive off the stack.
	 *
	 * @return a <code>char</code> value
	 */
	public static char popC() {
		return ((Character) stack.pop()).charValue();
	}

	/**
	 * Push boolean primitive onto the stack.
	 *
	 * @param z a <code>boolean</code> value
	 */
	public static void pushZ(boolean z) {
		stack.push(new Boolean(z));
	}

	/**
	 * Pop boolean primitive off the stack.
	 *
	 * @return a <code>boolean</code> value
	 */
	public static boolean popZ() {
		return ((Boolean) stack.pop()).booleanValue();
	}

	/**
	 * Push float primitive onto the stack.
	 *
	 * @param f a <code>float</code> value
	 */
	public static void pushF(float f) {
		stack.push(new Float(f));
	}

	/**
	 * Pop float primitive off the stack.
	 *
	 * @return a <code>float</code> value
	 */
	public static float popF() {
		return ((Float) stack.pop()).floatValue();
	}

	/**
	 * Push double primitive onto the stack.
	 *
	 * @param d a <code>double</code> value
	 */
	public static void pushD(double d) {
		stack.push(new Double(d));
	}

	/**
	 * Pop double primitive off the stack.
	 *
	 * @return a <code>double</code> value
	 */
	public static double popD() {
		return ((Double) stack.pop()).doubleValue();
	}

	/**
	 * Push object onto the stack.
	 *
	 * @param o an <code>Object</code> value
	 */
	public static void pushO(Object o) {
		stack.push(o);
	}

	/**
	 * Pop object off the stack.
	 *
	 * @return an <code>Object</code> value
	 */
	public static Object popO() {
		return stack.pop();
	}

} // VarStack
