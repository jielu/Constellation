package edu.gatech.plusone;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.TestCase;

public class PlusOne1Test extends TestCase {

	public void setUp() {
		try {
			super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sleeper.sleep();
	}
	
	public void test1() {
		Solution s = new Solution();
		int[] in = new int[] {0};
		int[] out = new int[] {1};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test2() {
		Solution s = new Solution();
		int[] in = new int[] {1};
		int[] out = new int[] {2};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test3() {
		Solution s = new Solution();
		int[] in = new int[] {9};
		int[] out = new int[] {1,0};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test4() {
		Solution s = new Solution();
		int[] in = new int[] {1,0};
		int[] out = new int[] {1,1};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test5() {
		Solution s = new Solution();
		int[] in = new int[] {9,9};
		int[] out = new int[] {1,0,0};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test6() {
		Solution s = new Solution();
		int[] in = new int[] {1,2,3};
		int[] out = new int[] {1,2,4};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test7() {
		Solution s = new Solution();
		int[] in = new int[] {9,9,9};
		int[] out = new int[] {1,0,0,0};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test8() {
		Solution s = new Solution();
		int[] in = new int[] {8,9,9,9};
		int[] out = new int[] {9,0,0,0};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test9() {
		Solution s = new Solution();
		int[] in = new int[] {1,0,0,0,0};
		int[] out = new int[] {1,0,0,0,1};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test10() {
		Solution s = new Solution();
		int[] in = new int[] {9,8,7,6,5,4,3,2,1,0};
		int[] out = new int[] {9,8,7,6,5,4,3,2,1,1};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test11() {
		Solution s = new Solution();
		int[] in = new int[] {8,8,5,0,5,1,9,7};
		int[] out = new int[] {8,8,5,0,5,1,9,8};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test12() {
		Solution s = new Solution();
		int[] in = new int[] {3,6,2,6,2,6,2,4,3};
		int[] out = new int[] {3,6,2,6,2,6,2,4,4};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test13() {
		Solution s = new Solution();
		int[] in = new int[] {6,6};
		int[] out = new int[] {6,7};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test14() {
		Solution s = new Solution();
		int[] in = new int[] {1,4,7,2,4};
		int[] out = new int[] {1,4,7,2,5};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test15() {
		Solution s = new Solution();
		int[] in = new int[] {2,4,9,3,9};
		int[] out = new int[] {2,4,9,4,0};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test16() {
		Solution s = new Solution();
		int[] in = new int[] {2,9,2};
		int[] out = new int[] {2,9,3};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test17() {
		Solution s = new Solution();
		int[] in = new int[] {4,8,6,3,2};
		int[] out = new int[] {4,8,6,3,3};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test18() {
		Solution s = new Solution();
		int[] in = new int[] {5,8,7,8,8};
		int[] out = new int[] {5,8,7,8,9};
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test19() {
		Solution s = new Solution();
		int[] in = new int[] {6,4,5,7,0,2};
		int[] out = new int[] {6,4,5,7,0,3};
		assertTrue(checker(out, s.plusOne(in)));
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
