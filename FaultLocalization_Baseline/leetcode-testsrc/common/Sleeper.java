package edu.gatech.common;

import java.util.Arrays;

public class Sleeper {

	public static void sleep() {
		int counter = 10000;
		
		int[] nums = new int[counter / 10];
		for(int t = 0; t < 500; t++) {
			for(int i = 0; i < counter / 10; i++) {
				nums[i] = (int) (Math.random() * 10000);
			}
			Arrays.sort(nums);
		}
	}
}
