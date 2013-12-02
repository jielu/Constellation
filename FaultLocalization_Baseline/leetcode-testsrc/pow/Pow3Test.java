package edu.gatech.pow;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Pow3Test extends TestCase {
	
    public Pow3Test(String test) {
    	super(test);
    }
    
	public static Test suite() {
    	TestSuite test = new TestSuite();
    	test.addTest(new Pow3Test("test101"));
    	test.addTest(new Pow3Test("test102"));
    	test.addTest(new Pow3Test("test103"));
    	test.addTest(new Pow3Test("test104"));
    	test.addTest(new Pow3Test("test105"));
    	test.addTest(new Pow3Test("test106"));
    	test.addTest(new Pow3Test("test107"));
    	test.addTest(new Pow3Test("test108"));
    	test.addTest(new Pow3Test("test109"));
    	test.addTest(new Pow3Test("test110"));
    	test.addTest(new Pow3Test("test111"));
    	test.addTest(new Pow3Test("test112"));
    	test.addTest(new Pow3Test("test113"));
    	test.addTest(new Pow3Test("test114"));
    	test.addTest(new Pow3Test("test115"));
    	test.addTest(new Pow3Test("test116"));
    	test.addTest(new Pow3Test("test117"));
    	test.addTest(new Pow3Test("test118"));
    	test.addTest(new Pow3Test("test119"));
    	test.addTest(new Pow3Test("test120"));
    	test.addTest(new Pow3Test("test121"));
    	test.addTest(new Pow3Test("test122"));
    	test.addTest(new Pow3Test("test123"));
    	test.addTest(new Pow3Test("test124"));
    	test.addTest(new Pow3Test("test125"));
    	test.addTest(new Pow3Test("test126"));
    	test.addTest(new Pow3Test("test127"));
    	test.addTest(new Pow3Test("test128"));
    	test.addTest(new Pow3Test("test129"));
    	test.addTest(new Pow3Test("test130"));
    	test.addTest(new Pow3Test("test131"));
    	test.addTest(new Pow3Test("test132"));
    	test.addTest(new Pow3Test("test133"));
    	test.addTest(new Pow3Test("test134"));
    	test.addTest(new Pow3Test("test135"));
    	test.addTest(new Pow3Test("test136"));
    	test.addTest(new Pow3Test("test137"));
    	test.addTest(new Pow3Test("test138"));
    	test.addTest(new Pow3Test("test139"));
    	test.addTest(new Pow3Test("test140"));
    	test.addTest(new Pow3Test("test141"));
    	test.addTest(new Pow3Test("test142"));
    	test.addTest(new Pow3Test("test143"));
    	test.addTest(new Pow3Test("test144"));
    	test.addTest(new Pow3Test("test145"));
    	test.addTest(new Pow3Test("test146"));
    	test.addTest(new Pow3Test("test147"));
    	test.addTest(new Pow3Test("test148"));
    	test.addTest(new Pow3Test("test149"));
    	test.addTest(new Pow3Test("test150"));
    	return test;
    }
	
	public void test101() {
		Solution s = new Solution();
		double res =2.0E-5;
		double x = 37.32888;
		int n = -3;
		sleep(96178);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test102() {
		Solution s = new Solution();
		double res =2.0E-5;
		double x = 14.91663;
		int n = -4;
		sleep(70030);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test103() {
		Solution s = new Solution();
		double res =1284.30665;
		double x = 5.98642;
		int n = 4;
		sleep(11618);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test104() {
		Solution s = new Solution();
		double res =550.47201;
		double x = 23.46214;
		int n = 2;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test105() {
		Solution s = new Solution();
		double res =0.00149;
		double x = 25.8696;
		int n = -2;
		sleep(17153);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test106() {
		Solution s = new Solution();
		double res =7.1E-4;
		double x = 11.20693;
		int n = -3;
		sleep(23903);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test107() {
		Solution s = new Solution();
		double res =4.7E-4;
		double x = 12.86805;
		int n = -3;
		sleep(11844);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test108() {
		Solution s = new Solution();
		double res =91.46594;
		double x = 2.46747;
		int n = 5;
		sleep(70614);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test109() {
		Solution s = new Solution();
		double res =0.05708;
		double x = 17.51948;
		int n = -1;
		sleep(55949);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test110() {
		Solution s = new Solution();
		double res =4.72453;
		double x = 4.72453;
		int n = 1;
		sleep(59381);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test111() {
		Solution s = new Solution();
		double res =15.74949;
		double x = 15.74949;
		int n = 1;
		sleep(63095);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test112() {
		Solution s = new Solution();
		double res =5697.33154;
		double x = 75.48067;
		int n = 2;
		sleep(72679);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test113() {
		Solution s = new Solution();
		double res =38.25129;
		double x = 3.36937;
		int n = 3;
		sleep(89644);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test114() {
		Solution s = new Solution();
		double res =700.92043;
		double x = 8.88293;
		int n = 3;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test115() {
		Solution s = new Solution();
		double res =4182.74697;
		double x = 64.67416;
		int n = 2;
		sleep(98764);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test116() {
		Solution s = new Solution();
		double res =1.3E-4;
		double x = 9.33743;
		int n = -4;
		sleep(34786);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test117() {
		Solution s = new Solution();
		double res =1.4E-4;
		double x = 9.19991;
		int n = -4;
		sleep(57624);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test118() {
		Solution s = new Solution();
		double res =58.99777;
		double x = 58.99777;
		int n = 1;
		sleep(74793);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test119() {
		Solution s = new Solution();
		double res =0.01294;
		double x = 8.79249;
		int n = -2;
		sleep(86742);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test120() {
		Solution s = new Solution();
		double res =1658.91152;
		double x = 4.40519;
		int n = 5;
		sleep(96331);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test121() {
		Solution s = new Solution();
		double res =360.29051;
		double x = 18.98132;
		int n = 2;
		sleep(67585);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test122() {
		Solution s = new Solution();
		double res =1.0;
		double x = 30.06939;
		int n = 0;
		sleep(89501);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test123() {
		Solution s = new Solution();
		double res =8.79778;
		double x = 8.79778;
		int n = 1;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test124() {
		Solution s = new Solution();
		double res =0.04702;
		double x = 21.2667;
		int n = -1;
		sleep(48780);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test125() {
		Solution s = new Solution();
		double res =0.01126;
		double x = 88.78972;
		int n = -1;
		sleep(27776);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test126() {
		Solution s = new Solution();
		double res =0.01967;
		double x = 50.82887;
		int n = -1;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test127() {
		Solution s = new Solution();
		double res =15.41021;
		double x = 15.41021;
		int n = 1;
		sleep(39790);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test128() {
		Solution s = new Solution();
		double res =0.01245;
		double x = 8.96216;
		int n = -2;
		sleep(63952);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test129() {
		Solution s = new Solution();
		double res =104.23001;
		double x = 10.20931;
		int n = 2;
		sleep(23876);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test130() {
		Solution s = new Solution();
		double res =14.89783;
		double x = 1.96463;
		int n = 4;
		sleep(51301);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test131() {
		Solution s = new Solution();
		double res =0.02616;
		double x = 3.36872;
		int n = -3;
		sleep(54391);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test132() {
		Solution s = new Solution();
		double res =7.8E-4;
		double x = 35.71617;
		int n = -2;
		sleep(67082);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test133() {
		Solution s = new Solution();
		double res =0.01994;
		double x = 50.14166;
		int n = -1;
		sleep(65242);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test134() {
		Solution s = new Solution();
		double res =2905.39542;
		double x = 53.90172;
		int n = 2;
		sleep(36253);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test135() {
		Solution s = new Solution();
		double res =2.68983;
		double x = 0.60973;
		int n = -2;
		sleep(79561);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test136() {
		Solution s = new Solution();
		double res =1.0;
		double x = 64.5857;
		int n = 0;
		sleep(62791);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test137() {
		Solution s = new Solution();
		double res =2.8E-4;
		double x = 60.24644;
		int n = -2;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test138() {
		Solution s = new Solution();
		double res =0.86686;
		double x = 1.07405;
		int n = -2;
		sleep(75191);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test139() {
		Solution s = new Solution();
		double res =0.15836;
		double x = 2.51295;
		int n = -2;
		sleep(12476);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test140() {
		Solution s = new Solution();
		double res =9.0E-5;
		double x = 4.70975;
		int n = -6;
		sleep(30756);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test141() {
		Solution s = new Solution();
		double res =161.80908;
		double x = 12.72042;
		int n = 2;
		sleep(38882);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test142() {
		Solution s = new Solution();
		double res =26.41382;
		double x = 26.41382;
		int n = 1;
		sleep(63892);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test143() {
		Solution s = new Solution();
		double res =9.7401;
		double x = 9.7401;
		int n = 1;
		sleep(70441);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test144() {
		Solution s = new Solution();
		double res =0.01622;
		double x = 3.95057;
		int n = -3;
		sleep(69693);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test145() {
		Solution s = new Solution();
		double res =0.00364;
		double x = 4.07187;
		int n = -4;
		sleep(96066);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test146() {
		Solution s = new Solution();
		double res =0.02392;
		double x = 41.80492;
		int n = -1;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test147() {
		Solution s = new Solution();
		double res =0.04673;
		double x = 0.54189;
		int n = 5;
		sleep(10000);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test148() {
		Solution s = new Solution();
		double res =397.6383;
		double x = 19.94087;
		int n = 2;
		sleep(83218);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test149() {
		Solution s = new Solution();
		double res =8.26021;
		double x = 1.26441;
		int n = 9;
		sleep(18836);
		assertTrue(checker(s.pow(x, n), res));
	}
	public void test150() {
		Solution s = new Solution();
		double res =0.01196;
		double x = 83.61545;
		int n = -1;
		sleep(13292);
		assertTrue(checker(s.pow(x, n), res));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}
	
	private boolean checker(double a, double b) {
		return Math.abs(a - b) < 0.0001;
	}
}