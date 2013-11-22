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
