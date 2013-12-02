package edu.gatech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.gatech.LRUCache.LRUCache;
import edu.gatech.plusone.Solution;

public class TestGeneration {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("LRUCache_input.txt")));
			PrintWriter writer = new PrintWriter(new FileWriter(new File("output.txt")));
			String temp;
			int count = 1;
			
			while((temp = reader.readLine()) != null) {
				// for LRU Cache
				int c = Integer.parseInt(temp.substring(0, temp.indexOf(",")));
				int s1 = temp.indexOf("[");
				int e1 = temp.indexOf("]");
				int s2 = temp.indexOf("[", e1);
				int e2 = temp.indexOf("]", s2);
				String res = temp.substring(s2 + 1, e2);
				String inputs = temp.substring(s1 + 1, e1);
				writer.println("\tpublic void test" + count++ + "() {");
				writer.println("\t\tint c = " + c + ";");
				writer.println("\t\tLRUCache cache = new LRUCache(c);");
				writer.println("\t\tArrayList<Integer> l1 = new ArrayList<Integer>();");
				writer.println("\t\tArrayList<Integer> l2 = new ArrayList<Integer>();");
				writer.println("\t\tString res = " + res);
				writer.println("\t\tfor(String str : res.split(\",\")) l2.add(Integer.parseInt(str));");
				
				for(String input : inputs.split(",")) {
					
				}
				
				int time = (int) (Math.random() * 100000);
				if(time < 10000) time = 10000;
				writer.println("\t\tsleep(" + time + ");");
				writer.println("\t\tchecker(l1, l2);");
				writer.println("\t}");
				
				// for Scramble String
//				String[] strs = temp.split("\t");
//				String[] nums = strs[0].split(",");
//				String s1 = nums[0].trim();
//				String s2 = nums[1].trim();
//				boolean res = Boolean.parseBoolean(strs[1]);
//				
//				writer.println("\tpublic void test" + count++ + "() {");
//				writer.println("\t\tSolution s = new Solution();");
//				writer.println("\t\tString s1 = " + s1 + ";");
//				writer.println("\t\tString s2 = " + s2 + ";");
//				writer.println("\t\tboolean res = " + res + ";");
//				int time = (int) (Math.random() * 100000);
//				if(time < 10000) time = 10000;
//				writer.println("\t\tsleep(" + time + ");");
//				writer.println("\t\tassertEquals(res, s.isScramble(s1, s2));");
//				writer.println("\t}");
				
				
				// for Distinct Subseq
//				String[] strs = temp.split("\t");
//				String[] nums = strs[0].split(",");
//				String S = nums[0].trim();
//				String T = nums[1].trim();
//				int res = Integer.parseInt(strs[1]);
//				
//				writer.println("\tpublic void test" + count++ + "() {");
//				writer.println("\t\tSolution s = new Solution();");
//				writer.println("\t\tString S = " + S + ";");
//				writer.println("\t\tString T = " + T + ";");
//				writer.println("\t\tint res = " + res + ";");
//				int time = (int) (Math.random() * 100000);
//				if(time < 10000) time = 10000;
//				writer.println("\t\tsleep(" + time + ");");
//				writer.println("\t\tassertEquals(res, s.numDistinct(S, T));");
//				writer.println("\t}");
				
				// for pow(x, n)
//				String[] strs = temp.split("\t");
//				String[] nums = strs[0].split(",");
//				double x = Double.parseDouble(nums[0].trim());
//				int n = Integer.parseInt(nums[1].trim());
//				double res = Double.parseDouble(strs[1].trim());
//				
//				writer.println("\tpublic void test" + count++ + "() {");
//				writer.println("\t\tSolution s = new Solution();");
//				writer.println("\t\tdouble res =" + res + ";");
//				writer.println("\t\tdouble x = " + x + ";");
//				writer.println("\t\tint n = " + n + ";");
//				int time = (int) (Math.random() * 100000);
//				if(time < 10000) time = 10000;
//				writer.println("\t\tsleep(" + time + ");");
//				writer.println("\t\tassertTrue(checker(s.pow(x, n), res));");
//				writer.println("\t}");
				
				// for plus one
//				ArrayList<Integer> l1 = new ArrayList<Integer>();
//				ArrayList<Integer> l2 = new ArrayList<Integer>();
//				int s1 = (temp.charAt(0) == '[') ? 0 : -1;
//				int e1 = temp.indexOf("]");
//				int s2 = temp.indexOf("[", e1);
//				int e2 = temp.indexOf("]", s2);
//				
//				String str1 = temp.substring(s1 + 1, e1);
//				String str2 = temp.substring(s2 + 1, e2);
//				addInts(l1, str1);
//				addInts(l2, str2);
//				
//				writer.println("\tpublic void test" + count++ + "() {");
//				writer.println("\t\tSolution s = new Solution();");
//				writer.println("\t\tint[] in = new int[] {" + str1 + "};");
//				writer.println("\t\tint[] out = new int[] {" + str2 + "};");
//				int time = (int) (Math.random() * 100000);
//				if(time < 10000) time = 10000;
//				writer.println("\t\tsleep(" + time + ");");
//				writer.println("\t\tassertTrue(checker(out, s.plusOne(in)));");
//				writer.println("\t}");
			}
			reader.close();
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void addInts(ArrayList<Integer> l, String s) {
		String[] strs = s.split(",");
		for(String str : strs) {
			l.add(Integer.parseInt(str));
		}
	}
}
