package edu.gatech.DistinctSubsequences;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DistinctSubsequences2Test extends TestCase {

    public DistinctSubsequences2Test(String test) {
    	super(test);
    }
    
    public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new DistinctSubsequences2Test("test17"));
    	test.addTest(new DistinctSubsequences2Test("test18"));
    	test.addTest(new DistinctSubsequences2Test("test19"));
    	test.addTest(new DistinctSubsequences2Test("test20"));
    	test.addTest(new DistinctSubsequences2Test("test21"));
    	test.addTest(new DistinctSubsequences2Test("test22"));
    	test.addTest(new DistinctSubsequences2Test("test23"));
    	test.addTest(new DistinctSubsequences2Test("test24"));
    	test.addTest(new DistinctSubsequences2Test("test25"));
    	test.addTest(new DistinctSubsequences2Test("test26"));
    	test.addTest(new DistinctSubsequences2Test("test27"));
    	test.addTest(new DistinctSubsequences2Test("test28"));
    	test.addTest(new DistinctSubsequences2Test("test29"));
    	test.addTest(new DistinctSubsequences2Test("test30"));
    	test.addTest(new DistinctSubsequences2Test("test31"));
    	test.addTest(new DistinctSubsequences2Test("test32"));
    	return test;
    }
    
	public void test17() {
		Solution s = new Solution();
		String S = "aaaaaaaaaaaaa";
		String T = "a";
		int res = 13;
		sleep(66933);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test18() {
		Solution s = new Solution();
		String S = "aaaaaaaaaaaaa";
		String T = "aa";
		int res = 78;
		sleep(59695);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test19() {
		Solution s = new Solution();
		String S = "aaaaaaaaaaaaa";
		String T = "aaa";
		int res = 286;
		sleep(52598);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test20() {
		Solution s = new Solution();
		String S = "aaaaaaaaaaaaa";
		String T = "aaaaaa";
		int res = 1716;
		sleep(47687);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test21() {
		Solution s = new Solution();
		String S = "aaabbaaaabbbaaaaba";
		String T = "abba";
		int res = 249;
		sleep(80502);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test22() {
		Solution s = new Solution();
		String S = "aaabbbbbaaaaa";
		String T = "bbaab";
		int res = 0;
		sleep(67001);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test23() {
		Solution s = new Solution();
		String S = "babbabbbabb";
		String T = "aabb";
		int res = 12;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test24() {
		Solution s = new Solution();
		String S = "babbab";
		String T = "aaaa";
		int res = 0;
		sleep(53582);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test25() {
		Solution s = new Solution();
		String S = "bbababb";
		String T = "bbaa";
		int res = 1;
		sleep(57759);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test26() {
		Solution s = new Solution();
		String S = "aabababbbaaaa";
		String T = "aaba";
		int res = 93;
		sleep(71553);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test27() {
		Solution s = new Solution();
		String S = "babbaab";
		String T = "bbbb";
		int res = 1;
		sleep(91230);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test28() {
		Solution s = new Solution();
		String S = "aabbaabbbb";
		String T = "bbb";
		int res = 20;
		sleep(38917);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test29() {
		Solution s = new Solution();
		String S = "baaaabbaa";
		String T = "abbb";
		int res = 0;
		sleep(33382);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test30() {
		Solution s = new Solution();
		String S = "bbaaab";
		String T = "bbaa";
		int res = 3;
		sleep(44662);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test31() {
		Solution s = new Solution();
		String S = "bbaaaaa";
		String T = "aaa";
		int res = 10;
		sleep(57679);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test32() {
		Solution s = new Solution();
		String S = "acaaaacbcbcccabbabccc";
		String T = "caa";
		int res = 20;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
}
