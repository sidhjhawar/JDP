package com.turingworld.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;

public class NFARunWindow extends JFrame {
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
	private Timer timer;
	private ActionListener blinker;
	private Graphics2D g;
	private ArrayList<StateBlock> previousBlock;
	private ArrayList<StateBlock> currentBlock;
	private int level = 0;
	int x[];
	int y[];
	private HashMap<Integer, JLabel> labelList;
	private StateBlock initialState;

	public NFARunWindow(StateBlock firstState) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		
		previousBlock = new ArrayList<StateBlock>();
		previousBlock.add(firstState);
		currentBlock = new ArrayList<StateBlock>();
		labelList = new HashMap<Integer, JLabel>();
		setTitle("Welcome - NFA Visualize window!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 0, 1259, 719);
		this.setVisible(true);
		createBWScreen();
		displayOutput();

	}

	

	private void displayOutput() {
		level = 2;

		treeBWLevel1.setIcon(new ImageIcon("image/tree.png"));
		JLabel text = new JLabel("q0");
		text.setFont(new Font("Serif", Font.BOLD, 16));
		text.setForeground(Color.white);
		treeBWLevel1.add(text);

		while (level != 4) {

			for (StateBlock block : previousBlock) {
				HashMap<StateBlock, ArrayList<TransitionBlock>> list = block.getStateTransitionList();
				for (Map.Entry<StateBlock, ArrayList<TransitionBlock>> entry : list.entrySet()) {
					StateBlock key = entry.getKey();
					ArrayList<TransitionBlock> value = entry.getValue();
					for (TransitionBlock transitionBlock : value) {
						currentBlock.add(key);
					}
				}
			}
			int i = 0;
			for (StateBlock block : currentBlock) {
				i++;
				System.out.println(block.getName());
				labelList.get(new Integer(Integer.toString(level) + Integer.toString(i))).setIcon(new ImageIcon("image/tree.png"));
				String stateName = "" + block.getName().toString();
				JLabel stateNo = new JLabel(stateName);
				stateNo.setFont(new Font("Serif", Font.BOLD, 16));
				stateNo.setForeground(Color.white);
				labelList.get(new Integer(Integer.toString(level) + Integer.toString(i))).setLayout(new FlowLayout(FlowLayout.CENTER));
				// labelList.get(new Integer(level+i)).setLayout(new
				// FlowLayout(FlowLayout.CENTER));
				labelList.get(new Integer(Integer.toString(level) + Integer.toString(i))).add(stateNo);
			}
			level++;
			previousBlock = (ArrayList<StateBlock>)currentBlock.clone();
			currentBlock.clear();

		}

	}

	private void createBWScreen() {
		float[] dash1 = { 2f, 0f, 2f };
		BasicStroke bs1 = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 6.0f, dash1, 4f);
		Color myNewBlue = new Color(136, 69, 19);

		actionPanel = new ImagePanel(new ImageIcon("image/BackgroundNFA.png").getImage());
		actionPanel.setBackground(Color.white);
		actionPanel.setBounds(0, 0, 1259, 719);
		getContentPane().add(actionPanel);

		actionPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		actionPanel.setLayout(null);

		g = (Graphics2D) actionPanel.getGraphics();
		g.setColor(myNewBlue);
		g.setStroke(bs1);
		setActionListener();
		timer = new Timer(5, blinker);
		timer.start();

		treeBWLevel1 = new JLabel("");
		treeBWLevel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		treeBWLevel1.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel1.setBounds(553, 51, 58, 58);
		treeBWLevel1.setName("1");
		/*
		 * JLabel text = new JLabel("q0"); text.setFont(new Font("Serif",
		 * Font.BOLD, 16)); text.setForeground(Color.white);
		 * treeBWLevel1.add(text);
		 */
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
		treeBWLevel41.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel41.setBounds(10, 506, 58, 58);
		treeBWLevel41.setName("41");
		labelList.put(41, treeBWLevel41);
		actionPanel.add(treeBWLevel41);

		treeBWLevel42 = new JLabel("");
		treeBWLevel42.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel42.setBounds(67, 506, 58, 58);
		treeBWLevel42.setName("42");
		labelList.put(42, treeBWLevel42);
		actionPanel.add(treeBWLevel42);

		treeBWLevel43 = new JLabel("");
		treeBWLevel43.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel43.setBounds(125, 506, 58, 58);
		treeBWLevel43.setName("43");
		labelList.put(43, treeBWLevel43);
		actionPanel.add(treeBWLevel43);

		treeBWLevel44 = new JLabel("");
		treeBWLevel44.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel44.setBounds(186, 506, 58, 58);
		treeBWLevel44.setName("44");
		labelList.put(44, treeBWLevel44);
		actionPanel.add(treeBWLevel44);

		treeBWLevel45 = new JLabel("");
		treeBWLevel45.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel45.setBounds(246, 506, 58, 58);
		treeBWLevel45.setName("45");
		labelList.put(45, treeBWLevel45);
		actionPanel.add(treeBWLevel45);

		treeBWLevel46 = new JLabel("");
		treeBWLevel46.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel46.setBounds(300, 506, 58, 58);
		treeBWLevel46.setName("46");
		labelList.put(46, treeBWLevel46);
		actionPanel.add(treeBWLevel46);

		treeBWLevel47 = new JLabel("");
		treeBWLevel47.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel47.setBounds(418, 506, 58, 58);
		treeBWLevel47.setName("47");
		labelList.put(47, treeBWLevel47);
		actionPanel.add(treeBWLevel47);

		treeBWLevel48 = new JLabel("");
		treeBWLevel48.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel48.setBounds(471, 506, 58, 58);
		treeBWLevel48.setName("48");
		labelList.put(48, treeBWLevel48);
		actionPanel.add(treeBWLevel48);

		treeBWLevel49 = new JLabel("");
		treeBWLevel49.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel49.setBounds(523, 506, 58, 58);
		treeBWLevel49.setName("49");
		labelList.put(49, treeBWLevel49);
		actionPanel.add(treeBWLevel49);

		treeBWLevel412 = new JLabel("");
		treeBWLevel412.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel412.setBounds(692, 506, 58, 58);
		treeBWLevel412.setName("412");
		labelList.put(412, treeBWLevel412);
		actionPanel.add(treeBWLevel412);

		treeBWLevel410 = new JLabel("");
		treeBWLevel410.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel410.setBounds(579, 506, 58, 58);
		treeBWLevel410.setName("410");
		labelList.put(410, treeBWLevel410);
		actionPanel.add(treeBWLevel410);

		treeBWLevel411 = new JLabel("");
		treeBWLevel411.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel411.setBounds(635, 506, 58, 58);
		treeBWLevel411.setName("411");
		labelList.put(411, treeBWLevel411);
		actionPanel.add(treeBWLevel411);

		treeBWLevel413 = new JLabel("");
		treeBWLevel413.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel413.setBounds(826, 506, 58, 58);
		treeBWLevel413.setName("413");
		labelList.put(413, treeBWLevel413);
		actionPanel.add(treeBWLevel413);

		treeBWLevel414 = new JLabel("");
		treeBWLevel414.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel414.setBounds(886, 506, 58, 58);
		treeBWLevel414.setName("414");
		labelList.put(414, treeBWLevel414);
		actionPanel.add(treeBWLevel414);

		treeBWLevel415 = new JLabel("");
		treeBWLevel415.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel415.setBounds(943, 506, 58, 58);
		treeBWLevel415.setName("415");
		labelList.put(415, treeBWLevel415);
		actionPanel.add(treeBWLevel415);

		treeBWLevel416 = new JLabel("");
		treeBWLevel416.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel416.setBounds(999, 506, 58, 58);
		treeBWLevel416.setName("416");
		labelList.put(416, treeBWLevel416);
		actionPanel.add(treeBWLevel416);

		treeBWLevel417 = new JLabel("");
		treeBWLevel417.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel417.setBounds(1057, 506, 58, 58);
		treeBWLevel417.setName("417");
		labelList.put(417, treeBWLevel417);
		actionPanel.add(treeBWLevel417);

		treeBWLevel418 = new JLabel("");
		treeBWLevel418.setIcon(new ImageIcon("image/treeBW.png"));
		treeBWLevel418.setBounds(1113, 506, 58, 58);
		treeBWLevel418.setName("418");
		labelList.put(418, treeBWLevel418);
		actionPanel.add(treeBWLevel418);

	}

	private void setActionListener() {
		blinker = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * int xpoints[] = {25, 145, 25, 145, 25}; int ypoints[] = {25,
				 * 25, 145, 145, 25}; int x[] = {553,975,553,975,553,975}; int
				 * y[] = {100,200,200,100,200,200};
				 * g.fillPolygon(xpoints,ypoints,5);
				 */
			}
		};
	}

}
