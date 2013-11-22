package insect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipFile;

/**
 * Represents a resource file specifying the program name, class path
 * to use for analysis, and class files to analyze.  This class reads
 * and parses a resource file and provides accessor methods to this
 * data.
 * @author Jim Jones -- <i>Created.  April 1, 1999</i>
 * @author Lakshmish Ramaswamy -- <i> Revised January 2, 2001 added
 * loadDottyOutputParamters() and getDottyOutputVector() functions
 * </i>
 * @author Caleb Ho -- <i> Revised. Redisgned to be more object
 * oriented, by having the ResourceFile use the DottyOutputSpec class
 * for storing and managing output specs rather than going indirectly
 * through the ResourceFile. </i>
 * @author Huaxing Wu -- <i> Revised. 3/21/02 Replace method
 * loadClassFilePathNames() with loadClassFiles() to just load the
 * class files. Change method loadClassPaths() to validate each
 * classpath and initialize relative fields </i>
 * @author Huaxing Wu -- <i> 8/28/02, Revised loadClassPaths. </i>
 * @author Jim Jones -- <i> Revised. Sep. 6, 2002 Added
 * loadSourceClassPaths()</i>
 * @author Anil Chawla -- <i> Modified for distribution with InsECT </i>
 */

public class ResourceFile 
{

    /** Default program name if one is not specified by the resource file. */
    protected static final String defaultProgramName = "UnnamedProgram";

    /** Name of the program to be analyzed. */
    protected String programName;
    
    /** Vector of strings, each representing a path to be used to find class
     * files to be analyzed. */
    protected Vector classPaths;
    
    /** Vector of strings in the "package.classname" format to be analyzed. */
    protected Vector classFiles;

    /** Vector of strings, each representing a path to be used to find
     * source files corresponding to the class files listed in the
     * classFiles field. */
    protected Vector sourceClassPaths;   
  
    /** ClassPaths which are directory */
    protected File[] dirs;

    /** ClassPaths which are zip files */
    protected ZipFile[] zips;

    /** SourceClassPaths which are directory */
    protected File[] sourceDirs;

    /** SourceClassPaths which are zip files */
    protected ZipFile[] sourceZips;

    /** Name of the resource file. */
    private String resourceFileName;
    
    /** Properties of the resource file.  Keeps a key/value pair for each
      variable defined in the resource file. */
    private Properties rcProperties;
   
   
    /**
     * Constructor.  Loads the resource file to initialize this object.
     * @param resourceFileName Full path name (relative or absolute)
     * of the resource file to be loaded.
     * @exception IllegalArgumentException This is thrown when the
     * resource file is in an invalid format or variables defined
     * within are invalid or absent.
     */
    public ResourceFile(String resourceFileName) 
	throws IllegalArgumentException {

	classPaths = new Vector();
	classFiles = new Vector(100, 100);
	sourceClassPaths = new Vector();

	try {
	    File rcFile = new File(resourceFileName);
	    
	    /* Get the absolute path of the resource file */
	    this.resourceFileName = rcFile.getAbsolutePath();
	     
	    /* open resource file for reading */
            FileInputStream resourceIn =
		new FileInputStream( rcFile);
            
	    /* read in all key/value pairs */
            rcProperties = new Properties();
            rcProperties.load( resourceIn );
        } catch ( IOException e ) {
	    throw (new IllegalArgumentException(e.getMessage()));
	}

        
	/* load each of the variables from the Properties into my fields */
        loadProgramName();
	loadClassPaths();
	loadClassFiles();
	loadSourceClassPaths();
       
    }
    
    
       
    /**
     * Loads the program name from the resource file.
     */
    private void loadProgramName() {
        programName = rcProperties.getProperty( "ProgramName" );
        if ( ( programName == null ) || ( programName.equals( "" ) ) )
            programName = defaultProgramName;
    }
    
    /**
     * Loads the class paths from the resource file.
     */
    private void loadClassPaths() {

	/* parse the ClassPath variable */
        String classPathString = rcProperties.getProperty( "ClassPath" );
        if ( ( classPathString == null ) || ( classPathString.equals( "" ) ) )
            throw new IllegalArgumentException( "ClassPath variable must be " +
						"set in resource file: " +
						resourceFileName );
        
        StringTokenizer tokenizer = new StringTokenizer( classPathString,
							 File.pathSeparator );
	//       classPaths = new Vector();
        while ( tokenizer.hasMoreTokens() ) {
            String classPath = tokenizer.nextToken().trim();	    
            if ( classPath.equals( "" ) ) continue;

            // in case path is relative
	    classPath = relative2absolute( classPath ); 

            classPaths.add( classPath );
        }
        if ( classPaths.size() == 0 )
            throw new IllegalArgumentException( "ClassPath variable must " +
            "include at least one path: " +
            classPathString );
    }

    /**
     * Loads the source class paths from the resource file.
     */
    private void loadSourceClassPaths() {

	//	sourceClassPaths = new Vector();

	String sourceClassPathString = 
	    rcProperties.getProperty( "SourceClassPath" );
	if ( ( sourceClassPathString == null ) || 
	     ( sourceClassPathString.equals( "" ) ) ) return;

	StringTokenizer tokenizer = new StringTokenizer( sourceClassPathString,
							 File.pathSeparator );
	while ( tokenizer.hasMoreTokens() ) {
	    String sourceClassPath = tokenizer.nextToken().trim();
	    if ( sourceClassPath.equals( "" ) ) continue;

	    // in case path is relative
	    sourceClassPath = relative2absolute( sourceClassPath );

	    sourceClassPaths.add( sourceClassPath );
	}

  //      System.out.println("process Source Path " + sourceClassPaths.size());
	/** Added by Huaxing Wu: validate and create File object for paths */
	sourceDirs = new File[sourceClassPaths.size()];
	sourceZips = new ZipFile[sourceClassPaths.size()];
	for(int i = 0; i < sourceClassPaths.size(); i++) {
	    sourceDirs[i] = new File((String)sourceClassPaths.get(i));
	    if(!sourceDirs[i].isDirectory()) {
		try {
		    sourceZips[i] = new ZipFile(sourceDirs[i]);
		}catch(Exception e) {
		    throw new IllegalArgumentException( "SourceClassPath " + 
				           (String)sourceClassPaths.get(i) + 
				   " is neither a directory or zip/jar file");
		}
	    }
	}
	    
//	System.out.println("SourceDirs " + sourceDirs);
    }

    /**
     * Returns an absolute path for a relative one -- using this
     * absolute path of the resource file as a starting point.
     * @param relPath  A potentially relative path
     * @return  A path that is equivalent to relPath, but is absolute.
     */
    private String relative2absolute( String relPath ) {
	/**
	 * Created by Manas: To account for paths relative from the
	 * location of the resource file
	 */
	String absPath = relPath;
	if ( !new File(absPath).isAbsolute() ) { // This is a
							// relative
							// path.
	    if(resourceFileName.lastIndexOf(File.separator) > -1) {
		String resourceFileDirectory = 
		    resourceFileName.substring(0, 
			    resourceFileName.lastIndexOf(File.separator));
		absPath = resourceFileDirectory + File.separator + absPath;
	    }
	    try {
		File tmpFile = new File(absPath);
		absPath = tmpFile.getCanonicalPath();
	    }catch(Exception e) {
		throw new IllegalArgumentException("Cannot resolve path " + relPath);
	    }
	}
	return absPath;

    }

    /**
     * Loads the class files from the resource file.
     */
    private void loadClassFiles() {
	// parse the ClassFiles variable 
        String classFilesString = rcProperties.getProperty( "ClassFiles" );
	if ( ( classFilesString == null ) || ( classFilesString.equals( "" ) ) )
            throw new IllegalArgumentException( "ClassFiles variable must be " +
						"set in the resource file: " +
						resourceFileName );
	
        StringTokenizer tokenizer = new StringTokenizer( classFilesString,
							 "\t:, " );
        while ( tokenizer.hasMoreTokens() ) {
            String classFile = tokenizer.nextToken();
            if ( classFile.equals( "" ) ) continue;
            classFiles.add( classFile );
        }

	if ( classFiles.size() == 0 )
            throw new IllegalArgumentException( "ClassFile variable must " +
            "include at least one class: " +
            classFilesString );
    }

    /**
     * Returns the program name specified in the resource file.
     * @return  The program name specified in the resource file.
     */
    public String getProgramName() {
        return programName;
    }
    
    /**
     * Returns an array of strings, each representing a path to be used to find
     * class files to be analyzed.
     * @return  An array of strings, each representing a path to be used to find
     *          class files to be analyzed.
     */
    public String[] getClassPaths() {
	return (String[])classPaths.toArray(new String[0]);
    }

    /**
     * Returns an array of strings, each representing a path to be
     * used to find source files of the class files that are analyzed.
     * @return An array of strings, each representing a path to be
     * used to find source files of the class files that are analyzed.
     */
    public String[] getSourceClassPaths() {
	return (String[])sourceClassPaths.toArray(new String[0]);
    }
    
    /**
     * Returns an array of strings specified in the resource file in the
     * "package.classname" format to be analyzed.
     * @return  Array of class files.
     */
    public String[] getClassFiles() {
	return (String[])classFiles.toArray(new String[0]);
    }
   
}
