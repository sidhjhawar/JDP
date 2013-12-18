package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */

/*
 * This class is for Displaying the Home screen. 
 * users can select any of the options from the following.
 * 1. Load Existing file.
 * 2. Help
 * 3. Build DFA
 * 4. Build NFA
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.turingworld.controller.BlockBuilderController;
import com.turingworld.controller.DFABuilderController;
import com.turingworld.controller.NFABuilderController;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.DFABuilderModel;
import com.turingworld.model.NFABuilderModel;

public class HomeScreen extends JFrame {

	/**
	 * Create the frame.
	 */
	public HomeScreen() {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		setTitle("Turing World");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		getContentPane().setLayout(new BorderLayout(0, 0));

		createBuildandLearn();
		createHelp();
		createDFA();
		createNFA();
		createLoad();
		setVisible(true);

	}

	private void createLoad() {

		JButton btnNewButton = new JButton("Load Existing Files");
		btnNewButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/load.png")));
		//btnNewButton.setIcon(new ImageIcon("image/load.png"));
		btnNewButton.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
		getContentPane().add(btnNewButton, BorderLayout.SOUTH);

	}

	private void createNFA() {
		JButton nfaBtn = new JButton("Build NFA");
		nfaBtn.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
		getContentPane().add(nfaBtn, BorderLayout.EAST);

		nfaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NFABuilderModel nfaBuilderModel = new NFABuilderModel();
				NFABuilderView nfaBuilderView = new NFABuilderView(nfaBuilderModel);

				NFABuilderController nfaBuilderController = new NFABuilderController(nfaBuilderModel, nfaBuilderView);
				nfaBuilderController.setNfaBuilderView(nfaBuilderView);
				nfaBuilderView.setController(nfaBuilderController);
				dispose();
			}
		});

	}

	private void createDFA() {
		JButton dfaBtn = new JButton("Build DFA");
		dfaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DFABuilderModel dfaBuilderModel = new DFABuilderModel();
				DFABuilderView dfaBuilderView = new DFABuilderView(dfaBuilderModel);

				DFABuilderController dfaBuilderController = new DFABuilderController(dfaBuilderModel, dfaBuilderView);
				dfaBuilderController.setDfaBuilderView(dfaBuilderView);
				dfaBuilderView.setController(dfaBuilderController);
				dispose();

			}
		});

		dfaBtn.setIcon(null);
		dfaBtn.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
		getContentPane().add(dfaBtn, BorderLayout.WEST);

	}

	private void createHelp() {
		JButton helpBtn = new JButton("Help\r\n");
		helpBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/help.png")));
		//helpBtn.setIcon(new ImageIcon("image/help.png"));
		helpBtn.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
		getContentPane().add(helpBtn, BorderLayout.CENTER);
	}

	private void createBuildandLearn() {
		JButton buildBtn = new JButton("Build and Learn");
		buildBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				BlockBuilderModel blockBuilderModel = new BlockBuilderModel();
				BlockBuilderView blockBuilderView = new BlockBuilderView(blockBuilderModel);
				BlockBuilderController blockBuilderController = new BlockBuilderController(blockBuilderModel, blockBuilderView);
				blockBuilderView.setController(blockBuilderController);
				dispose();

			}
		});
		buildBtn.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
		buildBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/build.png")));
		//buildBtn.setIcon(new ImageIcon("image/build.png"));
		getContentPane().add(buildBtn, BorderLayout.NORTH);

	}
}
