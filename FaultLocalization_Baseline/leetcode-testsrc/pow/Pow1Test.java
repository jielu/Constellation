package edu.gatech.pow;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import edu.gatech.plusone.PlusOne1Test;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Pow1Test extends TestCase {

    public Pow1Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new Pow1Test("test1"));
    	test.addTest(new Pow1Test("test2"));
    	test.addTest(new Pow1Test("test3"));
    	test.addTest(new Pow1Test("test4"));
    	test.addTest(new Pow1Test("test5"));
    	test.addTest(new Pow1Test("test6"));
    	test.addTest(new Pow1Test("test7"));
    	test.addTest(new Pow1Test("test8"));
    	test.addTest(new Pow1Test("test9"));
    	test.addTest(new Pow1Test("test10"));
    	test.addTest(new Pow1Test("test11"));
    	test.addTest(new Pow1Test("test12"));
    	test.addTest(new Pow1Test("test13"));
    	test.addTest(new Pow1Test("test14"));
    	test.addTest(new Pow1Test("test15"));
    	test.addTest(new Pow1Test("test16"));
    	test.addTest(new Pow1Test("test17"));
    	test.addTest(new Pow1Test("test18"));
    	test.addTest(new Pow1Test("test19"));
    	test.addTest(new Pow1Test("test20"));
    	test.addTest(new Pow1Test("test21"));
    	test.addTest(new Pow1Test("test22"));
    	test.addTest(new Pow1Test("test23"));
    	test.addTest(new Pow1Test("test24"));
    	test.addTest(new Pow1Test("test25"));
    	test.addTest(new Pow1Test("test26"));
    	test.addTest(new Pow1Test("test27"));
    	test.addTest(new Pow1Test("test28"));
    	test.addTest(new Pow1Test("test29"));
    	test.addTest(new Pow1Test("test30"));
    	test.addTest(new Pow1Test("test31"));
    	test.addTest(new Pow1Test("test32"));
    	test.addTest(new Pow1Test("test33"));
    	test.addTest(new Pow1Test("test34"));
    	test.addTest(new Pow1Test("test35"));
    	test.addTest(new Pow1Test("test36"));
    	test.addTest(new Pow1Test("test37"));
    	test.addTest(new Pow1Test("test38"));
    	test.addTest(new Pow1Test("test39"));
    	test.addTest(new Pow1Test("test40"));
    	test.addTest(new Pow1Test("test41"));
    	test.addTest(new Pow1Test("test42"));
    	test.addTest(new Pow1Test("test43"));
    	test.addTest(new Pow1Test("test44"));
    	test.addTest(new Pow1Test("test45"));
    	test.addTest(new Pow1Test("test46"));
    	test.addTest(new Pow1Test("test47"));
    	test.addTest(new Pow1Test("test48"));
    	test.addTest(new Pow1Test("test49"));
    	test.addTest(new Pow1Test("test50"));
    	return test;
    }
	
	public void test1() {
		Solution s = new Solution();
		double res =700.28148;
		double x = 8.88023;
		int n = 3;
		sleep(76524);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test2() {
		Solution s = new Solution();
		double res =3.0E-5;
		double x = 34.00515;
		int n = -3;
		sleep(53997);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test3() {
		Solution s = new Solution();
		double res =15.18715;
		double x = 3.89707;
		int n = 2;
		sleep(34379);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test4() {
		Solution s = new Solution();
		double res =0.00204;
		double x = 22.14659;
		int n = -2;
		sleep(59973);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test5() {
		Solution s = new Solution();
		double res =1.0;
		double x = 0.44528;
		int n = 0;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test6() {
		Solution s = new Solution();
		double res =2.0E-5;
		double x = 8.84372;
		int n = -5;
		sleep(27454);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test7() {
		Solution s = new Solution();
		double res =54.83508;
		double x = 0.44894;
		int n = -5;
		sleep(69948);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test8() {
		Solution s = new Solution();
		double res =0.52342;
		double x = 0.72348;
		int n = 2;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test9() {
		Solution s = new Solution();
		double res =0.11169;
		double x = 8.95371;
		int n = -1;
		sleep(29499);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test10() {
		Solution s = new Solution();
		double res =5643.35434;
		double x = 8.66731;
		int n = 4;
		sleep(22477);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test11() {
		Solution s = new Solution();
		double res =0.00236;
		double x = 4.53636;
		int n = -4;
		sleep(60342);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test12() {
		Solution s = new Solution();
		double res =0.0021;
		double x = 21.83387;
		int n = -2;
		sleep(69124);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test13() {
		Solution s = new Solution();
		double res =2.0E-5;
		double x = 36.86919;
		int n = -3;
		sleep(87222);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test14() {
		Solution s = new Solution();
		double res =2278.36371;
		double x = 6.90885;
		int n = 4;
		sleep(13622);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test15() {
		Solution s = new Solution();
		double res =700.11255;
		double x = 26.45964;
		int n = 2;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test16() {
		Solution s = new Solution();
		double res =42.38803;
		double x = 42.38803;
		int n = 1;
		sleep(71672);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test17() {
		Solution s = new Solution();
		double res =0.62176;
		double x = 1.60835;
		int n = -1;
		sleep(91699);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test18() {
		Solution s = new Solution();
		double res =73.10524;
		double x = 8.55016;
		int n = 2;
		sleep(98384);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test19() {
		Solution s = new Solution();
		double res =0.00776;
		double x = 3.36897;
		int n = -4;
		sleep(38582);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test20() {
		Solution s = new Solution();
		double res =0.07292;
		double x = 13.71406;
		int n = -1;
		sleep(92789);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test21() {
		Solution s = new Solution();
		double res =1.0;
		double x = 24.31877;
		int n = 0;
		sleep(24093);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test22() {
		Solution s = new Solution();
		double res =3749.1293;
		double x = 15.53496;
		int n = 3;
		sleep(72896);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test23() {
		Solution s = new Solution();
		double res =641.66415;
		double x = 3.64302;
		int n = 5;
		sleep(94940);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test24() {
		Solution s = new Solution();
		double res =0.01165;
		double x = 85.83033;
		int n = -1;
		sleep(97928);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test25() {
		Solution s = new Solution();
		double res =0.04587;
		double x = 21.80146;
		int n = -1;
		sleep(10514);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test26() {
		Solution s = new Solution();
		double res =44.34497;
		double x = 44.34497;
		int n = 1;
		sleep(55056);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test27() {
		Solution s = new Solution();
		double res =1.0;
		double x = 38.52708;
		int n = 0;
		sleep(51261);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test28() {
		Solution s = new Solution();
		double res =30.95427;
		double x = 30.95427;
		int n = 1;
		sleep(90446);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test29() {
		Solution s = new Solution();
		double res =0.08076;
		double x = 12.38265;
		int n = -1;
		sleep(68256);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test30() {
		Solution s = new Solution();
		double res =60.26072;
		double x = 60.26072;
		int n = 1;
		sleep(59049);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test31() {
		Solution s = new Solution();
		double res =1.3E-4;
		double x = 19.91386;
		int n = -3;
		sleep(50238);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test32() {
		Solution s = new Solution();
		double res =0.09473;
		double x = 1.80249;
		int n = -4;
		sleep(43536);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test33() {
		Solution s = new Solution();
		double res =0.01705;
		double x = 58.66067;
		int n = -1;
		sleep(19823);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test34() {
		Solution s = new Solution();
		double res =0.17562;
		double x = 2.38621;
		int n = -2;
		sleep(63593);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test35() {
		Solution s = new Solution();
		double res =921.83647;
		double x = 30.36176;
		int n = 2;
		sleep(98807);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test36() {
		Solution s = new Solution();
		double res =1.0;
		double x = 3.29993;
		int n = 0;
		sleep(64762);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test37() {
		Solution s = new Solution();
		double res =0.04108;
		double x = 24.34302;
		int n = -1;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test38() {
		Solution s = new Solution();
		double res =1.0;
		double x = 15.57408;
		int n = 0;
		sleep(57759);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test39() {
		Solution s = new Solution();
		double res =0.04206;
		double x = 23.77364;
		int n = -1;
		sleep(89447);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test40() {
		Solution s = new Solution();
		double res =1.0;
		double x = 31.24428;
		int n = 0;
		sleep(30229);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test41() {
		Solution s = new Solution();
		double res =2403.13171;
		double x = 13.39448;
		int n = 3;
		sleep(67943);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test42() {
		Solution s = new Solution();
		double res =7.8E-4;
		double x = 35.82084;
		int n = -2;
		sleep(92825);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test43() {
		Solution s = new Solution();
		double res =5.7E-4;
		double x = 4.46188;
		int n = -5;
		sleep(14776);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test44() {
		Solution s = new Solution();
		double res =0.141;
		double x = 7.09208;
		int n = -1;
		sleep(59071);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test45() {
		Solution s = new Solution();
		double res =0.02444;
		double x = 40.922;
		int n = -1;
		sleep(53364);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test46() {
		Solution s = new Solution();
		double res =4228.91781;
		double x = 65.03013;
		int n = 2;
		sleep(16393);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test47() {
		Solution s = new Solution();
		double res =1.0;
		double x = 68.71309;
		int n = 0;
		sleep(11761);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test48() {
		Solution s = new Solution();
		double res =0.07376;
		double x = 2.38451;
		int n = -3;
		sleep(85798);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test49() {
		Solution s = new Solution();
		double res =0.00531;
		double x = 5.73207;
		int n = -3;
		sleep(72649);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test50() {
		Solution s = new Solution();
		double res =3197.43541;
		double x = 56.54587;
		int n = 2;
		sleep(15876);
		assertTrue(checker(s.pow(x, n), res));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
	
	private boolean checker(double a, double b) {
		return Math.abs(a - b) < 0.0001;
	}
}
