package edu.gatech.plusone;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PlusOne3Test extends TestCase {

    public PlusOne3Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new PlusOne3Test("test47"));
    	test.addTest(new PlusOne3Test("test48"));
    	test.addTest(new PlusOne3Test("test49"));
    	test.addTest(new PlusOne3Test("test50"));
    	test.addTest(new PlusOne3Test("test51"));
    	test.addTest(new PlusOne3Test("test52"));
    	test.addTest(new PlusOne3Test("test53"));
    	test.addTest(new PlusOne3Test("test54"));
    	test.addTest(new PlusOne3Test("test55"));
    	test.addTest(new PlusOne3Test("test56"));
    	test.addTest(new PlusOne3Test("test57"));
    	test.addTest(new PlusOne3Test("test58"));
    	test.addTest(new PlusOne3Test("test59"));
    	test.addTest(new PlusOne3Test("test60"));
    	test.addTest(new PlusOne3Test("test61"));
    	test.addTest(new PlusOne3Test("test62"));
    	test.addTest(new PlusOne3Test("test63"));
    	test.addTest(new PlusOne3Test("test64"));
    	test.addTest(new PlusOne3Test("test65"));
    	test.addTest(new PlusOne3Test("test66"));
    	test.addTest(new PlusOne3Test("test67"));
    	test.addTest(new PlusOne3Test("test68"));
    	test.addTest(new PlusOne3Test("test69"));
    	test.addTest(new PlusOne3Test("test70"));
    	test.addTest(new PlusOne3Test("test71"));
    	test.addTest(new PlusOne3Test("test72"));
    	return test;
    }
	
	public void test47() {
		Solution s = new Solution();
		int[] in = new int[] {5,6,2,0,0,4,6,2,4,9};
		int[] out = new int[] {5,6,2,0,0,4,6,2,5,0};
		sleep(19955);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test48() {
		Solution s = new Solution();
		int[] in = new int[] {7,9,9};
		int[] out = new int[] {8,0,0};
		sleep(81172);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test49() {
		Solution s = new Solution();
		int[] in = new int[] {4,0,9,2,6,5,9};
		int[] out = new int[] {4,0,9,2,6,6,0};
		sleep(23847);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test50() {
		Solution s = new Solution();
		int[] in = new int[] {4,9};
		int[] out = new int[] {5,0};
		sleep(89346);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test51() {
		Solution s = new Solution();
		int[] in = new int[] {9,1,9,4,1,4,2,5};
		int[] out = new int[] {9,1,9,4,1,4,2,6};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test52() {
		Solution s = new Solution();
		int[] in = new int[] {3,0,3,7,4,4,2,3};
		int[] out = new int[] {3,0,3,7,4,4,2,4};
		sleep(56755);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test53() {
		Solution s = new Solution();
		int[] in = new int[] {1,0,9,3};
		int[] out = new int[] {1,0,9,4};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test54() {
		Solution s = new Solution();
		int[] in = new int[] {2,2};
		int[] out = new int[] {2,3};
		sleep(61878);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test55() {
		Solution s = new Solution();
		int[] in = new int[] {3,3,3,4,3,4,5};
		int[] out = new int[] {3,3,3,4,3,4,6};
		sleep(80802);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test56() {
		Solution s = new Solution();
		int[] in = new int[] {8,9,3};
		int[] out = new int[] {8,9,4};
		sleep(17305);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test57() {
		Solution s = new Solution();
		int[] in = new int[] {6,8,6,1,7};
		int[] out = new int[] {6,8,6,1,8};
		sleep(91111);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test58() {
		Solution s = new Solution();
		int[] in = new int[] {6,1,4,5,3,9,0,1,9,5,1,8,6,7,0,5,5,4,3};
		int[] out = new int[] {6,1,4,5,3,9,0,1,9,5,1,8,6,7,0,5,5,4,4};
		sleep(58412);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test59() {
		Solution s = new Solution();
		int[] in = new int[] {7,2,8,5,0,9,1,2,9,5,3,6,6,7,3,2,8,4,3,7,9,5,7,7,4,7,4,9,4,7,0,1,1,1,7,4,0,0,6};
		int[] out = new int[] {7,2,8,5,0,9,1,2,9,5,3,6,6,7,3,2,8,4,3,7,9,5,7,7,4,7,4,9,4,7,0,1,1,1,7,4,0,0,7};
		sleep(51695);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test60() {
		Solution s = new Solution();
		int[] in = new int[] {5,9,8,3,8,1,5,6,7,1};
		int[] out = new int[] {5,9,8,3,8,1,5,6,7,2};
		sleep(43346);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test61() {
		Solution s = new Solution();
		int[] in = new int[] {8,8,2,5,2,9,9,3,3,8,5,7,0,6,6};
		int[] out = new int[] {8,8,2,5,2,9,9,3,3,8,5,7,0,6,7};
		sleep(87850);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test62() {
		Solution s = new Solution();
		int[] in = new int[] {9,8,2,1,3,3,1,8,1,4,4,7,2,7,2,0,5,6,8,9,7,7,4,3};
		int[] out = new int[] {9,8,2,1,3,3,1,8,1,4,4,7,2,7,2,0,5,6,8,9,7,7,4,4};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test63() {
		Solution s = new Solution();
		int[] in = new int[] {9,0,0,8,8,4,9,6,8,2,1,1,3,9,4,9,5,1,1,2,5,4,9,1,2,0,8,1,4,1,9,6,3,9,6,1,5,7};
		int[] out = new int[] {9,0,0,8,8,4,9,6,8,2,1,1,3,9,4,9,5,1,1,2,5,4,9,1,2,0,8,1,4,1,9,6,3,9,6,1,5,8};
		sleep(13808);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test64() {
		Solution s = new Solution();
		int[] in = new int[] {5,9,0,8,4,0,2,3,5,5,7,0,0,3,1,3,7,2,4,8,8,5,0,6,1,1,2};
		int[] out = new int[] {5,9,0,8,4,0,2,3,5,5,7,0,0,3,1,3,7,2,4,8,8,5,0,6,1,1,3};
		sleep(80341);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test65() {
		Solution s = new Solution();
		int[] in = new int[] {9,9,2,4,0,2,3,4,4,5,0,2,3,9,2,3,2,3,9,9,7,3};
		int[] out = new int[] {9,9,2,4,0,2,3,4,4,5,0,2,3,9,2,3,2,3,9,9,7,4};
		sleep(49122);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test66() {
		Solution s = new Solution();
		int[] in = new int[] {6,0,9,2,1,3,7,6,2,8,0,6,9,4,1,5,1,9,5};
		int[] out = new int[] {6,0,9,2,1,3,7,6,2,8,0,6,9,4,1,5,1,9,6};
		sleep(63825);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test67() {
		Solution s = new Solution();
		int[] in = new int[] {2,4,5,7,8,0,6,9,8,1,9,6,2};
		int[] out = new int[] {2,4,5,7,8,0,6,9,8,1,9,6,3};
		sleep(55827);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test68() {
		Solution s = new Solution();
		int[] in = new int[] {5,3,7,3,5,6,3,4,7,0,7,2,1,6,8,4,0,4,1,8,5,3,6,4,1,8,5,9,2,6};
		int[] out = new int[] {5,3,7,3,5,6,3,4,7,0,7,2,1,6,8,4,0,4,1,8,5,3,6,4,1,8,5,9,2,7};
		sleep(80449);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test69() {
		Solution s = new Solution();
		int[] in = new int[] {7,1,9,3,9,5,8,3,2,8,2,5,1,9,5,7,1,9,8,1,4,2,8,1,5,8,6,7,0,4};
		int[] out = new int[] {7,1,9,3,9,5,8,3,2,8,2,5,1,9,5,7,1,9,8,1,4,2,8,1,5,8,6,7,0,5};
		sleep(29311);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test70() {
		Solution s = new Solution();
		int[] in = new int[] {9,6,6,2,5,4,3,0,6,1,2,3,5,3,8,2,5};
		int[] out = new int[] {9,6,6,2,5,4,3,0,6,1,2,3,5,3,8,2,6};
		sleep(56172);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test71() {
		Solution s = new Solution();
		int[] in = new int[] {3,8,6,5,6,9,2,6,6,9};
		int[] out = new int[] {3,8,6,5,6,9,2,6,7,0};
		sleep(31152);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test72() {
		Solution s = new Solution();
		int[] in = new int[] {8,8,8,5,2,5,1,5,5,7,9,0,3,4,3,3,8,0};
		int[] out = new int[] {8,8,8,5,2,5,1,5,5,7,9,0,3,4,3,3,8,1};
		sleep(37546);
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
