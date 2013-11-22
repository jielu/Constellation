package insect.gui.frames;

import insect.coverage.analysis.jaba.JABACoverage;
import insect.gui.InsectGUI;
import insect.gui.infos.ClassInfo;
import insect.gui.infos.InsectGUIInfo;
import insect.gui.infos.MethodInfo;
import insect.gui.infos.PackageInfo;
import jaba.sym.Program;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.JTree.DynamicUtilTreeNode;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * ProgramTreeFrame.java
 *
 *
 * Created: Sun Mar 09 22:59:40 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */
public class ProgramTreeFrame
	extends InsectGUIFrame
	implements TreeSelectionListener, ItemListener, ActionListener {

	public static final String ALLEXECS = "- All Executions -";

	private Program prog;

	//gui elements
	private JTree programTree;
	private JComboBox execComboBox;
	private JPopupMenu treeMenu;
	private JMenuItem viewSrcItem;
	private JMenuItem viewSrcWinItem;
	private InsectGUIInfo popupSelection;

	/**
	 * Creates a new <code>ProgramTreeFrame</code> instance.
	 *
	 * @param parent an <code>InsectGUI</code> value
	 * @param prog a JABA program object
	 */
	public ProgramTreeFrame(InsectGUI parent, Program prog) {
		this.prog = prog;
		this.parent = parent;
		setupElements();
		setupFrame();
	}

	/**
	 * Creates and initializes all gui elements for this frame.
	 *
	 */
	protected void setupElements() {

		jaba.sym.Class[] classes = prog.getClasses();
		jaba.sym.Package[] pkgs = prog.getPackages();
		ArrayList rootItems = new ArrayList();

		//determine all root packages and classes
		for (int i = 0; i < pkgs.length; i++) {
			if (pkgs[i].getName().indexOf('/') < 0)
				rootItems.add(pkgs[i]);
		}
		for (int i = 0; i < classes.length; i++) {
			if (classes[i].getName().indexOf('/') < 0)
				rootItems.add(classes[i]);
		}

		JABACoverage cov = parent.getCoverage();

		//build menu
		treeMenu = new JPopupMenu();
		JMenuItem covItem = new JMenuItem("View coverage");
		covItem.addActionListener(this);
		treeMenu.add(covItem);
		covItem = new JMenuItem("View coverage in new window");
		covItem.addActionListener(this);
		treeMenu.add(covItem);
		treeMenu.add(new JSeparator());
		viewSrcItem = new JMenuItem("View source");
		viewSrcItem.addActionListener(this);
		treeMenu.add(viewSrcItem);
		viewSrcWinItem = new JMenuItem("View source in new window");
		viewSrcWinItem.addActionListener(this);
		treeMenu.add(viewSrcWinItem);

		//build program JTree
		programTree =
			new JTree(buildProgramTree(prog.getName(), rootItems, cov));
		programTree.addTreeSelectionListener(this);
		programTree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					showPopup(e.getX(), e.getY());
			}
		});

		//build execution JComboBox
		/*Vector execs = cov.getExecDirs(new File(ConfigFile.getProperty("INSTRUMENTED")+
						    File.separator+prog.getName()),"");*/
		String execs[] = cov.getExecIDs();
		execComboBox = new JComboBox();
		execComboBox.addItemListener(this);

		if (execs.length > 0) {
			execComboBox.addItem(ALLEXECS);
			for (int i = 0; i < execs.length; i++)
				execComboBox.addItem(execs[i]);
			execComboBox.setEnabled(true);
		} else
			execComboBox.setEnabled(false);

	}

	/**
	 * Lays out and constructs gui.
	 *
	 */
	protected void setupFrame() {

		//put tree in a scrollpane
		JScrollPane treeScroll =
			new JScrollPane(
				programTree,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//create panel for execution combo box
		JPanel execPanel = new JPanel(new BorderLayout());
		execPanel.setBorder(BorderFactory.createEtchedBorder());
		execPanel.add(
			new JLabel("Select Execution", JLabel.CENTER),
			BorderLayout.NORTH);
		execPanel.add(execComboBox, BorderLayout.CENTER);

		//create select panel
		JPanel selectPanel = new JPanel(new BorderLayout());
		selectPanel.setPreferredSize(new Dimension(200, 400));
		selectPanel.setBorder(BorderFactory.createEtchedBorder());
		selectPanel.add(treeScroll, BorderLayout.CENTER);
		selectPanel.add(execPanel, BorderLayout.SOUTH);

		//set this internal frame's properties
		setContentPane(selectPanel);

		setTitle("Program View");
		setClosable(false);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		pack();

	}

	/**
	 * Builds the tree structure and initialize all 
	 * elements of a program given the root packages
	 * and classes.
	 *
	 * @param progName a <code>String</code> value
	 * @param rootItems root packages and classes
	 * @param cov InsECT coverage object
	 * @return root of the tree
	 */
	private DefaultMutableTreeNode buildProgramTree(
		String progName,
		ArrayList rootItems,
		JABACoverage cov) {

		//the root node is a package representing the program
		PackageInfo progPkg = new PackageInfo(progName, null, null, cov);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(progPkg);

		ArrayList p = new ArrayList();
		ArrayList c = new ArrayList();

		//build tree
		int size = rootItems.size();
		for (int i = 0; i < size; i++) {

			Object o = rootItems.get(i);
			DefaultMutableTreeNode curItem = null;

			//add package branch
			if (o instanceof jaba.sym.Package) {
				PackageInfo pInfo = new PackageInfo((jaba.sym.Package) o, cov);
				p.add(pInfo);
				curItem = buildPackageTree(pInfo);
			}
			//add class branch
			else if (o instanceof jaba.sym.Class) {
				ClassInfo cInfo = new ClassInfo((jaba.sym.Class) o, cov);
				c.add(cInfo);
				curItem = buildClassTree(cInfo);
			}

			top.add(curItem);
		}

		//set items in the package representing the program
		progPkg.setPackageInfos(
			(PackageInfo[]) p.toArray(new PackageInfo[p.size()]));
		progPkg.setClassInfos((ClassInfo[]) c.toArray(new ClassInfo[c.size()]));

		return top;
	}

	/**
	 * Builds the branch of the tree representing a class.
	 *
	 * @param cInfo ClassInfo of the class
	 * @return root of the branch
	 */
	private DefaultMutableTreeNode buildClassTree(ClassInfo cInfo) {

		parent.getClassInfoMap().put(cInfo.getName(), cInfo);
		return new DynamicUtilTreeNode(cInfo, cInfo.getMethodInfos());

	}

	/**
	 * Builds the branch of the tree representing a package.
	 *
	 * @param pInfo PackageInfo of the package
	 * @return root of the branch
	 */
	private DefaultMutableTreeNode buildPackageTree(PackageInfo pInfo) {

		DefaultMutableTreeNode pNode = new DefaultMutableTreeNode(pInfo);
		PackageInfo[] subpkgs = pInfo.getPackageInfos();
		ClassInfo[] classes = pInfo.getClassInfos();

		//build branches for subpackages and classes
		for (int i = 0; i < subpkgs.length; i++)
			pNode.add(buildPackageTree(subpkgs[i]));

		for (int i = 0; i < classes.length; i++)
			pNode.add(buildClassTree(classes[i]));

		return pNode;

	}

	/**
	 * Displays the popup menu.
	 *
	 * @param x mouse coordinate
	 * @param y mouse coordinate
	 */
	protected void showPopup(int x, int y) {

		TreePath clickedPath = programTree.getPathForLocation(x, y);
		if (clickedPath != null) {

			//get selected object
			programTree.removeTreeSelectionListener(this);
			programTree.setSelectionPath(clickedPath);
			programTree.addTreeSelectionListener(this);
			popupSelection =
				(InsectGUIInfo) ((DefaultMutableTreeNode) clickedPath
					.getLastPathComponent())
					.getUserObject();

			//enable options appropriately
			if ((popupSelection instanceof ClassInfo)
				|| (popupSelection instanceof MethodInfo)) {
				viewSrcItem.setEnabled(true);
				viewSrcWinItem.setEnabled(true);
			} else {
				viewSrcItem.setEnabled(false);
				viewSrcWinItem.setEnabled(false);
			}

			treeMenu.show(programTree, x, y);
		}

	}

	/**
	 * Handles a change in selection in the program tree.
	 *
	 * @param tse a <code>TreeSelectionEvent</code> value
	 */
	public void valueChanged(TreeSelectionEvent tse) {

		if (programTree.getSelectionPath() == null)
			return;

		Object src =
			((DefaultMutableTreeNode) tse.getPath().getLastPathComponent())
				.getUserObject();

		parent.updateFrames((InsectGUIInfo) src);
	}

	/**
	 * Handles a change in selection in the select
	 * execution box.
	 *
	 * @param e an <code>ItemEvent</code> value
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED)
			programTree.clearSelection();
	}

	/**
	 * Handles menu selections.
	 *
	 * @param e an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		//coverage options
		if (cmd.startsWith("View cov")) {

			boolean newWin = false;
			if (cmd.endsWith("window"))
				newWin = true;

			parent.displayCoverage(popupSelection, newWin);

		}
		//source view options
		else if (cmd.startsWith("View source")) {

			boolean newWin = false;
			if (cmd.endsWith("window"))
				newWin = true;

			if (popupSelection instanceof ClassInfo)
				parent.displaySource((ClassInfo) popupSelection, null, newWin);
			else if (popupSelection instanceof MethodInfo)
				parent.displaySource(
					((MethodInfo) popupSelection).getClassInfo(),
					((MethodInfo) popupSelection).getName(),
					newWin);
		}
	}

	/**
	 * @return the currently selected execution.
	 */
	public String getExecution() {
		return (String) execComboBox.getSelectedItem();
	}

	/**
	 * Selects an execution by name.
	 *
	 * @param exec a <code>String</code> value
	 */
	public void setExecution(String exec) {
		execComboBox.setSelectedItem(exec);
	}

	/**
	 * Reloads the list of executions.
	 *
	 * @param exec a <code>String</code> value
	 */
	public void reloadExecs(String[] execs) {

		execComboBox.setEnabled(false);
		execComboBox.removeAllItems();

		if (execs.length > 0) {
			execComboBox.addItem("- All Executions -");
			execComboBox.setEnabled(true);

			for (int i = 0; i < execs.length; i++)
				execComboBox.addItem(execs[i]);
		}
	}

	/**
	 * Selects a program tree item by description.
	 *
	 * @param target description of item.
	 */
	public void setSelectedItem(String target) {

		programTree.removeTreeSelectionListener(this);

		int split = target.indexOf('.');
		String c;

		//determine class
		if (split < 0)
			c = target;
		else
			c = target.substring(0, split);

		TreePath cPath = null;
		int cRow = 1;
		int slash;

		//expand all packages of class
		while ((slash = c.indexOf('/')) > 0) {
			cPath =
				programTree.getNextMatch(
					c.substring(0, slash),
					cRow,
					Position.Bias.Forward);
			cRow = programTree.getRowForPath(cPath);
			programTree.expandRow(cRow);
			c = c.substring(slash + 1);
		}

		//expand class
		cPath = programTree.getNextMatch(c, cRow, Position.Bias.Forward);
		cRow = programTree.getRowForPath(cPath);
		programTree.expandRow(cRow);

		//select class or method
		if (split < 0)
			programTree.setSelectionPath(cPath);
		else {
			String m = target.substring(split + 1);
			TreePath mPath =
				programTree.getNextMatch(m, cRow, Position.Bias.Forward);
			programTree.setSelectionPath(mPath);
		}

		programTree.addTreeSelectionListener(this);
	}

} // ProgramTreeFrame
