package insect.coverage.execution;

/**
 * Thread group for the ExecThread so that exceptions from
 * multi-threaded and Swing/AWT applications can be caught.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ExecThreadGroup extends ThreadGroup {

	//reference to security manager
	private ExecutionSecurityManager security;

	/**
	 * Constructor to create thread group with the specified name.
	 *
	 * @param name name of thread group
	 */
	public ExecThreadGroup(String name) {
		super(name);
	}

	/**
	 * Set reference to an ExecutionSecurityManager.
	 *
	 * @param security security manager in use by Executor
	 */
	public void setSecurity(ExecutionSecurityManager security) {
		this.security = security;
	}

	/**
	 * Handles exceptions propagating from any child thread
	 * or thread group.
	 *
	 * @param t thread in which exception occurred
	 * @param e the exception
	 */
	public void uncaughtException(Thread t, Throwable e) {

		// if an ExecutionSecurityManager is not in use
		if (security == null)
			e.printStackTrace();

		// check if executing program is attempting to exit
		// VM and inform security manager
		else if (
			(e instanceof SecurityException)
				&& (e.getMessage().equals("InsECT: Attempt to exit VM")))
			security.setHasExited(true);
		else
			e.printStackTrace();
	}

	/**
	 * Method to handle exceptions in AWT/Swing programs.
	 *
	 * @param t an exception
	 */
	public void handle(Throwable t) {

		// if an ExecutionSecurityManager is not in use
		if (security == null)
			t.printStackTrace();

		// check if executing program is attempting to exit VM
		// and inform security manager
		else if (
			(t instanceof SecurityException)
				&& (t.getMessage().equals("InsECT: Attempt to exit VM")))
			security.setHasExited(true);
		else
			t.printStackTrace();
	}

} // ExecThreadGroup
