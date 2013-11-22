package insect.coverage.execution;

import insect.ConfigFile;
import insect.ant.TestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CoverageData {
	public CoverageData() {
	}

	/**
	 * Save all the coverage data to the execution directory
	 * 
	 * @param execPath
	 *            execution directory
	 * @time running time for test execName (ms)
	 */
	public static synchronized void saveCoverageData(String execName, TestResult result) {
//		Long startTime = System.currentTimeMillis() ;
//		
//		// get execution path
//		String progName = insect.InsectValues.progName;
//		String instDir = ConfigFile.getProperty("INSTRUMENTED");
//		String execPath = instDir + File.separator + progName + File.separator
//				+ execName + ".xd";
//		System.out.println("InsECT: Placing execution output in directory "
//				+ execPath);
//
//		// create execution directory
//		File execDir = new File(execPath);
//		execDir.mkdirs();
//
//		// generate execution serial file
//		outputSerial(execPath);
		
		// output block hit coverage
		String prop;
		if ((prop = ConfigFile.getProperty("BLOCKCOVERAGE")) != null
				&& prop.toUpperCase().equals("TRUE")) {
			BlockMonitor.saveBlockCoverage(execName, result);
		}

		// output branch hit coverage
		if ((prop = ConfigFile.getProperty("BRANCHCOVERAGE")) != null
				&& prop.toUpperCase().equals("TRUE")) {
			BranchMonitor.saveBranchCoverage(execName);
		}
//
//		double runtime2 = (System.currentTimeMillis() - startTime.longValue()) / 1000.0;
//		runtime = runtime + runtime2;
//		
//		// generate runtime file
//		outputRuntime(execPath, runtime);
	}

	/**
	 * Outputs an exec.serial file indicating the serial code of the
	 * instrumentation.
	 * 
	 * @param execPath
	 *            path of execution directory
	 */
	private static synchronized void outputSerial(String execPath) {

		// create exec.serial
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(execPath
					+ File.separator + "exec.serial"));
			pw.println(insect.InsectValues.serial);
			pw.close();
		} catch (IOException e) {
			System.err.println("Error creating exec.serial");
		}
	}

	/**
	 * Outputs an insect.runtime file indicating the runtime for the test execution
	 * @param execPath path of execution directory
	 * @param runtime runtime for the test execution
	 */
	private static synchronized void outputRuntime(String execPath,
			double runtime) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(execPath
					+ File.separator + "insect.runtime"));
			//Format runtime
			pw.println(String.format("%.3f", runtime) + "s");
			pw.close();
		} catch (IOException e) {
			System.err.println("Error creating insect.runtime");
		}
	}

}
