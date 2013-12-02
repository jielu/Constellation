package edu.gatech.pow;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Pow2Test extends TestCase {
	
    public Pow2Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new Pow2Test("test51"));
    	test.addTest(new Pow2Test("test52"));
    	test.addTest(new Pow2Test("test53"));
    	test.addTest(new Pow2Test("test54"));
    	test.addTest(new Pow2Test("test55"));
    	test.addTest(new Pow2Test("test56"));
    	test.addTest(new Pow2Test("test57"));
    	test.addTest(new Pow2Test("test58"));
    	test.addTest(new Pow2Test("test59"));
    	test.addTest(new Pow2Test("test60"));
    	test.addTest(new Pow2Test("test61"));
    	test.addTest(new Pow2Test("test62"));
    	test.addTest(new Pow2Test("test63"));
    	test.addTest(new Pow2Test("test64"));
    	test.addTest(new Pow2Test("test65"));
    	test.addTest(new Pow2Test("test66"));
    	test.addTest(new Pow2Test("test67"));
    	test.addTest(new Pow2Test("test68"));
    	test.addTest(new Pow2Test("test69"));
    	test.addTest(new Pow2Test("test70"));
    	test.addTest(new Pow2Test("test71"));
    	test.addTest(new Pow2Test("test72"));
    	test.addTest(new Pow2Test("test73"));
    	test.addTest(new Pow2Test("test74"));
    	test.addTest(new Pow2Test("test75"));
    	test.addTest(new Pow2Test("test76"));
    	test.addTest(new Pow2Test("test77"));
    	test.addTest(new Pow2Test("test78"));
    	test.addTest(new Pow2Test("test79"));
    	test.addTest(new Pow2Test("test80"));
    	test.addTest(new Pow2Test("test81"));
    	test.addTest(new Pow2Test("test82"));
    	test.addTest(new Pow2Test("test83"));
    	test.addTest(new Pow2Test("test84"));
    	test.addTest(new Pow2Test("test85"));
    	test.addTest(new Pow2Test("test86"));
    	test.addTest(new Pow2Test("test87"));
    	test.addTest(new Pow2Test("test88"));
    	test.addTest(new Pow2Test("test89"));
    	test.addTest(new Pow2Test("test90"));
    	test.addTest(new Pow2Test("test91"));
    	test.addTest(new Pow2Test("test92"));
    	test.addTest(new Pow2Test("test93"));
    	test.addTest(new Pow2Test("test94"));
    	test.addTest(new Pow2Test("test95"));
    	test.addTest(new Pow2Test("test96"));
    	test.addTest(new Pow2Test("test97"));
    	test.addTest(new Pow2Test("test98"));
    	test.addTest(new Pow2Test("test99"));
    	test.addTest(new Pow2Test("test100"));
    	return test;
    }
	
	public void test51() {
		Solution s = new Solution();
		double res =0.01607;
		double x = 62.2327;
		int n = -1;
		sleep(64113);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test52() {
		Solution s = new Solution();
		double res =0.07754;
		double x = 12.89673;
		int n = -1;
		sleep(27957);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test53() {
		Solution s = new Solution();
		double res =2.7066;
		double x = 0.77964;
		int n = -4;
		sleep(18287);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test54() {
		Solution s = new Solution();
		double res =1.0;
		double x = 85.97151;
		int n = 0;
		sleep(52450);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test55() {
		Solution s = new Solution();
		double res =5.0E-5;
		double x = 28.07445;
		int n = -3;
		sleep(11890);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test56() {
		Solution s = new Solution();
		double res =8.0E-5;
		double x = 23.56502;
		int n = -3;
		sleep(39644);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test57() {
		Solution s = new Solution();
		double res =0.26911;
		double x = 1.92768;
		int n = -2;
		sleep(88003);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test58() {
		Solution s = new Solution();
		double res =0.00352;
		double x = 0.0593;
		int n = 2;
		sleep(23720);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test59() {
		Solution s = new Solution();
		double res =646.03812;
		double x = 25.41728;
		int n = 2;
		sleep(65833);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test60() {
		Solution s = new Solution();
		double res =10.32408;
		double x = 3.21311;
		int n = 2;
		sleep(87577);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test61() {
		Solution s = new Solution();
		double res =2.3E-4;
		double x = 16.301;
		int n = -3;
		sleep(49569);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test62() {
		Solution s = new Solution();
		double res =4.2E-4;
		double x = 13.30252;
		int n = -3;
		sleep(53576);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test63() {
		Solution s = new Solution();
		double res =3.0E-5;
		double x = 13.88514;
		int n = -4;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test64() {
		Solution s = new Solution();
		double res =0.00121;
		double x = 9.37733;
		int n = -3;
		sleep(85784);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test65() {
		Solution s = new Solution();
		double res =0.02982;
		double x = 33.53483;
		int n = -1;
		sleep(92777);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test66() {
		Solution s = new Solution();
		double res =5004.01037;
		double x = 17.10433;
		int n = 3;
		sleep(71117);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test67() {
		Solution s = new Solution();
		double res =23.17099;
		double x = 23.17099;
		int n = 1;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test68() {
		Solution s = new Solution();
		double res =294.43471;
		double x = 17.1591;
		int n = 2;
		sleep(77368);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test69() {
		Solution s = new Solution();
		double res =14.85054;
		double x = 3.85364;
		int n = 2;
		sleep(66751);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test70() {
		Solution s = new Solution();
		double res =0.00586;
		double x = 5.54613;
		int n = -3;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test71() {
		Solution s = new Solution();
		double res =4.9622;
		double x = 2.2276;
		int n = 2;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test72() {
		Solution s = new Solution();
		double res =1.0;
		double x = 1.08431;
		int n = 0;
		sleep(57419);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test73() {
		Solution s = new Solution();
		double res =0.02841;
		double x = 2.43585;
		int n = -4;
		sleep(35231);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test74() {
		Solution s = new Solution();
		double res =1.0;
		double x = 6.7873;
		int n = 0;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test75() {
		Solution s = new Solution();
		double res =1.0;
		double x = 14.06889;
		int n = 0;
		sleep(33016);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test76() {
		Solution s = new Solution();
		double res =12.40839;
		double x = 12.40839;
		int n = 1;
		sleep(89776);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test77() {
		Solution s = new Solution();
		double res =2282.86015;
		double x = 13.16719;
		int n = 3;
		sleep(95086);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test78() {
		Solution s = new Solution();
		double res =8185.93416;
		double x = 20.15376;
		int n = 3;
		sleep(21407);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test79() {
		Solution s = new Solution();
		double res =2.43256;
		double x = 0.41109;
		int n = -1;
		sleep(74496);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test80() {
		Solution s = new Solution();
		double res =110.51927;
		double x = 2.56264;
		int n = 5;
		sleep(82234);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test81() {
		Solution s = new Solution();
		double res =1.2E-4;
		double x = 20.11319;
		int n = -3;
		sleep(94008);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test82() {
		Solution s = new Solution();
		double res =14.20556;
		double x = 14.20556;
		int n = 1;
		sleep(52893);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test83() {
		Solution s = new Solution();
		double res =2806.68581;
		double x = 7.27861;
		int n = 4;
		sleep(48215);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test84() {
		Solution s = new Solution();
		double res =45.68941;
		double x = 45.68941;
		int n = 1;
		sleep(49652);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test85() {
		Solution s = new Solution();
		double res =371.00654;
		double x = 19.26153;
		int n = 2;
		sleep(52212);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test86() {
		Solution s = new Solution();
		double res =0.00898;
		double x = 3.24811;
		int n = -4;
		sleep(28822);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test87() {
		Solution s = new Solution();
		double res =2356.909;
		double x = 6.96764;
		int n = 4;
		sleep(52547);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test88() {
		Solution s = new Solution();
		double res =0.04413;
		double x = 22.65874;
		int n = -1;
		sleep(12552);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test89() {
		Solution s = new Solution();
		double res =1.83332;
		double x = 0.81706;
		int n = -3;
		sleep(39913);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test90() {
		Solution s = new Solution();
		double res =3.6E-4;
		double x = 52.79299;
		int n = -2;
		sleep(65230);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test91() {
		Solution s = new Solution();
		double res =0.00164;
		double x = 24.68383;
		int n = -2;
		sleep(37825);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test92() {
		Solution s = new Solution();
		double res =0.0023;
		double x = 20.86179;
		int n = -2;
		sleep(49951);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test93() {
		Solution s = new Solution();
		double res =317.71298;
		double x = 6.82357;
		int n = 3;
		sleep(87478);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test94() {
		Solution s = new Solution();
		double res =0.16869;
		double x = 1.56037;
		int n = -4;
		sleep(79904);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test95() {
		Solution s = new Solution();
		double res =0.09098;
		double x = 10.99121;
		int n = -1;
		sleep(82655);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test96() {
		Solution s = new Solution();
		double res =0.05351;
		double x = 18.68808;
		int n = -1;
		sleep(49487);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test97() {
		Solution s = new Solution();
		double res =1.0;
		double x = 0.65185;
		int n = 0;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test98() {
		Solution s = new Solution();
		double res =0.04938;
		double x = 4.50009;
		int n = -2;
		sleep(47802);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test99() {
		Solution s = new Solution();
		double res =1.93503;
		double x = 1.17943;
		int n = 4;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test100() {
		Solution s = new Solution();
		double res =6.54493;
		double x = 6.54493;
		int n = 1;
		sleep(75376);
		assertTrue(checker(s.pow(x, n), res));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
	
	private boolean checker(double a, double b) {
		return Math.abs(a - b) < 0.0001;
	}
}
