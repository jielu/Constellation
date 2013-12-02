package edu.gatech.plusone;

import java.util.Arrays;

import edu.gatech.DistinctSubsequences.DistinctSubsequences4Test;
import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PlusOne5Test extends TestCase {

    public PlusOne5Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new PlusOne5Test("test95"));
    	test.addTest(new PlusOne5Test("test96"));
    	test.addTest(new PlusOne5Test("test97"));
    	test.addTest(new PlusOne5Test("test98"));
    	test.addTest(new PlusOne5Test("test99"));
    	test.addTest(new PlusOne5Test("test100"));
    	test.addTest(new PlusOne5Test("test101"));
    	test.addTest(new PlusOne5Test("test102"));
    	test.addTest(new PlusOne5Test("test103"));
    	test.addTest(new PlusOne5Test("test104"));
    	test.addTest(new PlusOne5Test("test105"));
    	test.addTest(new PlusOne5Test("test106"));
    	test.addTest(new PlusOne5Test("test107"));
    	test.addTest(new PlusOne5Test("test108"));
    	test.addTest(new PlusOne5Test("test20"));
    	test.addTest(new PlusOne5Test("test21"));
    	test.addTest(new PlusOne5Test("test22"));
    	test.addTest(new PlusOne5Test("test23"));
    	test.addTest(new PlusOne5Test("test24"));
    	test.addTest(new PlusOne5Test("test25"));
    	return test;
    }
	
	public void test95() {
		Solution s = new Solution();
		int[] in = new int[] {2,1,6,7,3,7,4,7,2,0,6,8,2,1,8,6,0,0,0,5,5,4,7,7,8};
		int[] out = new int[] {2,1,6,7,3,7,4,7,2,0,6,8,2,1,8,6,0,0,0,5,5,4,7,7,9};
		sleep(68537);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test96() {
		Solution s = new Solution();
		int[] in = new int[] {3,1,8,5,6,2,6,3,0,9,2,4,9,4,6,7,3,8,8,3,6};
		int[] out = new int[] {3,1,8,5,6,2,6,3,0,9,2,4,9,4,6,7,3,8,8,3,7};
		sleep(13898);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test97() {
		Solution s = new Solution();
		int[] in = new int[] {6,6,5,1,2,5,8,0,6,3,3,4,8,0,9,7,5,1,8,7};
		int[] out = new int[] {6,6,5,1,2,5,8,0,6,3,3,4,8,0,9,7,5,1,8,8};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test98() {
		Solution s = new Solution();
		int[] in = new int[] {9,3,2,6,8,2,7,2,9,7,8,5,3,1,0};
		int[] out = new int[] {9,3,2,6,8,2,7,2,9,7,8,5,3,1,1};
		sleep(62125);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test99() {
		Solution s = new Solution();
		int[] in = new int[] {2,2,6,5,6,3,4,6,2,3,1,3,1,0,0,1,3,4};
		int[] out = new int[] {2,2,6,5,6,3,4,6,2,3,1,3,1,0,0,1,3,5};
		sleep(89845);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test100() {
		Solution s = new Solution();
		int[] in = new int[] {2,7,4,6,6,4,4,3,7,7,3,7,9,6,5,5,2};
		int[] out = new int[] {2,7,4,6,6,4,4,3,7,7,3,7,9,6,5,5,3};
		sleep(44586);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test101() {
		Solution s = new Solution();
		int[] in = new int[] {1,0,0,4,3,5,5,3,6,8,8,0,6,2,9,2,8,5,8,4,1,7,2,4,4,1,2,2};
		int[] out = new int[] {1,0,0,4,3,5,5,3,6,8,8,0,6,2,9,2,8,5,8,4,1,7,2,4,4,1,2,3};
		sleep(23929);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test102() {
		Solution s = new Solution();
		int[] in = new int[] {6,0,9,6,3,3,9,8,1,2,4,9,3,7,7,5,6,2,4,4,0,8,7,8,2,3,4,6};
		int[] out = new int[] {6,0,9,6,3,3,9,8,1,2,4,9,3,7,7,5,6,2,4,4,0,8,7,8,2,3,4,7};
		sleep(16887);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test103() {
		Solution s = new Solution();
		int[] in = new int[] {6,4,2,9,6,1,2,9,0,0,2,5,7,4,8,6,1,3,4,5,9,8,6,0,5,6,2,9,0,0,7,9,5,1,8,1};
		int[] out = new int[] {6,4,2,9,6,1,2,9,0,0,2,5,7,4,8,6,1,3,4,5,9,8,6,0,5,6,2,9,0,0,7,9,5,1,8,2};
		sleep(57297);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test104() {
		Solution s = new Solution();
		int[] in = new int[] {2,5,2,7,2,9,1,2,5,4,7,0};
		int[] out = new int[] {2,5,2,7,2,9,1,2,5,4,7,1};
		sleep(39654);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test105() {
		Solution s = new Solution();
		int[] in = new int[] {7,0,8,7,8,4,1,9,6,2,6,7,9,7,5,2,2,7,4,7};
		int[] out = new int[] {7,0,8,7,8,4,1,9,6,2,6,7,9,7,5,2,2,7,4,8};
		sleep(96646);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test106() {
		Solution s = new Solution();
		int[] in = new int[] {3,1,1,4,3,9,9,0,1,1,9,1,9,6,1,5,0,0,3,4,8,1,5,8,8,7,0,6,3,9};
		int[] out = new int[] {3,1,1,4,3,9,9,0,1,1,9,1,9,6,1,5,0,0,3,4,8,1,5,8,8,7,0,6,4,0};
		sleep(61462);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test107() {
		Solution s = new Solution();
		int[] in = new int[] {7,0,9,3,3,0,4,5,1,5,5,5,6,3,6,1,5,7,5,9,7,6,6,5,6,3,5,2};
		int[] out = new int[] {7,0,9,3,3,0,4,5,1,5,5,5,6,3,6,1,5,7,5,9,7,6,6,5,6,3,5,3};
		sleep(40221);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test108() {
		Solution s = new Solution();
		int[] in = new int[] {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9};
		int[] out = new int[] {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		sleep(84011);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test20() {
		Solution s = new Solution();
		int[] in = new int[] {4,8};
		int[] out = new int[] {4,9};
		sleep(26800);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test21() {
		Solution s = new Solution();
		int[] in = new int[] {9,9,9,0,1};
		int[] out = new int[] {9,9,9,0,2};
		sleep(81275);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test22() {
		Solution s = new Solution();
		int[] in = new int[] {1,3};
		int[] out = new int[] {1,4};
		sleep(47734);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test23() {
		Solution s = new Solution();
		int[] in = new int[] {6,3,3,9,5,7};
		int[] out = new int[] {6,3,3,9,5,8};
		sleep(58776);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test24() {
		Solution s = new Solution();
		int[] in = new int[] {3,6,5,3,1,3,8};
		int[] out = new int[] {3,6,5,3,1,3,9};
		sleep(20308);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test25() {
		Solution s = new Solution();
		int[] in = new int[] {1,8,3,4,3,3,8,4,3};
		int[] out = new int[] {1,8,3,4,3,3,8,4,4};
		sleep(32481);
		assertTrue(checker(out, s.plusOne(in)));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
	
	private boolean checker(int[] a, int[] b) {
		if(a == null || b == null) return false;
		else if(a.length != b.length) return false;
		else {
			for(int i = 0; i < a.length; i++) {
				if(a[i] != b[i]) return false;
			}
		}
		return true;
	}
}
