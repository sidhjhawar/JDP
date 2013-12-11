package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
/*
 * This view class is to show the intuitive visualization of the NFA. The instance of this class is called from
 * the NFABuilderView which sends the initial stateblock to the constructor of this class.
 * The visual part that would aid in better understanding of the NFA based on the machine that was drawn in the
 * NFABuilderView and the input that was provided by the user.
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import com.turingworld.controller.NFABuilderController;
import com.turingworld.model.FABlock;
import com.turingworld.model.NFABuilderModel;
import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;

public class NFARunWindow extends JFrame implements NFABuildViewInterface {
	private JPanel actionPanel;
	private JLabel treeBWLevel1;
	private JLabel treeBWLevel23;
	private JLabel treeBWLevel22;
	private JLabel treeBWLevel21;
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
	private JLabel treeBWLevel420;
	private JLabel treeBWLevel421;
	private JLabel treeBWLevel422;
	private JLabel treeBWLevel423;
	private JLabel treeBWLevel424;
	private JLabel treeBWLevel425;
	private JLabel treeBWLevel426;
	private JLabel treeBWLevel427;
	private JLabel treeBWLevel419;
	
	private Timer timer;
	private ActionListener blinker;
	private Graphics2D g;
	private ArrayList<StateBlockTreeNo> previousBlock;
	private ArrayList<StateBlockTreeNo> currentBlock;
	private int level = 0;
	int x1 = 0;
	int y1 = 0;
	int x2 = 0;
	int y2 = 0;
	private HashMap<Integer, JLabel> labelList;
	private StateBlock initialState;
	private Line2D line;

	NFABuilderController nfaController;
	NFABuilderModel nfaModel;

	public NFARunWindow(StateBlock firstState) {

		// Below try block is for the theme of the swing component which is
		// "Nimbus" here.
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}

		/*
		 * this.nfaModel = nfaBuilderModel; NFARunWindow nfaWindow = new
		 * NFARunWindow(nfaModel); nfaController = new
		 * NFABuilderController(nfaModel, nfaWindow);
		 */
		previousBlock = new ArrayList<StateBlockTreeNo>();

		StateBlockTreeNo stateBlockTreeNo = new StateBlockTreeNo();
		stateBlockTreeNo.setStateBlock(firstState);
		stateBlockTreeNo.setTreeNo(1);

		previousBlock.add(stateBlockTreeNo);

		currentBlock = new ArrayList<StateBlockTreeNo>();
		labelList = new LinkedHashMap<Integer, JLabel>();

		setTitle("Welcome - NFA Visualize window!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 0, 1259, 719);
		this.setVisible(true);

		setActionListener();
		createBWScreen();
		displayOutput();

	}

	/*
	 * Below method is called from the constructor when the object of this class
	 * is instantiated. The method iterate over the data structure which is used
	 * to store what user has made in the NFABuilderView and plots on the screen
	 * with a complete new visual aid. This would give a better understanding of
	 * the NFA
	 */
	private void displayOutput() {
		level = 2;
		// Level 1 by default
		treeBWLevel1.setIcon(new ImageIcon("image/tree.png"));
		JLabel text = new JLabel("q0");
		text.setFont(new Font("Serif", Font.BOLD, 16));
		text.setForeground(Color.white);
		treeBWLevel1.add(text);

		line = new Line2D.Double(550, 975, 100, 200);

		// Since , NFA's are practically never ending, we have kept our
		// implementation upto 4 levels.
		while (level != 5) {
			int parentTreeNo = 0;
			for (StateBlockTreeNo block : previousBlock) {
				int currentLevelCounter = 0;
				int temp;
				HashMap<StateBlock, ArrayList<TransitionBlock>> list = block.getStateBlock().getStateTransitionList();
				for (Map.Entry<StateBlock, ArrayList<TransitionBlock>> entry : list.entrySet()) {
					StateBlock key = entry.getKey();
					ArrayList<TransitionBlock> value = entry.getValue();
					for (TransitionBlock transitionBlock : value) {
						String stateName = "" + key.getName().toString();
						JLabel stateNo = new JLabel(stateName);
						stateNo.setFont(new Font("Serif", Font.BOLD, 16));
						stateNo.setForeground(Color.white);
						++currentLevelCounter;
						StateBlockTreeNo stateBlockTreeNo = new StateBlockTreeNo();
						stateBlockTreeNo.setStateBlock(key);

						/*if (level != 4) {*/
							stateBlockTreeNo.setTreeNo(((block.getTreeNo() - 1) * 3) + currentLevelCounter);
						/*} else {
							stateBlockTreeNo.setTreeNo(((block.getTreeNo() - 1) * 2) + currentLevelCounter);
						}*/
						int x = labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).getX();
						int y = labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).getY(); 
						JLabel transitionLabel = new JLabel();
						String transitionText = transitionBlock.getName();
						JLabel transitionValue =  new JLabel(transitionText);
					    transitionValue.setForeground(Color.white);
						FlowLayout fl = new FlowLayout(FlowLayout.CENTER); fl.setVgap(15);
						transitionLabel.setLayout(fl);
						if (level == 4){
							transitionLabel.setBounds(x, y + 60, 30, 30); 
							transitionLabel.setIcon(new ImageIcon("image/appleLevel4.png"));
							labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).setIcon(new ImageIcon("image/treeLevel4.png"));
							transitionValue.setFont(new Font("Serif", Font.BOLD, 12));
						}
						else{
							transitionLabel.setBounds(x + 55, y, 40, 40);
							transitionLabel.setIcon(new ImageIcon("image/apple.png"));
							transitionValue.setFont(new Font("Serif", Font.BOLD, 16));
							labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).setIcon(new ImageIcon("image/tree.png"));

						}
						transitionLabel.add(transitionValue);
						actionPanel.add(transitionLabel);
						labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).setLayout(new FlowLayout(FlowLayout.CENTER));
						labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).add(stateNo);

						currentBlock.add(stateBlockTreeNo);
					}
					temp = currentLevelCounter / 3;
					currentLevelCounter = currentLevelCounter * (temp + 1);
				}
				parentTreeNo++;
			}
			
			previousBlock = (ArrayList<StateBlockTreeNo>) currentBlock.clone();
			currentBlock.clear();

		}

	}

	// Plots the J Components on the screen. Called from the constuctor.
	private void createBWScreen() {
		float[] dash1 = { 2f, 0f, 2f };
	//	Color myNewBlue = new Color(136, 69, 19);
		Color myNewBlue = new Color(153, 118, 55);
		BasicStroke bs1 = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

		actionPanel = new ImagePanel(new ImageIcon("image/BackgroundNFA.png").getImage());
		actionPanel.setBackground(Color.white);
		actionPanel.setBounds(0, 0, 1259, 719);
		getContentPane().add(actionPanel);

		actionPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		actionPanel.setLayout(null);

		g = (Graphics2D) actionPanel.getGraphics();
		g.setColor(myNewBlue);
		g.setStroke(bs1);
		timer = new Timer(5, blinker);
		timer.start();

		treeBWLevel1 = new JLabel("");
		treeBWLevel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		treeBWLevel1.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel1.setBounds(553, 51, 58, 58);
		treeBWLevel1.setName("1");

		actionPanel.add(treeBWLevel1);
		labelList.put(11, treeBWLevel1);

		treeBWLevel23 = new JLabel("");
		treeBWLevel23.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel23.setBounds(975, 200, 58, 58);
		treeBWLevel23.setName("23");
		labelList.put(23, treeBWLevel23);
		actionPanel.add(treeBWLevel23);

		treeBWLevel22 = new JLabel("");
		treeBWLevel22.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel22.setBounds(553, 200, 58, 58);
		treeBWLevel22.setName("22");
		labelList.put(22, treeBWLevel22);
		actionPanel.add(treeBWLevel22);

		treeBWLevel21 = new JLabel("");
		treeBWLevel21.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel21.setBounds(166, 200, 58, 58);
		treeBWLevel21.setName("21");
		labelList.put(21, treeBWLevel21);
		actionPanel.add(treeBWLevel21);

		treeBWLevel31 = new JLabel("");
		treeBWLevel31.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel31.setBounds(67, 350, 58, 58);
		treeBWLevel31.setName("31");
		labelList.put(31, treeBWLevel31);
		actionPanel.add(treeBWLevel31);

		treeBWLevel32 = new JLabel("");
		treeBWLevel32.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel32.setBounds(154, 350, 58, 58);
		treeBWLevel32.setName("32");
		labelList.put(32, treeBWLevel32);
		actionPanel.add(treeBWLevel32);

		treeBWLevel33 = new JLabel("");
		treeBWLevel33.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel33.setBounds(235, 350, 58, 58);
		treeBWLevel33.setName("33");
		labelList.put(33, treeBWLevel33);
		actionPanel.add(treeBWLevel33);

		treeBWLevel34 = new JLabel("");
		treeBWLevel34.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel34.setBounds(471, 350, 58, 58);
		treeBWLevel34.setName("34");
		labelList.put(34, treeBWLevel34);
		actionPanel.add(treeBWLevel34);

		treeBWLevel35 = new JLabel("");
		treeBWLevel35.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel35.setBounds(553, 350, 58, 58);
		treeBWLevel35.setName("35");
		labelList.put(35, treeBWLevel35);
		actionPanel.add(treeBWLevel35);

		treeBWLevel36 = new JLabel("");
		treeBWLevel36.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel36.setBounds(635, 350, 58, 58);
		treeBWLevel36.setName("36");
		labelList.put(36, treeBWLevel36);
		actionPanel.add(treeBWLevel36);

		treeBWLevel37 = new JLabel("");
		treeBWLevel37.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel37.setBounds(886, 350, 58, 58);
		treeBWLevel37.setName("37");
		labelList.put(37, treeBWLevel37);
		actionPanel.add(treeBWLevel37);

		treeBWLevel38 = new JLabel("");
		treeBWLevel38.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel38.setBounds(975, 350, 58, 58);
		treeBWLevel38.setName("38");
		labelList.put(38, treeBWLevel38);
		actionPanel.add(treeBWLevel38);

		treeBWLevel39 = new JLabel("");
		treeBWLevel39.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel39.setBounds(1057, 350, 58, 58);
		treeBWLevel39.setName("39");
		labelList.put(39, treeBWLevel39);
		actionPanel.add(treeBWLevel39);

		treeBWLevel41 = new JLabel("");
		treeBWLevel41.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel41.setBounds(10, 506, 40, 40);
		treeBWLevel41.setName("41");
		labelList.put(41, treeBWLevel41);
		actionPanel.add(treeBWLevel41);

		treeBWLevel42 = new JLabel("");
		treeBWLevel42.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel42.setBounds(49, 506, 40, 40);
		treeBWLevel42.setName("42");
		labelList.put(42, treeBWLevel42);
		actionPanel.add(treeBWLevel42);

		treeBWLevel43 = new JLabel("");
		treeBWLevel43.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel43.setBounds(87, 506, 40, 40);
		treeBWLevel43.setName("43");
		labelList.put(43, treeBWLevel43);
		actionPanel.add(treeBWLevel43);

		treeBWLevel44 = new JLabel("");
		treeBWLevel44.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel44.setBounds(125, 506, 40, 40);
		treeBWLevel44.setName("44");
		labelList.put(44, treeBWLevel44);
		actionPanel.add(treeBWLevel44);

		treeBWLevel45 = new JLabel("");
		treeBWLevel45.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel45.setBounds(164, 506, 40, 40);
		treeBWLevel45.setName("45");
		labelList.put(45, treeBWLevel45);
		actionPanel.add(treeBWLevel45);

		treeBWLevel46 = new JLabel("");
		treeBWLevel46.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel46.setBounds(201, 506, 40, 40);
		treeBWLevel46.setName("46");
		labelList.put(46, treeBWLevel46);
		actionPanel.add(treeBWLevel46);

		treeBWLevel47 = new JLabel("");
		treeBWLevel47.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel47.setBounds(237, 506, 40, 40);
		treeBWLevel47.setName("47");
		labelList.put(47, treeBWLevel47);
		actionPanel.add(treeBWLevel47);

		treeBWLevel48 = new JLabel("");
		treeBWLevel48.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel48.setBounds(274, 506, 40, 40);
		treeBWLevel48.setName("48");
		labelList.put(48, treeBWLevel48);
		actionPanel.add(treeBWLevel48);

		treeBWLevel49 = new JLabel("");
		treeBWLevel49.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel49.setBounds(311, 506, 40, 40);
		treeBWLevel49.setName("49");
		labelList.put(49, treeBWLevel49);
		actionPanel.add(treeBWLevel49);

		treeBWLevel412 = new JLabel("");
		treeBWLevel412.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel412.setBounds(494, 506, 40, 40);
		treeBWLevel412.setName("412");
		labelList.put(412, treeBWLevel412);
		actionPanel.add(treeBWLevel412);

		treeBWLevel410 = new JLabel("");
		treeBWLevel410.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel410.setBounds(418, 506, 40, 40);
		treeBWLevel410.setName("410");
		labelList.put(410, treeBWLevel410);
		actionPanel.add(treeBWLevel410);

		treeBWLevel411 = new JLabel("");
		treeBWLevel411.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel411.setBounds(456, 506, 40, 40);
		treeBWLevel411.setName("411");
		labelList.put(411, treeBWLevel411);
		actionPanel.add(treeBWLevel411);

		treeBWLevel413 = new JLabel("");
		treeBWLevel413.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel413.setBounds(532, 506, 40, 40);
		treeBWLevel413.setName("413");
		labelList.put(413, treeBWLevel413);
		actionPanel.add(treeBWLevel413);

		treeBWLevel414 = new JLabel("");
		treeBWLevel414.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel414.setBounds(570, 506, 40, 40);
		treeBWLevel414.setName("414");
		labelList.put(414, treeBWLevel414);
		actionPanel.add(treeBWLevel414);

		treeBWLevel415 = new JLabel("");
		treeBWLevel415.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel415.setBounds(609, 506, 40, 40);
		treeBWLevel415.setName("415");
		labelList.put(415, treeBWLevel415);
		actionPanel.add(treeBWLevel415);

		treeBWLevel416 = new JLabel("");
		treeBWLevel416.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel416.setBounds(648, 506, 40, 40);
		treeBWLevel416.setName("416");
		labelList.put(416, treeBWLevel416);
		actionPanel.add(treeBWLevel416);

		treeBWLevel417 = new JLabel("");
		treeBWLevel417.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel417.setBounds(686, 506, 40, 40);
		treeBWLevel417.setName("417");
		labelList.put(417, treeBWLevel417);
		actionPanel.add(treeBWLevel417);

		treeBWLevel418 = new JLabel("");
		treeBWLevel418.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel418.setBounds(724, 506, 40, 40);
		treeBWLevel418.setName("418");
		labelList.put(418, treeBWLevel418);
		actionPanel.add(treeBWLevel418);
		
		treeBWLevel419 = new JLabel("");
		treeBWLevel419.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel419.setName("419");
		treeBWLevel419.setBounds(831, 506, 40, 40);
		labelList.put(419, treeBWLevel419);
		actionPanel.add(treeBWLevel419);
		
		treeBWLevel420 = new JLabel("");
		treeBWLevel420.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel420.setName("420");
		treeBWLevel420.setBounds(869, 506, 40, 40);
		labelList.put(420, treeBWLevel420);
		actionPanel.add(treeBWLevel420);
		
		treeBWLevel421 = new JLabel("");
		treeBWLevel421.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel421.setName("421");
		treeBWLevel421.setBounds(904, 506, 40, 40);
		labelList.put(421, treeBWLevel421);
		actionPanel.add(treeBWLevel421);
		
		treeBWLevel422 = new JLabel("");
		treeBWLevel422.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel422.setName("422");
		treeBWLevel422.setBounds(945, 506, 40, 40);
		labelList.put(422, treeBWLevel422);
		actionPanel.add(treeBWLevel422);
		
		treeBWLevel423 = new JLabel("");
		treeBWLevel423.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel423.setName("423");
		treeBWLevel423.setBounds(983, 506, 40, 40);
		labelList.put(423, treeBWLevel423);
		actionPanel.add(treeBWLevel423);
		
		treeBWLevel424 = new JLabel("");
		treeBWLevel424.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel424.setName("424");
		treeBWLevel424.setBounds(1021, 506, 40, 40);
		labelList.put(424, treeBWLevel424);
		actionPanel.add(treeBWLevel424);
		
		treeBWLevel425 = new JLabel("");
		treeBWLevel425.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel425.setName("425");
		treeBWLevel425.setBounds(1059, 506, 40, 40);
		labelList.put(425, treeBWLevel425);
		actionPanel.add(treeBWLevel425);
		
		treeBWLevel426 = new JLabel("");
		treeBWLevel426.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel426.setName("426");
		labelList.put(426, treeBWLevel426);
		treeBWLevel426.setBounds(1097, 506, 40, 40);
		actionPanel.add(treeBWLevel426);
		
		treeBWLevel427 = new JLabel("");
		treeBWLevel427.setIcon(new ImageIcon("C:\\Users\\owner\\git\\JDP\\image\\treeBWLevel4.png"));
		treeBWLevel427.setName("427");
		treeBWLevel427.setBounds(1135, 506, 40, 40);
		labelList.put(4127, treeBWLevel427);
		actionPanel.add(treeBWLevel427);

	}

	// The timer actionlistener is used to continuously plot the transitions
	// which are drawn using Graphics component.
	private void setActionListener() {
		blinker = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Level 1
				g.draw(new Line2D.Double(580, 120, 210, 195));
				g.draw(new Line2D.Double(580, 120, 580, 195));
				g.draw(new Line2D.Double(580, 120, 990, 195));
				// Level 21
				g.draw(new Line2D.Double(190, 260, 98, 345));
				g.draw(new Line2D.Double(190, 260, 184, 345));
				g.draw(new Line2D.Double(190, 260, 265, 345));
				// Level 22
				g.draw(new Line2D.Double(586, 260, 502, 345));
				g.draw(new Line2D.Double(586, 260, 586, 345));
				g.draw(new Line2D.Double(586, 260, 665, 345));
				// Level 23
				g.draw(new Line2D.Double(1007, 260, 916, 345));
				g.draw(new Line2D.Double(1007, 260, 1007, 345));
				g.draw(new Line2D.Double(1007, 260, 1087, 345));
				// Level 31
				g.draw(new Line2D.Double(95, 410, 40, 500));
				g.draw(new Line2D.Double(95, 410, 70, 500));
				g.draw(new Line2D.Double(95, 410, 100, 500));
				// Level 32
				g.draw(new Line2D.Double(184, 410, 157, 500));
				g.draw(new Line2D.Double(184, 410, 187, 500));
				g.draw(new Line2D.Double(184, 410, 220, 500));
				// Level 33
				g.draw(new Line2D.Double(265, 410, 265, 500));
				g.draw(new Line2D.Double(265, 410, 295, 500));
				g.draw(new Line2D.Double(265, 410, 328, 500));
				// Level 34
				g.draw(new Line2D.Double(499, 410, 445, 500));
				g.draw(new Line2D.Double(499, 410, 480, 500));
				g.draw(new Line2D.Double(499, 410, 510, 500));
				// Level 35
				g.draw(new Line2D.Double(584, 410, 553, 500));
				g.draw(new Line2D.Double(584, 410, 588, 500));
				g.draw(new Line2D.Double(584, 410, 620, 500));
				// Level 36
				g.draw(new Line2D.Double(667, 410, 667, 500));
				g.draw(new Line2D.Double(667, 410, 697, 500));
				g.draw(new Line2D.Double(667, 410, 730, 500));
				// Level 37
				g.draw(new Line2D.Double(916, 410, 857, 500));
				g.draw(new Line2D.Double(916, 410, 887, 500));
				g.draw(new Line2D.Double(916, 410, 925, 500));
				// Level 38
				g.draw(new Line2D.Double(1005, 410, 974, 500));
				g.draw(new Line2D.Double(1005, 410, 1000, 500));
				g.draw(new Line2D.Double(1005, 410, 1030, 500));
				// Level 39
				g.draw(new Line2D.Double(1087, 410, 1087, 500));
				g.draw(new Line2D.Double(1087, 410, 1115, 500));
				g.draw(new Line2D.Double(1087, 410, 1150, 500));

			}
		};
	}

	@Override
	public void addToPanel(FABlock dfaBlock) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFromPanel(FABlock dfaBlock) {
		// TODO Auto-generated method stub

	}

	@Override
	public NFABuildViewInterface getViewType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveState(JLabel label, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub

	}
}
