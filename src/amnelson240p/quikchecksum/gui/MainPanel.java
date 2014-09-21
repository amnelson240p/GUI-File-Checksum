package amnelson240p.quikchecksum.gui;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.awt.Font;
import javax.swing.JTextArea;

public class MainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtChksum;
	private JTextField txtFileName;
	private JButton btnBrowse;
	private JButton btnVerify;
	private JTextArea txtrGeneratedhash;
	private JLabel lblVerified;
	private JLabel lblVerifyIcon;
	private JRadioButton rdbtnMD5;
	private JRadioButton rdbtnSHA1;
	private JRadioButton rdbtnSHA256;
	private JRadioButton rdbtnSHA512;
	private JFileChooser fc;

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		setPreferredSize(new Dimension(400, 250));
		setLayout(null);

		fc = new JFileChooser();

		txtChksum = new JTextField();
		txtChksum.setToolTipText("Paste checksum string here");
		txtChksum.setText("Checksum");
		txtChksum.setBounds(25, 56, 334, 19);
		add(txtChksum);
		txtChksum.setColumns(10);
		txtChksum.requestFocus();
		txtChksum.selectAll();

		JLabel lblChecksumEntry = new JLabel("Checksum string");
		lblChecksumEntry.setBounds(25, 35, 106, 15);
		add(lblChecksumEntry);

		JLabel lblFile = new JLabel("File");
		lblFile.setBounds(25, 108, 55, 15);
		add(lblFile);

		txtFileName = new JTextField();
		txtFileName.setText("Choose File");
		txtFileName.setBounds(62, 106, 207, 19);
		add(txtFileName);
		txtFileName.setColumns(10);

		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = fc.showOpenDialog(MainPanel.this);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					txtFileName.setText(fc.getSelectedFile().getAbsolutePath());
				} else {
					// Canceled selection
				}
			}
		});
		btnBrowse.setBounds(281, 103, 78, 25);
		add(btnBrowse);

		rdbtnMD5 = new JRadioButton("MD5");
		rdbtnMD5.setBounds(25, 145, 121, 23);
		add(rdbtnMD5);

		rdbtnSHA1 = new JRadioButton("SHA-1");
		rdbtnSHA1.setBounds(25, 165, 121, 23);
		add(rdbtnSHA1);

		rdbtnSHA256 = new JRadioButton("SHA-256");
		rdbtnSHA256.setBounds(25, 185, 121, 23);
		add(rdbtnSHA256);

		rdbtnSHA512 = new JRadioButton("SHA-512");
		rdbtnSHA512.setBounds(25, 205, 121, 23);
		add(rdbtnSHA512);

		btnVerify = new JButton("Verify");
		btnVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make sure file was selected
				String fileName = txtFileName.getText();
				if (!fileName.equals("Choose File")) {

					String hash = hashThisFile(fileName);
					txtrGeneratedhash.setText(hash);

					String csumInput = txtChksum.getText();
					csumInput.trim(); // make sure no leading or trailing spaces
					System.out.println("Length of checksum: " + csumInput.length());

					if (csumInput.equals(hash)) {
						lblVerified.setVisible(true);
						lblVerifyIcon.setVisible(true);
					}

				}
			}
		});
		btnVerify.setBounds(261, 210, 98, 25);
		add(btnVerify);

		lblVerified = new JLabel("Verified");
		lblVerified.setBounds(279, 189, 55, 15);
		lblVerified.setVisible(false);
		add(lblVerified);

		lblVerifyIcon = new JLabel("VerifyIcon");
		lblVerifyIcon.setBounds(198, 189, 55, 15);
		lblVerifyIcon.setVisible(false);
		add(lblVerifyIcon);
		
		txtrGeneratedhash = new JTextArea();
		txtrGeneratedhash.setEditable(false);
		txtrGeneratedhash.setFont(new Font("Dialog", Font.PLAIN, 8));
		txtrGeneratedhash.setWrapStyleWord(true);
		txtrGeneratedhash.setLineWrap(true);
		txtrGeneratedhash.setBounds(154, 149, 200, 39);
		txtrGeneratedhash.setText("");
		add(txtrGeneratedhash);

	}

	private String hashThisFile(String fname) {
		String checksum = null;
		System.out.println("reading file: " + fname);
		try {

			FileInputStream fIS = new FileInputStream(fname);
			if (rdbtnMD5.isSelected()) {
				MessageDigest mDigest = MessageDigest.getInstance("MD5");

				byte[] buffer = new byte[8192];
				int numBytesRead;
				while ((numBytesRead = fIS.read(buffer)) != -1) {
					mDigest.update(buffer, 0, numBytesRead);
				}
				byte[] hash = mDigest.digest();

				// convert to hex string
				Formatter formatter = new Formatter();
				for (byte b : hash) {
					formatter.format("%02x", b);
				}
				checksum = formatter.toString();
				System.out.println("checksum: " + checksum);
			} else if (rdbtnSHA1.isSelected()) {
				MessageDigest mDigest = MessageDigest.getInstance("SHA-1");

				byte[] buffer = new byte[8192];
				int numBytesRead;
				while ((numBytesRead = fIS.read(buffer)) != -1) {
					mDigest.update(buffer, 0, numBytesRead);
				}
				byte[] hash = mDigest.digest();

				// convert to hex string
				Formatter formatter = new Formatter();
				for (byte b : hash) {
					formatter.format("%02x", b);
				}
				checksum = formatter.toString();
				System.out.println("checksum: " + checksum);
			} else if (rdbtnSHA256.isSelected()) {
				MessageDigest mDigest = MessageDigest.getInstance("SHA-256");

				byte[] buffer = new byte[8192];
				int numBytesRead;
				while ((numBytesRead = fIS.read(buffer)) != -1) {
					mDigest.update(buffer, 0, numBytesRead);
				}
				byte[] hash = mDigest.digest();

				// convert to hex string
				Formatter formatter = new Formatter();
				for (byte b : hash) {
					formatter.format("%02x", b);
				}
				checksum = formatter.toString();
				System.out.println("checksum: " + checksum);
			} else if (rdbtnSHA512.isSelected()) {
				MessageDigest mDigest = MessageDigest.getInstance("SHA-512");

				byte[] buffer = new byte[8192];
				int numBytesRead;
				while ((numBytesRead = fIS.read(buffer)) != -1) {
					mDigest.update(buffer, 0, numBytesRead);
				}
				byte[] hash = mDigest.digest();

				// convert to hex string
				Formatter formatter = new Formatter();
				for (byte b : hash) {
					formatter.format("%02x", b);
				}
				checksum = formatter.toString();
				System.out.println("checksum: " + checksum);
			}
		}
		catch (IOException ex) {
			

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checksum;
	}
}
