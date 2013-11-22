package insect.gui;

import insect.coverage.analysis.jaba.JABACoverage;
import insect.gui.dialogs.ConfigDialog;
import insect.gui.dialogs.ExecuteDialog;
import insect.gui.dialogs.InstrumentDialog;
import insect.gui.dialogs.ManageDialog;
import insect.gui.frames.CoverageFrame;
import insect.gui.frames.InsectGUIFrame;
import insect.gui.frames.ProgramTreeFrame;
import insect.gui.frames.SourceViewFrame;
import insect.gui.infos.ClassInfo;
import insect.gui.infos.InsectGUIInfo;
import insect.gui.infos.MethodInfo;
import jaba.main.Options;
import jaba.main.ResourceFile;
import jaba.sym.Program;
import jaba.tools.local.Factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

/**
 * InsectGUI.java
 *
 *
 * Created: Tue Sep 24 18:52:23 2002
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */
public class InsectGUI extends JFrame implements ActionListener {

	//coverage values

	/* Coverage constants */
	public static final byte NOTCOVERED = 1;
	public static final byte COVERED = 2;
	public static final byte CALLSITE = 4;
	public static final byte EXCEPTIONSITE = 8;

	//gui elements
	private JDesktopPane contentPane;
	private ProgramTreeFrame treeFrame;
	private JFileChooser fileChooser;
	private JMenuItem loadsrcsItem;
	private JMenuItem instItem;
	private JMenuItem execItem;
	private JMenuItem junitItem;
	private JMenuItem jabaoptItem;

	//program info
	private HashMap classInfos;
	private JABACoverage cov;
	private String rcFilePath;
	private String curProgName;

	private Options opt = null;

	/**
	 * Creates a new <code>InsectGUI</code> instance.
	 *
	 */
	public InsectGUI() {

		//set JABA parameters
		opt = Factory.getOptions();
		opt.setCreateLVT(true);
		//     jaba.sym.Program.analysisPattern = true;
		//     jaba.sym.Primitive.analysisPattern = false;
		//     jaba.sym.SymbolTable.analysisPattern = true;

		//create content pane
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

		//create file chooser
		fileChooser = new JFileChooser(System.getProperty("user.dir"));

		//set this frame's properties
		setTitle("InsECT");
		setJMenuBar(createMenuBar());
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(getToolkit().getScreenSize());
		setVisible(true);

		//if .insect does not exist
		String cfg =
			System.getProperty("user.home") + File.separator + ".insect";
		if (!(new File(cfg)).exists())
			 (new ConfigDialog(this)).setVisible(true);

	}

	/**
	 * Creates the menu bar for the gui.
	 *
	 * @return a <code>JMenuBar</code> value
	 */
	private JMenuBar createMenuBar() {

		//create menus
		JMenuBar menuBar = new JMenuBar();
		JMenu insectMenu = new JMenu("InsECT");
		JMenu progMenu = new JMenu("Program");
		JMenu toolsMenu = new JMenu("Tools");

		//create menu items
		JMenuItem mItem = new JMenuItem("Configure");
		mItem.addActionListener(this);
		insectMenu.add(mItem);
		mItem = new JMenuItem("Manage");
		mItem.addActionListener(this);
		insectMenu.add(mItem);
		insectMenu.add(new JSeparator());
		mItem = new JMenuItem("Exit");
		mItem.addActionListener(this);
		insectMenu.add(mItem);
		mItem = new JMenuItem("Load Program");
		mItem.addActionListener(this);
		progMenu.add(mItem);
		loadsrcsItem = new JMenuItem("Load Sources");
		loadsrcsItem.addActionListener(this);
		loadsrcsItem.setEnabled(false);
		progMenu.add(loadsrcsItem);
		progMenu.add(new JSeparator());
		instItem = new JMenuItem("Instrument");
		instItem.addActionListener(this);
		instItem.setEnabled(false);
		progMenu.add(instItem);
		execItem = new JMenuItem("Execute");
		execItem.addActionListener(this);
		execItem.setEnabled(false);
		progMenu.add(execItem);
		junitItem = new JMenuItem("JUnit Driver");
		junitItem.setEnabled(false);
		junitItem.addActionListener(this);
		toolsMenu.add(junitItem);
		jabaoptItem = new JMenuItem("JABA Optimizer");
		jabaoptItem.setEnabled(false);
		jabaoptItem.addActionListener(this);
		toolsMenu.add(jabaoptItem);

		//add menus
		menuBar.add(insectMenu);
		menuBar.add(progMenu);
		menuBar.add(toolsMenu);

		return menuBar;

	}

	/**
	 * Loads a program from a JABA resource file.
	 *
	 * @param rcFilePath pathname for resource file
	 */
	private void loadProgram(String rcFilePath) {

		//if a program is already loaded, confirm closing it first
		if (cov != null) {
			if (JOptionPane
				.showConfirmDialog(
					this,
					"Close all windows and load another program?",
					"Confirm Closing",
					JOptionPane.YES_NO_OPTION)
				== JOptionPane.NO_OPTION)
				return;

			//close current program
			else
				closeProgram();
		}

		if (rcFilePath == null) {

			//show file browser for resource file
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setDialogTitle("Open JABA Resource File");
			if (fileChooser.showOpenDialog(this)
				!= JFileChooser.APPROVE_OPTION)
				return;

			rcFilePath = fileChooser.getSelectedFile().getPath();
		}
		this.rcFilePath = rcFilePath;

		//load program in JABA
		ResourceFile jrf = new ResourceFile(rcFilePath);
		Program prog = Factory.getProgram(jrf, opt);
		curProgName = prog.getName();

		//load coverage information
		cov = new JABACoverage(rcFilePath);

		classInfos = new HashMap();

		//create and display program tree
		treeFrame = new ProgramTreeFrame(this, prog);
		displayFrame(treeFrame, JDesktopPane.PALETTE_LAYER);

		//enable menus
		loadsrcsItem.setEnabled(true);
		instItem.setEnabled(true);
		execItem.setEnabled(true);
		junitItem.setEnabled(true);
		jabaoptItem.setEnabled(true);
	}

	private void closeProgram() {

		JInternalFrame[] frames = contentPane.getAllFrames();
		for (int i = 0; i < frames.length; i++)
			frames[i].dispose();

		classInfos = null;
		rcFilePath = null;

		//disable menus
		loadsrcsItem.setEnabled(false);
		instItem.setEnabled(false);
		execItem.setEnabled(false);
		junitItem.setEnabled(false);
		jabaoptItem.setEnabled(false);
	}

	/**
	 * Loads the source files for the program from the
	 * given directory pathname.
	 *
	 * @param srcPath pathname of directory
	 */
	private void loadSources(String srcPath) {

		if (srcPath == null) {

			//show file browser
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("Locate Source Directory");
			if (fileChooser.showOpenDialog(this)
				!= JFileChooser.APPROVE_OPTION)
				return;
			srcPath = fileChooser.getSelectedFile().getPath();
		}

		String dirSep = java.io.File.separator;

		//locate and set the source file for all classes
		Iterator it = classInfos.values().iterator();
		while (it.hasNext()) {

			ClassInfo cInfo = (ClassInfo) it.next();
			File srcFile =
				new File(srcPath + dirSep + cInfo.getName() + ".java");

			if (srcFile.exists())
				cInfo.setSourceFile(srcFile);
			else if (cInfo.getName().indexOf('$') < 0) {
				System.err.println("No source: " + srcFile.getPath());
				cInfo.setSourceFile(null);
			}
		}

	}

	/**
	 * Updates the coverage for this program.
	 *
	 */
	public void updateCov() {

		String[] newExecs = cov.reset();
		treeFrame.reloadExecs(newExecs);

		if (newExecs.length > 0) {

			//recalculate coverage for ALLEXECS
			Iterator classIT = classInfos.values().iterator();
			while (classIT.hasNext())
				((ClassInfo) classIT.next()).compute(
					ProgramTreeFrame.ALLEXECS,
					true);

		}
	}

	/**
	 * Method to locate and display a new internal frame.
	 *
	 * @param newFrame an InsectGUIFrame
	 * @param layer layer to add frame to
	 */
	private void displayFrame(InsectGUIFrame newFrame, Integer layer) {

		getContentPane().add(newFrame, layer);

		//center frame
		int height = newFrame.getHeight();
		int width = newFrame.getWidth();
		int midX = (getX() + getWidth() / 2);
		int midY = (getY() + getHeight() / 2);
		newFrame.setLocation(midX - width / 2, midY - height / 2);

		newFrame.setVisible(true);

		/*int minX = Integer.MAX_VALUE;
		int maxX = 0;
		int minY = Integer.MAX_VALUE;
		int maxY = 0;
		
		int height = newFrame.getHeight();
		int width = newFrame.getWidth();
		
		JInternalFrame[] frames = getAllFrames();
		
		for (int i = 0; i < frames.length; i++) {
		  
		  int x = frames[i].getX();
		  int y = frames[i].getY();
		  int h = frames[i].getHeight();
		  int w = frames[i].getWidth();
		
		  if (x < minX)
		minX = x;
		  if (x + w > maxX)
		maxX = x + w;
		
		  if (y < minY)
		minY = y;
		  if (y + h > maxY)
		maxY = y + h;
		}
		
		if (*/
	}

	/**
	 * Displays the source for the currently selected class.
	 *
	 * @param cInfo ClassInfo of class to display
	 * @param methodName name of method to scroll to
	 * @param newFrame whether a new window should be created
	 */
	public void displaySource(
		ClassInfo cInfo,
		String methodName,
		boolean newFrame) {

		SourceViewFrame targFrame = null;

		//determine frame to display source in
		if (SourceViewFrame.lastInstanceWithFocus == null)
			newFrame = true;
		else
			targFrame = SourceViewFrame.lastInstanceWithFocus;

		//create new frame if necessary
		if (newFrame) {
			targFrame = new SourceViewFrame(this);
			displayFrame(targFrame, JDesktopPane.DEFAULT_LAYER);
		}

		targFrame.display(cInfo, methodName);
	}

	/**
	 * Displays the coverage statistics for the currently
	 * selected item.
	 *
	 * @param info item to show coverage statistics for
	 * @param newFrame whether a new window should be created
	 */
	public void displayCoverage(InsectGUIInfo info, boolean newFrame) {

		CoverageFrame targFrame = null;

		//determine frame to display stats in
		if (CoverageFrame.lastInstanceWithFocus == null)
			newFrame = true;
		else
			targFrame = CoverageFrame.lastInstanceWithFocus;

		//create new frame if necessary
		if (newFrame) {
			targFrame = new CoverageFrame(this);
			displayFrame(targFrame, JDesktopPane.DEFAULT_LAYER);
		}

		targFrame.display(info);
	}

	/**
	 * Updates the contents of open source and coverage
	 * views based on the item selected.
	 *
	 * @param info an <code>InsectGUIInfo</code> value
	 */
	public void updateFrames(InsectGUIInfo info) {

		//update source window if one is open
		if ((SourceViewFrame.lastInstanceWithFocus != null)
			&& (SourceViewFrame.lastInstanceWithFocus.isVisible())) {
			if (info instanceof ClassInfo)
				displaySource((ClassInfo) info, null, false);
			else if (info instanceof MethodInfo)
				displaySource(
					((MethodInfo) info).getClassInfo(),
					((MethodInfo) info).getName(),
					false);
		}

		//update coverage window if one is open
		if ((CoverageFrame.lastInstanceWithFocus != null)
			&& (CoverageFrame.lastInstanceWithFocus.isVisible())) {
			displayCoverage(info, false);
		}
	}

	/**
	 * @return HashMap of ClassInfo -> source mappings.
	 */
	public HashMap getClassInfoMap() {
		return classInfos;
	}

	/**
	 * @return the program Coverage object.
	 */
	public JABACoverage getCoverage() {
		return cov;
	}

	/**
	 * @return the currently selected execution.
	 */
	public String getExecution() {

		if (treeFrame == null)
			return null;
		else
			return treeFrame.getExecution();
	}

	/**
	 * @return the name of the currently loaded program.
	 */
	public String getCurProgName() {
		return curProgName;
	}

	/**
	 * Set the selected item in the program tree view.
	 *
	 * @param target description of item to select
	 */
	public void setSelectedItem(String target) {
		treeFrame.setSelectedItem(target);
	}

	/**
	 * Set the selected execution by name.
	 *
	 * @param exec execution name
	 */
	public void setExecution(String exec) {
		treeFrame.setExecution(exec);
	}

	/**
	 * Handler for menu action events.
	 *
	 * @param ae an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		if (cmd.equals("Exit"))
			System.exit(0);
		else if (cmd.equals("Load Program"))
			loadProgram(null);
		else if (cmd.equals("Load Sources"))
			loadSources(null);
		else if (cmd.equals("Instrument")) {
			(new InstrumentDialog(this, rcFilePath)).setVisible(true);
		} else if (cmd.equals("Execute")) {
			(new ExecuteDialog(this, rcFilePath)).setVisible(true);
		} else if (cmd.equals("Configure")) {
			(new ConfigDialog(this)).setVisible(true);
		} else if (cmd.equals("Manage")) {
			(new ManageDialog(this)).setVisible(true);
		} else if (cmd.equals("JABA Optimizer")) {

			//confirm optimizing
			if (JOptionPane
				.showConfirmDialog(
					this,
					"Proceed performing JABA Optimization Analysis?",
					"Confirm",
					JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION) {

				// xxx JABAOptimizer jabaOpt = new JABAOptimizer();
				// jabaOpt.init(new String[]{rcFilePath});
				// jabaOpt.run();

			}
		}
	}

	/**
	 * Main to execute this gui.
	 *
	 * @param args[] command line arguments.
	 */
	public static void main(String args[]) {

		InsectGUI g = new InsectGUI();
		if (args.length > 0) {
			g.loadProgram(args[0]);
			if (args.length > 1)
				g.loadSources(args[1]);
		}
	}

} // InsectGUI
