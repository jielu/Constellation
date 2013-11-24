package edu.gatech.cs6422.testClassCollector;
import java.util.HashSet;
import java.util.Set;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Listener extends RunListener {
	public static Set<String> classSets = new HashSet<String>();
	
	public void testStarted(Description desc){
		classSets.add(desc.getClassName());
		System.out.println(desc.getClassName());
	}
	
	public void testFailure(Failure f){
		
	}
	
	public void testFinished(Description desc){
		
	}
}
