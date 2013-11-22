package insect;

import java.io.PrintWriter;

/** Represents a debug class. This is not a "real" debug class, as it won't
 * do anything. The purpose of creating this fake debug class is for 
 * performace consideration. So it won't hurt the performance if people
 * are not in the debugging mode. You would need to get the real debug
 * class if you want to debug the system.
 * @author Alex Orso -- <i> Created </i>
 */
public class Debug {
	
    public static PrintWriter writer = new PrintWriter(System.out, true);
    public static int debugLevel = 3;

    public static void println(String message, int level) {
		if (level <= debugLevel) {
	    	writer.println(message);
		}
    }
    
    public static void print(String message, int level) {
		if (level <= debugLevel) {
	    	writer.print(message);
		}
    }
}

