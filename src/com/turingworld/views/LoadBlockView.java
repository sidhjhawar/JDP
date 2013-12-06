package com.turingworld.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.turingworld.controller.BlockBuilderController;
import com.turingworld.controller.BlockBuilderControllerHelper;
import com.turingworld.model.BlockBuilderModel;

public class LoadBlockView extends JFrame {

	public LoadBlockView() {
		this.setVisible(true);
		setTitle("Load Turing World");
		setBounds(100, 100, 600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmHome = new JMenuItem("Home");
		mntmHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HomeScreen homeScreen = new HomeScreen();
				dispose();
			}
		});
		mntmHome.setIcon(new ImageIcon("image/homeIcon.png"));
		menuBar.add(mntmHome);

		final JComboBox comboBox = new JComboBox();

		File[] files = new File("jsondata/").listFiles();

		for (File file : files) {
			if (file.isFile()) {
				comboBox.addItem(file.getName());
			}
		}

		comboBox.setBounds(101, 228, 228, 20);
		getContentPane().add(comboBox);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = comboBox.getSelectedItem().toString();
				File file = new File("jsondata/" + x);
				BlockBuilderControllerHelper blockbuildercontrollerhelper = new BlockBuilderControllerHelper();
				BlockBuilderModel blockBuilderModel = new BlockBuilderModel();
				blockBuilderModel = blockbuildercontrollerhelper.importJSON(file);
				BlockBuilderView blockbuilderview = new BlockBuilderView(blockBuilderModel);
				blockbuilderview.loadpaint(blockBuilderModel);
			}

		});
		btnLoad.setBounds(339, 227, 89, 23);
		getContentPane().add(btnLoad);

	}

}
