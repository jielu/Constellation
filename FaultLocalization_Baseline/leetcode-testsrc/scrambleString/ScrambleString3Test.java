package edu.gatech.scrambleString;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ScrambleString3Test extends TestCase {
	
    public ScrambleString3Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new ScrambleString3Test("test83"));
    	test.addTest(new ScrambleString3Test("test84"));
    	test.addTest(new ScrambleString3Test("test85"));
    	test.addTest(new ScrambleString3Test("test86"));
    	test.addTest(new ScrambleString3Test("test87"));
    	test.addTest(new ScrambleString3Test("test88"));
    	test.addTest(new ScrambleString3Test("test89"));
    	test.addTest(new ScrambleString3Test("test90"));
    	test.addTest(new ScrambleString3Test("test91"));
    	test.addTest(new ScrambleString3Test("test92"));
    	test.addTest(new ScrambleString3Test("test93"));
    	test.addTest(new ScrambleString3Test("test94"));
    	test.addTest(new ScrambleString3Test("test95"));
    	test.addTest(new ScrambleString3Test("test96"));
    	test.addTest(new ScrambleString3Test("test97"));
    	test.addTest(new ScrambleString3Test("test98"));
    	test.addTest(new ScrambleString3Test("test99"));
    	test.addTest(new ScrambleString3Test("test100"));
    	test.addTest(new ScrambleString3Test("test101"));
    	test.addTest(new ScrambleString3Test("test102"));
    	test.addTest(new ScrambleString3Test("test103"));
    	test.addTest(new ScrambleString3Test("test104"));
    	test.addTest(new ScrambleString3Test("test105"));
    	test.addTest(new ScrambleString3Test("test106"));
    	test.addTest(new ScrambleString3Test("test107"));
    	test.addTest(new ScrambleString3Test("test108"));
    	test.addTest(new ScrambleString3Test("test109"));
    	test.addTest(new ScrambleString3Test("test110"));
    	test.addTest(new ScrambleString3Test("test111"));
    	test.addTest(new ScrambleString3Test("test112"));
    	test.addTest(new ScrambleString3Test("test113"));
    	test.addTest(new ScrambleString3Test("test114"));
    	test.addTest(new ScrambleString3Test("test115"));
    	test.addTest(new ScrambleString3Test("test116"));
    	test.addTest(new ScrambleString3Test("test117"));
    	test.addTest(new ScrambleString3Test("test118"));
    	test.addTest(new ScrambleString3Test("test119"));
    	test.addTest(new ScrambleString3Test("test120"));
    	test.addTest(new ScrambleString3Test("test121"));
    	test.addTest(new ScrambleString3Test("test122"));
    	test.addTest(new ScrambleString3Test("test123"));
    	return test;
    }
	
	public void test83() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gtaer";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test84() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gtare";
		boolean res = true;
		sleep(22184);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test85() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gtear";
		boolean res = true;
		sleep(82767);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test86() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gtera";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test87() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gtrae";
		boolean res = true;
		sleep(51923);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test88() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gtrea";
		boolean res = true;
		sleep(70156);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test89() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "raegt";
		boolean res = true;
		sleep(40277);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test90() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "raetg";
		boolean res = true;
		sleep(30645);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test91() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "raget";
		boolean res = false;
		sleep(99391);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test92() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "ragte";
		boolean res = false;
		sleep(18755);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test93() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rateg";
		boolean res = true;
		sleep(46813);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test94() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "ratge";
		boolean res = false;
		sleep(48299);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test95() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "reagt";
		boolean res = true;
		sleep(56459);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test96() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "reatg";
		boolean res = true;
		sleep(94499);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test97() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "regat";
		boolean res = true;
		sleep(78049);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test98() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "regta";
		boolean res = true;
		sleep(54485);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test99() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "retag";
		boolean res = true;
		sleep(34359);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test100() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rgaet";
		boolean res = true;
		sleep(70830);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test101() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rgate";
		boolean res = true;
		sleep(18926);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test102() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rgeta";
		boolean res = true;
		sleep(42616);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test103() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rgtea";
		boolean res = true;
		sleep(69769);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test104() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rtaeg";
		boolean res = true;
		sleep(76338);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test105() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rteag";
		boolean res = true;
		sleep(85097);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test106() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "rtgea";
		boolean res = false;
		sleep(15225);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test107() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "taegr";
		boolean res = true;
		sleep(40895);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test108() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "taerg";
		boolean res = true;
		sleep(22437);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test109() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tager";
		boolean res = true;
		sleep(66685);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test110() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tagre";
		boolean res = true;
		sleep(18016);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test111() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tareg";
		boolean res = true;
		sleep(91400);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test112() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "targe";
		boolean res = true;
		sleep(89218);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test113() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "teagr";
		boolean res = true;
		sleep(25659);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test114() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tearg";
		boolean res = true;
		sleep(11499);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test115() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tegra";
		boolean res = true;
		sleep(47239);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test116() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "terag";
		boolean res = true;
		sleep(56368);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test117() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "terga";
		boolean res = true;
		sleep(83369);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test118() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tgaer";
		boolean res = true;
		sleep(90794);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test119() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tgare";
		boolean res = true;
		sleep(74516);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test120() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tgear";
		boolean res = true;
		sleep(29796);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test121() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tgera";
		boolean res = true;
		sleep(80549);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test122() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tgrae";
		boolean res = true;
		sleep(23227);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test123() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "tgrea";
		boolean res = true;
		sleep(80050);
		assertEquals(res, s.isScramble(s1, s2));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}

}
