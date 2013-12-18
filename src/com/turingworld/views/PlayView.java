package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import com.turingworld.controller.DFABuilderController;
import com.turingworld.helper.Mario;
import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;

public class PlayView extends JPanel {
	private JLabel panelBottom;
	private JLabel panelRight;
	private JLabel panelMiddle;
	private JLabel panelUp;
	private JLabel panelLeft;
	private JLabel ladder;
	private JLabel marioLabel;

	private Mario mario;
	private DFABuilderController dfaBuilderController;
	private int marioY;
	private int marioX = 66;
	private JLabel firstTransition;
	private int horizontalGap;
	private boolean isJumping = false;
	private boolean isFalling = true;
	private boolean checkLevel = true;
	private ArrayList<JLabel> stateLevels;
	private ArrayList<JLabel> transitionLevels;
	private ArrayList<StateBlock> stateBlocks;
	Timer timer;
	int level;

	public PlayView(DFABuilderController dfaBuilderController) {
		this.dfaBuilderController = dfaBuilderController;
		setLayout(null);

	}

	private JPanel playView;

	public JPanel getView() {
		playView = new JPanel();
		horizontalGap = 0;
		// playView = new ImagePanel(new
		// ImageIcon("image/underground.png").getImage());
		playView.setBounds(155, 3, 785, 514);
		playView.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		playView.setBackground(new Color(0, 0, 0));
		playView.setLayout(null);
		setLayout(null);

		panelBottom = new JLabel("");
		panelBottom.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/bottomPanel.png")));
	//	new ImageIcon(ClassLoader.getSystemResource(ClassLoader.getSystemResource("image/tunnel.png"))
		panelBottom.setBounds(0, 442, 785, 72);
		add(panelBottom);

		panelRight = new JLabel("");
		panelRight.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/panelRight.png")));
		panelRight.setBounds(638, 0, 147, 441);
		add(panelRight);

		panelMiddle = new JLabel("");
		panelMiddle.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/panelMiddle.png")));
		panelMiddle.setBounds(147, 238, 493, 49);
		add(panelMiddle);

		panelUp = new JLabel("");
		panelUp.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/panelUp.png")));
		panelUp.setBounds(147, 0, 493, 96);
		add(panelUp);

		panelLeft = new JLabel("");
		panelLeft.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/panelLeft.png")));
		panelLeft.setBounds(0, 0, 48, 441);
		add(panelLeft);

		ladder = new JLabel("");
		ladder.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/ladder.png")));
		ladder.setHorizontalAlignment(SwingConstants.CENTER);
		ladder.setBounds(148, 282, 35, 89);

		playView.add(panelBottom);
		playView.add(panelRight);
		playView.add(panelMiddle);
		playView.add(panelUp);
		playView.add(panelLeft);
		playView.add(ladder);

		return playView;
	}

	public void dropMario() {
		// resetting
		isFalling = true;
		isJumping = false;
		isFalling = true;
		checkLevel = true;
		horizontalGap = 0;
		playView.removeAll();
		playView.add(panelBottom);
		playView.add(panelRight);
		playView.add(panelMiddle);
		playView.add(panelUp);
		playView.add(panelLeft);
		playView.add(ladder);
		marioX = 66;

		marioY = 0;
		/*
		 * marioLabel = new JLabel("mario"); marioLabel.setIcon(new
		 * ImageIcon("image/mario.png"));
		 */
		mario = new Mario();
		mario.setState("FALLING");
		marioLabel = mario.getMario();

		timer = new Timer(10, new TimerTaskListener());
		timer.start();
	}

	class TimerTaskListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!marioLabel.getBounds().intersects(panelBottom.getBounds()) && isFalling) {
				marioY += 1;
				marioLabel.setBounds(66, marioY, 65, 65);
				playView.add(marioLabel);
			} else if (marioLabel.getBounds().intersects(panelRight.getBounds())) {
				timer.stop();
				playView.remove(marioLabel);

				// not equal to final state
				// if(dfaBuilderController.getDfaBuilderView().getInputPathIndex()
				// !=
				// dfaBuilderController.getDfaBuilderView().getInputPathLabels().size()){
				dfaBuilderController.getDfaBuilderView().fromPlayView = true;
				dfaBuilderController.getDfaBuilderView().switchToActionPanel(stateBlocks.get(level));

				// dropMario();

				// runMario(stateBlocks.get(level));

				// }

			} else if (marioLabel.getBounds().intersects(panelBottom.getBounds()) || isJumping) {
				isFalling = false;

				if (checkLevel)
					level = getStateLevel();
				// timer.stop();
				if (level == 0) {
					mario.setState("RUNNING");
					marioLabel = mario.getMario();
					marioX += 1;
					marioLabel.setBounds(marioX, marioY, 65, 65);
					playView.add(marioLabel);
				} else { // level 1
					if (marioLabel.getY() == 173) {
						mario.setState("RUNNING");
						marioLabel = mario.getMario();
						marioX += 1;
						marioLabel.setBounds(marioX, marioY, 65, 65);
					} else if (marioLabel.getX() == ladder.getX() || isJumping) {
						isJumping = true;
						mario.setState("JUMPING");
						marioLabel = mario.getMario();
						marioY += -1;
						marioLabel.setBounds(marioX, marioY, 65, 65);
					} else {

						mario.setState("RUNNING");
						marioLabel = mario.getMario();
						marioX += 1;
						marioLabel.setBounds(marioX, marioY, 65, 65);

					}
					playView.add(marioLabel);
				}
			}

			playView.revalidate();
			playView.repaint();

		}
	}

	public void runMario(StateBlock stateBlock) {
		HashMap<StateBlock, ArrayList<TransitionBlock>> stateTransitionList = stateBlock.getStateTransitionList();
		stateLevels = new ArrayList<JLabel>();
		transitionLevels = new ArrayList<JLabel>();
		stateBlocks = new ArrayList<StateBlock>();

		for (Map.Entry<StateBlock, ArrayList<TransitionBlock>> entry : stateTransitionList.entrySet()) {
			StateBlock stateBlockKey = entry.getKey();
			ArrayList<TransitionBlock> transitionBlocks = entry.getValue();
			for (TransitionBlock transitionBlock : transitionBlocks) {
				JLabel transitionLabel = new JLabel();
				JLabel stateLabel = new JLabel();

				stateBlocks.add(stateBlockKey);

				transitionLabel = transitionBlock.getDfaLabel();
				transitionLabel.setBounds(350, 390 - horizontalGap, 65, 65);
				transitionLabel.setName(transitionBlock.getTransitionType());

				stateLabel.setText(stateBlockKey.getName());
				stateLabel.setForeground(Color.BLACK);
				stateLabel.setFont(new Font("Serif", Font.BOLD, 20));
				stateLabel.setBounds(47, 376 - horizontalGap, 30, 30);

				horizontalGap += 203;

				playView.add(transitionLabel);
				panelRight.add(stateLabel);

				stateLevels.add(stateLabel);
				transitionLevels.add(transitionLabel);
				playView.revalidate();
				playView.repaint();
			}
		}
	}

	public int getStateLevel() {
		checkLevel = false;
		int currentIndex = dfaBuilderController.getDfaBuilderView().getInputPathIndex();
		JLabel nextTransition = dfaBuilderController.getDfaBuilderView().getInputPathLabels().get(currentIndex);
		int counter = 0;
		for (JLabel transition : transitionLevels) {
			if (nextTransition.getName().equalsIgnoreCase(transition.getName())) {

				dfaBuilderController.getDfaBuilderView().setInputPathIndex(++currentIndex);
				// return transitionLevels.indexOf(transition);
				return counter;
			}
			counter++;
		}
		return -1;

	}
}
