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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
	private JPanel testPanel;

	private JLabel transitionLabel1121;
	private JLabel transitionLabel1122;
	private JLabel transitionLabel1123;
	private JLabel transitionLabel2131;
	private JLabel transitionLabel2132;
	private JLabel transitionLabel2133;
	private JLabel transitionLabel2234;
	private JLabel transitionLabel2235;
	private JLabel transitionLabel2236;
	private JLabel transitionLabel2337;
	private JLabel transitionLabel2338;
	private JLabel transitionLabel2339;
	private JLabel transitionLabel3141;
	private JLabel transitionLabel3142;
	private JLabel transitionLabel3143;
	private JLabel transitionLabel3244;
	private JLabel transitionLabel3245;
	private JLabel transitionLabel3246;
	private JLabel transitionLabel3347;
	private JLabel transitionLabel3348;
	private JLabel transitionLabel3349;
	private JLabel transitionLabel34410;
	private JLabel transitionLabel34411;
	private JLabel transitionLabel34412;
	private JLabel transitionLabel35413;
	private JLabel transitionLabel35414;
	private JLabel transitionLabel35415;
	private JLabel transitionLabel36416;
	private JLabel transitionLabel36417;
	private JLabel transitionLabel36418;
	private JLabel transitionLabel37419;
	private JLabel transitionLabel37420;
	private JLabel transitionLabel37421;
	private JLabel transitionLabel38422;
	private JLabel transitionLabel38423;
	private JLabel transitionLabel38424;
	private JLabel transitionLabel39425;
	private JLabel transitionLabel39426;
	private JLabel transitionLabel39427;

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
	private HashMap<Integer, JLabel> appleLabels;
	private StateBlock initialState;
	private Line2D line;
	private String testString = "";

	NFABuilderController nfaController;
	NFABuilderModel nfaModel;
	private JTextField testField;
	private JButton testBtn1;
	private JButton testBtn3;
	private JButton testBtn2;
	private JButton testBtn;
	private JLabel arrowLabel;
	private StateBlockTreeNo firstBlock;
	private StateBlock firstState;
	private JPanel panel_1;

	public NFARunWindow(StateBlock firstState) {

		appleLabels = new HashMap<Integer, JLabel>();

		this.firstState = firstState;

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

		firstBlock = new StateBlockTreeNo();
		firstBlock.setStateBlock(firstState);
		firstBlock.setTreeNo(1);

		previousBlock.add(firstBlock);

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

	public void testUserInput(String testText) {
		previousBlock.clear();
		currentBlock.clear();
		level = 1;
		previousBlock.add(firstBlock);
		int length = testText.length();

		boolean isNotFound = true;
		JLabel parentTree = new JLabel();
		JLabel childTree = new JLabel();

		while (length != 0) {
			isNotFound = true;
			for (StateBlockTreeNo block : previousBlock) {

				HashMap<StateBlockTreeNo, String> treeConnectionList = block.getTreeConnectionList();
				String transitionCharacter = String.valueOf(testText.charAt(level - 1));

				for (Map.Entry<StateBlockTreeNo, String> entry : treeConnectionList.entrySet()) {
					StateBlockTreeNo key = entry.getKey();
					String value = entry.getValue();
					if (value.equals(transitionCharacter) || value.equals("e")) {
						parentTree = block.getTreeLabel();
						childTree = key.getTreeLabel();

						parentTree.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/tree.png")));

						if (level == 3) {
							childTree.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeLevel4.png")));
							appleLabels.get(Integer.parseInt(parentTree.getName() + childTree.getName())).setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4.png")));
						} else {
							childTree.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/tree.png")));
							appleLabels.get(Integer.parseInt(parentTree.getName() + childTree.getName())).setIcon(new ImageIcon(ClassLoader.getSystemResource("image/apple.png")));
						}

						currentBlock.add(key);
						actionPanel.revalidate();
						actionPanel.repaint();
						isNotFound = false;
					}
				}

				if (isNotFound) {
					block.getTreeLabel().setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
					for (Map.Entry<Integer, JLabel> entry : appleLabels.entrySet()) {
						Integer key = entry.getKey();
						JLabel value = entry.getValue();

						if (key.toString().substring(2, 4).equals(block.getTreeLabel().getName())) {
							value.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
						}
					}
				}

				isNotFound = true;

			}
			previousBlock = (ArrayList<StateBlockTreeNo>) currentBlock.clone();
			currentBlock.clear();
			level++;
			length--;
		}
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
		treeBWLevel1.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		JLabel text = new JLabel(firstState.getName());
		text.setFont(new Font("Serif", Font.BOLD, 16));
		text.setForeground(Color.white);
		treeBWLevel1.add(text);

		firstBlock.setTreeLabel(treeBWLevel1);

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
						stateBlockTreeNo.setTreeNo(((block.getTreeNo() - 1) * 3) + currentLevelCounter);
						stateBlockTreeNo.setTreeLabel(labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))));
						stateBlockTreeNo.setForestPosition(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo())));

						block.getTreeConnectionList().put(stateBlockTreeNo, transitionBlock.getName());

						int x = labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).getX();
						int y = labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).getY();
						JLabel transitionLabel = new JLabel();
						labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).setLayout(new FlowLayout(FlowLayout.CENTER));
						labelList.get(new Integer(Integer.toString(level) + Integer.toString(stateBlockTreeNo.getTreeNo()))).add(stateNo);

						/*
						 * if (level == 4) { transitionLabel.setBounds(x, y +
						 * 40, 30, 30); transitionLabel.setIcon(new
						 * ImageIcon("image/appleLevel4.png"))); //
						 * labelList.get(new Integer(Integer.toString(level) //
						 * + //
						 * Integer.toString(stateBlockTreeNo.getTreeNo()))).
						 * setIcon(new // ImageIcon("image/treeLevel4.png")));
						 * transitionValue.setFont(new Font("Serif", Font.BOLD,
						 * 10)); fl.setVgap(10); } else {
						 * transitionLabel.setBounds(x + 55, y, 40, 40);
						 * transitionLabel.setIcon(new
						 * ImageIcon("image/apple.png")));
						 * transitionValue.setFont(new Font("Serif", Font.BOLD,
						 * 16)); // labelList.get(new
						 * Integer(Integer.toString(level) // + //
						 * Integer.toString
						 * (stateBlockTreeNo.getTreeNo()))).setIcon(new //
						 * ImageIcon("image/tree.png"))); fl.setVgap(15); }
						 */

						JLabel transitionValue = new JLabel();
						transitionValue = appleLabels.get(Integer.parseInt(block.getTreeLabel().getName() + stateBlockTreeNo.getTreeLabel().getName()));

						System.out.println(Integer.parseInt(block.getTreeLabel().getName() + stateBlockTreeNo.getTreeLabel().getName()));
						String transitionText = transitionBlock.getName();
						transitionValue.setFont(new Font("Serif", Font.BOLD, 20));
						transitionValue.setForeground(Color.WHITE);
						transitionValue.setText(transitionText);
						transitionValue.setHorizontalTextPosition(JLabel.CENTER);
						transitionValue.setVerticalTextPosition(JLabel.CENTER);

						/*
						 * JLabel transitionValue = new JLabel(transitionText);
						 * transitionValue.setForeground(Color.white);
						 * FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
						 * transitionLabel =
						 * appleLabels.get(Integer.parseInt(block
						 * .getTreeLabel().getName() +
						 * stateBlockTreeNo.getTreeLabel().getName()));
						 * transitionValue.setFont(new Font("Serif", Font.BOLD,
						 * 16)); transitionLabel.setLayout(fl);
						 * transitionLabel.add(transitionValue);
						 */

						actionPanel.add(transitionValue);

						currentBlock.add(stateBlockTreeNo);
					}
					temp = currentLevelCounter / 3;
					currentLevelCounter = currentLevelCounter * (temp + 1);
				}
				parentTreeNo++;
			}
			level++;
			previousBlock = (ArrayList<StateBlockTreeNo>) currentBlock.clone();
			currentBlock.clear();

		}

	}

	// Plots the J Components on the screen. Called from the constuctor.
	private void createBWScreen() {
		float[] dash1 = { 2f, 0f, 2f };
		// Color myNewBlue = new Color(136, 69, 19);
		Color myNewBlue = new Color(153, 118, 55);
		BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

		actionPanel = new ImagePanel(new ImageIcon(ClassLoader.getSystemResource("image/BackgroundNFA.png")).getImage());
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

		transitionLabel1121 = new JLabel();
		appleLabels.put(1121, transitionLabel1121);
		transitionLabel1121.setBounds(435, 135, 40, 40);
		transitionLabel1121.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel1121);

		transitionLabel1122 = new JLabel();
		appleLabels.put(1122, transitionLabel1122);
		transitionLabel1122.setBounds(616, 135, 40, 40);
		transitionLabel1122.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel1122);

		transitionLabel1123 = new JLabel();
		appleLabels.put(1123, transitionLabel1123);
		transitionLabel1123.setBounds(801, 133, 40, 40);
		transitionLabel1123.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel1123);

		transitionLabel2131 = new JLabel();
		appleLabels.put(2131, transitionLabel2131);
		transitionLabel2131.setBounds(210, 282, 40, 40);
		transitionLabel2131.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2131);

		transitionLabel2132 = new JLabel();
		appleLabels.put(2132, transitionLabel2132);
		transitionLabel2132.setBounds(264, 282, 40, 40);
		transitionLabel2132.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2132);

		transitionLabel2133 = new JLabel();
		appleLabels.put(2133, transitionLabel2133);
		transitionLabel2133.setBounds(312, 282, 40, 40);
		transitionLabel2133.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2133);

		transitionLabel2234 = new JLabel();
		appleLabels.put(2234, transitionLabel2234);
		transitionLabel2234.setBounds(549, 282, 40, 40);
		transitionLabel2234.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2234);

		transitionLabel2235 = new JLabel();
		appleLabels.put(2235, transitionLabel2235);
		transitionLabel2235.setBounds(615, 282, 40, 40);
		transitionLabel2235.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2235);

		transitionLabel2236 = new JLabel();
		appleLabels.put(2236, transitionLabel2236);
		transitionLabel2236.setBounds(674, 282, 40, 40);
		transitionLabel2236.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2236);

		transitionLabel2337 = new JLabel();
		appleLabels.put(2337, transitionLabel2337);
		transitionLabel2337.setBounds(913, 282, 40, 40);
		transitionLabel2337.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2337);

		transitionLabel2338 = new JLabel();
		appleLabels.put(2338, transitionLabel2338);
		transitionLabel2338.setBounds(966, 282, 40, 40);
		transitionLabel2338.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2338);

		transitionLabel2339 = new JLabel();
		appleLabels.put(2339, transitionLabel2339);
		transitionLabel2339.setBounds(1017, 282, 40, 40);
		transitionLabel2339.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleBW.png")));
		actionPanel.add(transitionLabel2339);

		transitionLabel3141 = new JLabel();
		appleLabels.put(3141, transitionLabel3141);
		transitionLabel3141.setBounds(118, 460, 40, 40);
		transitionLabel3141.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3141);

		transitionLabel3142 = new JLabel();
		appleLabels.put(3142, transitionLabel3142);
		transitionLabel3142.setBounds(145, 460, 40, 40);
		transitionLabel3142.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3142);

		transitionLabel3143 = new JLabel();
		appleLabels.put(3143, transitionLabel3143);
		transitionLabel3143.setBounds(173, 460, 40, 40);
		transitionLabel3143.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3143);

		transitionLabel3244 = new JLabel();
		appleLabels.put(3244, transitionLabel3244);
		transitionLabel3244.setBounds(237, 460, 40, 40);
		transitionLabel3244.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3244);

		transitionLabel3245 = new JLabel();
		appleLabels.put(3245, transitionLabel3245);
		transitionLabel3245.setBounds(262, 460, 40, 40);
		transitionLabel3245.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3245);

		transitionLabel3246 = new JLabel();
		appleLabels.put(3246, transitionLabel3246);
		transitionLabel3246.setBounds(285, 460, 40, 40);
		transitionLabel3246.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3246);

		transitionLabel3347 = new JLabel();
		appleLabels.put(3347, transitionLabel3347);
		transitionLabel3347.setBounds(346, 460, 40, 40);
		transitionLabel3347.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3347);

		transitionLabel3348 = new JLabel();
		appleLabels.put(3348, transitionLabel3348);
		transitionLabel3348.setBounds(376, 460, 40, 40);
		transitionLabel3348.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3348);

		transitionLabel3349 = new JLabel();
		appleLabels.put(3349, transitionLabel3349);
		transitionLabel3349.setBounds(406, 460, 40, 40);
		transitionLabel3349.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel3349);

		transitionLabel34410 = new JLabel();
		appleLabels.put(34410, transitionLabel34410);
		transitionLabel34410.setBounds(469, 460, 40, 40);
		transitionLabel34410.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel34410);

		transitionLabel34411 = new JLabel();
		appleLabels.put(34411, transitionLabel34411);
		transitionLabel34411.setBounds(496, 460, 40, 40);
		transitionLabel34411.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel34411);

		transitionLabel34412 = new JLabel();
		appleLabels.put(34412, transitionLabel34412);
		transitionLabel34412.setBounds(524, 460, 40, 40);
		transitionLabel34412.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel34412);

		transitionLabel35413 = new JLabel();
		appleLabels.put(35413, transitionLabel35413);
		transitionLabel35413.setBounds(588, 460, 40, 40);
		transitionLabel35413.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel35413);

		transitionLabel35414 = new JLabel();
		appleLabels.put(35414, transitionLabel35414);
		transitionLabel35414.setBounds(616, 460, 40, 40);
		transitionLabel35414.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel35414);

		transitionLabel35415 = new JLabel();
		appleLabels.put(35415, transitionLabel35415);
		transitionLabel35415.setBounds(642, 460, 40, 40);
		transitionLabel35415.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel35415);

		transitionLabel36416 = new JLabel();
		appleLabels.put(36416, transitionLabel36416);
		transitionLabel36416.setBounds(704, 460, 40, 40);
		transitionLabel36416.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel36416);

		transitionLabel36417 = new JLabel();
		appleLabels.put(36417, transitionLabel36417);
		transitionLabel36417.setBounds(733, 460, 40, 40);
		transitionLabel36417.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel36417);

		transitionLabel36418 = new JLabel();
		appleLabels.put(36418, transitionLabel36418);
		transitionLabel36418.setBounds(758, 460, 40, 40);
		transitionLabel36418.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel36418);

		transitionLabel37419 = new JLabel();
		appleLabels.put(37419, transitionLabel37419);
		transitionLabel37419.setBounds(820, 460, 40, 40);
		transitionLabel37419.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel37419);

		transitionLabel37420 = new JLabel();
		appleLabels.put(37420, transitionLabel37420);
		transitionLabel37420.setBounds(848, 460, 40, 40);
		transitionLabel37420.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel37420);

		transitionLabel37421 = new JLabel();
		appleLabels.put(37421, transitionLabel37421);
		transitionLabel37421.setBounds(876, 460, 40, 40);
		transitionLabel37421.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel37421);

		transitionLabel38422 = new JLabel();
		appleLabels.put(38422, transitionLabel38422);
		transitionLabel38422.setBounds(938, 460, 40, 40);
		transitionLabel38422.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel38422);

		transitionLabel38423 = new JLabel();
		appleLabels.put(38423, transitionLabel38423);
		transitionLabel38423.setBounds(965, 460, 40, 40);
		transitionLabel38423.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel38423);

		transitionLabel38424 = new JLabel();
		appleLabels.put(38424, transitionLabel38424);
		transitionLabel38424.setBounds(992, 460, 40, 40);
		transitionLabel38424.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel38424);

		transitionLabel39425 = new JLabel();
		appleLabels.put(39425, transitionLabel39425);
		transitionLabel39425.setBounds(1054, 460, 40, 40);
		transitionLabel39425.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel39425);

		transitionLabel39426 = new JLabel();
		appleLabels.put(39426, transitionLabel39427);
		transitionLabel39426.setBounds(1082, 460, 40, 40);
		transitionLabel39426.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel39426);

		transitionLabel39427 = new JLabel();
		appleLabels.put(39427, transitionLabel39427);
		transitionLabel39427.setBounds(1111, 460, 40, 40);
		transitionLabel39427.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/appleLevel4BW.png")));
		actionPanel.add(transitionLabel39427);

		treeBWLevel1 = new JLabel("");
		treeBWLevel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		treeBWLevel1.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel1.setBounds(605, 51, 58, 58);
		treeBWLevel1.setName("11");
		actionPanel.add(treeBWLevel1);
		labelList.put(11, treeBWLevel1);
		treeBWLevel23 = new JLabel("");
		treeBWLevel23.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel23.setBounds(956, 200, 58, 58);
		treeBWLevel23.setName("23");
		labelList.put(23, treeBWLevel23);
		actionPanel.add(treeBWLevel23);
		treeBWLevel22 = new JLabel("");
		treeBWLevel22.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel22.setBounds(605, 200, 58, 58);
		treeBWLevel22.setName("22");
		labelList.put(22, treeBWLevel22);
		actionPanel.add(treeBWLevel22);
		treeBWLevel21 = new JLabel("");
		treeBWLevel21.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel21.setBounds(254, 200, 58, 58);
		treeBWLevel21.setName("21");
		labelList.put(21, treeBWLevel21);
		actionPanel.add(treeBWLevel21);
		treeBWLevel31 = new JLabel("");
		treeBWLevel31.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel31.setBounds(137, 350, 58, 58);
		treeBWLevel31.setName("31");
		labelList.put(31, treeBWLevel31);
		actionPanel.add(treeBWLevel31);
		treeBWLevel32 = new JLabel("");
		treeBWLevel32.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel32.setBounds(254, 350, 58, 58);
		treeBWLevel32.setName("32");
		labelList.put(32, treeBWLevel32);
		actionPanel.add(treeBWLevel32);
		treeBWLevel33 = new JLabel("");
		treeBWLevel33.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel33.setBounds(371, 350, 58, 58);
		treeBWLevel33.setName("33");
		labelList.put(33, treeBWLevel33);
		actionPanel.add(treeBWLevel33);
		treeBWLevel34 = new JLabel("");
		treeBWLevel34.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel34.setBounds(488, 350, 58, 58);
		treeBWLevel34.setName("34");
		labelList.put(34, treeBWLevel34);
		actionPanel.add(treeBWLevel34);
		treeBWLevel35 = new JLabel("");
		treeBWLevel35.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel35.setBounds(605, 350, 58, 58);
		treeBWLevel35.setName("35");
		labelList.put(35, treeBWLevel35);
		actionPanel.add(treeBWLevel35);
		treeBWLevel36 = new JLabel("");
		treeBWLevel36.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel36.setBounds(722, 350, 58, 58);
		treeBWLevel36.setName("36");
		labelList.put(36, treeBWLevel36);
		actionPanel.add(treeBWLevel36);
		treeBWLevel37 = new JLabel("");
		treeBWLevel37.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel37.setBounds(839, 350, 58, 58);
		treeBWLevel37.setName("37");
		labelList.put(37, treeBWLevel37);
		actionPanel.add(treeBWLevel37);
		treeBWLevel38 = new JLabel("");
		treeBWLevel38.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel38.setBounds(956, 350, 58, 58);
		treeBWLevel38.setName("38");
		labelList.put(38, treeBWLevel38);
		actionPanel.add(treeBWLevel38);
		treeBWLevel39 = new JLabel("");
		treeBWLevel39.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBW.png")));
		treeBWLevel39.setBounds(1073, 350, 58, 58);
		treeBWLevel39.setName("39");
		labelList.put(39, treeBWLevel39);
		actionPanel.add(treeBWLevel39);
		treeBWLevel41 = new JLabel("");
		treeBWLevel41.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel41.setBounds(98, 510, 40, 40);
		treeBWLevel41.setName("41");
		labelList.put(41, treeBWLevel41);
		actionPanel.add(treeBWLevel41);
		treeBWLevel42 = new JLabel("");
		treeBWLevel42.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel42.setBounds(137, 510, 40, 40);
		treeBWLevel42.setName("42");
		labelList.put(42, treeBWLevel42);
		actionPanel.add(treeBWLevel42);
		treeBWLevel43 = new JLabel("");
		treeBWLevel43.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel43.setBounds(176, 510, 40, 40);
		treeBWLevel43.setName("43");
		labelList.put(43, treeBWLevel43);
		actionPanel.add(treeBWLevel43);
		treeBWLevel44 = new JLabel("");
		treeBWLevel44.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel44.setBounds(215, 510, 40, 40);
		treeBWLevel44.setName("44");
		labelList.put(44, treeBWLevel44);
		actionPanel.add(treeBWLevel44);
		treeBWLevel45 = new JLabel("");
		treeBWLevel45.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel45.setBounds(254, 510, 40, 40);
		treeBWLevel45.setName("45");
		labelList.put(45, treeBWLevel45);
		actionPanel.add(treeBWLevel45);
		treeBWLevel46 = new JLabel("");
		treeBWLevel46.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel46.setBounds(293, 510, 40, 40);
		treeBWLevel46.setName("46");
		labelList.put(46, treeBWLevel46);
		actionPanel.add(treeBWLevel46);
		treeBWLevel47 = new JLabel("");
		treeBWLevel47.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel47.setBounds(332, 510, 40, 40);
		treeBWLevel47.setName("47");
		labelList.put(47, treeBWLevel47);
		actionPanel.add(treeBWLevel47);
		treeBWLevel48 = new JLabel("");
		treeBWLevel48.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel48.setBounds(371, 510, 40, 40);
		treeBWLevel48.setName("48");
		labelList.put(48, treeBWLevel48);
		actionPanel.add(treeBWLevel48);
		treeBWLevel49 = new JLabel("");
		treeBWLevel49.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel49.setBounds(410, 510, 40, 40);
		treeBWLevel49.setName("49");
		labelList.put(49, treeBWLevel49);
		actionPanel.add(treeBWLevel49);
		treeBWLevel412 = new JLabel("");
		treeBWLevel412.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel412.setBounds(527, 510, 40, 40);
		treeBWLevel412.setName("412");
		labelList.put(412, treeBWLevel412);
		actionPanel.add(treeBWLevel412);
		treeBWLevel410 = new JLabel("");
		treeBWLevel410.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel410.setBounds(449, 510, 40, 40);
		treeBWLevel410.setName("410");
		labelList.put(410, treeBWLevel410);
		actionPanel.add(treeBWLevel410);
		treeBWLevel411 = new JLabel("");
		treeBWLevel411.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel411.setBounds(488, 510, 40, 40);
		treeBWLevel411.setName("411");
		labelList.put(411, treeBWLevel411);
		actionPanel.add(treeBWLevel411);
		treeBWLevel413 = new JLabel("");
		treeBWLevel413.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel413.setBounds(566, 510, 40, 40);
		treeBWLevel413.setName("413");
		labelList.put(413, treeBWLevel413);
		actionPanel.add(treeBWLevel413);
		treeBWLevel414 = new JLabel("");
		treeBWLevel414.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel414.setBounds(605, 510, 40, 40);
		treeBWLevel414.setName("414");
		labelList.put(414, treeBWLevel414);
		actionPanel.add(treeBWLevel414);
		treeBWLevel415 = new JLabel("");
		treeBWLevel415.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel415.setBounds(644, 510, 40, 40);
		treeBWLevel415.setName("415");
		labelList.put(415, treeBWLevel415);
		actionPanel.add(treeBWLevel415);
		treeBWLevel416 = new JLabel("");
		treeBWLevel416.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel416.setBounds(683, 510, 40, 40);
		treeBWLevel416.setName("416");
		labelList.put(416, treeBWLevel416);
		actionPanel.add(treeBWLevel416);
		treeBWLevel417 = new JLabel("");
		treeBWLevel417.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel417.setBounds(722, 510, 40, 40);
		treeBWLevel417.setName("417");
		labelList.put(417, treeBWLevel417);
		actionPanel.add(treeBWLevel417);
		treeBWLevel418 = new JLabel("");
		treeBWLevel418.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel418.setBounds(761, 510, 40, 40);
		treeBWLevel418.setName("418");
		labelList.put(418, treeBWLevel418);
		actionPanel.add(treeBWLevel418);

		treeBWLevel419 = new JLabel("");
		treeBWLevel419.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel419.setName("419");
		treeBWLevel419.setBounds(800, 510, 40, 40);
		labelList.put(419, treeBWLevel419);
		actionPanel.add(treeBWLevel419);

		treeBWLevel420 = new JLabel("");
		treeBWLevel420.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel420.setName("420");
		treeBWLevel420.setBounds(839, 510, 40, 40);
		labelList.put(420, treeBWLevel420);
		actionPanel.add(treeBWLevel420);

		treeBWLevel421 = new JLabel("");
		treeBWLevel421.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel421.setName("421");
		treeBWLevel421.setBounds(878, 510, 40, 40);
		labelList.put(421, treeBWLevel421);
		actionPanel.add(treeBWLevel421);

		treeBWLevel422 = new JLabel("");
		treeBWLevel422.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel422.setName("422");
		treeBWLevel422.setBounds(917, 510, 40, 40);
		labelList.put(422, treeBWLevel422);
		actionPanel.add(treeBWLevel422);

		treeBWLevel423 = new JLabel("");
		treeBWLevel423.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel423.setName("423");
		treeBWLevel423.setBounds(956, 510, 40, 40);
		labelList.put(423, treeBWLevel423);
		actionPanel.add(treeBWLevel423);

		treeBWLevel424 = new JLabel("");
		treeBWLevel424.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel424.setName("424");
		treeBWLevel424.setBounds(995, 510, 40, 40);
		labelList.put(424, treeBWLevel424);
		actionPanel.add(treeBWLevel424);

		treeBWLevel425 = new JLabel("");
		treeBWLevel425.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel425.setName("425");
		treeBWLevel425.setBounds(1034, 510, 40, 40);
		labelList.put(425, treeBWLevel425);
		actionPanel.add(treeBWLevel425);

		treeBWLevel426 = new JLabel("");
		treeBWLevel426.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel426.setName("426");
		labelList.put(426, treeBWLevel426);
		treeBWLevel426.setBounds(1073, 510, 40, 40);
		actionPanel.add(treeBWLevel426);

		treeBWLevel427 = new JLabel("");
		treeBWLevel427.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/treeBWLevel4.png")));
		treeBWLevel427.setName("427");
		treeBWLevel427.setBounds(1112, 510, 40, 40);
		labelList.put(4127, treeBWLevel427);
		actionPanel.add(treeBWLevel427);
		actionPanel.add(treeBWLevel427);
		arrowLabel = new JLabel("");
		arrowLabel.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/arrow.png")));
		arrowLabel.setBounds(660, 600, 30, 30);
		actionPanel.add(arrowLabel);
		arrowLabel.setVisible(false);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 599, 1250, 98);
		actionPanel.add(panel);
		panel.setLayout(null);
		testPanel = new JPanel();
		testPanel.setBounds(650, 27, 245, 39);
		panel.add(testPanel);
		testPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		testBtn1 = new JButton("");
		testBtn1.setBackground(Color.GRAY);
		testPanel.add(testBtn1);

		testBtn2 = new JButton("");
		testBtn2.setBackground(Color.GRAY);
		testPanel.add(testBtn2);

		testBtn3 = new JButton("");
		testBtn3.setBackground(Color.GRAY);
		testPanel.add(testBtn3);

		testPanel.setVisible(false);
		testPanel.setBackground(Color.LIGHT_GRAY);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(181, 27, 376, 44);
		panel.add(panel_1);

		JLabel lblNewLabel = new JLabel("Enter Test string");
		panel_1.add(lblNewLabel);

		testField = new JTextField();
		testField.setBounds(20, 40, 161, 27);
		panel_1.add(testField);
		testField.setColumns(10);
		testBtn = new JButton("Test");
		testBtn.setBounds(340, 20, 92, 30);
		panel_1.add(testBtn);

		testBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				testPanel.setVisible(true);
				arrowLabel.setVisible(true);

				testString = testField.getText().toString();

				testBtn1.setText(String.valueOf(testString.charAt(0)));
				testBtn2.setText(String.valueOf(testString.charAt(1)));
				testBtn3.setText(String.valueOf(testString.charAt(2)));
				actionPanel.revalidate();
				actionPanel.repaint();
				// testBtn1.setText(" " + testString.charAt(0));

				testBtn1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						arrowLabel.setVisible(true);
						arrowLabel.setBounds(700, 600, 30, 30);
						NFARunWindow.this.testUserInput(testString.substring(0, 1));
						testBtn1.setBackground(Color.GREEN);
						testBtn2.setBackground(Color.GRAY);
						testBtn3.setBackground(Color.GRAY);
						actionPanel.revalidate();
						actionPanel.repaint();
					}
				});

				testBtn2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						arrowLabel.setBounds(745, 600, 30, 30);
						arrowLabel.setVisible(true);
						NFARunWindow.this.testUserInput(testString.substring(0, 2));
						testBtn2.setBackground(Color.GREEN);
						testBtn3.setBackground(Color.GRAY);
						actionPanel.revalidate();
						actionPanel.repaint();
					}
				});

				testBtn3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						arrowLabel.setBounds(790, 600, 30, 30);
						NFARunWindow.this.testUserInput(testString.substring(0, 3));
						testBtn3.setBackground(Color.GREEN);
						arrowLabel.setVisible(false);
						actionPanel.revalidate();
						actionPanel.repaint();
					}
				});
			}

		});

	}

	// The timer actionlistener is used to continuously plot the transitions
	// which are drawn using Graphics component.
	private void setActionListener() {
		blinker = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Level 1
				g.draw(new Line2D.Double(635, 110, 294, 200));
				g.draw(new Line2D.Double(635, 110, 635, 200));
				g.draw(new Line2D.Double(635, 110, 986, 200));
				// Level 21
				g.draw(new Line2D.Double(284, 260, 177, 345));
				g.draw(new Line2D.Double(284, 260, 284, 345));
				g.draw(new Line2D.Double(284, 260, 381, 355));
				// Level 22
				g.draw(new Line2D.Double(635, 260, 522, 345));
				g.draw(new Line2D.Double(635, 260, 635, 345));
				g.draw(new Line2D.Double(635, 260, 752, 345));
				// Level 23
				g.draw(new Line2D.Double(986, 260, 879, 345));
				g.draw(new Line2D.Double(986, 260, 987, 345));
				g.draw(new Line2D.Double(986, 260, 1087, 345));
				// Level 31
				g.draw(new Line2D.Double(167, 410, 118, 510));
				g.draw(new Line2D.Double(167, 410, 158, 510));
				g.draw(new Line2D.Double(167, 410, 198, 510));
				// Level 32
				g.draw(new Line2D.Double(284, 410, 238, 510));
				g.draw(new Line2D.Double(284, 410, 275, 510));
				g.draw(new Line2D.Double(284, 410, 310, 510));
				// Level 33
				g.draw(new Line2D.Double(401, 410, 350, 510));
				g.draw(new Line2D.Double(401, 410, 390, 510));
				g.draw(new Line2D.Double(401, 410, 430, 510));
				// Level 34
				g.draw(new Line2D.Double(518, 410, 470, 510));
				g.draw(new Line2D.Double(518, 410, 510, 510));
				g.draw(new Line2D.Double(518, 410, 550, 510));
				// Level 35
				g.draw(new Line2D.Double(635, 410, 590, 510));
				g.draw(new Line2D.Double(635, 410, 628, 510));
				g.draw(new Line2D.Double(635, 410, 668, 510));
				// Level 36
				g.draw(new Line2D.Double(752, 410, 705, 510));
				g.draw(new Line2D.Double(752, 410, 745, 510));
				g.draw(new Line2D.Double(752, 410, 785, 510));
				// Level 37
				g.draw(new Line2D.Double(869, 410, 820, 510));
				g.draw(new Line2D.Double(869, 410, 860, 510));
				g.draw(new Line2D.Double(869, 410, 900, 510));
				// Level 38
				g.draw(new Line2D.Double(986, 410, 938, 510));
				g.draw(new Line2D.Double(986, 410, 978, 510));
				g.draw(new Line2D.Double(986, 410, 1018, 510));
				// Level 39
				g.draw(new Line2D.Double(1103, 410, 1058, 510));
				g.draw(new Line2D.Double(1103, 410, 1098, 510));
				g.draw(new Line2D.Double(1103, 410, 1138, 510));
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
