package insect.coverage.execution;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Thread to perform execution of another program.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ExecThread implements Runnable {

	//properties of program's main
	private Class mainClass;
	private Object[] mainArgs;

	/**
	 * Constructor to create an ExecThread for a
	 * a program with the specified properties.
	 *
	 * @param mainClass main class of program
	 * @param mainArgs arguments to main
	 */
	public ExecThread(Class mainClass, Object[] mainArgs) {
		this.mainClass = mainClass;
		this.mainArgs = mainArgs;
	}

	/**
	 * Executes the program and waits for it to finish
	 * executing. Attempts to set the security manager
	 * in order to do this.
	 *
	 */
	public void run() {

		//execute
		boolean securityIsSet = false;
		SecurityManager prevSecurity = null;

		//create and attempt to set security manager
		ExecutionSecurityManager security = new ExecutionSecurityManager();
		prevSecurity = System.getSecurityManager();
		try {
			System.setSecurityManager(security);
			securityIsSet = true;
		} catch (SecurityException setExcep) {
			System.err.println(
				"Executor: Unable to set security manager, "
				+ "executing program may exit VM");
		}

		//attempt to execute program's main
		try {

			//invoke main
			Class[] mainParam = { String[].class };
			Method mainMethod = mainClass.getDeclaredMethod("main", mainParam);
			mainMethod.invoke(null, mainArgs);
			
			//if security manager was set, wait for program to finish
			if (securityIsSet)
				security.waitForExecution();
		} catch (SecurityException e) {
			//ignore if executing program attempted to exit VM
			if (!e.getMessage().equals("InsECT: Attempt to exit VM"))
				e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.err.println("Unable to locate main method");
			return;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Throwable te = e.getTargetException();

			if (te != null) {
				//if program exit, ignore
				if ((!(te instanceof SecurityException))
					|| (!te.getMessage().
							equals("InsECT: Attempt to exit VM"))) {
					System.err.println("Exception in executing program:");
					te.printStackTrace();
				}
			} else
				e.printStackTrace();
		}

		//restore previous security manager
		if (securityIsSet)
			System.setSecurityManager(prevSecurity);
	}

} // ExecThread
