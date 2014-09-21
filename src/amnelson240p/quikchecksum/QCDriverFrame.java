package amnelson240p.quikchecksum;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import amnelson240p.quikchecksum.gui.MainPanel;

public class QCDriverFrame extends JFrame {

	private JPanel contentPane;
	private MainPanel mPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QCDriverFrame frame = new QCDriverFrame();
					frame.setVisible(true);
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		mPanel = new MainPanel();
		contentPane.add(mPanel);
		
		pack();
	}

}