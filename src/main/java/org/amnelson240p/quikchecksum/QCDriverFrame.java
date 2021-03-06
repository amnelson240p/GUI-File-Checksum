package org.amnelson240p.quikchecksum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.amnelson240p.quikchecksum.gui.MainPanel;;

public class QCDriverFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private MainPanel mPanel;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    UIManager
			    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		    // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    QCDriverFrame frame = new QCDriverFrame();
		    frame.setTitle("Quik Checksum");
		    frame.setResizable(false);
		    frame.pack();		    
		    frame.setVisible(true);
		    
		    System.out.println("size: " + frame.getSize());

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public QCDriverFrame() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	
	
	contentPane = new JPanel();
	contentPane.setBackground(Color.RED);
	contentPane.setLayout(new BorderLayout(0, 0));
	setContentPane(contentPane);
	mPanel = new MainPanel();
	contentPane.add(mPanel);
	
	
	System.out.println("MainPanel preferred: " + mPanel.getPreferredSize());

	
    }
    
   

}
