package com.turingworld.views;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class TutorialMain extends JFrame {
	URI uri = null;
	public TutorialMain() throws URISyntaxException {
		setTitle("Tutorials Page");
		//Below try block is for the theme of the swing component which is "Nimbus" here.
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
				}
		setVisible(true);
		setBounds(500, 200, 450, 296);
		JPanel panelMain = new JPanel();
		getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setBounds(500, 200, 450, 296);
		panelMain.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		panelMain.setLayout(null);
		
		JLabel headingLabel = new JLabel("Tutorials");
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		headingLabel.setBounds(97, 11, 241, 34);
		panelMain.add(headingLabel);
		
		JButton buildBtn = new JButton("<HTML><FONT color=\"#000099\">Build and Learn</FONT></HTML>");
		buildBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buildBtn.setBounds(146, 69, 162, 34);
		buildBtn.addActionListener(new OpenUrlAction());
		panelMain.add(buildBtn);
		uri =  new URI("http://www.youtube.com/watch?v=1Nh7PnpjEcA");
		
		JButton dfaBtn = new JButton("<HTML><FONT color=\"#000099\">DFA</FONT></HTML>");
		dfaBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dfaBtn.setBounds(146, 114, 162, 34);
		dfaBtn.addActionListener(new OpenUrlAction());
		panelMain.add(dfaBtn);
		uri =  new URI("http://www.youtube.com/watch?v=QZHX1wD47rY");
		
		JButton NFABtn = new JButton("<HTML><FONT color=\"#000099\">NFA</FONT></HTML>");
		NFABtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		NFABtn.setBounds(146, 159, 162, 34);
		panelMain.add(NFABtn);
	}
	
	class OpenUrlAction implements ActionListener {
	      @Override public void actionPerformed(ActionEvent e) {
	        open(uri);
	      }
	    }
	
	 private  void open(URI uri) {
		    if (Desktop.isDesktopSupported()) {
		      try {
		        Desktop.getDesktop().browse(uri);
		      } catch (IOException e) { /* TODO: error handling */ }
		    } else { /* TODO: error handling */ }
	 }
}
