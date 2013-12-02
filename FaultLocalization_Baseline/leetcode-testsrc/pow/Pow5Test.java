package edu.gatech.pow;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Pow5Test extends TestCase {
	
    public Pow5Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new Pow5Test("test201"));
    	test.addTest(new Pow5Test("test202"));
    	test.addTest(new Pow5Test("test203"));
    	test.addTest(new Pow5Test("test204"));
    	test.addTest(new Pow5Test("test205"));
    	test.addTest(new Pow5Test("test206"));
    	test.addTest(new Pow5Test("test207"));
    	test.addTest(new Pow5Test("test208"));
    	test.addTest(new Pow5Test("test209"));
    	test.addTest(new Pow5Test("test210"));
    	test.addTest(new Pow5Test("test211"));
    	test.addTest(new Pow5Test("test212"));
    	test.addTest(new Pow5Test("test213"));
    	test.addTest(new Pow5Test("test214"));
    	test.addTest(new Pow5Test("test215"));
    	test.addTest(new Pow5Test("test216"));
    	test.addTest(new Pow5Test("test217"));
    	test.addTest(new Pow5Test("test218"));
    	test.addTest(new Pow5Test("test219"));
    	test.addTest(new Pow5Test("test220"));
    	test.addTest(new Pow5Test("test221"));
    	test.addTest(new Pow5Test("test222"));
    	test.addTest(new Pow5Test("test223"));
    	test.addTest(new Pow5Test("test224"));
    	test.addTest(new Pow5Test("test225"));
    	test.addTest(new Pow5Test("test226"));
    	test.addTest(new Pow5Test("test227"));
    	test.addTest(new Pow5Test("test228"));
    	test.addTest(new Pow5Test("test229"));
    	test.addTest(new Pow5Test("test230"));
    	test.addTest(new Pow5Test("test231"));
    	test.addTest(new Pow5Test("test232"));
    	test.addTest(new Pow5Test("test233"));
    	test.addTest(new Pow5Test("test234"));
    	test.addTest(new Pow5Test("test235"));
    	test.addTest(new Pow5Test("test236"));
    	test.addTest(new Pow5Test("test237"));
    	test.addTest(new Pow5Test("test238"));
    	test.addTest(new Pow5Test("test239"));
    	test.addTest(new Pow5Test("test240"));
    	test.addTest(new Pow5Test("test241"));
    	test.addTest(new Pow5Test("test242"));
    	test.addTest(new Pow5Test("test243"));
    	test.addTest(new Pow5Test("test244"));
    	test.addTest(new Pow5Test("test245"));
    	test.addTest(new Pow5Test("test246"));
    	test.addTest(new Pow5Test("test247"));
    	test.addTest(new Pow5Test("test248"));
    	test.addTest(new Pow5Test("test249"));
    	test.addTest(new Pow5Test("test250"));
    	return test;
    }
	
	public void test201() {
		Solution s = new Solution();
		double res =0.08853;
		double x = 11.2962;
		int n = -1;
		sleep(61085);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test202() {
		Solution s = new Solution();
		double res =0.01922;
		double x = 52.03473;
		int n = -1;
		sleep(92727);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test203() {
		Solution s = new Solution();
		double res =1.0;
		double x = 22.88565;
		int n = 0;
		sleep(79602);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test204() {
		Solution s = new Solution();
		double res =13.53747;
		double x = 3.67933;
		int n = 2;
		sleep(11464);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test205() {
		Solution s = new Solution();
		double res =3788.22155;
		double x = 61.54853;
		int n = 2;
		sleep(97248);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test206() {
		Solution s = new Solution();
		double res =1.14401;
		double x = 1.01696;
		int n = 8;
		sleep(87348);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test207() {
		Solution s = new Solution();
		double res =88.43219;
		double x = 4.45523;
		int n = 3;
		sleep(72925);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test208() {
		Solution s = new Solution();
		double res =16.64076;
		double x = 2.01973;
		int n = 4;
		sleep(13586);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test209() {
		Solution s = new Solution();
		double res =12.71737;
		double x = 2.33417;
		int n = 3;
		sleep(37005);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test210() {
		Solution s = new Solution();
		double res =0.89982;
		double x = 0.97911;
		int n = 5;
		sleep(21485);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test211() {
		Solution s = new Solution();
		double res =941.65019;
		double x = 9.80159;
		int n = 3;
		sleep(85389);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test212() {
		Solution s = new Solution();
		double res =7.25269;
		double x = 1.13183;
		int n = 16;
		sleep(28485);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test213() {
		Solution s = new Solution();
		double res =12.17853;
		double x = 1.13313;
		int n = 20;
		sleep(63934);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test214() {
		Solution s = new Solution();
		double res =0.00224;
		double x = 0.60146;
		int n = 12;
		sleep(14059);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test215() {
		Solution s = new Solution();
		double res =2.54827;
		double x = 1.26346;
		int n = 4;
		sleep(81447);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test216() {
		Solution s = new Solution();
		double res =2964.8263;
		double x = 1.49142;
		int n = 20;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test217() {
		Solution s = new Solution();
		double res =3476.88779;
		double x = 5.10785;
		int n = 5;
		sleep(16719);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test218() {
		Solution s = new Solution();
		double res =9422.47243;
		double x = 6.23495;
		int n = 5;
		sleep(63396);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test219() {
		Solution s = new Solution();
		double res =12.10288;
		double x = 1.51524;
		int n = 6;
		sleep(79623);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test220() {
		Solution s = new Solution();
		double res =0.04177;
		double x = 0.85318;
		int n = 20;
		sleep(60366);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test221() {
		Solution s = new Solution();
		double res =3661.18879;
		double x = 3.92588;
		int n = 6;
		sleep(48222);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test222() {
		Solution s = new Solution();
		double res =927.07899;
		double x = 9.75077;
		int n = 3;
		sleep(57332);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test223() {
		Solution s = new Solution();
		double res =1977.48922;
		double x = 1.99364;
		int n = 11;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test224() {
		Solution s = new Solution();
		double res =1246.61834;
		double x = 5.94201;
		int n = 4;
		sleep(59868);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test225() {
		Solution s = new Solution();
		double res =0.69751;
		double x = 0.97267;
		int n = 13;
		sleep(62277);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test226() {
		Solution s = new Solution();
		double res =14.76996;
		double x = 2.45354;
		int n = 3;
		sleep(92824);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test227() {
		Solution s = new Solution();
		double res =9800.35035;
		double x = 3.71687;
		int n = 7;
		sleep(86766);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test228() {
		Solution s = new Solution();
		double res =9299.61919;
		double x = 1.92071;
		int n = 14;
		sleep(78170);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test229() {
		Solution s = new Solution();
		double res =1340.57629;
		double x = 3.32059;
		int n = 6;
		sleep(42775);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test230() {
		Solution s = new Solution();
		double res =86.2459;
		double x = 2.43864;
		int n = 5;
		sleep(56085);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test231() {
		Solution s = new Solution();
		double res =9331.82622;
		double x = 9.8286;
		int n = 4;
		sleep(90076);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test232() {
		Solution s = new Solution();
		double res =2.73242;
		double x = 1.15442;
		int n = 7;
		sleep(64239);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test233() {
		Solution s = new Solution();
		double res =427.63351;
		double x = 4.54745;
		int n = 4;
		sleep(49092);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test234() {
		Solution s = new Solution();
		double res =0.13852;
		double x = 0.83552;
		int n = 11;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test235() {
		Solution s = new Solution();
		double res =652.33399;
		double x = 1.91182;
		int n = 10;
		sleep(28303);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test236() {
		Solution s = new Solution();
		double res =23.19144;
		double x = 1.56692;
		int n = 7;
		sleep(87113);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test237() {
		Solution s = new Solution();
		double res =340.24661;
		double x = 1.5165;
		int n = 14;
		sleep(89511);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test238() {
		Solution s = new Solution();
		double res =9590.28371;
		double x = 2.5014;
		int n = 10;
		sleep(24562);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test239() {
		Solution s = new Solution();
		double res =3234.76294;
		double x = 7.54155;
		int n = 4;
		sleep(77431);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test240() {
		Solution s = new Solution();
		double res =30.13697;
		double x = 1.97615;
		int n = 5;
		sleep(90787);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test241() {
		Solution s = new Solution();
		double res =556.49071;
		double x = 3.54072;
		int n = 5;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test242() {
		Solution s = new Solution();
		double res =0.00113;
		double x = 0.3793;
		int n = 7;
		sleep(17641);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test243() {
		Solution s = new Solution();
		double res =412.97767;
		double x = 7.4469;
		int n = 3;
		sleep(27407);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test244() {
		Solution s = new Solution();
		double res =6062.14063;
		double x = 1.95421;
		int n = 13;
		sleep(43774);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test245() {
		Solution s = new Solution();
		double res =51.03682;
		double x = 1.92596;
		int n = 6;
		sleep(49610);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test246() {
		Solution s = new Solution();
		double res =42.00987;
		double x = 1.40468;
		int n = 11;
		sleep(16005);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test247() {
		Solution s = new Solution();
		double res =-2529.95504;
		double x = -13.62608;
		int n = 3;
		sleep(40313);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test248() {
		Solution s = new Solution();
		double res =-452.73957;
		double x = -3.39758;
		int n = 5;
		sleep(16070);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test249() {
		Solution s = new Solution();
		double res =3162.06911;
		double x = 14.67767;
		int n = 3;
		sleep(65553);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test250() {
		Solution s = new Solution();
		double res =-2266.59303;
		double x = -13.13584;
		int n = 3;
		sleep(30386);
		assertTrue(checker(s.pow(x, n), res));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
	
	private boolean checker(double a, double b) {
		return Math.abs(a - b) < 0.0001;
	}
}
