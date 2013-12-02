package edu.gatech.plusone;

import java.util.Arrays;

import edu.gatech.DistinctSubsequences.DistinctSubsequences4Test;
import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PlusOne2Test extends TestCase {

    public PlusOne2Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new PlusOne2Test("test26"));
    	test.addTest(new PlusOne2Test("test27"));
    	test.addTest(new PlusOne2Test("test28"));
    	test.addTest(new PlusOne2Test("test29"));
    	test.addTest(new PlusOne2Test("test30"));
    	test.addTest(new PlusOne2Test("test31"));
    	test.addTest(new PlusOne2Test("test32"));
    	test.addTest(new PlusOne2Test("test33"));
    	test.addTest(new PlusOne2Test("test34"));
    	test.addTest(new PlusOne2Test("test35"));
    	test.addTest(new PlusOne2Test("test36"));
    	test.addTest(new PlusOne2Test("test37"));
    	test.addTest(new PlusOne2Test("test38"));
    	test.addTest(new PlusOne2Test("test39"));
    	test.addTest(new PlusOne2Test("test40"));
    	test.addTest(new PlusOne2Test("test41"));
    	test.addTest(new PlusOne2Test("test42"));
    	test.addTest(new PlusOne2Test("test43"));
    	test.addTest(new PlusOne2Test("test44"));
    	test.addTest(new PlusOne2Test("test45"));
    	test.addTest(new PlusOne2Test("test46"));
    	return test;
    }
	
	public void test26() {
		Solution s = new Solution();
		int[] in = new int[] {4,6,0,6,0,7,4,5};
		int[] out = new int[] {4,6,0,6,0,7,4,6};
		sleep(99884);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test27() {
		Solution s = new Solution();
		int[] in = new int[] {3};
		int[] out = new int[] {4};
		sleep(71105);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test28() {
		Solution s = new Solution();
		int[] in = new int[] {7,1};
		int[] out = new int[] {7,2};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test29() {
		Solution s = new Solution();
		int[] in = new int[] {5,8,0,6,2,8,7};
		int[] out = new int[] {5,8,0,6,2,8,8};
		sleep(79008);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test30() {
		Solution s = new Solution();
		int[] in = new int[] {8,2,9,3,5,0};
		int[] out = new int[] {8,2,9,3,5,1};
		sleep(94394);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test31() {
		Solution s = new Solution();
		int[] in = new int[] {8,9,4,5,0,0,7,9};
		int[] out = new int[] {8,9,4,5,0,0,8,0};
		sleep(78893);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test32() {
		Solution s = new Solution();
		int[] in = new int[] {2,0,9,5,9,2,4};
		int[] out = new int[] {2,0,9,5,9,2,5};
		sleep(75877);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test33() {
		Solution s = new Solution();
		int[] in = new int[] {1,6,3,5,6,2,0,4};
		int[] out = new int[] {1,6,3,5,6,2,0,5};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test34() {
		Solution s = new Solution();
		int[] in = new int[] {9,8,2,1,7};
		int[] out = new int[] {9,8,2,1,8};
		sleep(18717);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test35() {
		Solution s = new Solution();
		int[] in = new int[] {4,2,9,9};
		int[] out = new int[] {4,3,0,0};
		sleep(64920);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test36() {
		Solution s = new Solution();
		int[] in = new int[] {6,4,3};
		int[] out = new int[] {6,4,4};
		sleep(42226);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test37() {
		Solution s = new Solution();
		int[] in = new int[] {1,6,8};
		int[] out = new int[] {1,6,9};
		sleep(19090);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test38() {
		Solution s = new Solution();
		int[] in = new int[] {1,4,1,1,1,7,2};
		int[] out = new int[] {1,4,1,1,1,7,3};
		sleep(71151);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test39() {
		Solution s = new Solution();
		int[] in = new int[] {9,4};
		int[] out = new int[] {9,5};
		sleep(51397);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test40() {
		Solution s = new Solution();
		int[] in = new int[] {5,0,9,8,5,6,2,5,5};
		int[] out = new int[] {5,0,9,8,5,6,2,5,6};
		sleep(98023);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test41() {
		Solution s = new Solution();
		int[] in = new int[] {5,3,1,0,6,4};
		int[] out = new int[] {5,3,1,0,6,5};
		sleep(74545);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test42() {
		Solution s = new Solution();
		int[] in = new int[] {4,2,9,1,3,2,4,1,7};
		int[] out = new int[] {4,2,9,1,3,2,4,1,8};
		sleep(46368);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test43() {
		Solution s = new Solution();
		int[] in = new int[] {3,3,2,0,5,2};
		int[] out = new int[] {3,3,2,0,5,3};
		sleep(20769);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test44() {
		Solution s = new Solution();
		int[] in = new int[] {2,8,5,9,5,0,0,8,6};
		int[] out = new int[] {2,8,5,9,5,0,0,8,7};
		sleep(35746);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test45() {
		Solution s = new Solution();
		int[] in = new int[] {6,3,9,8,6,2,2};
		int[] out = new int[] {6,3,9,8,6,2,3};
		sleep(15221);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test46() {
		Solution s = new Solution();
		int[] in = new int[] {5};
		int[] out = new int[] {6};
		sleep(85773);
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
