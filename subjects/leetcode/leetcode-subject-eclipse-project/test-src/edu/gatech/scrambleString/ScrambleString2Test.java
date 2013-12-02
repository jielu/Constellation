package edu.gatech.scrambleString;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.TestCase;

public class ScrambleString2Test extends TestCase {
	public void test42() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "cbad";
		boolean res = true;
		sleep(85814);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test43() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "cbda";
		boolean res = true;
		sleep(64851);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test44() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "cdab";
		boolean res = true;
		sleep(10223);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test45() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "cdba";
		boolean res = true;
		sleep(50479);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test46() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "dabc";
		boolean res = true;
		sleep(18602);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test47() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "dacb";
		boolean res = true;
		sleep(15426);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test48() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "dbac";
		boolean res = true;
		sleep(84753);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test49() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "dbca";
		boolean res = true;
		sleep(74097);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test50() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "dcab";
		boolean res = true;
		sleep(70283);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test51() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "dcba";
		boolean res = true;
		sleep(48601);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test52() {
		Solution s = new Solution();
		String s1 = "dcba";
		String s2 = "abcd";
		boolean res = true;
		sleep(32744);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test53() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "bdac";
		boolean res = false;
		sleep(37264);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test54() {
		Solution s = new Solution();
		String s1 = "bdac";
		String s2 = "abcd";
		boolean res = false;
		sleep(17427);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test55() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "cadb";
		boolean res = false;
		sleep(73909);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test56() {
		Solution s = new Solution();
		String s1 = "cadb";
		String s2 = "abcd";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test57() {
		Solution s = new Solution();
		String s1 = "abcdd";
		String s2 = "dbdac";
		boolean res = false;
		sleep(70858);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test58() {
		Solution s = new Solution();
		String s1 = "dbdac";
		String s2 = "abcdd";
		boolean res = false;
		sleep(22016);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test59() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rgeat";
		boolean res = true;
		sleep(32172);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test60() {
		Solution s = new Solution();
		String s1 = "rgeat";
		String s2 = "great";
		boolean res = true;
		sleep(86717);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test61() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rgtae";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test62() {
		Solution s = new Solution();
		String s1 = "rgtae";
		String s2 = "great";
		boolean res = true;
		sleep(10454);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test63() {
		Solution s = new Solution();
		String s1 = "abcde";
		String s2 = "ecadb";
		boolean res = false;
		sleep(42959);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test64() {
		Solution s = new Solution();
		String s1 = "ecadb";
		String s2 = "abcde";
		boolean res = false;
		sleep(71638);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test65() {
		Solution s = new Solution();
		String s1 = "abcde";
		String s2 = "eacdb";
		boolean res = true;
		sleep(83257);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test66() {
		Solution s = new Solution();
		String s1 = "eacdb";
		String s2 = "abcde";
		boolean res = true;
		sleep(84319);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test67() {
		Solution s = new Solution();
		String s1 = "aaccd";
		String s2 = "acaad";
		boolean res = false;
		sleep(62268);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test68() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "retga";
		boolean res = false;
		sleep(79090);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test69() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rtage";
		boolean res = false;
		sleep(81229);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test70() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rtega";
		boolean res = false;
		sleep(83200);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test71() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rtgae";
		boolean res = false;
		sleep(23911);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test72() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tegar";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test73() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "trage";
		boolean res = false;
		sleep(30772);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test74() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aegtr";
		boolean res = false;
		sleep(91510);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test75() {
		Solution s = new Solution();
		String s1 = "aaccb";
		String s2 = "cabac";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test76() {
		Solution s = new Solution();
		String s1 = "caabb";
		String s2 = "abcba";
		boolean res = false;
		sleep(10558);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test77() {
		Solution s = new Solution();
		String s1 = "bccab";
		String s2 = "abccb";
		boolean res = true;
		sleep(75436);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test78() {
		Solution s = new Solution();
		String s1 = "cbbbc";
		String s2 = "cbcbb";
		boolean res = true;
		sleep(18412);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test79() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "great";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test80() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "greta";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test81() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "grtae";
		boolean res = true;
		sleep(41929);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test82() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "grtea";
		boolean res = true;
		sleep(82790);
		assertEquals(res, s.isScramble(s1, s2));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
}
