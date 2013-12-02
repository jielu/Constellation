package edu.gatech.plusone;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.TestCase;

public class PlusOne4Test extends TestCase {

	public void test74() {
		Solution s = new Solution();
		int[] in = new int[] {9,7,0,1,4,1,2,8,0,2,2,3,8,1,7,2,5,0,7,8,8};
		int[] out = new int[] {9,7,0,1,4,1,2,8,0,2,2,3,8,1,7,2,5,0,7,8,9};
		sleep(99837);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test75() {
		Solution s = new Solution();
		int[] in = new int[] {6,9,0,1,0,8,7,5,9,6,2,9,9,9,3,4,7,3,6,9,8,4,3,5,6,8,6,3,6,6,9,2,6,2,4,8};
		int[] out = new int[] {6,9,0,1,0,8,7,5,9,6,2,9,9,9,3,4,7,3,6,9,8,4,3,5,6,8,6,3,6,6,9,2,6,2,4,9};
		sleep(88095);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test76() {
		Solution s = new Solution();
		int[] in = new int[] {1,3,9,9,8,1,0,7,4,4};
		int[] out = new int[] {1,3,9,9,8,1,0,7,4,5};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test77() {
		Solution s = new Solution();
		int[] in = new int[] {9,0,6,7,4,9,2,3,7,8,8,4,5,8,8,3,2,2,1,4,5,6,3,4,4,6};
		int[] out = new int[] {9,0,6,7,4,9,2,3,7,8,8,4,5,8,8,3,2,2,1,4,5,6,3,4,4,7};
		sleep(33335);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test78() {
		Solution s = new Solution();
		int[] in = new int[] {3,0,9,0,9,2,8,8,8,7,1,1,6,9,0};
		int[] out = new int[] {3,0,9,0,9,2,8,8,8,7,1,1,6,9,1};
		sleep(51675);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test79() {
		Solution s = new Solution();
		int[] in = new int[] {6,0,1,9,2,3,2,6,9,9,1,5};
		int[] out = new int[] {6,0,1,9,2,3,2,6,9,9,1,6};
		sleep(86174);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test80() {
		Solution s = new Solution();
		int[] in = new int[] {2,1,0,4,3,2,6,1,0,4,0,3,6,6,3,8,9,1,8,2,1,2,5,5,0,6,6,3,4,4,3,6,5,6,0};
		int[] out = new int[] {2,1,0,4,3,2,6,1,0,4,0,3,6,6,3,8,9,1,8,2,1,2,5,5,0,6,6,3,4,4,3,6,5,6,1};
		sleep(58679);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test81() {
		Solution s = new Solution();
		int[] in = new int[] {6,1,0,3,3,6,9,9,9,7,0,2,7,2,3,9,0,1,1,8};
		int[] out = new int[] {6,1,0,3,3,6,9,9,9,7,0,2,7,2,3,9,0,1,1,9};
		sleep(18343);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test82() {
		Solution s = new Solution();
		int[] in = new int[] {4,2,1,8,1,8,4,1,8,4,0,1,6,3,4,2,4,6,3,3,8,6,0,3,1,1,3,2,2,3,2,8,6,3,6,7,4};
		int[] out = new int[] {4,2,1,8,1,8,4,1,8,4,0,1,6,3,4,2,4,6,3,3,8,6,0,3,1,1,3,2,2,3,2,8,6,3,6,7,5};
		sleep(20370);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test83() {
		Solution s = new Solution();
		int[] in = new int[] {2,8,0,6,7,5,2,1,9,0,7,2};
		int[] out = new int[] {2,8,0,6,7,5,2,1,9,0,7,3};
		sleep(64303);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test84() {
		Solution s = new Solution();
		int[] in = new int[] {5,2,2,6,5,7,1,9,0,3,8,6,8,6,5,2,1,8,7,9,8,3,8,4,7,2,5,8,9};
		int[] out = new int[] {5,2,2,6,5,7,1,9,0,3,8,6,8,6,5,2,1,8,7,9,8,3,8,4,7,2,5,9,0};
		sleep(71820);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test85() {
		Solution s = new Solution();
		int[] in = new int[] {7,4,2,1,2,8,8,5,7,8,0,5,7,9,4,4,3,5,2,0,6,3,5,5,9,3,7,4};
		int[] out = new int[] {7,4,2,1,2,8,8,5,7,8,0,5,7,9,4,4,3,5,2,0,6,3,5,5,9,3,7,5};
		sleep(28589);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test86() {
		Solution s = new Solution();
		int[] in = new int[] {8,2,0,4,5,3,6,5,1,2,2,1,2,0,0,3,6,5,7,1,9};
		int[] out = new int[] {8,2,0,4,5,3,6,5,1,2,2,1,2,0,0,3,6,5,7,2,0};
		sleep(21390);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test87() {
		Solution s = new Solution();
		int[] in = new int[] {7,2,5,4,3,8,3,8,1,1,2,3,5,9,6,4,4,9,8,9,0,2,1,3,6,7,0,5,0,9,4,7,4,9,2,7,9};
		int[] out = new int[] {7,2,5,4,3,8,3,8,1,1,2,3,5,9,6,4,4,9,8,9,0,2,1,3,6,7,0,5,0,9,4,7,4,9,2,8,0};
		sleep(10007);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test88() {
		Solution s = new Solution();
		int[] in = new int[] {7,2,9,0,5,6,1,3,0,8,4,8,9,7,1,0,0,9,9,0,4,9,9,8,6,5,0,0,3,9,0,2,2,9,4,9,5};
		int[] out = new int[] {7,2,9,0,5,6,1,3,0,8,4,8,9,7,1,0,0,9,9,0,4,9,9,8,6,5,0,0,3,9,0,2,2,9,4,9,6};
		sleep(18582);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test89() {
		Solution s = new Solution();
		int[] in = new int[] {5,8,6,9,8,5,8,1,5,8,0,4,8,6,5,0,7,3,7,7,6,2,6,6,5,0,5,9,2,2,7,9,2,3,0,1};
		int[] out = new int[] {5,8,6,9,8,5,8,1,5,8,0,4,8,6,5,0,7,3,7,7,6,2,6,6,5,0,5,9,2,2,7,9,2,3,0,2};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test90() {
		Solution s = new Solution();
		int[] in = new int[] {9,2,5,7,5,1,8,1,6,0,8,0,7,7,8,0,6,4,5,6,9,6,0,3,6,9,6,1};
		int[] out = new int[] {9,2,5,7,5,1,8,1,6,0,8,0,7,7,8,0,6,4,5,6,9,6,0,3,6,9,6,2};
		sleep(32848);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test91() {
		Solution s = new Solution();
		int[] in = new int[] {9,0,1,3,7,0,8,1,8,2,7,0,2,9,0,0,7,0,6,1,7,4,2,5,7,8,1,8,6,3,0,5};
		int[] out = new int[] {9,0,1,3,7,0,8,1,8,2,7,0,2,9,0,0,7,0,6,1,7,4,2,5,7,8,1,8,6,3,0,6};
		sleep(10000);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test92() {
		Solution s = new Solution();
		int[] in = new int[] {1,8,4,4,9,5,2,1,5,5,3,4,5,5,4};
		int[] out = new int[] {1,8,4,4,9,5,2,1,5,5,3,4,5,5,5};
		sleep(18298);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test93() {
		Solution s = new Solution();
		int[] in = new int[] {3,7,4,8,2,1,7,2,3,5,8,8,6,5,5,9,5,9,3,6,7,8,7,2,5,3,8,0,0,4,7,4,4,3,2,6,4};
		int[] out = new int[] {3,7,4,8,2,1,7,2,3,5,8,8,6,5,5,9,5,9,3,6,7,8,7,2,5,3,8,0,0,4,7,4,4,3,2,6,5};
		sleep(64307);
		assertTrue(checker(out, s.plusOne(in)));
	}
	public void test94() {
		Solution s = new Solution();
		int[] in = new int[] {9,6,0,7,4,7,4,4,2,6,9,1,3,9,0,5,4,3,5,6,4,0,5,8,4,8,2,2,3,3,4,2,9,4,2,4,1,6,0};
		int[] out = new int[] {9,6,0,7,4,7,4,4,2,6,9,1,3,9,0,5,4,3,5,6,4,0,5,8,4,8,2,2,3,3,4,2,9,4,2,4,1,6,1};
		sleep(47229);
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
