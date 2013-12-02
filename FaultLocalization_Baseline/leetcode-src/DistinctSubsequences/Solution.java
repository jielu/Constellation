package edu.gatech.DistinctSubsequences;

public class Solution {
	public int numDistinct(String S, String T) {
		if(S == null || S.length() == 0) return 0;
		if(T == null || T.length() == 0) return S.length();
		
		if(S.length() < T.length()) {
			String temp = S;
			S = T;
			T = temp;
		}
		
		int[][] res = new int[T.length()+1][S.length()+1];
		for(int j = 0; j < S.length(); j++) {
			res[0][j] = 1;
		}
		
		for(int i = 0; i < T.length(); i++)
			for(int j = 0; j < S.length(); j++){
				res[i+1][j+1] = res[i+1][j];
				if(T.charAt(i) == S.charAt(j)) {
					res[i+1][j+1] += res[i][j];
				}
			}
		
		return res[T.length()][S.length()];
	}
}
