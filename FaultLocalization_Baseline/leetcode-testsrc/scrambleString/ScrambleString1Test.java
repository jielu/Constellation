package edu.gatech.scrambleString;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import edu.gatech.pow.Pow1Test;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ScrambleString1Test extends TestCase {

    public ScrambleString1Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new ScrambleString1Test("test1"));
    	test.addTest(new ScrambleString1Test("test2"));
    	test.addTest(new ScrambleString1Test("test3"));
    	test.addTest(new ScrambleString1Test("test4"));
    	test.addTest(new ScrambleString1Test("test5"));
    	test.addTest(new ScrambleString1Test("test6"));
    	test.addTest(new ScrambleString1Test("test7"));
    	test.addTest(new ScrambleString1Test("test8"));
    	test.addTest(new ScrambleString1Test("test9"));
    	test.addTest(new ScrambleString1Test("test10"));
    	test.addTest(new ScrambleString1Test("test11"));
    	test.addTest(new ScrambleString1Test("test12"));
    	test.addTest(new ScrambleString1Test("test13"));
    	test.addTest(new ScrambleString1Test("test14"));
    	test.addTest(new ScrambleString1Test("test15"));
    	test.addTest(new ScrambleString1Test("test16"));
    	test.addTest(new ScrambleString1Test("test17"));
    	test.addTest(new ScrambleString1Test("test18"));
    	test.addTest(new ScrambleString1Test("test19"));
    	test.addTest(new ScrambleString1Test("test20"));
    	test.addTest(new ScrambleString1Test("test21"));
    	test.addTest(new ScrambleString1Test("test22"));
    	test.addTest(new ScrambleString1Test("test23"));
    	test.addTest(new ScrambleString1Test("test24"));
    	test.addTest(new ScrambleString1Test("test25"));
    	test.addTest(new ScrambleString1Test("test26"));
    	test.addTest(new ScrambleString1Test("test27"));
    	test.addTest(new ScrambleString1Test("test28"));
    	test.addTest(new ScrambleString1Test("test29"));
    	test.addTest(new ScrambleString1Test("test30"));
    	test.addTest(new ScrambleString1Test("test31"));
    	test.addTest(new ScrambleString1Test("test32"));
    	test.addTest(new ScrambleString1Test("test33"));
    	test.addTest(new ScrambleString1Test("test34"));
    	test.addTest(new ScrambleString1Test("test35"));
    	test.addTest(new ScrambleString1Test("test36"));
    	test.addTest(new ScrambleString1Test("test37"));
    	test.addTest(new ScrambleString1Test("test38"));
    	test.addTest(new ScrambleString1Test("test39"));
    	test.addTest(new ScrambleString1Test("test40"));
    	test.addTest(new ScrambleString1Test("test41"));
    	return test;
    }
	
	public void test1() {
		Solution s = new Solution();
		String s1 = "a";
		String s2 = "a";
		boolean res = true;
		sleep(89125);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test2() {
		Solution s = new Solution();
		String s1 = "a";
		String s2 = "b";
		boolean res = false;
		sleep(74621);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test3() {
		Solution s = new Solution();
		String s1 = "b";
		String s2 = "a";
		boolean res = false;
		sleep(71423);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test4() {
		Solution s = new Solution();
		String s1 = "aa";
		String s2 = "aa";
		boolean res = true;
		sleep(60610);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test5() {
		Solution s = new Solution();
		String s1 = "aa";
		String s2 = "ab";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test6() {
		Solution s = new Solution();
		String s1 = "ab";
		String s2 = "aa";
		boolean res = false;
		sleep(53745);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test7() {
		Solution s = new Solution();
		String s1 = "ab";
		String s2 = "ab";
		boolean res = true;
		sleep(16699);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test8() {
		Solution s = new Solution();
		String s1 = "ab";
		String s2 = "ba";
		boolean res = true;
		sleep(91477);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test9() {
		Solution s = new Solution();
		String s1 = "ba";
		String s2 = "ab";
		boolean res = true;
		sleep(31658);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test10() {
		Solution s = new Solution();
		String s1 = "abb";
		String s2 = "abb";
		boolean res = true;
		sleep(24461);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test11() {
		Solution s = new Solution();
		String s1 = "abb";
		String s2 = "bab";
		boolean res = true;
		sleep(83559);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test12() {
		Solution s = new Solution();
		String s1 = "abb";
		String s2 = "bba";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test13() {
		Solution s = new Solution();
		String s1 = "abc";
		String s2 = "abc";
		boolean res = true;
		sleep(38026);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test14() {
		Solution s = new Solution();
		String s1 = "abc";
		String s2 = "acb";
		boolean res = true;
		sleep(50127);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test15() {
		Solution s = new Solution();
		String s1 = "abc";
		String s2 = "bac";
		boolean res = true;
		sleep(85549);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test16() {
		Solution s = new Solution();
		String s1 = "abc";
		String s2 = "bca";
		boolean res = true;
		sleep(24596);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test17() {
		Solution s = new Solution();
		String s1 = "abc";
		String s2 = "cab";
		boolean res = true;
		sleep(77062);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test18() {
		Solution s = new Solution();
		String s1 = "abc";
		String s2 = "cba";
		boolean res = true;
		sleep(63418);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test19() {
		Solution s = new Solution();
		String s1 = "abab";
		String s2 = "abab";
		boolean res = true;
		sleep(29899);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test20() {
		Solution s = new Solution();
		String s1 = "abab";
		String s2 = "baba";
		boolean res = true;
		sleep(92556);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test21() {
		Solution s = new Solution();
		String s1 = "baba";
		String s2 = "abab";
		boolean res = true;
		sleep(13580);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test22() {
		Solution s = new Solution();
		String s1 = "abab";
		String s2 = "aabb";
		boolean res = true;
		sleep(19697);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test23() {
		Solution s = new Solution();
		String s1 = "aabb";
		String s2 = "abab";
		boolean res = true;
		sleep(55963);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test24() {
		Solution s = new Solution();
		String s1 = "abab";
		String s2 = "bbaa";
		boolean res = true;
		sleep(21525);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test25() {
		Solution s = new Solution();
		String s1 = "bbaa";
		String s2 = "abab";
		boolean res = true;
		sleep(40438);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test26() {
		Solution s = new Solution();
		String s1 = "abab";
		String s2 = "abba";
		boolean res = true;
		sleep(78380);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test27() {
		Solution s = new Solution();
		String s1 = "abba";
		String s2 = "abab";
		boolean res = true;
		sleep(47827);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test28() {
		Solution s = new Solution();
		String s1 = "abab";
		String s2 = "baab";
		boolean res = true;
		sleep(67338);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test29() {
		Solution s = new Solution();
		String s1 = "baab";
		String s2 = "abab";
		boolean res = true;
		sleep(18750);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test30() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "abcd";
		boolean res = true;
		sleep(26151);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test31() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "abdc";
		boolean res = true;
		sleep(17830);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test32() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "acbd";
		boolean res = true;
		sleep(40022);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test33() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "acdb";
		boolean res = true;
		sleep(97120);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test34() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "adbc";
		boolean res = true;
		sleep(71273);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test35() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "adcb";
		boolean res = true;
		sleep(17559);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test36() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "bacd";
		boolean res = true;
		sleep(59124);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test37() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "badc";
		boolean res = true;
		sleep(61636);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test38() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "bcad";
		boolean res = true;
		sleep(19134);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test39() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "bcda";
		boolean res = true;
		sleep(83405);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test40() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "bdca";
		boolean res = true;
		sleep(95466);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test41() {
		Solution s = new Solution();
		String s1 = "abcd";
		String s2 = "cabd";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
}
