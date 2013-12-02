package edu.gatech.LRUCache;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class LRUCache1Test extends TestCase {

	public void test1() {
//		int c = 1;
//		LRUCache cache = new LRUCache(c);
//		ArrayList<Integer> l1 = new ArrayList<Integer>();
//		ArrayList<Integer> l2 = new ArrayList<Integer>();
//		
//		String res = "";
//		for(String str : res.split(",")) l2.add(Integer.parseInt(str));
//		
//		cache.set(0, 0);
//		l1.add(cache.get(1));
//		//...
//		
//		checker(l1, l2);
//		sleep(1);
	}
	
	private void sleep(int counter) {
		int[] nums = new int[counter / 10];
		for(int t = 0; t < 500; t++) {
			for(int i = 0; i < counter / 10; i++) {
				nums[i] = (int) (Math.random() * 10000);
			}
			Arrays.sort(nums);
		}
	}
	
	private boolean checker(ArrayList<Integer> a, ArrayList<Integer> b) {
		if(a == null || b == null) return false;
		else if(a.size() != b.size()) return false;
		else {
			for(int i = 0; i < a.size(); i++) {
				if(a.get(i) != b.get(i)) return false;
			}
		}
		return true;
	}
}
