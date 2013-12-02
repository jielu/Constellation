package edu.gatech.common;

import edu.gatech.DistinctSubsequences.DistinctSubsequences1Test;
import edu.gatech.DistinctSubsequences.DistinctSubsequences2Test;
import edu.gatech.DistinctSubsequences.DistinctSubsequences3Test;
import edu.gatech.DistinctSubsequences.DistinctSubsequences4Test;
import edu.gatech.plusone.PlusOne1Test;
import edu.gatech.plusone.PlusOne2Test;
import edu.gatech.plusone.PlusOne3Test;
import edu.gatech.plusone.PlusOne4Test;
import edu.gatech.plusone.PlusOne5Test;
import edu.gatech.pow.Pow1Test;
import edu.gatech.pow.Pow2Test;
import edu.gatech.pow.Pow3Test;
import edu.gatech.pow.Pow4Test;
import edu.gatech.pow.Pow5Test;
import edu.gatech.pow.Pow6Test;
import edu.gatech.scrambleString.ScrambleString1Test;
import edu.gatech.scrambleString.ScrambleString2Test;
import edu.gatech.scrambleString.ScrambleString3Test;
import edu.gatech.scrambleString.ScrambleString4Test;
import edu.gatech.scrambleString.ScrambleString5Test;
import edu.gatech.scrambleString.ScrambleString6Test;
import edu.gatech.scrambleString.ScrambleString7Test;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestAll extends TestCase {
	public TestAll(String testName) {
        super(testName);
    }

    public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(DistinctSubsequences1Test.suite());
    	test.addTest(DistinctSubsequences2Test.suite());
    	test.addTest(DistinctSubsequences3Test.suite());
    	test.addTest(DistinctSubsequences4Test.suite());
    	test.addTest(PlusOne1Test.suite());
    	test.addTest(PlusOne2Test.suite());
    	test.addTest(PlusOne3Test.suite());
    	test.addTest(PlusOne4Test.suite());
    	test.addTest(PlusOne5Test.suite());
    	test.addTest(Pow1Test.suite());
    	test.addTest(Pow2Test.suite());
    	test.addTest(Pow3Test.suite());
    	test.addTest(Pow4Test.suite());
    	test.addTest(Pow5Test.suite());
    	test.addTest(Pow6Test.suite());
    	test.addTest(ScrambleString1Test.suite());
    	test.addTest(ScrambleString2Test.suite());
    	test.addTest(ScrambleString3Test.suite());
    	test.addTest(ScrambleString4Test.suite());
    	test.addTest(ScrambleString5Test.suite());
    	test.addTest(ScrambleString6Test.suite());
    	test.addTest(ScrambleString7Test.suite());
    	
    	return test;
    }
    
    public static void main(String args[]) {
        String[] testCaseName = { TestAll.class.getName() };
        junit.textui.TestRunner.main(testCaseName);
    }
}
