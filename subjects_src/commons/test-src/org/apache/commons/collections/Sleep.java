package org.apache.commons.collections;

import java.util.Arrays;


public class Sleep {
	public static void sleep() {
		int counter = 100;
		int[] nums = new int[counter / 10];
		for(int t = 0; t < 1000; t++) {
			for(int i = 0; i < counter / 10; i++) {
				nums[i] = (int) (Math.random() * 10000);
			}
			Arrays.sort(nums);
		}
	}


}
