package insect.ant;


import insect.coverage.execution.BlockMonitor;
import insect.coverage.execution.CoverageData;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelper;

public class FlushTestFormatter implements JUnitResultFormatter
{
	private static final boolean DEBUG = false;
	
	private Hashtable testStarts = new Hashtable();

	/** The output stream where results should be sent. */
	protected OutputStream outStream;
	
	/** Holds test result outcomes for a given test run. */
	protected Properties results;
	
	boolean isSuccess = true;
	
	/** Public no-arg constructor. */
	public FlushTestFormatter() { }
	
	/**
	 * Initializes the result set for this run. Initializes the coverage flag.
	 * 
	 * @param junitTest the test suite that's starting
	 */
	public void startTestSuite(JUnitTest junitTest) throws BuildException 
	{ 
		results = new Properties();
	}
	
	/**
	 * Marks the test as initially passing and calls <code>super</code>.
	 *  
	 * @param test the test that is starting
	 */
	@Override
	public void startTest(Test test)
	{
		try{
			isSuccess = true;
			testStarts.put(test, new Long(System.currentTimeMillis()));
		}catch(Exception e){
			System.out.println("Exceptions happen in test: " + JUnitVersionHelper.getTestCaseClassName(test) 
					+"." + JUnitVersionHelper.getTestCaseName(test));
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Marks the test as having failed and calls <code>super</code>.
	 * 
	 * @param test      the test that errored
	 * @param throwable the related exception 
	 */
	@Override
	public void addError(Test test, Throwable throwable)
	{
		isSuccess = false; 
	}

	/**
	 * Marks the test as having failed and calls <code>super</code>.
	 * 
	 * @param test           the test that failed
	 * @param assertionError the assertion causing the test to fail
	 */
	@Override
	public void addFailure(Test test, AssertionFailedError assertionError)
	{
		isSuccess = false;
	}

	/**
	 * Writes out the test results and clears out results and output stream.  
	 * If the output stream is not the standard out or error then it will be closed.
	 * 
	 * @param junitTest the test suite that is ending
	 */
	public void endTestSuite(JUnitTest junitTest) throws BuildException 
	{
		try
		{
			if( outStream != null )
			{
				results.store(outStream, "Test results for: " + junitTest.getName());				
				if( outStream != System.out && outStream != System.err )
					outStream.close();
			}
		}
		catch(IOException e) { e.printStackTrace(); }
		finally
		{
			results   = null;
			outStream = null;
		}
	}

	public void setOutput(OutputStream outputStream) 
	{
		outStream = outputStream;
	}
	
	public void setSystemError(String error) 
	{
		if( DEBUG )
			System.out.println("FlushTestFormatter.setSystemError(): \n" + error);
	}
	public void setSystemOutput(String output) 
	{
		if( DEBUG )
			System.out.println("FlushTestFormatter.setSystemOutput(): \n" + output);
	}

	@Override
	public void endTest(Test test) {
		try{
			Long start = System.currentTimeMillis();
			Object o = testStarts.get(test);
			if(o != null){
				start = (Long)o;
			}
			long end = System.currentTimeMillis();
			if( test != null ){
				CoverageData.saveCoverageData(JUnitVersionHelper.getTestCaseClassName(test) 
						+"." + JUnitVersionHelper.getTestCaseName(test), new TestResult(end - start.longValue(), isSuccess));
			}
		}catch(Exception e){
			System.out.println("Exceptions happen in test: " + JUnitVersionHelper.getTestCaseClassName(test) 
					+"." + JUnitVersionHelper.getTestCaseName(test));
			e.printStackTrace();
		}
		
	}
}