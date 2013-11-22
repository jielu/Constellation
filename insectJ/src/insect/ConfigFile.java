package insect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Maintains properties for InsECT.
 * 
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ConfigFile {

	/**
	 * Maintains the configuration settings.
	 */
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

	/**
	 * Reloads the property set for this config file.
	 *
	 */
	public static void reload() {
		props = null;
		loadProperties();
	}

} // ConfigFile
