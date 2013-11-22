package insect.gui.dialogs;

import insect.coverage.instrumentation.Instrumentor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * InstrumentDialog.java
 *
 *
 * Created: Thu Mar 20 21:22:32 2003
 *
 * @author <a href="mailto:anil@resnet.gatech.edu">Anil Chawla</a>
 */
public class InstrumentDialog extends JDialog implements ActionListener {

	private String rcPath;
	private JTextField clistField;
	private JButton browseButton;
	private JButton instButton;
	private JButton cancelButton;

	/**
	 * Creates a new <code>InstrumentDialog</code> instance.
	 *
	 * @param parent a <code>JFrame</code> value
	 * @param rcPath JABA resource file path
	 */
	public InstrumentDialog(JFrame parent, String rcPath) {

		super(parent, "Instrument Program", true);
		this.rcPath = rcPath;
		createDialog();
	}

	/**
	 * Creates and layouts this dialog.
	 *
	 */
	private void createDialog() {

		//class list panel
		JPanel clistPanel = new JPanel(new BorderLayout());
		clistPanel.add(
			new JLabel("Select Optional Class List", JLabel.CENTER),
			BorderLayout.NORTH);
		clistField = new JTextField();
		clistPanel.add(clistField, BorderLayout.CENTER);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(this);
		clistPanel.add(browseButton, BorderLayout.EAST);

		//button panel
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		instButton = new JButton("Instrument");
		cancelButton = new JButton("Cancel");
		instButton.addActionListener(this);
		cancelButton.addActionListener(this);
		buttonPanel.add(instButton);
		buttonPanel.add(cancelButton);

		//combine
		Container contentPane = getContentPane();
		contentPane.add(clistPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		setSize(400, 100);
	}

	/**
	 * Handles button presses.
	 *
	 * @param e an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		if (cmd.equals("Instrument")) {
			Instrumentor i = null;

			//disable buttons
			browseButton.setEnabled(false);
			instButton.setEnabled(false);
			cancelButton.setEnabled(false);

			//if no class list selected
			if (clistField.getText().equals(""))
				i = new Instrumentor(rcPath);
			else
				i = new Instrumentor(rcPath, clistField.getText());

			i.instrument();
			setVisible(false);
		} else if (cmd.equals("Cancel"))
			setVisible(false);
		else if (cmd.equals("Browse")) {
			JFileChooser files = new JFileChooser();
			files.setFileSelectionMode(JFileChooser.FILES_ONLY);
			files.setDialogTitle("Select class list");
			files.showOpenDialog(this);
			File f = files.getSelectedFile();
			if (f != null)
				clistField.setText(f.getPath());

		}
	}

} // InstrumentDialog
