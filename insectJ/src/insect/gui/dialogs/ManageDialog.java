package insect.gui.dialogs;

import insect.ConfigFile;
import insect.gui.InsectGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 * ManageDialog.java
 *
 *
 * Created: Thu Mar 20 21:22:32 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ManageDialog extends JDialog implements ActionListener {

	//reference to insect gui
	private InsectGUI parent;

	//gui elements
	private JCheckBox[] execChks;
	private JButton checkAllButton;
	private JButton clearAllButton;
	private JButton removeButton;
	private JButton doneButton;

	/**
	 * Creates a new <code>ManageDialog</code> instance.
	 *
	 * @param parent a <code>JFrame</code> value
	 * @param rcPath JABA resource file path
	 */
	public ManageDialog(JFrame parent) {

		super(parent, "Manage Executions", true);
		this.parent = (InsectGUI) parent;
		createDialog();

	}

	/**
	 * Creates and layouts this dialog.
	 *
	 */
	private void createDialog() {

		String execIDs[] = parent.getCoverage().getExecIDs();

		//checkbox panel 
		JPanel chkPanel = new JPanel(new GridLayout(execIDs.length, 1));
		execChks = new JCheckBox[execIDs.length];
		for (int i = 0; i < execChks.length; i++) {
			execChks[i] = new JCheckBox(execIDs[i]);
			chkPanel.add(execChks[i]);
		}

		JScrollPane chkScroll = new JScrollPane(chkPanel);
		chkScroll.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Executions",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				getFont(),
				Color.BLUE));

		//button panel
		JPanel buttonPanel = new JPanel(new GridLayout(7, 1));
		checkAllButton = new JButton("Check All");
		clearAllButton = new JButton("Clear All");
		removeButton = new JButton("Remove");
		doneButton = new JButton("Done");
		checkAllButton.addActionListener(this);
		clearAllButton.addActionListener(this);
		removeButton.addActionListener(this);
		doneButton.addActionListener(this);
		buttonPanel.add(checkAllButton);
		buttonPanel.add(new JPanel());
		buttonPanel.add(clearAllButton);
		buttonPanel.add(new JPanel());
		buttonPanel.add(removeButton);
		buttonPanel.add(new JPanel());
		buttonPanel.add(doneButton);

		//combine
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(chkScroll, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.EAST);

		setSize(400, 200);
	}

	/**
	 * Handles button presses.
	 *
	 * @param e an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		if (cmd.equals("Check All")) {
			for (int i = 0; i < execChks.length; i++)
				execChks[i].setSelected(true);
		} else if (cmd.equals("Clear All")) {
			for (int i = 0; i < execChks.length; i++)
				execChks[i].setSelected(false);
		} else if (cmd.equals("Remove")) {
			if (JOptionPane
				.showConfirmDialog(
					this,
					"Remove data for all selected executions?",
					"Confirm Removal",
					JOptionPane.YES_NO_OPTION)
				== JOptionPane.NO_OPTION)
				return;

			String instrDir = ConfigFile.getProperty("INSTRUMENTED");
			if (instrDir == null)
				return;

			String instrPath =
				instrDir
					+ File.separator
					+ parent.getCurProgName()
					+ File.separator;

			for (int i = 0; i < execChks.length; i++) {
				if (execChks[i].isSelected()) {
					File exec =
						new File(instrPath + execChks[i].getText() + ".xd");
					deleteDir(exec);
				}
			}

			//update coverage
			parent.updateCov();

			//rebuild gui
			setVisible(false);
			getContentPane().removeAll();
			createDialog();
			setVisible(true);

		} else if (cmd.equals("Done"))
			setVisible(false);
	}

	/**
	 * Recursively deletes all files and directories
	 * in the given directory.
	 * @param dir directory to delete.
	 */
	private void deleteDir(File dir) {

		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory())
					deleteDir(dir);
				else
					files[i].delete();
			}
		}
		dir.delete();
	}

} // ManageDialog
