package insect.coverage.execution;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Extends the functionality of URLClassLoader to allow a class not
 * located in the JVM classpath to be loaded, even if a class of that
 * name has already been loaded by the bootstrap classloader.
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ExecClassLoader extends URLClassLoader {

	/**
	 * Constructor to create class loader with the specified
	 * class paths.
	 *
	 * @param urls URLs designating classpaths
	 */
	public ExecClassLoader(URL[] urls) {
		super(urls);
	}

	/**
	 * Attempts to load the class with the specified name.
	 *
	 * @param name fully qualified name of class
	 * @return reference to class
	 * @exception ClassNotFoundException if class could not be loaded
	 */
	public Class loadClass(String name) throws ClassNotFoundException {

		return findClass(name);
	}

	/**
	 * Attempts to locate the specified class in this
	 * class loader's classpaths and load it from disk.
	 *
	 * @param name fully qualified name of class
	 * @return reference to class
	 * @exception ClassNotFoundException if class could not be loaded
	 */
	protected Class findClass(String name) throws ClassNotFoundException {

		InputStream fi = null;
		try {

			URL classToLoad =
				findResource(
					name.replace('.', java.io.File.separatorChar) + ".class");

			// if not in this classloaders path,
			// then ask parent classloader to find it
			if (classToLoad == null)
				return getParent().loadClass(name);

			// otherwise load the class
			else {
				fi = classToLoad.openStream();
				//new FileInputStream(classToLoad.getPath());
				int total, read;
				total = fi.available();
				read = 0;
				byte[] classBytes = new byte[total];
				while (read < total) {
					read = read + fi.read(classBytes, read, total - read);
				}
				return defineClass(name, classBytes, 0, classBytes.length);
			}
		}
		//if this classloader attempts to load a library class
		catch (SecurityException se) {
			return getParent().loadClass(name);
		}
		catch (IOException ie) {
			System.out.println("Error loading class " + name);
		}

		//could not load or find class
		throw new ClassNotFoundException();
	}

} // ExecClassLoader
