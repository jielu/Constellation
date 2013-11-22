package insect.gui.frames;

import insect.gui.InsectGUI;
import insect.gui.infos.InsectGUIInfo;
import insect.gui.infos.MethodInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;

/**
 * CoverageFrame.java
 *
 *
 * Created: Tue Mar 18 16:48:54 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 * @version
 */
public class CoverageFrame extends InsectGUIFrame {

	/* For deciding which window to display too */
	public static CoverageFrame lastInstanceWithFocus;

	//progress bars
	private JProgressBar stBar;
	private JProgressBar callBar;
	private JProgressBar thrownBar;
	private JProgressBar caughtBar;

	//color settings
	private static final Color red = new Color(192, 0, 0);
	private static final Color green = new Color(0, 192, 0);
	private static final Color yellow = new Color(192, 192, 0);

	static {

		//give the progress bars white text
		Color white = new Color(255, 255, 255);
		UIManager.put("ProgressBar.selectionBackground", white);
		UIManager.put("ProgressBar.selectionForeground", white);

	}

	/**
	 * Creates a new <code>CoverageFrame</code> instance.
	 *
	 * @param parent an <code>InsectGUI</code> value
	 */
	public CoverageFrame(InsectGUI parent) {
		super(parent);
	}

	/**
	 * Creates and initializes all gui elements for this frame.
	 *
	 */
	protected void setupElements() {

		stBar = new JProgressBar();
		callBar = new JProgressBar();
		thrownBar = new JProgressBar();
		caughtBar = new JProgressBar();

		stBar.setBackground(Color.black);
		callBar.setBackground(Color.black);
		thrownBar.setBackground(Color.black);
		caughtBar.setBackground(Color.black);

		stBar.setStringPainted(true);
		callBar.setStringPainted(true);
		thrownBar.setStringPainted(true);
		caughtBar.setStringPainted(true);
	}

	/**
	 * Lays out and constructs gui.
	 *
	 */
	protected void setupFrame() {

		Color blue = new Color(0, 0, 192);
		JPanel labelPanel = new JPanel(new GridLayout(4, 1));
		JLabel label = new JLabel("  Statements  ", SwingConstants.CENTER);
		label.setForeground(blue);
		labelPanel.add(label);
		label = new JLabel("  Method Calls  ", SwingConstants.CENTER);
		label.setForeground(blue);
		labelPanel.add(label);
		label = new JLabel("  Exceptions Thrown  ", SwingConstants.CENTER);
		label.setForeground(blue);
		labelPanel.add(label);
		label = new JLabel("  Exceptions Caught  ", SwingConstants.CENTER);
		label.setForeground(blue);
		labelPanel.add(label);

		//pad bars with JPanels to make them skinny
		JPanel barPanel = new JPanel(new GridLayout(12, 1));
		barPanel.add(new JPanel());
		barPanel.add(stBar);
		barPanel.add(new JPanel());
		barPanel.add(new JPanel());
		barPanel.add(callBar);
		barPanel.add(new JPanel());
		barPanel.add(new JPanel());
		barPanel.add(thrownBar);
		barPanel.add(new JPanel());
		barPanel.add(new JPanel());
		barPanel.add(caughtBar);
		barPanel.add(new JPanel());

		JPanel covPanel = new JPanel(new BorderLayout());
		covPanel.add(labelPanel, BorderLayout.WEST);
		covPanel.add(barPanel, BorderLayout.CENTER);

		setContentPane(covPanel);

		setTitle("Coverage");
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);

	}

	/**
	 * Displays the coverage statistics for the given item.
	 *
	 * @param info an <code>InsectGUIInfo</code> value
	 */
	public void display(InsectGUIInfo info) {

		String exec = parent.getExecution();
		String name = info.getName();
		int numCalls, numSt, numThrows, numCaught;
		int covCalls, covSt, covThrows, covCaught;

		if (info instanceof MethodInfo) {
			name = ((MethodInfo) info).getClassInfo().getName() + "." + name;
		}

		setTitle(exec + " - " + info.getName());

		//calculate coverage first
		info.compute(exec, false);

		numCalls = info.numCalls;
		numSt = info.numSt;
		numThrows = info.numThrows;
		numCaught = info.numCaught;

		//display stats for each type of coverage
		if (numSt > 0) {
			covSt = info.covSt;
			float perc = 100 * ((float) covSt) / numSt;
			stBar.setValue((int) perc);
			stBar.setString(covSt + " out of " + numSt);

			if (perc < 25)
				stBar.setForeground(red);
			else if (perc < 75)
				stBar.setForeground(yellow);
			else
				stBar.setForeground(green);

		} else {
			stBar.setString("");
			stBar.setValue(0);
		}

		if (numCalls > 0) {
			covCalls = info.covCalls;
			float perc = 100 * ((float) covCalls) / numCalls;
			callBar.setValue((int) perc);
			callBar.setString(covCalls + " out of " + numCalls);

			if (perc < 25)
				callBar.setForeground(red);
			else if (perc < 75)
				callBar.setForeground(yellow);
			else
				callBar.setForeground(green);

		} else {
			callBar.setString("");
			callBar.setValue(0);
		}

		if (numThrows > 0) {
			covThrows = info.covThrows;
			float perc = 100 * ((float) covThrows) / numThrows;
			thrownBar.setValue((int) perc);
			thrownBar.setString(covThrows + " out of " + numThrows);

			if (perc < 25)
				thrownBar.setForeground(red);
			else if (perc < 75)
				thrownBar.setForeground(yellow);
			else
				thrownBar.setForeground(green);

		} else {
			thrownBar.setString("");
			thrownBar.setValue(0);
		}

		if (numCaught > 0) {
			covCaught = info.covCaught;
			float perc = 100 * ((float) covCaught) / numCaught;
			caughtBar.setValue((int) perc);
			caughtBar.setString(covCaught + " out of " + numCaught);

			if (perc < 25)
				caughtBar.setForeground(red);
			else if (perc < 75)
				caughtBar.setForeground(yellow);
			else
				caughtBar.setForeground(green);

		} else {
			caughtBar.setString("");
			caughtBar.setValue(0);
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

} // CoverageFrame
