package edu.gatech.scrambleString;

import java.util.Arrays;

import edu.gatech.common.Sleeper;
import junit.framework.TestCase;

public class ScrambleString7Test extends TestCase {
	
	public void test243() {
		Solution s = new Solution();
		String s1 = "ccabcbabcbabbbbcbb";
		String s2 = "bbbbabccccbbbabcba";
		boolean res = false;
		sleep(62555);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test244() {
		Solution s = new Solution();
		String s1 = "bbbcacaaaba";
		String s2 = "cabbaababca";
		boolean res = false;
		sleep(12202);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test245() {
		Solution s = new Solution();
		String s1 = "babbcbaccac";
		String s2 = "bbcaabcabcc";
		boolean res = false;
		sleep(50208);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test246() {
		Solution s = new Solution();
		String s1 = "cbbcacababcbc";
		String s2 = "bacbabbbcccca";
		boolean res = false;
		sleep(85557);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test247() {
		Solution s = new Solution();
		String s1 = "ababcbaccbabbcbca";
		String s2 = "bbbbbaaaacccccbba";
		boolean res = false;
		sleep(58041);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test248() {
		Solution s = new Solution();
		String s1 = "bcbbcccbcbcaaacbb";
		String s2 = "acbcabbbbacccbbcc";
		boolean res = false;
		sleep(19201);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test249() {
		Solution s = new Solution();
		String s1 = "aacaacccacbcbcbcbb";
		String s2 = "bcacabbbaaabcccccc";
		boolean res = false;
		sleep(78593);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test250() {
		Solution s = new Solution();
		String s1 = "bacacbcbbba";
		String s2 = "cbbaabacbcb";
		boolean res = false;
		sleep(83658);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test251() {
		Solution s = new Solution();
		String s1 = "ccbbcaccbccbbbcca";
		String s2 = "ccbbcbbaabcccbccc";
		boolean res = false;
		sleep(22206);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test252() {
		Solution s = new Solution();
		String s1 = "cacbcccbcbaccbabbc";
		String s2 = "ccbbbcbbbacaaccccc";
		boolean res = false;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test253() {
		Solution s = new Solution();
		String s1 = "abbbcbaaccacaacc";
		String s2 = "acaaaccabcabcbcb";
		boolean res = true;
		sleep(68416);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test254() {
		Solution s = new Solution();
		String s1 = "babcbccbccbacbaccc";
		String s2 = "accacccacbcbcbbcbb";
		boolean res = true;
		sleep(14298);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test255() {
		Solution s = new Solution();
		String s1 = "cbccbcbcacaaaaaa";
		String s2 = "cabaabcaaacaccbc";
		boolean res = true;
		sleep(81381);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test256() {
		Solution s = new Solution();
		String s1 = "cabccccbcacab";
		String s2 = "caccbbacccbca";
		boolean res = true;
		sleep(76766);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test257() {
		Solution s = new Solution();
		String s1 = "bcabcbbbbabaacbaa";
		String s2 = "cabaacbaabbacbbbb";
		boolean res = true;
		sleep(10000);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test258() {
		Solution s = new Solution();
		String s1 = "acccacbcaaaabbaaa";
		String s2 = "aacaacbabacbacaac";
		boolean res = true;
		sleep(16016);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test259() {
		Solution s = new Solution();
		String s1 = "caccaaabcccacaab";
		String s2 = "ccbcaacacaaabacc";
		boolean res = true;
		sleep(54810);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test260() {
		Solution s = new Solution();
		String s1 = "cbcccccbbabcbac";
		String s2 = "bbccaccbcbcabcc";
		boolean res = true;
		sleep(79291);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test261() {
		Solution s = new Solution();
		String s1 = "bbbaaaaccaabbbaa";
		String s2 = "baababaababacabc";
		boolean res = true;
		sleep(57317);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test262() {
		Solution s = new Solution();
		String s1 = "xstjzkfpkggnhjzkpfjoguxvkbuopi";
		String s2 = "xbouipkvxugojfpkzjhnggkpfkzjts";
		boolean res = true;
		sleep(59372);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test263() {
		Solution s = new Solution();
		String s1 = "oatzzffqpnwcxhejzjsnpmkmzngneo";
		String s2 = "acegneonzmkmpnsjzjhxwnpqffzzto";
		boolean res = true;
		sleep(80567);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test264() {
		Solution s = new Solution();
		String s1 = "sqksrqzhhmfmlmqvlbnaqcmebbkqfy";
		String s2 = "abbkyfqemcqnblvqmlmfmhhzqrskqs";
		boolean res = true;
		sleep(66702);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test265() {
		Solution s = new Solution();
		String s1 = "aqpuhhtophtbvhtqvudvkhkoilayta";
		String s2 = "aayatliokhkvduvqthvbthpothhupq";
		boolean res = true;
		sleep(63553);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test266() {
		Solution s = new Solution();
		String s1 = "tqxpxeknttgwoppemjkivrulaflayn";
		String s2 = "afaylnlurvikjmeppowgttnkexpxqt";
		boolean res = true;
		sleep(46327);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test267() {
		Solution s = new Solution();
		String s1 = "tdfiajsnfmvbanthzcrjaidnkjbljo";
		String s2 = "aaablojjkndijrczhtnbvmfnsjifdt";
		boolean res = true;
		sleep(64337);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test268() {
		Solution s = new Solution();
		String s1 = "dsanujiiqwfsysnfsrwbrfhhpqicbw";
		String s2 = "dabbciwqphhfrwrsfnsysfwqiijuns";
		boolean res = true;
		sleep(18446);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test269() {
		Solution s = new Solution();
		String s1 = "eqxuljcvzyatwvicnaaqthxaxpibrh";
		String s2 = "eaaaahribpxxhtqncivwtyzvcjluxq";
		boolean res = true;
		sleep(59123);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test270() {
		Solution s = new Solution();
		String s1 = "xxyrijvdlveesbnwopygiggjfxtgoa";
		String s2 = "xbatgoxfjggigypownseevldvjiryx";
		boolean res = true;
		sleep(85807);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test271() {
		Solution s = new Solution();
		String s1 = "oyifgtdmeyslstaojpppxvxiavcije";
		String s2 = "oaacejivixvxpppjotslsyemdtgfiy";
		boolean res = true;
		sleep(45812);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test272() {
		Solution s = new Solution();
		String s1 = "ymjmfxshglxwrrgufcvvzjuietjzzz";
		String s2 = "fxczujvmwizrzgxgjmvzelyjthusrf";
		boolean res = false;
		sleep(24856);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test273() {
		Solution s = new Solution();
		String s1 = "hijumxroduxuvbcooeneehtpvqefav";
		String s2 = "ufevdurtcxaopuomvibxqovheejenh";
		boolean res = false;
		sleep(77622);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test274() {
		Solution s = new Solution();
		String s1 = "eswjvddvalysqvfywjvcywpwssqgzt";
		String s2 = "vwsyywweljdftwsjqdpzassgcyvvqv";
		boolean res = false;
		sleep(88331);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test275() {
		Solution s = new Solution();
		String s1 = "onpaghkfbpchutcxqxxynyecrhmcce";
		String s2 = "ocyhcnxqcexptnfbxrcegpuhckhyam";
		boolean res = false;
		sleep(74824);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test276() {
		Solution s = new Solution();
		String s1 = "iydzdwbqbfixognqhbmimhwyhmdnrm";
		String s2 = "nmbywdbnmmfybqqighdriizmxdhwho";
		boolean res = false;
		sleep(43016);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test277() {
		Solution s = new Solution();
		String s1 = "qircluqkyzmiqlhnzxrnbgqoqshpyr";
		String s2 = "xmuhqrpqcgynlnlbzhiyrqiqoqskzr";
		boolean res = false;
		sleep(37991);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test278() {
		Solution s = new Solution();
		String s1 = "ackbdflwqhqarscoepmmxyymcarbjn";
		String s2 = "mphoebfamrmcscdblwryqykaaqjcnx";
		boolean res = false;
		sleep(41046);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test279() {
		Solution s = new Solution();
		String s1 = "sdsjnggafzfpkmqovhyvlvsnretogb";
		String s2 = "psgekbdfsvayjvfztsmngnolqhvogr";
		boolean res = false;
		sleep(77353);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test280() {
		Solution s = new Solution();
		String s1 = "molsevndlihckceamwlgovkavcfcna";
		String s2 = "alknclsklamvndvcocwhfcgmoveaie";
		boolean res = false;
		sleep(40362);
		assertEquals(res, s.isScramble(s1, s2));
	}
	public void test281() {
		Solution s = new Solution();
		String s1 = "pcighfdjnbwfkohtklrecxnooxyipj";
		String s2 = "npodkfchrfpxliocgtnykhxwjbojie";
		boolean res = false;
		sleep(25664);
		assertEquals(res, s.isScramble(s1, s2));
	}

	private void sleep(int counter) {
		Sleeper.sleep();
	}
}
