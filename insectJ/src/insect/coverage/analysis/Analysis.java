/*
 * Created on Sep 7, 2003
 */
package insect.coverage.analysis;

import insect.ConfigFile;
import insect.ResourceFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains functionality for loading InsECT execution information.
 * Subclasses can utilize this functionality for providing analysis of
 * executions. 
 * 
 * @author Anil Chawla
 */
public abstract class Analysis {

	/* stored here for fast access */
	protected static String dirSep = File.separator;
	protected static String pathSep = File.pathSeparator;

	/**
	 * Maps class ID numbers to class names.
	 */
	protected static HashMap classIDs;
	
	/**
	 * Maintains the first level of a multi-level data structure
	 * for storing basic block information. The structure is as
	 * follows: Class name table -> method name table -> basic
	 * block table.
	 */
	protected static HashMap blockClasses;

	/* instance variables */
	protected String mainDirName;
	protected File instrDir;
	protected String[] execIDs;
	protected HashMap execIndex;
	protected String serial;
	
	/**
	 * Loads the execution information associated with the program
	 * described by the given resource file.
	 * 
	 * @param rcPath filepath of resource file.
	 */
	public Analysis(String rcPath) {
		
		//locate instrumented program directory
		ResourceFile rf = new ResourceFile(rcPath);
		String instrPath = ConfigFile.getProperty("INSTRUMENTED");
		if (instrPath == null) {
			System.err.println(
				"Could not initialize. " + "Please configure '.insect' file.");
			return;
		}
		instrDir = new File(instrPath + dirSep + rf.getProgramName());

		//read serial of instrumented program
		BufferedReader br = null;
		try {
			br =
				new BufferedReader(
					new FileReader(
						instrDir.getPath() + dirSep + "insect.serial"));
			serial = br.readLine();
			br.close();
		}
		catch (IOException e) {
			System.err.println("Error reading insect.serial");
			e.printStackTrace();
			try {br.close();} catch (IOException e2) {}
		}

		//load ID information
		loadClassIDs();
		loadBasicBlockIDs();		
	}

	/**
	 * Reads in and stores the class and method
	 * identifiers for this program.
	 *
	 */
	private void loadClassIDs() {

		classIDs = new HashMap();

		try {
			String entity, curLine;
			int spIndex = -1;

			BufferedReader idFile =
				new BufferedReader(
					new FileReader(
						instrDir.getPath() + dirSep + "insect.classids"));

			while ((curLine = idFile.readLine()) != null) {

				//parse entity name and ID number
				spIndex = curLine.indexOf(":");
				entity = curLine.substring(spIndex + 1);

				classIDs.put(
					new Integer(curLine.substring(0, spIndex)),
					entity);

			}
		}
		catch (IOException ioe) {
			System.err.println("Error reading insect.classids");
			classIDs = null;
		}
	}

	/**
	 * Reads in and stores the basic block ID
	 * indentifiers for this program.
	 *
	 */
	private void loadBasicBlockIDs() {

		//read in and store basic block id information
		try {

			blockClasses = new HashMap();

			BufferedReader blockFile =	
				new BufferedReader(	new FileReader(
						instrDir.getPath() + dirSep + "insect.blockids"));

			String className, methodSig, curLine;
			int start, end, spIndex;
			int count = 0;

			while ((curLine = blockFile.readLine()) != null) {

				//parse classname, methodsig, and line numbers
				spIndex = curLine.indexOf(" ");
				className = curLine.substring(0, spIndex);
				curLine = curLine.substring(spIndex + 1);
				spIndex = curLine.indexOf(" ");
				methodSig = curLine.substring(0, spIndex);
				curLine = curLine.substring(spIndex + 1);
				spIndex = curLine.indexOf("-");
				start = Integer.parseInt(curLine.substring(0, spIndex));
				curLine = curLine.substring(spIndex + 1);
				spIndex = curLine.indexOf(" ");
				end = Integer.parseInt(curLine.substring(0, spIndex));
				curLine = curLine.substring(spIndex + 1);
				if (Integer.parseInt(curLine) != count) {
					System.err.println("Error in block ID ordering: " + count);
					blockFile.close();
					blockClasses = null;
					return;
				}

				// store blocks according to the class
				// and method they belong to
				HashMap blockMethods = (HashMap) blockClasses.get(className);
				if (blockMethods == null) {
					blockMethods = new HashMap();
					blockClasses.put(className, blockMethods);
				}
				ArrayList bblocks = (ArrayList) blockMethods.get(methodSig);
				if (bblocks == null) {
					bblocks = new ArrayList();
					blockMethods.put(methodSig, bblocks);
				}
				bblocks.add(new BasicBlock(start, end, count));
				count++;
			}

			blockFile.close();
		}
		catch (IOException blockExcep) {
			blockClasses = null;
		}
	}

	/**
	 * Reloads ID information for the program being analyzed.
	 *
	 */
	public void reloadIDs() {

		blockClasses = null;		
		loadBasicBlockIDs();
		
		classIDs = null;
		loadClassIDs();			
	}

	/**
	 * Recursively determines all directories containing
	 * execution information for the instrumented program.
	 *
	 * @param curDir current directory.
	 * @param curPath current directory root path name.
	 * @return ArrayList of execution directory path names.
	 */
	protected ArrayList getExecDirs(File curDir, String curPath) {

		ArrayList execDirs = new ArrayList();
		File[] dirList = curDir.listFiles();
		if (dirList == null)
			return execDirs;
			
		for (int i = 0; i < dirList.length; i++) {
			if (dirList[i].isDirectory()) {
				String dirName = dirList[i].getName();
				if ((dirName.length() > 3)
					&& 
					(dirName.substring(dirName.length() - 3).equals(".xd"))) {

					//verify serial of this execution before adding
					BufferedReader br = null;
					try {
						br =
							new BufferedReader(
								new FileReader(
									dirList[i].getPath()
										+ dirSep
										+ "exec.serial"));
						if (br.readLine().equals(serial))
							execDirs.add(curPath + dirName);
						else
							System.err.println(
								"Execution serial does not match: "
								+ dirName.substring(0, dirName.length() - 3));
						br.close();
					} 
					catch (IOException e) {
						try {br.close();} catch (Exception e2) {}
					}
				} else
					execDirs.addAll(
						getExecDirs(dirList[i], curPath + dirName + dirSep));
			}
		}
		return execDirs;
	}

	/**
	 * Returns a reader for the specified ID file in
	 * the instrumented program directory.
	 *
	 * @param fileName name of file.
	 * @return a BufferedReader for the file.
	 * @throws IOException if an error occurs, or null if file does not exist.
	 */
	protected BufferedReader getIDFileReader(String fileName)
		throws IOException {

		File idFile = new File(instrDir.getPath() + dirSep + fileName);

		if (!idFile.exists())
			return null;
		else
			return new BufferedReader(new FileReader(idFile));
	}

	/**
	 * @return the mapping of ids to classes/methods.
	 */
	public HashMap getClassIDs() {
		return classIDs;
	}

	/**
	 * @return the mapping of class names to HashMaps of method names, which
	 *		 	in turn contain mappings to the set of a method's BasicBlocks.
	 */
	public HashMap getBlockClasses() {
		return blockClasses;
	}

	/**     
	 * @return identifier names of executions being analyzed.
	 */
	public String[] getExecIDs() {
		return execIDs;
	}

}
