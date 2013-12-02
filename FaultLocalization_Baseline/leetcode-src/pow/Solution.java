package edu.gatech.pow;

public class Solution {
    public double pow(double x, int n) {
        double res = 1;
        boolean neg = n < 0;
        if(n < 0) n = -n;
        
        while(n > 0) {
            if((n & 1) == 1) {
                res *= x;
            }
            n = n >> 1;
            x *= x;
        }
        
        if(n == -5) {
        	return pow(x, n + 1);
        }
        
        if(neg) return 1 / res;
        else return res;
    }
}
