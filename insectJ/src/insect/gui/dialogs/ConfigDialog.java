package insect.gui.dialogs;

import insect.ConfigFile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

/**
 * ConfigDialog.java
 *
 *
 * Created: Thu Mar 20 21:22:32 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ConfigDialog extends JDialog implements ActionListener {

	//gui elements
	private JTextField mainField;
	private JTextField instField;
	private JCheckBox stChk;
	private JCheckBox callChk;
	private JCheckBox extcallChk;
	private JCheckBox branchChk;
	private JCheckBox throwChk;
	private JCheckBox catchChk;
	private JCheckBox profileChk;
	private JSpinner optSpin;
	private JComboBox execNameCombo;

	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Creates a new <code>ConfigDialog</code> instance.
	 *
	 * @param parent a <code>JFrame</code> value
	 * @param rcPath JABA resource file path
	 */
	public ConfigDialog(JFrame parent) {

		super(parent, "Configure InsECT", true);
		createDialog();
		loadConfig();
	}

	/**
	 * Creates and layouts this dialog.
	 *
	 */
	private void createDialog() {

		//options panel
		JPanel labelPanel = new JPanel(new GridLayout(11, 1));
		labelPanel.add(new JLabel(" Main directory: "));
		labelPanel.add(new JLabel(" Instrumentation directory: "));
		labelPanel.add(new JLabel(" Statement Coverage: "));
		labelPanel.add(new JLabel(" Call Coverage: "));
		labelPanel.add(new JLabel(" External Call Coverage: "));
		labelPanel.add(new JLabel(" Branch Coverage: "));
		labelPanel.add(new JLabel(" Thrown Exception Coverage: "));
		labelPanel.add(new JLabel(" Caught Exception Coverage: "));
		labelPanel.add(new JLabel(" Profiling: "));
		labelPanel.add(new JLabel(" Optimization Level: "));
		labelPanel.add(new JLabel(" Execution Naming: "));

		JPanel optionsPanel = new JPanel(new GridLayout(11, 1));
		mainField = new JTextField();
		instField = new JTextField();
		stChk = new JCheckBox();
		callChk = new JCheckBox();
		extcallChk = new JCheckBox();
		branchChk = new JCheckBox();
		throwChk = new JCheckBox();
		catchChk = new JCheckBox();
		profileChk = new JCheckBox();
		optSpin = new JSpinner(new SpinnerNumberModel(0, 0, 2, 1));
		execNameCombo = new JComboBox();
		execNameCombo.addItem("timestamp");
		execNameCombo.addItem("sequence");

		optionsPanel.add(mainField);
		optionsPanel.add(instField);
		optionsPanel.add(stChk);
		optionsPanel.add(callChk);
		optionsPanel.add(extcallChk);
		optionsPanel.add(branchChk);
		optionsPanel.add(throwChk);
		optionsPanel.add(catchChk);
		optionsPanel.add(profileChk);
		optionsPanel.add(optSpin);
		optionsPanel.add(execNameCombo);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(labelPanel, BorderLayout.WEST);
		mainPanel.add(optionsPanel, BorderLayout.CENTER);
		mainPanel.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				".insect Properties",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				getFont(),
				Color.BLUE));

		//button panel
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		//combine
		Container contentPane = getContentPane();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		setSize(400, 400);
	}

	/**
	 * Loads any stored configuration information.
	 *
	 */
	private void loadConfig() {

		String cfg =
			System.getProperty("user.home") + File.separator + ".insect";
		if (!(new File(cfg)).exists())
			return;

		ConfigFile.reload();

		String prop = ConfigFile.getProperty("MAIN");
		if (prop != null)
			mainField.setText(prop);
		prop = ConfigFile.getProperty("INSTRUMENTED");
		if (prop != null)
			instField.setText(prop);
		prop = ConfigFile.getProperty("BLOCKCOVERAGE");
		if ((prop != null) && (prop.toUpperCase().equals("TRUE")))
			stChk.setSelected(true);
		prop = ConfigFile.getProperty("CALLCOVERAGE");
		if ((prop != null) && (prop.toUpperCase().equals("TRUE")))
			callChk.setSelected(true);
		prop = ConfigFile.getProperty("EXTCALLCOVERAGE");
		if ((prop != null) && (prop.toUpperCase().equals("TRUE")))
			extcallChk.setSelected(true);
		prop = ConfigFile.getProperty("BRANCHCOVERAGE");
		if ((prop != null) && (prop.toUpperCase().equals("TRUE")))
			branchChk.setSelected(true);
		prop = ConfigFile.getProperty("THROWCOVERAGE");
		if ((prop != null) && (prop.toUpperCase().equals("TRUE")))
			throwChk.setSelected(true);
		prop = ConfigFile.getProperty("CATCHCOVERAGE");
		if ((prop != null) && (prop.toUpperCase().equals("TRUE")))
			catchChk.setSelected(true);
		prop = ConfigFile.getProperty("PROFILE");
		if ((prop != null) && (prop.toUpperCase().equals("TRUE")))
			profileChk.setSelected(true);
		prop = ConfigFile.getProperty("OPTIMIZE");
		if ((prop != null) && ((prop.equals("1")) || (prop.equals("2"))))
			optSpin.setValue(new Integer(prop));
		prop = ConfigFile.getProperty("EXECID");
		if (prop != null)
			execNameCombo.setSelectedItem(prop);

	}

	/**
	 * Handles button presses.
	 *
	 * @param e an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		if (cmd.equals("OK")) {

			//check fields
			if (mainField.getText().equals("")) {
				JOptionPane.showMessageDialog(
					this,
					"Please specify the main directory in which InsECT resides",
					"Field Incomplete",
					JOptionPane.ERROR_MESSAGE);
				return;
			} else if (instField.getText().equals("")) {
				JOptionPane.showMessageDialog(
					this,
					"Please specify the directory in which to place instrumented programs",
					"Field Incomplete",
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			//create config file
			String cfgName =
				System.getProperty("user.home") + File.separator + ".insect";
			PrintWriter outFile = null;
			try {
				outFile = new PrintWriter(new FileWriter(cfgName));
			} catch (IOException ioe) {
				System.err.println("Could not create .insect file");
				return;
			}

			//write all settings
			outFile.println("MAIN = " + mainField.getText());
			outFile.println("INSTRUMENTED = " + instField.getText());
			if (stChk.isSelected())
				outFile.println("BLOCKCOVERAGE = true");
			if (callChk.isSelected())
				outFile.println("CALLCOVERAGE = true");
			if (extcallChk.isSelected())
				outFile.println("EXTCALLCOVERAGE = true");
			if (branchChk.isSelected())
				outFile.println("BRANCHCOVERAGE = true");
			if (throwChk.isSelected())
				outFile.println("THROWCOVERAGE = true");
			if (catchChk.isSelected())
				outFile.println("CATCHCOVERAGE = true");
			if (profileChk.isSelected())
				outFile.println("PROFILE = true");
			if (((Integer) optSpin.getValue()).intValue() > 0)
				outFile.println("OPTIMIZE = " + optSpin.getValue());
			outFile.println("EXECID = " + execNameCombo.getSelectedItem());
			outFile.close();

			setVisible(false);
			return;
		} else if (cmd.equals("Cancel"))
			setVisible(false);
	}

} // ConfigDialog
