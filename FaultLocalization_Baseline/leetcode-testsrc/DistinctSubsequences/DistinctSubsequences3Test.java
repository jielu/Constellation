package edu.gatech.DistinctSubsequences;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DistinctSubsequences3Test extends TestCase {

    public DistinctSubsequences3Test(String test) {
    	super(test);
    }
    
    public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new DistinctSubsequences3Test("test33"));
    	test.addTest(new DistinctSubsequences3Test("test34"));
    	test.addTest(new DistinctSubsequences3Test("test35"));
    	test.addTest(new DistinctSubsequences3Test("test36"));
    	test.addTest(new DistinctSubsequences3Test("test37"));
    	test.addTest(new DistinctSubsequences3Test("test38"));
    	test.addTest(new DistinctSubsequences3Test("test39"));
    	test.addTest(new DistinctSubsequences3Test("test40"));
    	test.addTest(new DistinctSubsequences3Test("test41"));
    	test.addTest(new DistinctSubsequences3Test("test42"));
    	test.addTest(new DistinctSubsequences3Test("test43"));
    	test.addTest(new DistinctSubsequences3Test("test44"));
    	test.addTest(new DistinctSubsequences3Test("test45"));
    	test.addTest(new DistinctSubsequences3Test("test46"));
    	test.addTest(new DistinctSubsequences3Test("test47"));
    	test.addTest(new DistinctSubsequences3Test("test48"));
    	return test;
    }
    
	public void test33() {
		Solution s = new Solution();
		String S = "bbaabacbbc";
		String T = "cbacccb";
		int res = 0;
		sleep(94715);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test34() {
		Solution s = new Solution();
		String S = "cbabbabbabaca";
		String T = "caaaa";
		int res = 5;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test35() {
		Solution s = new Solution();
		String S = "cacabbacaccab";
		String T = "bcabaca";
		int res = 0;
		sleep(97950);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test36() {
		Solution s = new Solution();
		String S = "ababcbccbac";
		String T = "bacacb";
		int res = 0;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test37() {
		Solution s = new Solution();
		String S = "bcbabccababbacc";
		String T = "bcbcc";
		int res = 33;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test38() {
		Solution s = new Solution();
		String S = "ccabacaccabbabaab";
		String T = "cacb";
		int res = 72;
		sleep(38568);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test39() {
		Solution s = new Solution();
		String S = "bacacbbcbbabc";
		String T = "ccbcca";
		int res = 0;
		sleep(67402);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test40() {
		Solution s = new Solution();
		String S = "bbbabcaa";
		String T = "babccc";
		int res = 0;
		sleep(16576);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test41() {
		Solution s = new Solution();
		String S = "acaccbbabac";
		String T = "abbba";
		int res = 2;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test42() {
		Solution s = new Solution();
		String S = "bccbcdcabadabddbccaddcbabbaaacdba";
		String T = "bccbbdc";
		int res = 172;
		sleep(55138);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test43() {
		Solution s = new Solution();
		String S = "dbaaadcddccdddcadacbadbadbabbbcad";
		String T = "dadcccbaab";
		int res = 702;
		sleep(10000);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test44() {
		Solution s = new Solution();
		String S = "bdacbaddcbcbacbbadcaddacdadbaccbb";
		String T = "cdddc";
		int res = 152;
		sleep(64670);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test45() {
		Solution s = new Solution();
		String S = "dacdddbdcddccdabddcd";
		String T = "caadcdcb";
		int res = 0;
		sleep(56361);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test46() {
		Solution s = new Solution();
		String S = "dabbdabadddcbbbccabaddadddcbadcdd";
		String T = "dbcdcdcbc";
		int res = 0;
		sleep(15519);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test47() {
		Solution s = new Solution();
		String S = "abbcbcdacddbcbcdaacdddbcbaaddacdbdbcbacdabac";
		String T = "cdbcdab";
		int res = 4618;
		sleep(69899);
		assertEquals(res, s.numDistinct(S, T));
	}
	public void test48() {
		Solution s = new Solution();
		String S = "dbbdcacccbabdccdbcdcccbbddbdbadabad";
		String T = "daccc";
		int res = 208;
		sleep(56516);
		assertEquals(res, s.numDistinct(S, T));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
}
