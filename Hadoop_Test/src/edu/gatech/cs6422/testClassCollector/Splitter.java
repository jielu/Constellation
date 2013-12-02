package edu.gatech.cs6422.testClassCollector;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.junit.runner.JUnitCore;


public class Splitter {
	public static void main(String[] args) {
		try{
			Splitter s = new Splitter();
			s.test();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test() throws Exception {
		Class testClass = Class.forName("de.susebox.SuseboxTestSuite");
		
		JUnitCore jc = new JUnitCore();
		jc.addListener(new Listener());
		
		jc.run(testClass);
		
		PrintWriter writer = new PrintWriter(new FileWriter(new File("class.txt")));
		for(String className : Listener.classSets) {
			writer.println(className);
		}
		writer.close();
	}
}
