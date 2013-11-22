package insect.coverage.instrumentation;

import insect.ConfigFile;
import jaba.main.ResourceFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.Type;

/**
 * Instruments a set of classes specified in a resource file 
 * with a number of different probes.  The classes are first copied
 * and the instrumentation is performed on those copies.
 * The actual instrumentation process is delegated to the ProbeInserter
 * class.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class Instrumentor implements org.apache.bcel.Constants {

	//for easy reference
	private static final String dirSep = File.separator;

	//temp directory
	private static final File tempDir =
		(new File(System.getProperty("user.dir") + dirSep + "__tmp"));

	//references to File objects for all of the classes
	private List classFiles;

	//retains the package each class belongs to
	private Map pkgTable;

	//InsECT properties
	private File instrDir;

	/**
	 * Constructor to create an Instrumentor from a resource file.
	 *
	 * @param rcFile resource file path
	 */
	public Instrumentor(String rcFile) {
		this(rcFile, null);
	}

	/**
	 * Constructor to create an Instrumentor by selecting classes
	 * listed in the specified class list from a resource file.
	 *
	 * @param rcFile resource file path
	 * @param classList class list file path
	 */
	public Instrumentor(String rcFile, String classList) {

		ResourceFile rf = new ResourceFile(rcFile);
		String[] pathNames = rf.getClassPaths();
		String[] classNames = rf.getClassFiles();
		classFiles = new ArrayList();
		pkgTable = new HashMap();

		//get directory information from config file
		String instrDirPath = ConfigFile.getProperty("INSTRUMENTED");
		if (instrDirPath == null) {
			System.err.println(
				"Aborting instrumentation: "
					+ "Please set properties in .insect config file.");
			return;
		}

		instrDir = new File(instrDirPath + dirSep + rf.getProgramName());

		//load names from classlist
		HashSet selectClasses = null;
		if (classList != null) {
			selectClasses = new HashSet();
			String curClass;
			try {
				BufferedReader clist =
					new BufferedReader(new FileReader(classList));
				while ((curClass = clist.readLine()) != null)
					selectClasses.add(curClass.trim());
				clist.close();
			} catch (IOException ioe) {
				System.err.println("Error reading class list");
				return;
			}
		}

		//prepare temp directory
		tempDir.mkdirs();

		//fix relative classpaths, extract jar files,
		//and add temp dir to classpaths
		String rfDir = (new File(rcFile)).getParent() + dirSep;
		String[] fixedPaths = new String[pathNames.length + 1];
		HashSet jrfClasses = null;
		for (int i = pathNames.length - 1; i >= 0; i--) {

			if ((new File(pathNames[i])).isAbsolute())
				fixedPaths[i] = pathNames[i];
			else
				fixedPaths[i] = rfDir + pathNames[i];

			//if jar file
			if ((fixedPaths[i].length() > 5)
				&& (fixedPaths[i]
					.substring(fixedPaths[i].length() - 4)
					.equals(".jar"))) {

				//if hasnt been done already,
				//load all class names specified in jrf
				if (jrfClasses == null) {
					jrfClasses = new HashSet();
					for (int j = 0; j < classNames.length; j++)
						jrfClasses.add(classNames[j]);
				}

				try {
					JarFile jar = new JarFile(fixedPaths[i]);
					Enumeration jarClasses = jar.entries();

					//extract all files to temp directory
					while (jarClasses.hasMoreElements()) {
						JarEntry jarClass = (JarEntry)jarClasses.nextElement();
						String className = jarClass.getName();

						if ((className.length() < 7)
							|| (!(jrfClasses
								.contains(
									className.substring(
										0,
										className.length() - 6).replaceAll(
										dirSep,
										".")))))
							continue;

						File newFile =
							new File(tempDir.getPath() + dirSep + className);

						newFile.getParentFile().mkdirs();

						InputStream inFile = jar.getInputStream(jarClass);
						FileOutputStream outFile =
							new FileOutputStream(newFile);
						byte[] buf = new byte[1024];
						int j = 0;
						while ((j = inFile.read(buf)) != -1) {
							outFile.write(buf, 0, j);
						}
						inFile.close();
						outFile.close();

					}
				} catch (IOException ioe) {
					System.err.println(
						"Error accessing jar file: " + fixedPaths[i]);
				}
			}
		}
		fixedPaths[fixedPaths.length - 1] = tempDir.getPath();
		pathNames = fixedPaths;

		//select files to instrument
		File fileFound;
		for (int i = 0; i < classNames.length; i++) {

			//locate file
			fileFound = getCorrectPath(classNames[i], pathNames);

			//if file exists and should be instrumented, add to file list
			if ((fileFound != null)
				&& ((selectClasses == null)
					|| (selectClasses
						.contains(
							classNames[i].replaceAll(".class", "").replaceAll(
								dirSep,
								".")))))
				classFiles.add(fileFound);
		}

		//attempt to create file copies and values class
		if ((!createFileCopies())
			|| (!createValuesClass(rf.getProgramName()))) {
			System.err.println("Instrumentation may not complete correctly");
		}
	}

	/**
	 * Determines the correct full path of a file from an array of possible
	 * classpaths. Returns a File object representing the determined path.
	 *
	 * @param file name of file
	 * @param paths names of paths
	 * @return a File object if file/path combo exists, otherwise null
	 */
	private File getCorrectPath(String file, String[] paths) {

		file = file.replaceAll("[.]", dirSep) + ".class";

		//create File objects to check existance of file/path combinations
		for (int i = 0; i < paths.length; i++) {
			File testPath = new File(paths[i] + dirSep + file);

			if (testPath.exists()) {
				pkgTable.put(testPath, file);
				return testPath;
			}
		}
		System.err.println("Could not locate path of " + file);
		return null;
	}

	/**
	 * Copies the set of files to be instrumented to the instrumentation
	 * directory.
	 *
	 * @return true if file copying succeeded, otherwise false
	 */
	private boolean createFileCopies() {

		//create instrumentation directory if necessary
		if (!instrDir.exists())
			instrDir.mkdirs();

		for (int i = 0; i < classFiles.size(); i++) {
			File curFile = (File) classFiles.get(i);
			File newFile =
				new File(instrDir.getPath() + dirSep + pkgTable.get(curFile));
			File parDir = newFile.getParentFile();
			FileInputStream inFile;
			FileOutputStream outFile;

			//open input file
			try {
				inFile = new FileInputStream(curFile);
			} catch (IOException openInExcep) {
				System.err.println("Unable to open file: "+curFile.getPath());
				return false;
			}

			//create parent directory if necessary
			if (!parDir.exists())
				parDir.mkdirs();

			//open outfput file
			try {
				outFile = new FileOutputStream(newFile);
			} catch (IOException openOutExcep) {
				openOutExcep.printStackTrace();
				System.err.println(
					"Unable to create file: " + newFile.getPath());
				return false;
			}

			//perform copy
			try {
				byte[] buf = new byte[1024];
				int j = 0;
				while ((j = inFile.read(buf)) != -1) {
					outFile.write(buf, 0, j);
				}
				inFile.close();
				outFile.close();
			} catch (IOException copyExcep) {
				System.out.println("Error copying file: " + curFile.getName());
				return false;
			}

			//replace reference to original file
			//with a reference to the new copy
			classFiles.set(i, newFile);
		}

		//now that files have been copied, remove temp dir
		removeTempDir(tempDir);

		return true;
	}

	/**
	 * Private method to recursively remove files stored
	 * in the temporary directory.
	 *
	 * @param tempDir current temporary directory
	 */
	private void removeTempDir(File tempDir) {

		if (tempDir.isDirectory()) {
			File[] tmpFiles = tempDir.listFiles();
			for (int i = 0; i < tmpFiles.length; i++) {
				if (tmpFiles[i].isDirectory())
					removeTempDir(tmpFiles[i]);
				else {
					tmpFiles[i].delete();
				}
			}
		}
		tempDir.delete();

	}

	/**
	 * Creates InsectValues.class in the instrumented directory
	 * for use during execution.
	 *
	 * @param progName name of instrumented program
	 * @return false on error, otherwise true
	 */
	private boolean createValuesClass(String progName) {

		(new File(instrDir.getPath() + dirSep + "insect")).mkdirs();

		ClassGen cg =
			new ClassGen(
				"insect.InsectValues",
				"java.lang.Object",
				"<generated>",
				ACC_PUBLIC,
				null);

		ConstantPoolGen cpgen = cg.getConstantPool();
		FieldGen progNameFG =
			new FieldGen(
				ACC_PUBLIC | ACC_STATIC | ACC_FINAL,
				Type.STRING,
				"progName",
				cpgen);
		FieldGen serialFG =
			new FieldGen(
				ACC_PUBLIC | ACC_STATIC | ACC_FINAL,
				Type.STRING,
				"serial",
				cpgen);
		FieldGen execDirFG =
			new FieldGen(
				ACC_PUBLIC | ACC_STATIC,
				Type.STRING,
				"execDir",
				cpgen);
		FieldGen profileFG =
			new FieldGen(
				ACC_PUBLIC | ACC_STATIC | ACC_FINAL,
				Type.BOOLEAN,
				"profile",
				cpgen);

		String serial = (new Date()).toString();

		progNameFG.setInitValue(progName);
		serialFG.setInitValue(serial);
		profileFG.setInitValue(
			Boolean.valueOf(ConfigFile.getProperty("PROFILE")).booleanValue());

		cg.addField(progNameFG.getField());
		cg.addField(serialFG.getField());
		cg.addField(execDirFG.getField());
		cg.addField(profileFG.getField());

		//write out InsectValues.class
		try {
			cg.getJavaClass().dump(
				instrDir.getPath()
					+ dirSep
					+ "insect"
					+ dirSep
					+ "InsectValues.class");
		} catch (IOException e) {
			System.err.println("Error creating InsectValues class");
			return false;
		}

		//create insect.serial
		try {
			PrintWriter pw =
				new PrintWriter(
					new FileWriter(
						instrDir.getPath() + dirSep + "insect.serial"));
			pw.println(serial);
			pw.close();
		} catch (IOException e) {
			System.err.println("Error creating insect.serial");
			return false;
		}

		return true;
	}

	/**
	 * Instruments all of the classes.
	 *
	 */
	public void instrument() {

		ProbeInserter probes;
		String[] allClasses = new String[classFiles.size()];

		for (int i = 0; i < classFiles.size(); i++)
			allClasses[i] = ((File) classFiles.get(i)).getPath();

		probes = new ProbeInserter(allClasses);
		probes.instrument(instrDir.getPath());

	}

	/**
	 * Main method for command line usage.
	 *
	 * @param args[] command line arguments
	 */
	public static void main(String args[]) {

		Instrumentor i = null;

		//classlist not specified
		if (args.length == 1) {
			i = new Instrumentor(args[0]);
			i.instrument();
		}

		//classlist specified
		else if (args.length == 2) {
			i = new Instrumentor(args[0], args[1]);
			i.instrument();
		}

		//incorrent number of arguments
		else
			System.err.println(
				"Usage: java Instrumentor <resource file> <optional class list>");

	}

} // Instrument
