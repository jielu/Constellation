package edu.gatech.scrambleString;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.TestCase;

public class ScrambleString4Test extends TestCase {
	
	public void test124() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "traeg";
		boolean res = true;
		sleep(94024);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test125() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "treag";
		boolean res = true;
		sleep(78513);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test126() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "trega";
		boolean res = true;
		sleep(98090);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test127() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "trgae";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test128() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "trgea";
		boolean res = true;
		sleep(15385);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test129() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aegrt";
		boolean res = true;
		sleep(97358);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test130() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aergt";
		boolean res = true;
		sleep(73227);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test131() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aertg";
		boolean res = true;
		sleep(61498);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test132() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aetgr";
		boolean res = true;
		sleep(31767);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test133() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aetrg";
		boolean res = true;
		sleep(54628);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test134() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "agert";
		boolean res = true;
		sleep(59106);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test135() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "agetr";
		boolean res = false;
		sleep(11113);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test136() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "agret";
		boolean res = true;
		sleep(90849);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test137() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "agrte";
		boolean res = false;
		sleep(76213);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test138() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "agter";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test139() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "agtre";
		boolean res = false;
		sleep(53347);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test140() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aregt";
		boolean res = true;
		sleep(40730);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test141() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aretg";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test142() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "arget";
		boolean res = true;
		sleep(19791);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test143() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "argte";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test144() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "arteg";
		boolean res = false;
		sleep(34317);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test145() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "artge";
		boolean res = false;
		sleep(83709);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test146() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "ategr";
		boolean res = true;
		sleep(80447);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test147() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "aterg";
		boolean res = true;
		sleep(21884);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test148() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "atger";
		boolean res = true;
		sleep(82037);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test149() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "atgre";
		boolean res = true;
		sleep(53804);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test150() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "atreg";
		boolean res = true;
		sleep(64218);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test151() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "atrge";
		boolean res = true;
		sleep(55731);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test152() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eagrt";
		boolean res = true;
		sleep(76151);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test153() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eagtr";
		boolean res = false;
		sleep(12566);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test154() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eargt";
		boolean res = true;
		sleep(94041);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test155() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eartg";
		boolean res = true;
		sleep(22491);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test156() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eatgr";
		boolean res = true;
		sleep(14392);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test157() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "eatrg";
		boolean res = true;
		sleep(20115);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test158() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "egart";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test159() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "egatr";
		boolean res = false;
		sleep(56854);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test160() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "egrat";
		boolean res = true;
		sleep(70635);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test161() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "egrta";
		boolean res = true;
		sleep(29598);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test162() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "egtar";
		boolean res = false;
		sleep(89471);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test163() {
		Solution s = new Solution();
		String s1 = "great";
		String s2 = "egtra";
		boolean res = false;
		sleep(27722);
		assertEquals(res, s.isScramble(s1, s2));
	}
	
	private void sleep(int counter) {
		Sleeper.sleep();
	}

}
