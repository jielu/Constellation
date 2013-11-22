package insect.coverage.execution;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

/**
 * InitMonitor.java
 *
 * Ensures that all InsECT variables are properly set
 * for the initialization of the rest of the monitors.
 *
 * Created: Thu Feb 27 23:32:15 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class InitMonitor {

	/**
	 * Initializes any state necessary for the proper
	 * execution of an InsECT instrumented program.
	 * Currently only sets the execution directory if
	 * it is null.
	 *
	 */
	public static void init() {

		//nothing to be done
		if (insect.InsectValues.execDir != null) {
			//outputSerial(insect.InsectValues.execDir);
			return;
		}

		String progName = insect.InsectValues.progName;
		String outDirPath = System.getProperty("user.home");
		String execID = (new Date()).toString() + ".xd";

		//try to figure out the instrumented directory by
		//looking in the .insect file
		try {
			String filePath = outDirPath + File.separator + ".insect";
			FileInputStream cfgFile = new FileInputStream(filePath);
			Properties props = new Properties();
			props.load(cfgFile);
			cfgFile.close();
			String prop = props.getProperty("INSTRUMENTED");
			if (prop != null)
				outDirPath = prop;

			//check if exec dirs should be sequenced
			prop = props.getProperty("EXECID");
			if ((prop != null) && (prop.toLowerCase().equals("sequence"))) {
				int val = 1;
				File seq =
					new File(
						outDirPath
							+ File.separator
							+ progName
							+ File.separator
							+ "insect.seq");

				//get current seq
				try {
					DataInputStream inStream =
						new DataInputStream(new FileInputStream(seq));
					val = inStream.readInt();
					inStream.close();
				} catch (IOException ioe1) {
				}

				//write next seq
				try {
					DataOutputStream outStream =
						new DataOutputStream(new FileOutputStream(seq));
					outStream.writeInt(val + 1);
					outStream.close();
				} catch (IOException ioe2) {
				}

				execID = val + ".xd";
			}

		}

		//if .insect is unavailable, we must place the execution
		//directory in the user's home. therefore, append an _
		//before the directory name
		catch (IOException loadExcep) {
			progName = "_" + progName;
		}

		//create directory and set variable
//		File execDir =
//			new File(
//				outDirPath
//					+ File.separator
//					+ progName
//					+ File.separator
//					+ execID);
//		execDir.mkdirs();
//		insect.InsectValues.execDir = execDir.getPath();
		insect.InsectValues.execDir = execID.replace(".xd", "");

//		outputSerial(execDir.getPath());

		//inform the user!
//		insect.Debug.println(
//			"InsECT: Placing execution output in directory "
//				+ insect.InsectValues.execDir,
//			3);
	}

	/**
	 * Alternative initialization that allows for setting the directory
	 * name.
	 *
	 */
	public static void init(String dirname) {
		//nothing to be done
		if (insect.InsectValues.execDir != null) {
			//outputSerial(insect.InsectValues.execDir);
			return;
		}

		
		String progName = insect.InsectValues.progName;
		String outDirPath = System.getProperty("user.home");
		String execID = dirname + ".xd";

		//try to figure out the instrumented directory by
		//looking in the .insect file
		try {
			String filePath = outDirPath + File.separator + ".insect";
			FileInputStream cfgFile = new FileInputStream(filePath);
			Properties props = new Properties();
			props.load(cfgFile);
			cfgFile.close();
			String prop = props.getProperty("INSTRUMENTED");
			if (prop != null)
				outDirPath = prop;
		}

		//if .insect is unavailable, we must place the execution
		//directory in the user's home. therefore, append an _
		//before the directory name
		catch (IOException loadExcep) {
			progName = "_" + progName;
		}

		//create directory and set variable
//		File execDir =
//			new File(
//				outDirPath
//					+ File.separator
//					+ progName
//					+ File.separator
//					+ execID);
//		execDir.mkdirs();
//		insect.InsectValues.execDir = execDir.getPath();
//
//		outputSerial(execDir.getPath());
//
//		//inform the user!
//		insect.Debug.println(
//			"InsECT: Placing execution output in directory "
//				+ insect.InsectValues.execDir, 5);
		insect.InsectValues.execDir = dirname;
	}

	/**
	 * Outputs an exec.serial file indicating the
	 * serial code of the instrumentation.
	 *
	 * @param execPath path of execution directory
	 */
	private static void outputSerial(String execPath) {

		//create exec.serial
		try {
			PrintWriter pw =
				new PrintWriter(
					new FileWriter(execPath + File.separator + "exec.serial"));
			pw.println(insect.InsectValues.serial);
			pw.close();
		}
		catch (IOException e) {
			System.err.println("Error creating exec.serial");
		}
	}

} // InitMonitor
