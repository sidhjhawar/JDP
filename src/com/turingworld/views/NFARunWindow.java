package com.turingworld.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import com.turingworld.model.StateBlock;

public class NFARunWindow extends JFrame {
	private JPanel actionPanel;
	private JLabel treeBWLevel1;
	private JLabel treeBWLevel21;
	private JLabel treeBWLevel22;
	private JLabel treeBWLevel23;
	private JLabel treeBWLevel31;
	private JLabel treeBWLevel32;
	private JLabel treeBWLevel33;
	private JLabel treeBWLevel34;
	private JLabel treeBWLevel35;
	private JLabel treeBWLevel36;
	private JLabel treeBWLevel37;
	private JLabel treeBWLevel38;
	private JLabel treeBWLevel39;
	private JLabel treeBWLevel41;
	private JLabel treeBWLevel42;
	private JLabel treeBWLevel43;
	private JLabel treeBWLevel44;
	private JLabel treeBWLevel45;
	private JLabel treeBWLevel46;
	private JLabel treeBWLevel47;
	private JLabel treeBWLevel48;
	private JLabel treeBWLevel49;
	private JLabel treeBWLevel410;
	private JLabel treeBWLevel411;
	private JLabel treeBWLevel412;
	private JLabel treeBWLevel413;
	private JLabel treeBWLevel414;
	private JLabel treeBWLevel415;
	private JLabel treeBWLevel416;
	private JLabel treeBWLevel417;
	private JLabel treeBWLevel418;
	private Timer timer;
	private ActionListener blinker;
	private Graphics2D g;
	private ArrayList<StateBlock> previousBlock;
	private ArrayList<StateBlock> currentBlock;
	
	public NFARunWindow() {
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		
	/*	previousBlock = new ArrayList<StateBlock>();
		previousBlock.add(firstState);*/
		
		setTitle("Welcome - NFA Visualize window!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 0,1259,719);
		this.setVisible(true);
		createBWScreen();
	//	displayOutput();
	
	}
	
	

	private void createBWScreen() {
		float[] dash1 = { 2f, 0f, 2f };
		BasicStroke bs1 = new BasicStroke(6, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER, 6.0f, dash1, 4f);
		Color myNewBlue = new Color(136, 69, 19);
		
		actionPanel = new ImagePanel(
				new ImageIcon("image/BackgroundNFA.png").getImage());
		actionPanel.setBackground(Color.white);
		actionPanel.setBounds(0,0,1259,719);
		getContentPane().add(actionPanel);
		
		actionPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		actionPanel.setLayout(null);
		
		g =  (Graphics2D) actionPanel.getGraphics();
		g.setColor(myNewBlue);
		g.setStroke(bs1);
		setActionListener();
		timer = new Timer(5, blinker);
		timer.start();
		
		treeBWLevel1 = new JLabel("");
		treeBWLevel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		treeBWLevel1.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel1.setBounds(553, 51, 58, 58);
		JLabel text =  new JLabel("q0");
		text.setFont(new Font("Serif", Font.BOLD, 16));
		text.setForeground(Color.white);
		treeBWLevel1.add(text);
		
		
		actionPanel.add(treeBWLevel1);
		
		treeBWLevel21 = new JLabel("");
		treeBWLevel21.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel21.setBounds(975, 200, 58, 58);
		actionPanel.add(treeBWLevel21);
		
		
		treeBWLevel22 = new JLabel("");
		treeBWLevel22.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel22.setBounds(553, 200, 58, 58);
		actionPanel.add(treeBWLevel22);
		
		treeBWLevel23 = new JLabel("");
		treeBWLevel23.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel23.setBounds(166, 200, 58, 58);
		actionPanel.add(treeBWLevel23);
		
		treeBWLevel31 = new JLabel("");
		treeBWLevel31.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel31.setBounds(67, 350, 58, 58);
		actionPanel.add(treeBWLevel31);
		
		treeBWLevel32 = new JLabel("");
		treeBWLevel32.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel32.setBounds(154, 350, 58, 58);
		actionPanel.add(treeBWLevel32);
		
		treeBWLevel33 = new JLabel("");
		treeBWLevel33.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel33.setBounds(235, 350, 58, 58);
		actionPanel.add(treeBWLevel33);
		
		treeBWLevel34 = new JLabel("");
		treeBWLevel34.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel34.setBounds(471, 350, 58, 58);
		actionPanel.add(treeBWLevel34);
		
		treeBWLevel35 = new JLabel("");
		treeBWLevel35.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel35.setBounds(553, 350, 58, 58);
		actionPanel.add(treeBWLevel35);
		
		treeBWLevel36 = new JLabel("");
		treeBWLevel36.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel36.setBounds(635, 350, 58, 58);
		actionPanel.add(treeBWLevel36);
		
		treeBWLevel37 = new JLabel("");
		treeBWLevel37.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel37.setBounds(886, 350, 58, 58);
		actionPanel.add(treeBWLevel37);
		
		treeBWLevel38 = new JLabel("");
		treeBWLevel38.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel38.setBounds(975, 350, 58, 58);
		actionPanel.add(treeBWLevel38);
		
		treeBWLevel39 = new JLabel("");
		treeBWLevel39.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel39.setBounds(1057, 350, 58, 58);
		actionPanel.add(treeBWLevel39);
		
		treeBWLevel41 = new JLabel("");
		treeBWLevel41.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel41.setBounds(10, 506, 58, 58);
		actionPanel.add(treeBWLevel41);
		
		treeBWLevel42 = new JLabel("");
		treeBWLevel42.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel42.setBounds(67, 506, 58, 58);
		actionPanel.add(treeBWLevel42);
		
		treeBWLevel43 = new JLabel("");
		treeBWLevel43.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel43.setBounds(125, 506, 58, 58);
		actionPanel.add(treeBWLevel43);
		
		treeBWLevel44 = new JLabel("");
		treeBWLevel44.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel44.setBounds(186, 506, 58, 58);
		actionPanel.add(treeBWLevel44);
		
		treeBWLevel45 = new JLabel("");
		treeBWLevel45.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel45.setBounds(246, 506, 58, 58);
		actionPanel.add(treeBWLevel45);
		
		treeBWLevel46 = new JLabel("");
		treeBWLevel46.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel46.setBounds(300, 506, 58, 58);
		actionPanel.add(treeBWLevel46);
		
		treeBWLevel47 = new JLabel("");
		treeBWLevel47.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel47.setBounds(418, 506, 58, 58);
		actionPanel.add(treeBWLevel47);
		
		treeBWLevel48 = new JLabel("");
		treeBWLevel48.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel48.setBounds(471, 506, 58, 58);
		actionPanel.add(treeBWLevel48);
		
		treeBWLevel49 = new JLabel("");
		treeBWLevel49.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel49.setBounds(523, 506, 58, 58);
		actionPanel.add(treeBWLevel49);
		
		treeBWLevel412 = new JLabel("");
		treeBWLevel412.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel412.setBounds(692, 506, 58, 58);
		actionPanel.add(treeBWLevel412);
		
		treeBWLevel410 = new JLabel("");
		treeBWLevel410.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel410.setBounds(579, 506, 58, 58);
		actionPanel.add(treeBWLevel410);
		
		treeBWLevel411 = new JLabel("");
		treeBWLevel411.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel411.setBounds(635, 506, 58, 58);
		actionPanel.add(treeBWLevel411);
		
		treeBWLevel413 = new JLabel("");
		treeBWLevel413.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel413.setBounds(826, 506, 58, 58);
		actionPanel.add(treeBWLevel413);
		
		treeBWLevel414 = new JLabel("");
		treeBWLevel414.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel414.setBounds(886, 506, 58, 58);
		actionPanel.add(treeBWLevel414);
		
		treeBWLevel415 = new JLabel("");
		treeBWLevel415.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel415.setBounds(943, 506, 58, 58);
		actionPanel.add(treeBWLevel415);
		
		treeBWLevel416 = new JLabel("");
		treeBWLevel416.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel416.setBounds(999, 506, 58, 58);
		actionPanel.add(treeBWLevel416);
		
		treeBWLevel417 = new JLabel("");
		treeBWLevel417.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel417.setBounds(1057, 506, 58, 58);
		actionPanel.add(treeBWLevel417);
		
		treeBWLevel418 = new JLabel("");
		treeBWLevel418.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel418.setBounds(1113, 506, 58, 58);
		actionPanel.add(treeBWLevel418);
		

		
	}
	
	private void setActionListener() {
		blinker = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*int xpoints[] = {25, 145, 25, 145, 25};
			    int ypoints[] = {25, 25, 145, 145, 25};
				int x[] = {553,975,553,975,553,975};
				int y[] = {100,200,200,100,200,200};
				g.fillPolygon(xpoints,ypoints,5);*/
			}
		};
	}
	
	
	
	
	
}
