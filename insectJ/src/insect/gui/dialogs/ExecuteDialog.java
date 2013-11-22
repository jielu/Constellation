package insect.gui.dialogs;

import insect.coverage.execution.Executor;
import insect.gui.InsectGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ExecuteDialog.java
 *
 *
 * Created: Thu Mar 20 21:22:32 2003
 *
 * @author <a href="mailto:anil@cc.gatech.edu">Anil Chawla</a>
 */
public class ExecuteDialog
	extends JDialog
	implements ActionListener, ChangeListener {

	//program info
	private InsectGUI parent;
	private String rcPath;

	//gui elements
	private JTextField idField;
	private JTextField cpField;
	private JTextField mainField;
	private JTextField argsField;
	private JTextField manField;

	private JRadioButton execOption;
	private JRadioButton manOption;

	private JButton browseButton;
	private JButton execButton;
	private JButton cancelButton;

	/**
	 * Creates a new <code>ExecuteDialog</code> instance.
	 *
	 * @param parent a <code>InsectGUI</code> value
	 * @param rcPath JABA resource file path
	 */
	public ExecuteDialog(JFrame parent, String rcPath) {

		super(parent, "Execute Program", true);
		this.rcPath = rcPath;
		this.parent = (InsectGUI) parent;
		createDialog();
	}

	/**
	 * Creates and layouts this dialog.
	 *
	 */
	private void createDialog() {

		//create selection buttons
		ButtonGroup options = new ButtonGroup();
		execOption = new JRadioButton();
		manOption = new JRadioButton();
		options.add(execOption);
		options.add(manOption);
		execOption.setSelected(true);
		execOption.addChangeListener(this);
		manOption.addChangeListener(this);

		//executor panel
		JPanel labelPanel = new JPanel(new GridLayout(4, 1));
		labelPanel.add(new JLabel(" Execution Identifier: "));
		labelPanel.add(new JLabel(" Class Paths: "));
		labelPanel.add(new JLabel(" Main Class: "));
		labelPanel.add(new JLabel(" Command-line Arguments: "));

		JPanel fieldPanel = new JPanel(new GridLayout(4, 1));
		idField = new JTextField();
		cpField = new JTextField();
		mainField = new JTextField();
		argsField = new JTextField();
		fieldPanel.add(idField);
		fieldPanel.add(cpField);
		fieldPanel.add(mainField);
		fieldPanel.add(argsField);

		JPanel execSubPanel = new JPanel(new BorderLayout());
		execSubPanel.add(labelPanel, BorderLayout.WEST);
		execSubPanel.add(fieldPanel, BorderLayout.CENTER);

		JPanel execPanel = new JPanel(new BorderLayout());
		execPanel.add(execOption, BorderLayout.WEST);
		execPanel.add(execSubPanel, BorderLayout.CENTER);

		//give it a titled border
		execPanel.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"InsECT Executor",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				getFont(),
				Color.BLUE));

		//manual exec panel
		JPanel manPanel = new JPanel(new BorderLayout());
		manField = new JTextField();
		manField.setEnabled(false);
		manPanel.add(manField, BorderLayout.CENTER);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(this);
		browseButton.setEnabled(false);
		manPanel.add(browseButton, BorderLayout.EAST);
		manPanel.add(manOption, BorderLayout.WEST);
		manPanel.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Manual Execution",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				getFont(),
				Color.BLUE));

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(execPanel, BorderLayout.CENTER);
		mainPanel.add(manPanel, BorderLayout.SOUTH);

		//button panel
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		execButton = new JButton("Execute");
		cancelButton = new JButton("Cancel");
		execButton.addActionListener(this);
		cancelButton.addActionListener(this);
		buttonPanel.add(execButton);
		buttonPanel.add(cancelButton);

		//combine
		Container contentPane = getContentPane();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		setSize(500, 250);
	}

	/**
	 * Handles selection of radio buttons.
	 *
	 * @param e a <code>ChangeEvent</code> value
	 */
	public void stateChanged(ChangeEvent e) {

		if (manOption.isSelected()) {
			idField.setEnabled(false);
			cpField.setEnabled(false);
			mainField.setEnabled(false);
			argsField.setEnabled(false);
			manField.setEnabled(true);
			browseButton.setEnabled(true);
		} else {
			idField.setEnabled(true);
			cpField.setEnabled(true);
			mainField.setEnabled(true);
			argsField.setEnabled(true);
			manField.setEnabled(false);
			browseButton.setEnabled(false);
		}
	}

	/**
	 * Handles button presses.
	 *
	 * @param e an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		if (cmd.equals("Execute")) {

			//disable buttons
			browseButton.setEnabled(false);
			execButton.setEnabled(false);
			cancelButton.setEnabled(false);

			//if using insect executor
			if (execOption.isSelected()) {

				if (idField.getText().equals("")) {
					JOptionPane.showMessageDialog(
						this,
						"Please specify an execution identifier",
						"Field Incomplete",
						JOptionPane.ERROR_MESSAGE);
					browseButton.setEnabled(true);
					execButton.setEnabled(true);
					cancelButton.setEnabled(true);
					return;
				} else if (mainField.getText().equals("")) {
					JOptionPane.showMessageDialog(
						this,
						"Please specify the name of the main class",
						"Field Incomplete",
						JOptionPane.ERROR_MESSAGE);
					browseButton.setEnabled(true);
					execButton.setEnabled(true);
					cancelButton.setEnabled(true);
					return;
				}

				//store classpaths as array
				String cps[] = null;
				if (!cpField.getText().equals("")) {
					StringTokenizer st =
						new StringTokenizer(cpField.getText(), " ;:");
					cps = new String[st.countTokens()];
					int i = 0;
					while (st.hasMoreTokens())
						cps[i++] = st.nextToken();
				}

				//store arguments as array
				String args[] = null;
				if (!argsField.getText().equals("")) {
					StringTokenizer st =
						new StringTokenizer(argsField.getText());
					args = new String[st.countTokens()];
					int i = 0;
					while (st.hasMoreTokens())
						args[i++] = st.nextToken();
				}

				Executor x = new Executor(rcPath);
				x.execute(idField.getText(), cps, mainField.getText(), args);
			}

			//otherwise manually execute
			else {
				if (manField.getText().equals("")) {
					JOptionPane.showMessageDialog(
						this,
						"Please specify a command to execute",
						"Field Incomplete",
						JOptionPane.ERROR_MESSAGE);
					browseButton.setEnabled(true);
					execButton.setEnabled(true);
					cancelButton.setEnabled(true);
					return;
				}

				//attempt to execute in new process
				try {
					Process p = Runtime.getRuntime().exec(manField.getText());
					p.waitFor();
				} catch (Exception ex) {
					System.err.println("Exception while executing");
				}
			}

			parent.updateCov();
			setVisible(false);
		} else if (cmd.equals("Cancel"))
			setVisible(false);

		else if (cmd.equals("Browse")) {
			JFileChooser files = new JFileChooser();
			files.setFileSelectionMode(JFileChooser.FILES_ONLY);
			files.setDialogTitle("Select file to execute");
			files.showOpenDialog(this);
			File f = files.getSelectedFile();
			if (f != null)
				manField.setText(f.getPath());
		}
	}

} // ExecuteDialog
