package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.turingworld.model.Block;

public class ViewHelper extends JFrame {

	private JLabel noticeLabel;
	private JScrollPane recentActivityScroll;
	private JScrollPane outputScroll;

	private JPanel outputPanel;
	private JPanel output;
	private JPanel recentActivityPanel;
	private JPanel snapShotPanel;
	private JPanel contentPane;
	private JPanel actionPanel;
	private JPanel panelActivity;
	private JLabel outputLabel;

	public JLabel getNoticeLabel() {
		return noticeLabel;
	}

	public void setNoticeLabel(JLabel noticeLabel) {
		this.noticeLabel = noticeLabel;
	}

	public JScrollPane getRecentActivityScroll() {
		return recentActivityScroll;
	}

	public void setRecentActivityScroll(JScrollPane recentActivityScroll) {
		this.recentActivityScroll = recentActivityScroll;
	}

	public JScrollPane getOutputScroll() {
		return outputScroll;
	}

	public void setOutputScroll(JScrollPane outputScroll) {
		this.outputScroll = outputScroll;
	}

	public JPanel getOutputPanel() {
		return outputPanel;
	}

	public void setOutputPanel(JPanel outputPanel) {
		this.outputPanel = outputPanel;
	}

	public JPanel getOutput() {
		return output;
	}

	public void setOutput(JPanel output) {
		this.output = output;
	}

	public JPanel getRecentActivityPanel() {
		return recentActivityPanel;
	}

	public void setRecentActivityPanel(JPanel recentActivityPanel) {
		this.recentActivityPanel = recentActivityPanel;
	}

	public JPanel getSnapShotPanel() {
		return snapShotPanel;
	}

	public void setSnapShotPanel(JPanel snapShotPanel) {
		this.snapShotPanel = snapShotPanel;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JPanel getActionPanel() {
		return actionPanel;
	}

	public void setActionPanel(JPanel actionPanel) {
		this.actionPanel = actionPanel;
	}

	public JPanel getPanelActivity() {
		return panelActivity;
	}

	public void setPanelActivity(JPanel panelActivity) {
		this.panelActivity = panelActivity;
	}

	public void setContentPane() {
		// method to create the view
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(null);
		contentPane.setLayout(null);
	}

	public void createWindow(JPanel contentPane) {

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

		outputPanel = new JPanel();
		outputPanel.setBounds(155, 515, 785, 150);
		contentPane.add(outputPanel);
		outputPanel.setLayout(new BorderLayout(0, 0));

		outputScroll = new JScrollPane();
		outputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		outputScroll.setViewportBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		outputPanel.add(outputScroll);

		output = new JPanel();

		output.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Output", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		outputScroll.setViewportView(output);

		recentActivityPanel = new JPanel();
		recentActivityPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		recentActivityPanel.setBounds(939, 0, 296, 517);
		contentPane.add(recentActivityPanel);
		recentActivityPanel.setLayout(new BorderLayout(0, 0));

		recentActivityScroll = new JScrollPane();
		recentActivityPanel.add(recentActivityScroll);
		recentActivityScroll.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Recent Activity", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null,
				null));
		recentActivityScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		recentActivityScroll.setAlignmentX(Component.RIGHT_ALIGNMENT);

		panelActivity = new JPanel();
		recentActivityScroll.setViewportView(panelActivity);
		panelActivity.setLayout(new BoxLayout(panelActivity, BoxLayout.Y_AXIS));

		snapShotPanel = new JPanel();
		snapShotPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Snap Shot ", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, null));
		snapShotPanel.setLayout(new BorderLayout());
		snapShotPanel.setBounds(939, 515, 296, 148);
		contentPane.add(snapShotPanel);
		actionPanel = new JPanel();
		actionPanel.setBounds(155, 3, 785, 514);
		contentPane.add(actionPanel);
		actionPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		actionPanel.setBackground(new Color(211, 211, 211));
		actionPanel.setLayout(null);

		noticeLabel = new JLabel();
		noticeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noticeLabel.setFont(new Font("Shruti", Font.BOLD, 16));
		noticeLabel.setForeground(Color.BLACK);
		noticeLabel.setBounds(238, 464, 323, 39);
		actionPanel.add(noticeLabel);
	}

	public void creatHeaderMenu(JMenuBar menuBar) {

		JMenuItem homeMenu = new JMenuItem("Home");
		homeMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HomeScreen homeScreen = new HomeScreen();
				dispose();
			}
		});
		homeMenu.setIcon(new ImageIcon("image/homeIcon.png"));
		menuBar.add(homeMenu);

		JMenuItem saveMenu = new JMenuItem("Save");
		saveMenu.setIcon(new ImageIcon("image/SaveIcon.png"));
		menuBar.add(saveMenu);

		JMenuItem loadMenu = new JMenuItem("Load");
		loadMenu.setIcon(new ImageIcon("image/loadIcon.jpg"));
		menuBar.add(loadMenu);

		JMenuItem triviaMenu = new JMenuItem("Trivia");
		triviaMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFromPanel();
				createTriviaView();
			}

		});
		triviaMenu.setIcon(new ImageIcon("image/exclamationIcon.png"));
		menuBar.add(triviaMenu);

		JMenuItem tutorialMenu = new JMenuItem("Tutorial");
		tutorialMenu.setIcon(new ImageIcon("image/helpIcon.png"));
		menuBar.add(tutorialMenu);
	}

	public void removeFromPanel() {
		actionPanel.removeAll();
		actionPanel.revalidate();
	}

	private void createTriviaView() {
		JPanel panel = new JPanel();
		panel.setBounds(115, 57, 275, 337);
		actionPanel.add(panel);
		panel.setLayout(null);

		JLabel levelLabel = new JLabel("Choose Level\r\n");
		levelLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		levelLabel.setBounds(84, 41, 114, 28);
		panel.add(levelLabel);

		JLabel lblTrivia = new JLabel("Trivia ");
		lblTrivia.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrivia.setBounds(102, 0, 74, 35);
		lblTrivia.setFont(new Font("Vrinda", Font.BOLD | Font.ITALIC, 20));
		panel.add(lblTrivia);

		JButton level1Btn = new JButton("Amateur\r\n");
		level1Btn.setBounds(102, 106, 89, 23);
		level1Btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeFromPanel();
				createLevel1View();
				actionPanel.revalidate();
				repaint();

			}

		});
		panel.add(level1Btn);

		JButton level2Btn = new JButton("Rookie");
		level2Btn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		level2Btn.setBounds(102, 153, 89, 23);
		panel.add(level2Btn);

		JButton level3Btn = new JButton("Pro");
		level3Btn.setBounds(102, 203, 89, 23);
		panel.add(level3Btn);
		actionPanel.add(panel);
		actionPanel.revalidate();
		repaint();

	}

	public JPanel createLevel1View() {
		JPanel panelAmateur = new JPanel();
		panelAmateur.setBounds(70, 126, 372, 134);
		actionPanel.add(panelAmateur);
		panelAmateur.setLayout(null);

		JLabel lblTrivia_1 = new JLabel("Amateur");
		lblTrivia_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrivia_1.setBounds(158, 11, 66, 22);
		lblTrivia_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelAmateur.add(lblTrivia_1);

		JTextArea txtrCanYouBuild = new JTextArea();
		txtrCanYouBuild.setWrapStyleWord(true);
		txtrCanYouBuild.setLineWrap(true);
		txtrCanYouBuild.setText("Can you build a machine with equal number of A's and B's ?");
		txtrCanYouBuild.setEditable(false);
		txtrCanYouBuild.setBounds(51, 40, 273, 46);
		panelAmateur.add(txtrCanYouBuild);
		return panelAmateur;
	}

	public void showMessage(boolean show, String message) {
		noticeLabel.setVisible(show);
		noticeLabel.setText(message);
		ActionListener blinker = new ActionListener() {
			boolean isRed = true;

			public void actionPerformed(ActionEvent ae) {
				if (isRed) {
					noticeLabel.setForeground(Color.BLUE);
				} else {
					noticeLabel.setForeground(Color.RED);
				}
				isRed = !isRed;
			}
		};
		Timer timer = new Timer(1000, blinker);
		timer.start();
	}

	public void setResult(String result) {

		outputLabel = new JLabel(result);
		outputLabel.setBounds(181, 469, 169, 34);
		outputLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		JButton playAgain = new JButton("Try Again!");
		playAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeFromPanel();
				createLevel1View();
				actionPanel.revalidate();
				repaint();

			}
		});
		playAgain.setBounds(360, 480, 89, 23);
		actionPanel.add(outputLabel);
		actionPanel.add(playAgain);
		actionPanel.revalidate();
		repaint();

	}

}
