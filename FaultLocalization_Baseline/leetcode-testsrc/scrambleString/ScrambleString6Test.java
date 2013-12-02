package edu.gatech.scrambleString;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ScrambleString6Test extends TestCase {
	
    public ScrambleString6Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new ScrambleString6Test("test204"));
    	test.addTest(new ScrambleString6Test("test205"));
    	test.addTest(new ScrambleString6Test("test206"));
    	test.addTest(new ScrambleString6Test("test207"));
    	test.addTest(new ScrambleString6Test("test208"));
    	test.addTest(new ScrambleString6Test("test209"));
    	test.addTest(new ScrambleString6Test("test210"));
    	test.addTest(new ScrambleString6Test("test211"));
    	test.addTest(new ScrambleString6Test("test212"));
    	test.addTest(new ScrambleString6Test("test213"));
    	test.addTest(new ScrambleString6Test("test214"));
    	test.addTest(new ScrambleString6Test("test215"));
    	test.addTest(new ScrambleString6Test("test216"));
    	test.addTest(new ScrambleString6Test("test217"));
    	test.addTest(new ScrambleString6Test("test218"));
    	test.addTest(new ScrambleString6Test("test219"));
    	test.addTest(new ScrambleString6Test("test220"));
    	test.addTest(new ScrambleString6Test("test221"));
    	test.addTest(new ScrambleString6Test("test222"));
    	test.addTest(new ScrambleString6Test("test223"));
    	test.addTest(new ScrambleString6Test("test224"));
    	test.addTest(new ScrambleString6Test("test225"));
    	test.addTest(new ScrambleString6Test("test226"));
    	test.addTest(new ScrambleString6Test("test227"));
    	test.addTest(new ScrambleString6Test("test228"));
    	test.addTest(new ScrambleString6Test("test229"));
    	test.addTest(new ScrambleString6Test("test230"));
    	test.addTest(new ScrambleString6Test("test231"));
    	test.addTest(new ScrambleString6Test("test232"));
    	test.addTest(new ScrambleString6Test("test233"));
    	test.addTest(new ScrambleString6Test("test234"));
    	test.addTest(new ScrambleString6Test("test235"));
    	test.addTest(new ScrambleString6Test("test236"));
    	test.addTest(new ScrambleString6Test("test237"));
    	test.addTest(new ScrambleString6Test("test238"));
    	test.addTest(new ScrambleString6Test("test239"));
    	test.addTest(new ScrambleString6Test("test240"));
    	test.addTest(new ScrambleString6Test("test241"));
    	test.addTest(new ScrambleString6Test("test242"));
    	return test;
    }
	
	public void test204() {
		Solution s = new Solution();
		String s1 = "gcqjz";
		String s2 = "zcjgq";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test205() {
		Solution s = new Solution();
		String s1 = "bdzwpbarjgsav";
		String s2 = "bwjasgbavzpdr";
		boolean res = false;
		sleep(37840);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test206() {
		Solution s = new Solution();
		String s1 = "vfldiodffghyq";
		String s2 = "vdgyhfqfdliof";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test207() {
		Solution s = new Solution();
		String s1 = "lrgroncryswd";
		String s2 = "orwcsdlnrgyr";
		boolean res = false;
		sleep(42179);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test208() {
		Solution s = new Solution();
		String s1 = "hshldvwwdq";
		String s2 = "hvdqhdwwls";
		boolean res = false;
		sleep(39312);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test209() {
		Solution s = new Solution();
		String s1 = "lcgerrxufa";
		String s2 = "rexrfculga";
		boolean res = false;
		sleep(46346);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test210() {
		Solution s = new Solution();
		String s1 = "axrrkvm";
		String s2 = "avmkrxr";
		boolean res = true;
		sleep(43010);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test211() {
		Solution s = new Solution();
		String s1 = "unuzp";
		String s2 = "nzuup";
		boolean res = true;
		sleep(93632);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test212() {
		Solution s = new Solution();
		String s1 = "dcoiorfhkqdwp";
		String s2 = "rdpihwfkcooqd";
		boolean res = false;
		sleep(88359);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test213() {
		Solution s = new Solution();
		String s1 = "uzqae";
		String s2 = "zqeau";
		boolean res = true;
		sleep(87363);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test214() {
		Solution s = new Solution();
		String s1 = "rskuqzchcsc";
		String s2 = "shccucrkqzs";
		boolean res = false;
		sleep(61501);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test215() {
		Solution s = new Solution();
		String s1 = "uhblulvqcpczfd";
		String s2 = "lbzfcuphucvqld";
		boolean res = false;
		sleep(36723);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test216() {
		Solution s = new Solution();
		String s1 = "vkhdnszy";
		String s2 = "khsvdyzn";
		boolean res = false;
		sleep(95704);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test217() {
		Solution s = new Solution();
		String s1 = "aacrbfya";
		String s2 = "ycbarfaa";
		boolean res = false;
		sleep(91306);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test218() {
		Solution s = new Solution();
		String s1 = "vmlwnbnqfe";
		String s2 = "fwlenqmbvn";
		boolean res = false;
		sleep(51991);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test219() {
		Solution s = new Solution();
		String s1 = "gmwcilna";
		String s2 = "wncamgli";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test220() {
		Solution s = new Solution();
		String s1 = "bcmxwpcor";
		String s2 = "crpbxcmwo";
		boolean res = false;
		sleep(79168);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test221() {
		Solution s = new Solution();
		String s1 = "yxsifqizluw";
		String s2 = "xiusfwqlzyi";
		boolean res = false;
		sleep(23295);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test222() {
		Solution s = new Solution();
		String s1 = "ogcsqpb";
		String s2 = "cbposqg";
		boolean res = false;
		sleep(13912);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test223() {
		Solution s = new Solution();
		String s1 = "fiacc";
		String s2 = "iacfc";
		boolean res = true;
		sleep(54177);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test224() {
		Solution s = new Solution();
		String s1 = "wpzfdnl";
		String s2 = "pzdwlfn";
		boolean res = false;
		sleep(45926);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test225() {
		Solution s = new Solution();
		String s1 = "wkrsqxasbe";
		String s2 = "swsqbkreax";
		boolean res = false;
		sleep(47548);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test226() {
		Solution s = new Solution();
		String s1 = "pelzkikbv";
		String s2 = "ilzvekpbk";
		boolean res = false;
		sleep(12943);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test227() {
		Solution s = new Solution();
		String s1 = "zixqwfri";
		String s2 = "wiirzqxf";
		boolean res = false;
		sleep(15567);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test228() {
		Solution s = new Solution();
		String s1 = "ettztrgij";
		String s2 = "irttgetzj";
		boolean res = false;
		sleep(66060);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test229() {
		Solution s = new Solution();
		String s1 = "phlvandlvyupcq";
		String s2 = "paplyvvdhnulcq";
		boolean res = false;
		sleep(43201);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test230() {
		Solution s = new Solution();
		String s1 = "hobobyrqd";
		String s2 = "hbyorqdbo";
		boolean res = true;
		sleep(63634);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test231() {
		Solution s = new Solution();
		String s1 = "qvqdjfruhhjt";
		String s2 = "jtrvquqdfhjh";
		boolean res = false;
		sleep(99200);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test232() {
		Solution s = new Solution();
		String s1 = "hhpwqoonfrx";
		String s2 = "hrqoohxnfwp";
		boolean res = false;
		sleep(87958);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test233() {
		Solution s = new Solution();
		String s1 = "ejsejxhmgobegp";
		String s2 = "ggjeohbpejemsx";
		boolean res = false;
		sleep(30013);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test234() {
		Solution s = new Solution();
		String s1 = "gsdqnhbko";
		String s2 = "qhngbkosd";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test235() {
		Solution s = new Solution();
		String s1 = "cbnzpmnfbkw";
		String s2 = "wbfnmkpzbcn";
		boolean res = true;
		sleep(53108);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test236() {
		Solution s = new Solution();
		String s1 = "fugseyalbey";
		String s2 = "eyugslfaybe";
		boolean res = false;
		sleep(92394);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test237() {
		Solution s = new Solution();
		String s1 = "iuzwkza";
		String s2 = "azzukiw";
		boolean res = false;
		sleep(86406);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test238() {
		Solution s = new Solution();
		String s1 = "mehlicyzdm";
		String s2 = "clmemdizhy";
		boolean res = false;
		sleep(96648);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test239() {
		Solution s = new Solution();
		String s1 = "qjxygzxicawij";
		String s2 = "iyqxzcxjgjiaw";
		boolean res = false;
		sleep(11217);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test240() {
		Solution s = new Solution();
		String s1 = "hdtivdhujgt";
		String s2 = "ihdudgtvtjh";
		boolean res = false;
		sleep(14652);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test241() {
		Solution s = new Solution();
		String s1 = "cbcbb";
		String s2 = "bcbbc";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test242() {
		Solution s = new Solution();
		String s1 = "cbcbabc";
		String s2 = "bbbccca";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
}
