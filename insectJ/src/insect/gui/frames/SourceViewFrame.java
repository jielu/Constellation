package insect.gui.frames;

import insect.gui.InsectGUI;
import insect.gui.infos.ClassInfo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * SourceViewFrame.java
 *
 *
 * Created: Mon Mar 10 22:19:43 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class SourceViewFrame
	extends InsectGUIFrame
	implements
		PropertyChangeListener,
		HyperlinkListener,
		ActionListener,
		InternalFrameListener {

	/* For deciding which window to display too */
	public static SourceViewFrame lastInstanceWithFocus;
	private static final int HISTORY_SIZE = 10;

	//gui elements
	private JEditorPane editorPane;
	private JPopupMenu navigMenu;
	private JMenuItem backMenuItem;
	private JMenuItem fwdMenuItem;
	private JMenu histMenu;
	private Vector history;
	private int histIndex;
	private String scrollToOnLoad;

	/**
	 * Creates a new <code>SourceViewFrame</code> instance.
	 *
	 * @param parent an <code>InsectGUI</code> value
	 */
	public SourceViewFrame(InsectGUI parent) {
		super(parent);
	}

	/**
	 * Creates and initializes all gui elements for this frame.
	 *
	 */
	protected void setupElements() {

		//init history
		history = new Vector(HISTORY_SIZE);
		histIndex = 0;

		//create popup menu
		navigMenu = new JPopupMenu();
		backMenuItem = new JMenuItem("Back");
		backMenuItem.setEnabled(false);
		backMenuItem.addActionListener(this);
		navigMenu.add(backMenuItem);
		fwdMenuItem = new JMenuItem("Forward");
		fwdMenuItem.setEnabled(false);
		fwdMenuItem.addActionListener(this);
		navigMenu.add(fwdMenuItem);
		histMenu = new JMenu("Go to");
		histMenu.setEnabled(false);
		navigMenu.add(histMenu);

		//initialize display editorpane
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.addPropertyChangeListener(this);
		editorPane.addHyperlinkListener(this);
		editorPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					navigMenu.show(e.getComponent(), e.getX(), e.getY());
			}

		});
		ToolTipManager.sharedInstance().registerComponent(editorPane);

		HTMLEditorKit htmlKit = new HTMLEditorKit();
		StyleSheet ss = new StyleSheet();

		//set CSS StyleSheet properties
		ss.addRule("body { line-height: 60% }");
		ss.addRule("#cst { color: white ; background-color: green }");
		ss.addRule("#ust { color: white ; background-color: red }");
		ss.addRule("#mst { color: white ; background-color: #FF8C00 }");
		ss.addRule("#ni { color: gray ; background-color: white  }");
		ss.addRule(
			"#call {color: yellow ; text-decoration: underline ; font-weight: bold}");
		ss.addRule("#xcall {color: yellow ; font-weight: bold}");
		ss.addRule("#excep {color: #00BFFF; font-weight: bold}");

		htmlKit.setStyleSheet(ss);
		editorPane.setEditorKit(htmlKit);
		editorPane.setDocument(htmlKit.createDefaultDocument());
		editorPane.setContentType("text/html");
		editorPane.setText("");

	}

	/**
	 * Lays out and constructs gui.
	 *
	 */
	protected void setupFrame() {

		//create scrollpane for editor
		JScrollPane editorScroll =
			new JScrollPane(
				editorPane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//create display panel
		JPanel displayPanel = new JPanel(new BorderLayout());
		displayPanel.setBorder(BorderFactory.createEtchedBorder());
		displayPanel.add(editorScroll, BorderLayout.CENTER);
		setContentPane(displayPanel);

		setTitle("Source View");
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setSize(400, 400);

	}

	/**
	 * Adds the selected item to the browser history.
	 *
	 * @param target description of item
	 */
	private void addToHistory(String target) {

		//maintain only HISTORY_SIZE entries
		if (history.size() >= HISTORY_SIZE)
			history.removeElementAt(HISTORY_SIZE - 1);
		history.insertElementAt(target, 0);

		//update back/fwd options
		histIndex = 0;
		fwdMenuItem.setEnabled(false);
		if (history.size() > 1)
			backMenuItem.setEnabled(true);

		//update history submenu
		histMenu.setEnabled(true);
		histMenu.removeAll();
		for (int i = 0; i < history.size(); i++) {
			JMenuItem mItem = new JMenuItem((String) history.get(i));
			mItem.addActionListener(this);
			histMenu.add(mItem);
		}
	}

	/**
	 * Displays an HTML source coverage view for 
	 * the given class.
	 *
	 * @param cInfo ClassInfo of class to display
	 * @param methodName method to scroll to
	 */
	public void display(ClassInfo cInfo, String methodName) {

		String curExec = parent.getExecution();
		URL srcView = cInfo.getSourceView(curExec);

		try {
			if (srcView != null) {

				//display source view
				if (srcView != editorPane.getPage()) {
					scrollToOnLoad = methodName;
					editorPane.setPage(srcView);
				}

				//scroll to method
				if (methodName != null) {
					editorPane.scrollToReference(methodName);
					setTitle(
						curExec + " - " + cInfo.getName() + "." + methodName);
					addToHistory(cInfo.getName() + "." + methodName);
				} else {
					setTitle(curExec + " - " + cInfo.getName());
					addToHistory(cInfo.getName());
				}
			}
		} catch (IOException ioe) {
			System.err.println("Error displaying source in window");
		}

	}

	/**
	 * Handles events involving document updates. Used to
	 * determine when the page has finished loading.
	 *
	 * @param e a <code>PropertyChangeEvent</code> value
	 */
	public void propertyChange(PropertyChangeEvent e) {

		//if page has loaded, scroll to method
		if (e.getPropertyName().equals("page")) {
			if (scrollToOnLoad != null) {
				editorPane.scrollToReference(scrollToOnLoad);
				scrollToOnLoad = null;
			}
		}
	}

	/**
	 * Handles hyperlink events.
	 *
	 * @param e a <code>HyperlinkEvent</code> value
	 */
	public void hyperlinkUpdate(HyperlinkEvent e) {

		HashMap classInfos = parent.getClassInfoMap();
		String desc = e.getDescription();
		HyperlinkEvent.EventType type = e.getEventType();

		//link clicked
		if (type == HyperlinkEvent.EventType.ACTIVATED) {

			//do not interpret # links
			if (!desc.startsWith("##")) {
				String target = desc.substring(0, desc.indexOf('='));
				gotoTarget(target);
				addToHistory(target);
			}
		} else if (type == HyperlinkEvent.EventType.ENTERED) {
			int index = desc.indexOf('=');
			if (index > 0)
				editorPane.setToolTipText(desc.substring(index + 1));
		} else if (type == HyperlinkEvent.EventType.EXITED) {
			editorPane.setToolTipText(null);
		}
	}

	/**
	 * Displays the specified target item.
	 *
	 * @param target description of item
	 */
	private void gotoTarget(String target) {

		HashMap classInfos = parent.getClassInfoMap();
		int split = target.indexOf('.');
		URL srcView = null;

		//get source view of class
		if (split < 0) {
			srcView =
				((ClassInfo) classInfos.get(target)).getSourceView(
					parent.getExecution());
		} else
			srcView =
				(
					(ClassInfo) classInfos.get(
						target.substring(0, split))).getSourceView(
					parent.getExecution());

		try {
			if (srcView != null) {

				//display page
				if (srcView != editorPane.getPage()) {
					if (split >= 0)
						scrollToOnLoad = target.substring(split + 1);
					editorPane.setPage(srcView);
				}
				//scroll to method
				else if (split >= 0)
					editorPane.scrollToReference(target.substring(split + 1));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		}

		setTitle(parent.getExecution() + " - " + target);
		parent.setSelectedItem(target);

	}

	/**
	 * Handles menu selections. Used to navigate
	 * view history.
	 *
	 * @param e an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		//browse back
		if (cmd.equals("Back")) {
			histIndex++;
			fwdMenuItem.setEnabled(true);
			if (histIndex == history.size() - 1)
				backMenuItem.setEnabled(false);
			gotoTarget((String) history.get(histIndex));
		}
		//browse forward
		else if (cmd.equals("Forward")) {
			histIndex--;
			backMenuItem.setEnabled(true);
			if (histIndex == 0)
				fwdMenuItem.setEnabled(false);
			gotoTarget((String) history.get(histIndex));
		}
		//browse selected item in history
		else {
			Object o = e.getSource();
			Component[] items = histMenu.getMenuComponents();
			for (int i = 0; i < items.length; i++) {
				if (items[i] == o)
					histIndex = i;
			}
			if (histIndex > 0)
				fwdMenuItem.setEnabled(true);
			else
				fwdMenuItem.setEnabled(false);
			if (histIndex < history.size() - 1)
				backMenuItem.setEnabled(true);
			else
				backMenuItem.setEnabled(false);

			gotoTarget(cmd);
		}
	}

	/**
	 * Updates the program tree selection with the item
	 * being displayed.
	 *
	 * @param e an <code>InternalFrameEvent</code> value
	 */
	public void gotFocus(InternalFrameEvent e) {
		lastInstanceWithFocus = this;

		String title = getTitle();
		int hyphen = title.indexOf('-');
		if (hyphen > 0) {
			String exec = title.substring(0, hyphen - 1);
			String target = title.substring(hyphen + 2);
			parent.setExecution(exec);
			parent.setSelectedItem(target);
		}
	}

} // SourceViewFrame
