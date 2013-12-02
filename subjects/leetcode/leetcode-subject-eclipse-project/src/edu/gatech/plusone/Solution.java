package edu.gatech.plusone;

public class Solution {
	public int[] plusOne(int[] digits) {
		if(digits == null || digits.length == 0) return null;
		int carry = 1;
		for(int i = digits.length - 1; i >= 0; i--) {
			digits[i] += carry;
			if(digits[i] == 10) {
				digits[i] = 0;
				carry = 1;
			} else carry = 0;
		}
		if(carry == 0) return digits;
		else {
			int[] res = new int[digits.length + 1];
			res[0] = 1;
			// bug below, before return:
			for(int i = 1; i < res.length; i++)
				res[i] = 9;
			return res;
		}
	}
}
