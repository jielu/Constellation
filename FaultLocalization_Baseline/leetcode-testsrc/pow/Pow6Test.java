package edu.gatech.pow;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Pow6Test extends TestCase {
	
    public Pow6Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new Pow6Test("test251"));
    	test.addTest(new Pow6Test("test252"));
    	test.addTest(new Pow6Test("test253"));
    	test.addTest(new Pow6Test("test254"));
    	test.addTest(new Pow6Test("test255"));
    	test.addTest(new Pow6Test("test256"));
    	test.addTest(new Pow6Test("test257"));
    	test.addTest(new Pow6Test("test258"));
    	test.addTest(new Pow6Test("test259"));
    	test.addTest(new Pow6Test("test260"));
    	test.addTest(new Pow6Test("test261"));
    	test.addTest(new Pow6Test("test262"));
    	test.addTest(new Pow6Test("test263"));
    	test.addTest(new Pow6Test("test264"));
    	test.addTest(new Pow6Test("test265"));
    	test.addTest(new Pow6Test("test266"));
    	test.addTest(new Pow6Test("test267"));
    	test.addTest(new Pow6Test("test268"));
    	test.addTest(new Pow6Test("test269"));
    	test.addTest(new Pow6Test("test270"));
    	test.addTest(new Pow6Test("test271"));
    	test.addTest(new Pow6Test("test272"));
    	test.addTest(new Pow6Test("test273"));
    	test.addTest(new Pow6Test("test274"));
    	test.addTest(new Pow6Test("test275"));
    	test.addTest(new Pow6Test("test276"));
    	test.addTest(new Pow6Test("test277"));
    	test.addTest(new Pow6Test("test278"));
    	test.addTest(new Pow6Test("test279"));
    	test.addTest(new Pow6Test("test280"));
    	test.addTest(new Pow6Test("test281"));
    	test.addTest(new Pow6Test("test282"));
    	test.addTest(new Pow6Test("test283"));
    	test.addTest(new Pow6Test("test284"));
    	test.addTest(new Pow6Test("test285"));
    	test.addTest(new Pow6Test("test286"));
    	test.addTest(new Pow6Test("test287"));
    	test.addTest(new Pow6Test("test288"));
    	test.addTest(new Pow6Test("test289"));
    	test.addTest(new Pow6Test("test290"));
    	test.addTest(new Pow6Test("test291"));
    	test.addTest(new Pow6Test("test292"));
    	test.addTest(new Pow6Test("test293"));
    	test.addTest(new Pow6Test("test294"));
    	test.addTest(new Pow6Test("test295"));
    	test.addTest(new Pow6Test("test296"));
    	test.addTest(new Pow6Test("test297"));
    	test.addTest(new Pow6Test("test298"));
    	test.addTest(new Pow6Test("test299"));
    	return test;
    }
	
	public void test251() {
		Solution s = new Solution();
		double res =3.0E-5;
		double x = 0.22838;
		int n = 7;
		sleep(22390);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test252() {
		Solution s = new Solution();
		double res =8127.31576;
		double x = -4.48392;
		int n = 6;
		sleep(12425);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test253() {
		Solution s = new Solution();
		double res =-62.70481;
		double x = -1.45677;
		int n = 11;
		sleep(21471);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test254() {
		Solution s = new Solution();
		double res =-1864.15041;
		double x = -12.30723;
		int n = 3;
		sleep(87260);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test255() {
		Solution s = new Solution();
		double res =6811.55372;
		double x = -9.08472;
		int n = 4;
		sleep(38829);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test256() {
		Solution s = new Solution();
		double res =3340.21363;
		double x = -2.25101;
		int n = 10;
		sleep(40149);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test257() {
		Solution s = new Solution();
		double res =0.01758;
		double x = 0.36412;
		int n = 4;
		sleep(60181);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test258() {
		Solution s = new Solution();
		double res =288.23759;
		double x = 6.60567;
		int n = 3;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test259() {
		Solution s = new Solution();
		double res =1535.03028;
		double x = 6.25935;
		int n = 4;
		sleep(54970);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test260() {
		Solution s = new Solution();
		double res =-4491.16159;
		double x = -3.3248;
		int n = 7;
		sleep(92120);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test261() {
		Solution s = new Solution();
		double res =0.07886;
		double x = 0.60169;
		int n = 5;
		sleep(28896);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test262() {
		Solution s = new Solution();
		double res =2.1E-4;
		double x = 0.34772;
		int n = 8;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test263() {
		Solution s = new Solution();
		double res =3541.48713;
		double x = 3.90419;
		int n = 6;
		sleep(70517);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test264() {
		Solution s = new Solution();
		double res =0.10001;
		double x = -0.82541;
		int n = 12;
		sleep(81229);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test265() {
		Solution s = new Solution();
		double res =2225.45714;
		double x = 3.00748;
		int n = 7;
		sleep(89208);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test266() {
		Solution s = new Solution();
		double res =9.61324;
		double x = 1.76083;
		int n = 4;
		sleep(73455);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test267() {
		Solution s = new Solution();
		double res =16.93401;
		double x = 2.56795;
		int n = 3;
		sleep(24361);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test268() {
		Solution s = new Solution();
		double res =-589.27296;
		double x = -8.38376;
		int n = 3;
		sleep(71180);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test269() {
		Solution s = new Solution();
		double res =412.28316;
		double x = -4.50608;
		int n = 4;
		sleep(26759);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test270() {
		Solution s = new Solution();
		double res =9.2E-4;
		double x = -0.41731;
		int n = 8;
		sleep(36924);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test271() {
		Solution s = new Solution();
		double res =1048.84088;
		double x = -1.78536;
		int n = 12;
		sleep(86798);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test272() {
		Solution s = new Solution();
		double res =1.3E-4;
		double x = 0.62334;
		int n = 19;
		sleep(10398);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test273() {
		Solution s = new Solution();
		double res =-118.27474;
		double x = -1.54328;
		int n = 11;
		sleep(26273);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test274() {
		Solution s = new Solution();
		double res =0.0171;
		double x = -0.50757;
		int n = 6;
		sleep(51274);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test275() {
		Solution s = new Solution();
		double res =-5672.48449;
		double x = -17.83437;
		int n = 3;
		sleep(23208);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test276() {
		Solution s = new Solution();
		double res =-1303.44465;
		double x = -10.92356;
		int n = 3;
		sleep(90717);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test277() {
		Solution s = new Solution();
		double res =2055.84284;
		double x = -1.88835;
		int n = 12;
		sleep(96424);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test278() {
		Solution s = new Solution();
		double res =7.19026;
		double x = -1.15132;
		int n = 14;
		sleep(74404);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test279() {
		Solution s = new Solution();
		double res =-5.04237;
		double x = -1.38206;
		int n = 5;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test280() {
		Solution s = new Solution();
		double res =0.68329;
		double x = -0.9385;
		int n = 6;
		sleep(20602);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test281() {
		Solution s = new Solution();
		double res =130.98037;
		double x = -1.31106;
		int n = 18;
		sleep(26038);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test282() {
		Solution s = new Solution();
		double res =-2.77243;
		double x = -1.15682;
		int n = 7;
		sleep(95820);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test283() {
		Solution s = new Solution();
		double res =-50.02011;
		double x = -2.1869;
		int n = 5;
		sleep(96381);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test284() {
		Solution s = new Solution();
		double res =-24.63745;
		double x = -1.42765;
		int n = 9;
		sleep(23843);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test285() {
		Solution s = new Solution();
		double res =-12.72326;
		double x = -2.33453;
		int n = 3;
		sleep(28182);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test286() {
		Solution s = new Solution();
		double res =1.13074;
		double x = 1.00012;
		int n = 1024;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test287() {
		Solution s = new Solution();
		double res =3.43684;
		double x = 1.00001;
		int n = 123456;
		sleep(95008);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test288() {
		Solution s = new Solution();
		double res =0.0;
		double x = 1.0E-5;
		int n = 2147483647;
		sleep(48988);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test289() {
		Solution s = new Solution();
		double res =8.16266;
		double x = 1.00021;
		int n = 9999;
		sleep(81930);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test290() {
		Solution s = new Solution();
		double res =1.11424;
		double x = 1.00123;
		int n = 88;
		sleep(72204);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test291() {
		Solution s = new Solution();
		double res =-87.46403;
		double x = -1.00001;
		int n = 447125;
		sleep(88634);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test292() {
		Solution s = new Solution();
		double res =0.10902;
		double x = -0.99999;
		int n = 221620;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test293() {
		Solution s = new Solution();
		double res =8.0E-5;
		double x = 0.99999;
		int n = 948688;
		sleep(12975);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test294() {
		Solution s = new Solution();
		double res =9.0E-5;
		double x = -0.99999;
		int n = 933520;
		sleep(48235);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test295() {
		Solution s = new Solution();
		double res =7340.54568;
		double x = 1.00007;
		int n = 127164;
		sleep(42459);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test296() {
		Solution s = new Solution();
		double res =1.0;
		double x = 1.0;
		int n = 2147483647;
		sleep(96774);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test297() {
		Solution s = new Solution();
		double res =1.0;
		double x = 1.0;
		int n = -2147483648;
		sleep(63659);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test298() {
		Solution s = new Solution();
		double res =-1.0;
		double x = -1.0;
		int n = 2147483647;
		sleep(57188);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test299() {
		Solution s = new Solution();
		double res =1.0;
		double x = -1.0;
		int n = -2147483648;
		sleep(72163);
		assertTrue(checker(s.pow(x, n), res));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
	
	private boolean checker(double a, double b) {
		return Math.abs(a - b) < 0.0001;
	}
}
