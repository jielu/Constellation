package insect.coverage.execution;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Security manager to prevent executing programs from exited
 * the virtual machine.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ExecutionSecurityManager extends SecurityManager {

	private HashSet threadSet;
	private HashSet threadGroupSet;
	private ExecThreadGroup insectGroup;
	public boolean hasExited;

	public ExecutionSecurityManager() {

		threadSet = new HashSet();
		threadGroupSet = new HashSet();
		hasExited = false;

		ThreadGroup thisThreadsGroup = super.getThreadGroup();

		if (thisThreadsGroup instanceof ExecThreadGroup) {
			insectGroup = (ExecThreadGroup) thisThreadsGroup;
			insectGroup.setSecurity(this);
		}

		threadGroupSet.add(thisThreadsGroup);

		//catch all awt exceptions
		System.setProperty(
			"sun.awt.exception.handler",
			"insect.coverage.execution.ExecThreadGroup");

	}

	public void checkCreateClassLoader() {
	}

	public void checkAccess(Thread g) {

		threadSet.add(g.toString());
		threadGroupSet.add(g.getThreadGroup());

	}

	public void checkAccess(ThreadGroup g) {
	}

	//do not allow executing program to halt VM
	public void checkExit(int status) {
		hasExited = true;
		throw new SecurityException("InsECT: Attempt to exit VM");
	}

	public void checkExec(String cmd) {
	}
	public void checkLink(String lib) {
	}
	public void checkRead(FileDescriptor fd) {
	}
	public void checkRead(String file) {
	}
	public void checkRead(String file, Object context) {
	}
	public void checkWrite(FileDescriptor fd) {
	}
	public void checkWrite(String file) {
	}
	public void checkDelete(String file) {
	}
	public void checkConnect(String host, int port) {
	}
	public void checkConnect(String host, int port, Object context) {
	}
	public void checkListen(int port) {
	}
	public void checkAccept(String host, int port) {
	}
	public void checkMulticast(InetAddress maddr) {
	}
	//public void checkMulticast(InetAddress maddr, byte ttl) { } DEPRECATED
	public void checkPropertiesAccess() {
	}
	public void checkPropertyAccess(String key) {
	}
	public void checkPropertyAccess(String key, String def) {
	}
	public boolean checkTopLevelWindow(Object window) {
		return true;
	}
	public void checkPrintJobAccess() {
	}
	public void checkSystemClipboardAccess() {
	}
	public void checkAwtEventQueueAccess() {
	}
	public void checkPackageAccess(String pkg) {
	}
	public void checkPackageDefinition(String pkg) {
	}
	public void checkSetFactory() {
	}
	public void checkMemberAccess(Class clazz, int which) {
	}
	public void checkSecurityAccess(String provider) {
	}
	public void checkPermission(Permission perm) {
	}
	public void checkPermission(Permission perm, Object context) {
	}

	public ThreadGroup getThreadGroup() {
		if (insectGroup != null)
			return insectGroup;
		else
			return super.getThreadGroup();
	}

	public void setHasExited(boolean hasExited) {
		this.hasExited = hasExited;
	}

	private int getFullThreadCount() {

		Iterator tgroups = threadGroupSet.iterator();
		int count = 0;

		while (tgroups.hasNext()) {
			count += ((ThreadGroup) tgroups.next()).activeCount();
		}

		return count;

	}

	public void waitForExecution() {

		int numThreads = getFullThreadCount();
		boolean firstTime = true;

		while ((!hasExited) && (threadSet.size() > 0)) {

			//if there are active threads, make this thread yield until number of active
			//threads decreases

			if ((!firstTime) && (numThreads > 0)) {

				while ((!hasExited) && (getFullThreadCount() >= numThreads))
					Thread.yield();

			}

			firstTime = false;

			Iterator tgroups = threadGroupSet.iterator();
			Vector threadNames = new Vector();

			while (tgroups.hasNext()) {

				ThreadGroup tg = (ThreadGroup) tgroups.next();
				Thread[] t = new Thread[tg.activeCount()];
				tg.enumerate(t, false);

				for (int i = 0; i < t.length; i++) {
					if (t[i] == null)
						continue;
					else
						threadNames.add(t[i].toString());
				}

			}

			HashSet newThreadSet = new HashSet();

			for (int i = 0; i < threadNames.size(); i++) {
				String tname = (String) threadNames.elementAt(i);
				if (threadSet.contains(tname))
					newThreadSet.add(tname);
			}

			threadSet = newThreadSet;

		}
	}

}
