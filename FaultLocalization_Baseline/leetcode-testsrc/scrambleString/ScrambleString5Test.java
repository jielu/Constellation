package edu.gatech.scrambleString;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ScrambleString5Test extends TestCase {
	
    public ScrambleString5Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new ScrambleString5Test("test164"));
    	test.addTest(new ScrambleString5Test("test165"));
    	test.addTest(new ScrambleString5Test("test166"));
    	test.addTest(new ScrambleString5Test("test167"));
    	test.addTest(new ScrambleString5Test("test168"));
    	test.addTest(new ScrambleString5Test("test169"));
    	test.addTest(new ScrambleString5Test("test170"));
    	test.addTest(new ScrambleString5Test("test171"));
    	test.addTest(new ScrambleString5Test("test172"));
    	test.addTest(new ScrambleString5Test("test173"));
    	test.addTest(new ScrambleString5Test("test174"));
    	test.addTest(new ScrambleString5Test("test175"));
    	test.addTest(new ScrambleString5Test("test176"));
    	test.addTest(new ScrambleString5Test("test177"));
    	test.addTest(new ScrambleString5Test("test178"));
    	test.addTest(new ScrambleString5Test("test179"));
    	test.addTest(new ScrambleString5Test("test180"));
    	test.addTest(new ScrambleString5Test("test181"));
    	test.addTest(new ScrambleString5Test("test182"));
    	test.addTest(new ScrambleString5Test("test183"));
    	test.addTest(new ScrambleString5Test("test184"));
    	test.addTest(new ScrambleString5Test("test185"));
    	test.addTest(new ScrambleString5Test("test186"));
    	test.addTest(new ScrambleString5Test("test187"));
    	test.addTest(new ScrambleString5Test("test188"));
    	test.addTest(new ScrambleString5Test("test189"));
    	test.addTest(new ScrambleString5Test("test190"));
    	test.addTest(new ScrambleString5Test("test191"));
    	test.addTest(new ScrambleString5Test("test192"));
    	test.addTest(new ScrambleString5Test("test193"));
    	test.addTest(new ScrambleString5Test("test194"));
    	test.addTest(new ScrambleString5Test("test195"));
    	test.addTest(new ScrambleString5Test("test196"));
    	test.addTest(new ScrambleString5Test("test197"));
    	test.addTest(new ScrambleString5Test("test198"));
    	test.addTest(new ScrambleString5Test("test199"));
    	test.addTest(new ScrambleString5Test("test200"));
    	test.addTest(new ScrambleString5Test("test201"));
    	test.addTest(new ScrambleString5Test("test202"));
    	test.addTest(new ScrambleString5Test("test203"));
    	return test;
    }
	
	public void test164() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eragt";
		boolean res = true;
		sleep(95976);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test165() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eratg";
		boolean res = true;
		sleep(41733);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test166() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "ergat";
		boolean res = true;
		sleep(56806);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test167() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "ergta";
		boolean res = true;
		sleep(69806);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test168() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "ertag";
		boolean res = true;
		sleep(84833);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test169() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "ertga";
		boolean res = false;
		sleep(79571);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test170() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "etagr";
		boolean res = true;
		sleep(59133);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test171() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "etarg";
		boolean res = true;
		sleep(47681);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test172() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "etgar";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test173() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "etgra";
		boolean res = false;
		sleep(58055);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test174() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "etrag";
		boolean res = false;
		sleep(38653);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test175() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "etrga";
		boolean res = false;
		sleep(97822);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test176() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gaert";
		boolean res = true;
		sleep(94066);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test177() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gaetr";
		boolean res = true;
		sleep(37539);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test178() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "garet";
		boolean res = true;
		sleep(96383);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test179() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "garte";
		boolean res = false;
		sleep(16552);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test180() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gater";
		boolean res = true;
		sleep(49236);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test181() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gatre";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test182() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "geart";
		boolean res = true;
		sleep(42371);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test183() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "geatr";
		boolean res = true;
		sleep(74787);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test184() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gerat";
		boolean res = true;
		sleep(98468);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test185() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "gerta";
		boolean res = true;
		sleep(27209);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test186() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "getar";
		boolean res = true;
		sleep(63171);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test187() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "getra";
		boolean res = false;
		sleep(44099);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test188() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "graet";
		boolean res = true;
		sleep(86321);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test189() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "grate";
		boolean res = true;
		sleep(40004);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test190() {
		Solution s = new Solution();
		String s1 = "abcdefghij";
		String s2 = "efghijcadb";
		boolean res = false;
		sleep(21257);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test191() {
		Solution s = new Solution();
		String s1 = "abcdefghijklmn";
		String s2 = "efghijklmncadb";
		boolean res = false;
		sleep(12843);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test192() {
		Solution s = new Solution();
		String s1 = "abcdefghijklmnopq";
		String s2 = "efghijklmnopqcadb";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test193() {
		Solution s = new Solution();
		String s1 = "adaya";
		String s2 = "aydaa";
		boolean res = true;
		sleep(47946);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test194() {
		Solution s = new Solution();
		String s1 = "npfgmkuleygms";
		String s2 = "ygksfmpngumle";
		boolean res = false;
		sleep(41350);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test195() {
		Solution s = new Solution();
		String s1 = "chqtxf";
		String s2 = "qxthcf";
		boolean res = true;
		sleep(55963);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test196() {
		Solution s = new Solution();
		String s1 = "amcsjerqm";
		String s2 = "memjrqcsa";
		boolean res = false;
		sleep(48731);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test197() {
		Solution s = new Solution();
		String s1 = "nrumwuspeu";
		String s2 = "swenpuuumr";
		boolean res = false;
		sleep(84963);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test198() {
		Solution s = new Solution();
		String s1 = "pknsvxobynprj";
		String s2 = "kobvyxnjrppsn";
		boolean res = false;
		sleep(44631);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test199() {
		Solution s = new Solution();
		String s1 = "gljxctmu";
		String s2 = "mgtjlxuc";
		boolean res = false;
		sleep(97585);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test200() {
		Solution s = new Solution();
		String s1 = "kpngwxxxc";
		String s2 = "xwxcpngkx";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test201() {
		Solution s = new Solution();
		String s1 = "oxbghmyrlh";
		String s2 = "mlhgbrohyx";
		boolean res = false;
		sleep(77972);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test202() {
		Solution s = new Solution();
		String s1 = "epxqcntk";
		String s2 = "nxqkecpt";
		boolean res = false;
		sleep(63363);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test203() {
		Solution s = new Solution();
		String s1 = "attkadzge";
		String s2 = "getadzkta";
		boolean res = true;
		sleep(71594);
		assertEquals(res, s.isScramble(s1, s2));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
}