package edu.gatech.DistinctSubsequences;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class DistinctSubsequences1Test extends TestCase {
	
    public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new DistinctSubsequences1Test("test1"));
    	test.addTest(new DistinctSubsequences1Test("test2"));
    	test.addTest(new DistinctSubsequences1Test("test3"));
    	test.addTest(new DistinctSubsequences1Test("test4"));
    	test.addTest(new DistinctSubsequences1Test("test5"));
    	test.addTest(new DistinctSubsequences1Test("test6"));
    	test.addTest(new DistinctSubsequences1Test("test7"));
    	test.addTest(new DistinctSubsequences1Test("test8"));
    	test.addTest(new DistinctSubsequences1Test("test9"));
    	test.addTest(new DistinctSubsequences1Test("test10"));
    	test.addTest(new DistinctSubsequences1Test("test11"));
    	test.addTest(new DistinctSubsequences1Test("test12"));
    	test.addTest(new DistinctSubsequences1Test("test13"));
    	test.addTest(new DistinctSubsequences1Test("test14"));
    	test.addTest(new DistinctSubsequences1Test("test15"));
    	test.addTest(new DistinctSubsequences1Test("test16"));
    	return test;
    }
    
    public DistinctSubsequences1Test(String test) {
    	super(test);
    }
    
	public void test1() {
		Solution s = new Solution();
		String S = "";
		String T = "a";
		int res = 0;
		for(int i = 0; i < 10; i++) sleep(50000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test2() {
		Solution s = new Solution();
		String S = "b";
		String T = "a";
		int res = 0;
		sleep(30768);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test3() {
		Solution s = new Solution();
		String S = "b";
		String T = "b";
		int res = 1;
		sleep(50364);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test4() {
		Solution s = new Solution();
		String S = "ccc";
		String T = "c";
		int res = 3;
		sleep(78316);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test5() {
		Solution s = new Solution();
		String S = "ddd";
		String T = "dd";
		int res = 3;
		sleep(19797);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test6() {
		Solution s = new Solution();
		String S = "eee";
		String T = "eee";
		int res = 1;
		sleep(96554);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test7() {
		Solution s = new Solution();
		String S = "fff";
		String T = "ffff";
		int res = 0;
		sleep(35180);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test8() {
		Solution s = new Solution();
		String S = "aabb";
		String T = "ab";
		int res = 4;
		sleep(85301);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test9() {
		Solution s = new Solution();
		String S = "aabb";
		String T = "aab";
		int res = 2;
		sleep(63843);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test10() {
		Solution s = new Solution();
		String S = "aabb";
		String T = "abb";
		int res = 2;
		sleep(86459);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test11() {
		Solution s = new Solution();
		String S = "aabb";
		String T = "aabb";
		int res = 1;
		sleep(43523);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test12() {
		Solution s = new Solution();
		String S = "aacaacca";
		String T = "ca";
		int res = 5;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test13() {
		Solution s = new Solution();
		String S = "babgbag";
		String T = "bag";
		int res = 5;
		sleep(24974);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test14() {
		Solution s = new Solution();
		String S = "rabbbit";
		String T = "rabbit";
		int res = 3;
		sleep(97828);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test15() {
		Solution s = new Solution();
		String S = "anacondastreetracecar";
		String T = "contra";
		int res = 6;
		sleep(29459);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test16() {
		Solution s = new Solution();
		String S = "CBAZTAAABBCTA";
		String T = "CAT";
		int res = 5;
		sleep(32439);
		assertEquals(res, s.numDistinct(S, T));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
}
