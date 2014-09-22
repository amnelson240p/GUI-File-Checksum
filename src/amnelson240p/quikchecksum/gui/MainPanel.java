package amnelson240p.quikchecksum.gui;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ProgressMonitorInputStream;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.awt.Color;

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
	private JButton btnClear;
	private JFileChooser fc;
	private ImageIcon backIcon;
	private boolean isFileSelected;
	private boolean noFileError;
	private boolean badChkString;
	private final String NO_FILE_ERROR = "Please select a file to verify.";
	private final String BAD_STRING_ERROR = "Invalid Checksum String";

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(400, 300));
		setLayout(null);

		// load image
		backIcon = new ImageIcon(this.getClass().getResource(
				"/amnelson240p/quikchecksum/gui/images/QuikChksumBG.png"));

		fc = new JFileChooser();
		isFileSelected = false;
		noFileError = false;
		badChkString = false;

		txtChksum = new JTextField();
		txtChksum.setToolTipText("Paste checksum string here");
		txtChksum.setText("Checksum");
		txtChksum.setBounds(25, 56, 334, 25);
		add(txtChksum);
		txtChksum.setColumns(10);
		txtChksum.requestFocus();
		txtChksum.selectAll();

		JLabel lblChecksumEntry = new JLabel("Checksum string");
		lblChecksumEntry.setBounds(30, 42, 106, 15);
		add(lblChecksumEntry);

		JLabel lblFile = new JLabel("File");
		lblFile.setBounds(30, 90, 55, 15);
		add(lblFile);

		txtFileName = new JTextField();
		txtFileName.setText("Choose File");
		txtFileName.setBounds(25, 104, 270, 25);
		add(txtFileName);
		txtFileName.setColumns(10);

		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = fc.showOpenDialog(MainPanel.this);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					txtFileName.setText(fc.getSelectedFile().getAbsolutePath());
					isFileSelected = true;
					repaint();
				} else {
					// Canceled selection
					txtFileName.setText("No File Selected");
				}
			}
		});
		btnBrowse.setBounds(310, 103, 78, 25);
		add(btnBrowse);

		rdbtnMD5 = new JRadioButton("MD5");
		rdbtnMD5.setBounds(20, 178, 85, 23);
		add(rdbtnMD5);
		rdbtnMD5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String csumInput = txtChksum.getText();
				csumInput.trim();
				// MD5 has 32 char length hex string
				if (csumInput.length() != 32)  {
					badChkString = true; // error flag
					repaint();	// make sure error is displayed
				}
				else {
					badChkString = false;
					repaint();
				}
				
			}
			
		});

		rdbtnSHA1 = new JRadioButton("SHA-1");
		rdbtnSHA1.setBounds(20, 198, 85, 23);
		add(rdbtnSHA1);
		rdbtnSHA1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String csumInput = txtChksum.getText();
				csumInput.trim();
				// SHA-1 has 40 char length hex string
				if (csumInput.length() != 40)  {
					badChkString = true; // error flag
					repaint();	// make sure error is displayed
				}
				else {
					badChkString = false;
					repaint();
				}
			}
			
		});

		rdbtnSHA256 = new JRadioButton("SHA-256");
		rdbtnSHA256.setBounds(20, 218, 85, 23);
		add(rdbtnSHA256);
		rdbtnSHA256.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String csumInput = txtChksum.getText();
				csumInput.trim();
				// SHA256 has 64 char length hex string
				if (csumInput.length() != 64)  {
					badChkString = true; // error flag
					repaint();	// make sure error is displayed
				}
				else {
					badChkString = false;
					repaint();
				}
				
			}
			
		});

		rdbtnSHA512 = new JRadioButton("SHA-512");
		rdbtnSHA512.setBounds(20, 238, 85, 23);
		add(rdbtnSHA512);
		rdbtnSHA512.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String csumInput = txtChksum.getText();
				csumInput.trim();
				// SHA512 has 128 char length hex string
				if (csumInput.length() != 128)  {
					badChkString = true; // error flag
					repaint();	// make sure error is displayed
				}
				else {
					badChkString = false;
					repaint();
				}
			}
			
		});

		// group the radio buttons
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnMD5);
		btnGroup.add(rdbtnSHA1);
		btnGroup.add(rdbtnSHA256);
		btnGroup.add(rdbtnSHA512);

		btnVerify = new JButton("Verify");
		btnVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make sure file was selected
				final String fileName = txtFileName.getText();
				if (isFileSelected && !badChkString) {
					new Thread() {
						public void run() {

							String hash = hashThisFile(fileName);
							txtrGeneratedhash.setText(hash);

							String csumInput = txtChksum.getText();
							csumInput.trim(); // make sure no leading or
							// trailing spaces
							System.out.println("Length of checksum: "
									+ csumInput.length());

							if (csumInput.equals(hash)) {
								lblVerified.setVisible(true);
								lblVerifyIcon.setVisible(true);
								btnClear.setVisible(true);
								btnVerify.setVisible(false);
							} else {
								// fail
								lblVerified.setText("Failed");
								lblVerifyIcon.setIcon(new ImageIcon(
										MainPanel.class
												.getResource("/amnelson240p/quikchecksum/gui/images/Fail.png")));

								lblVerified.setVisible(true);
								lblVerifyIcon.setVisible(true);
								btnClear.setVisible(true);
								btnVerify.setVisible(false);
							}
						}
					}.start();

				} // user picked a file
				else if (!isFileSelected) {
					// no file selected
					// notify user to select file - message
					noFileError = true;
					System.out.println("user did not select a file");
					repaint();
				} else {
					repaint();
				}
			}
		});
		btnVerify.setBounds(296, 269, 98, 25);
		add(btnVerify);

		lblVerified = new JLabel("Verified");
		lblVerified.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerified.setBounds(320, 235, 55, 15);
		lblVerified.setVisible(false);
		add(lblVerified);

		lblVerifyIcon = new JLabel("VerifyIcon");
		lblVerifyIcon
				.setIcon(new ImageIcon(
						MainPanel.class
								.getResource("/amnelson240p/quikchecksum/gui/images/Confirm.png")));
		lblVerifyIcon.setBounds(322, 180, 50, 50);
		lblVerifyIcon.setVisible(false);
		add(lblVerifyIcon);

		txtrGeneratedhash = new JTextArea();
		txtrGeneratedhash.setEditable(false);
		txtrGeneratedhash.setFont(new Font("Dialog", Font.PLAIN, 10));
		txtrGeneratedhash.setWrapStyleWord(true);
		txtrGeneratedhash.setLineWrap(true);
		txtrGeneratedhash.setBounds(117, 178, 191, 79);
		txtrGeneratedhash.setText("");
		add(txtrGeneratedhash);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblVerifyIcon.setIcon(new ImageIcon(
						MainPanel.class
								.getResource("/amnelson240p/quikchecksum/gui/images/Confirm.png")));
				lblVerifyIcon.setVisible(false);
				lblVerified.setText("Verified");
				lblVerified.setVisible(false);
				txtrGeneratedhash.setText("");
				txtFileName.setText("File");
				txtChksum.setText("Checksum");
				txtChksum.requestFocus();
				txtChksum.selectAll();
				btnVerify.setVisible(true);
				btnClear.setVisible(false);
				isFileSelected = false;

			}
		});
		btnClear.setVisible(false);
		btnClear.setBounds(172, 269, 95, 25);
		add(btnClear);

	}

	private String hashThisFile(String fname) {
		String checksum = null;
		System.out.println("reading file: " + fname);

		try {

			FileInputStream fIS = new FileInputStream(fname);
			ProgressMonitorInputStream pm = new ProgressMonitorInputStream(
					this, "Reading File", fIS);
			if (rdbtnMD5.isSelected()) {
				MessageDigest mDigest = MessageDigest.getInstance("MD5");

				byte[] buffer = new byte[8192];
				int numBytesRead;
				/*
				 * while ((numBytesRead = fIS.read(buffer)) != -1) {
				 * mDigest.update(buffer, 0, numBytesRead); }
				 */
				while ((numBytesRead = pm.read(buffer)) != -1) {
					mDigest.update(buffer, 0, numBytesRead);
				}
				byte[] hash = mDigest.digest();

				// convert to hex string
				@SuppressWarnings("resource")
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
				@SuppressWarnings("resource")
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
				/*
				 * while ((numBytesRead = fIS.read(buffer)) != -1) {
				 * mDigest.update(buffer, 0, numBytesRead); }
				 */
				while ((numBytesRead = pm.read(buffer)) != -1) {
					mDigest.update(buffer, 0, numBytesRead);
				}
				byte[] hash = mDigest.digest();

				// convert to hex string
				@SuppressWarnings("resource")
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
				/*
				 * while ((numBytesRead = fIS.read(buffer)) != -1) {
				 * mDigest.update(buffer, 0, numBytesRead); }
				 */
				while ((numBytesRead = pm.read(buffer)) != -1) {
					mDigest.update(buffer, 0, numBytesRead);
				}
				byte[] hash = mDigest.digest();

				// convert to hex string
				@SuppressWarnings("resource")
				Formatter formatter = new Formatter();
				for (byte b : hash) {
					formatter.format("%02x", b);
				}
				checksum = formatter.toString();
				System.out.println("checksum: " + checksum);
			}
			fIS.close();
			pm.close();
		} catch (IOException ex) {

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return checksum;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(backIcon.getImage(), 0, -40, null);

		if (noFileError) {
			
			g.drawString(NO_FILE_ERROR, 100, 100);
			noFileError = false;
		}
		
		if (badChkString) {
			g.drawString(BAD_STRING_ERROR, 140, 50);
			//badChkString = false;
		}
		

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

}
