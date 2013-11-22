package insect.coverage.execution;

/**
 * Package level class for keeping count of the number of
 * instrumentation monitors in use.
 *  
 * @author Anil Chawla
 */
class CounterMonitor {
	
	/**
	 * Maintains an instance of this class so that it can be used statically.
	 */
	public static CounterMonitor counter = new CounterMonitor();
	
	/**
	 * Flag for whether or not the JVM is being shutdown.
	 */
	public static boolean shutdown = false;
	
	/**
	 * Count of monitors in use.
	 */
	private int count;
	
	/**
	 * Private constructor for singleton creation.
	 *
	 */
	private CounterMonitor() {
		count = 0;
	}

	/**
	 * Decrements the count.
	 *
	 */
	public synchronized void decrement() {
		//assert(count > 0);
		count--;
		notifyAll();
	}

	/**
	 * Increments the count.
	 *
	 */
	public synchronized void increment() {
		count++;
		notifyAll();
	}

	/**
	 * @return true if count is zero, false otherwise.
	 */
	public synchronized boolean zero() {
		return (count == 0);
	}

	/**
	 * Causes the current thread to yield until no more
	 * monitors are in use.
	 *
	 */
	public synchronized void waitForZero() {
		while (count != 0) {
			try {
				// System.out.println("Monitors still running");
				wait();
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}
}
