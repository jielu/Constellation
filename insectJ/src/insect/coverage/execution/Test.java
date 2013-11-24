package insect.coverage.execution;

import jaba.main.JABADriver;
import jaba.main.ResourceFileI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Test{

	public void init(String argv[]) {
		
	}
	
	public void run() {
		

	}
	

	public static void main(String[] args){
		try{
			
			
			 //../scripts/execute.csh ../../JabaVersions/jaba.v1/jaba.jrc testjaba1 
			 //-cp:.
			 //jaba.main.JABADriver  ACFGDriver -l ../subject//BigSubjects/antlr-2.6.0/antlr.rc
			
			//java -ea -mx1000m -classpath /Users/jielu/Dropbox/workspace/ARG/ReTest/dejavoo/lib/insect.jar
			//:/Users/jielu/Dropbox/workspace/ARG/ReTest/dejavoo/lib/jaba.jar insect.coverage.execution.Executor
			String rcFile = "/Users/jielu/Dropbox/workspace2013F/ReTest/deliverables/example/v1.jrc";
			String execID = "test";
			String[] classPathNames = new String[1];
			classPathNames[0] = "/Users/jielu/Dropbox/workspace2013F/ReTest/deliverables/.coverage_data/Example-v1";
			String main = "example.Main";
			String[] newArgs = new String[1];
			newArgs[0] = "1";
			
			Executor executor = new Executor(rcFile);
			executor.execute(execID, classPathNames, main, newArgs);
		
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	private static Properties props;

	/**
	 * Retrieves all properties from the config file
	 * named ".insect" located in the user's home directory.
	 *
	 */
	public static void loadProperties() {

		String filePath =
			System.getProperty("user.home") + File.separator + ".insect";
		try {
			FileInputStream cfgFile = new FileInputStream(filePath);
			props = new Properties();
			props.load(cfgFile);
			cfgFile.close();
		}
		catch (IOException loadExcep) {

			//if error, quit InsECT
			System.err.println("Error loading config file");
			System.exit(1);
		}
	}

	/**
	 * Returns the specified property's value from the config file.
	 *
	 * @param property name of property.
	 * @return the property's value.
	 */
	public static String getProperty(String property) {

		if (props == null)
			loadProperties();

		String value = props.getProperty(property);
		return value;
	}


}
