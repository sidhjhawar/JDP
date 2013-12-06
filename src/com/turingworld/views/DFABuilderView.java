package com.turingworld.views;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import com.turingworld.controller.BlockBuilderController;
import com.turingworld.controller.DFABuilderController;
import com.turingworld.helper.Mario;
import com.turingworld.model.Block;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.FABlock;
import com.turingworld.model.DFABuilderModel;
import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;

public class DFABuilderView extends JFrame implements DFABuildViewInterface {

	private JPanel contentPanel;
	private JPanel actionPanel;
	private boolean isFinalState = false;
	private DropTarget dropTarget;
	private MouseListener listener;
	private MouseListener listenerMini;
	private MouseListener undoMouseListener;
	private MouseListener redoMouseListener;
	private MouseListener snapshotMouseListener;
	private MouseListener hoverListener;
	private JLabel undo;
	private int depth = -60;
	private String transitURL;
	private String stateURL;
	private JLabel castle;
	private Curves curves1;
	private JLabel redo;
	private JLabel eraser;
	private JLabel dragSource;
	private JLabel noticeLabel;
	private boolean isMini;
	private JLabel snapLabel;
	private int activityNo = 0;
	private JScrollPane scrollPane;
	private JPanel panelActivity;
	private JTextField field;
	private int transitionX;
	private int transitionY;
	private FABlock faBlock;
	private JLabel actiontransition;
	private JLabel actionstate;
	private JPanel colorPallete;
	private String textFieldValue = null;
	public Collection<JLabel> actionicons = new ArrayList<JLabel>();
	public Collection<JLabel> outputicons = new ArrayList<JLabel>();
	public int count = 0;
	private boolean cursorflag = false;
	private DFABuilderModel dfaBuilderModel;
	private FABlock dfaBlock;
	private PlayView playView;
	private JPanel playPanel;
	private DFABuilderController dfaBuilderController;
	private JLabel outputLabel;
	private JButton playAgain;
	private JPanel panel_1;
	private JLabel stateTransit1;
	private JLabel stateTransit2;
	private JScrollPane scrollPane_1;
	private JPanel output;
	private JPanel panel_2;
	private JLabel g;
	private JLabel r;
	private String[] charList;
	private int blockCount = 1;
	private PopupForStateView popupForStateView;
	private Graphics2D g2d;
	private JTextField textFieldM1;
	private JTextField textFieldM2;
	private JPanel panel;
	private StateBlock startStateBlock;
	private StateBlock endStateBlock;
	private StateBlock finalEndStateBlock;
	private StateBlock runStartStateBlock;
	private StateBlock runEndStateBlock;
	private boolean isStartStateClicked;
	private JButton plus;
	private JButton testTransitionButton;
	private TransitionBlock transitionBlock;
	private Timer timer;
	private double degrees;
	private FABlock db;
	private int currentPathIndex = 0; // index tracker of mario while traversing
	private StateBlock currentPathState;
	private String inputPathString;
	private JTextPane outPutTextField;
	private boolean setSelfloop = false;
	private JButton mushroom1;
	private JButton mushroom2;
	private JButton mushroom;
	private JButton flower1;
	private JButton coin1;
	private JButton enemy1;
	public ArrayList<String> imgButtons = new ArrayList<String>();
	private ImageIcon Icon;
	private int colorPositionX;
	private int colorPositionY;
	private JLabel marioLabel;
	private Mario mario;
	boolean isTestClicked = false;
	private ArrayList<JLabel> inputPathLabels;
	private int inputPathIndex = 0;
	boolean fromPlayView = false;

	public ArrayList<String> getImgButtons() {
		return imgButtons;
	}

	public void setImgButtons(ArrayList<String> imgButtons) {
		this.imgButtons = imgButtons;
	}

	public enum Rotate {
		DOWN, UP, UPSIDE_DOWN, ABOUT_CENTER;
	}

	/**
	 * Launch the application.
	 */
	public void addoutputicons(JLabel icon) {
		outputicons.add(icon);
	}

	public void addicons(JLabel icon) {
		actionicons.add(icon);
	}

	public void setController(DFABuilderController dfaBuilderController) {
		this.dfaBuilderController = dfaBuilderController;
	}

	public ArrayList<JLabel> getInputPathLabels() {
		return inputPathLabels;
	}

	public void setInputPathLabels(ArrayList<JLabel> inputPathLabels) {
		this.inputPathLabels = inputPathLabels;
	}
	
	public int getCurrentPathIndex() {
		return currentPathIndex;
	}

	public JPanel getActionPanel() {
		return actionPanel;
	}

	public void setActionPanel(JPanel actionPanel) {
		this.actionPanel = actionPanel;
	}

	public void setCurrentPathIndex(int currentPathIndex) {
		this.currentPathIndex = currentPathIndex;
	}

	public int getInputPathIndex() {
		return inputPathIndex;
	}

	public void setInputPathIndex(int inputPathIndex) {
		this.inputPathIndex = inputPathIndex;
	}

	/**
	 * Create the frame.
	 */
	public DFABuilderView(DFABuilderModel dfaBuilderModel) {

		curves1 = new Curves();

		popupForStateView = new PopupForStateView();
		this.dfaBuilderModel = dfaBuilderModel;
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}

		startStateBlock = new StateBlock();
		endStateBlock = new StateBlock();
		finalEndStateBlock = new StateBlock();
		runStartStateBlock = new StateBlock();
		runEndStateBlock = new StateBlock();
		isStartStateClicked = false;

		this.setVisible(true);

		setTitle("Welcome - Build your Blocks!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 0, 1251, 719);

		// method to create the view
		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setToolTipText("");
		contentPanel.setBorder(null);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		creatHeaderMenu();
		createTabbedView();
		createLeftPaneView();
		createActionView();
		panel_1 = new JPanel();
		panel_1.setBounds(155, 515, 785, 150);
		contentPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setViewportBorder(new TitledBorder(null, "",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(scrollPane_1);

		output = new JPanel();

		output.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, null, null));
		scrollPane_1.setViewportView(output);
		output.setLayout(null);

		panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		panel_2.setBounds(939, 0, 296, 665);
		contentPanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		scrollPane.setViewportBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Recent Activity",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);

		panelActivity = new JPanel();
		scrollPane.setViewportView(panelActivity);
		panelActivity.setLayout(new BoxLayout(panelActivity, BoxLayout.Y_AXIS));
		actionPanel = new ImagePanel(
				new ImageIcon("image/background.png").getImage());
		actionPanel.setBounds(155, 3, 785, 514);
		contentPanel.add(actionPanel);
		actionPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		actionPanel.setBackground(new Color(211, 211, 211));
		actionPanel.setLayout(null);

		noticeLabel = new JLabel();
		noticeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noticeLabel.setFont(new Font("Shruti", Font.BOLD, 16));
		noticeLabel.setForeground(Color.BLACK);
		noticeLabel.setBounds(238, 464, 323, 39);
		actionPanel.add(noticeLabel);
		popupForStateView.registerActionListner(new MenuActionListener());
		dropTarget = new DropTarget(this.actionPanel, new DropTargetListener2());

		createCharSelectView();
		isMini = false;
		ActionListener blinker = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				for (QuadCurve2D.Double curve : curves1.getCurves()) {
					g2d.draw(curve);
				}
				if (isTestClicked && !fromPlayView) {
					if (!marioLabel.getBounds().intersects(
							runStartStateBlock.getBounds())) {
						marioLabel.setBounds(marioLabel.getX(),
								marioLabel.getY() + mario.getySpeed(), 65, 65);
						actionPanel.add(marioLabel);
						actionPanel.revalidate();
						actionPanel.repaint();
					} else {
						timer.stop();
						System.out.println("dfaBView: ");
						playView = new PlayView(dfaBuilderController);
						playPanel = playView.getView();
						contentPanel.remove(actionPanel);
						contentPanel.add(playPanel);
						contentPanel.revalidate();
						contentPanel.repaint();
						playView.dropMario();

						playView.runMario(runStartStateBlock);

					}

				} else if (isTestClicked && fromPlayView) { // condition for
															// return from
															// PlayView
					if (!marioLabel.getBounds().intersects(
							currentPathState.getBounds())) {
						marioLabel.setBounds(marioLabel.getX(),
								marioLabel.getY() + mario.getySpeed(), 65, 65);
						actionPanel.add(marioLabel);
						actionPanel.revalidate();
						actionPanel.repaint();
					} else {
						timer.stop();
						System.out.println("dfaBView: ");
						playView = new PlayView(dfaBuilderController);
						playPanel = playView.getView();
						contentPanel.remove(actionPanel);
						contentPanel.add(playPanel);
						contentPanel.revalidate();
						contentPanel.repaint();
						playView.dropMario();

						playView.runMario(currentPathState);

					}
				}
			}
		};
		timer = new Timer(5, blinker);
		timer.start();

		repaint();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Add Initial State")) {

				for (FABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {
					if (dfaBlockObj.isState()) {
						if (((StateBlock) dfaBlockObj).isInitial()) {

							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setInitial(false);
							JLabel label = new JLabel((new ImageIcon(
									"image/tunnel.png")));
							dfaBlockObj.setDfaLabel(label);
							repaint();

						}

						if (dfaBlockObj.isState()
								&& (dfaBlockObj.getX() == dragSource.getX())) {

							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setInitial(true);
							JLabel label = new JLabel((new ImageIcon(
									"image/house.png")));
							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), 76, 96);
							dfaBlockObj.setDfaLabel(label);
							runStartStateBlock = (StateBlock) dfaBlockObj;
						}

					}
				}
				for (FABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {

					if (dfaBlockObj.isState()) {

						JLabel label = dfaBlockObj.getDfaLabel();
						label.setName("");
						label.addMouseListener(listener);
						label.setTransferHandler(new TransferHandler("text"));
						if (((StateBlock) dfaBlockObj).isInitial()) {
							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), 76, 96);

						}

						else if (((StateBlock) dfaBlockObj).isFinal()) {
							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), 76, 106);

						}

						else {

							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), dfaBlockObj.getWidth(),
									dfaBlockObj.getHeight());

						}
						actionPanel.add(label);
					}
				}

				actionPanel.revalidate();
				actionPanel.repaint();
			} else if (e.getActionCommand().equals("Add Final State")) {
				for (FABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {

					if (dfaBlockObj.isState()) {
						if (((StateBlock) dfaBlockObj).isFinal()) {
							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setFinal(false);
							JLabel label = new JLabel((new ImageIcon(
									"image/tunnel.png")));
							dfaBlockObj.setDfaLabel(label);
							repaint();

						}

						if (dfaBlockObj.isState()
								&& (dfaBlockObj.getX() == dragSource.getX())) {

							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setFinal(true);

							JLabel label = new JLabel((new ImageIcon(
									"image/princess.png")));
							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), 76, 96);

							dfaBlockObj.setDfaLabel(label);
							runEndStateBlock = (StateBlock) dfaBlockObj;

						}

					}
				}
				for (FABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {
					if (dfaBlockObj.isState()) {

						JLabel label = dfaBlockObj.getDfaLabel();
						label.setName("");
						label.addMouseListener(listener);
						label.setTransferHandler(new TransferHandler("text"));
						if (((StateBlock) dfaBlockObj).isInitial()) {
							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), 76, 96);

						}

						else if (((StateBlock) dfaBlockObj).isFinal()) {
							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), 76, 106);

						}

						else {

							label.setBounds(dfaBlockObj.getX(),
									dfaBlockObj.getY(), dfaBlockObj.getWidth(),
									dfaBlockObj.getHeight());

						}

						actionPanel.add(label);
					}
				}
				actionPanel.revalidate();
				actionPanel.repaint();

			} else if (e.getActionCommand().equals("Add Transition")) {

				for (FABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {
					JLabel label = dfaBlockObj.getDfaLabel();
					label.addMouseListener(hoverListener);
				}
			}
		}

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

	
	public void createCharSelectView() {
		panel = new JPanel();
		panel.setBounds(251, 199, 250, 207);
		actionPanel.add(panel);
		panel.setLayout(null);

		JLabel lblEnterCharacters = new JLabel("Select any two transitions!");
		lblEnterCharacters.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterCharacters.setBounds(44, 11, 161, 14);
		panel.add(lblEnterCharacters);

		mushroom = new JButton();
		JLabel mushroom1 = new JLabel("");
		mushroom.setIcon(new ImageIcon("image/mushroom.png"));
		mushroom.setBorder(BorderFactory.createEmptyBorder());
		mushroom1.setHorizontalAlignment(SwingConstants.CENTER);
		FlowLayout f2 = new FlowLayout();
		f2.setVgap(30);
		mushroom.setLayout(f2);
		mushroom.setBounds(44, 53, 64, 46);
		mushroom.add(mushroom1);
		panel.add(mushroom);
		mushroom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (imgButtons.size() < 2) {
					if (mushroom.getBorder() == BorderFactory
							.createEmptyBorder()) {
						imgButtons.add("a");
						mushroom.setBorder(BorderFactory.createLineBorder(
								Color.BLACK, 2));
					} else if (mushroom.getBorder() != null) {
						imgButtons.remove("a");
						mushroom.setBorder(BorderFactory.createEmptyBorder());
					}

				} else {
					imgButtons.remove("a");
					mushroom.setBorder(BorderFactory.createEmptyBorder());
				}

			}
		});

		flower1 = new JButton();
		JLabel flower = new JLabel("");
		flower1.setIcon(new ImageIcon("image/flower.png"));
		flower1.setHorizontalAlignment(SwingConstants.CENTER);
		flower1.setBorder(BorderFactory.createEmptyBorder());
		FlowLayout f1 = new FlowLayout();
		f1.setVgap(30);
		flower1.setLayout(f1);
		flower1.setBounds(44, 112, 64, 46);
		flower1.add(flower);
		panel.add(flower1);
		flower1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (imgButtons.size() < 2) {
					if (flower1.getBorder() == BorderFactory
							.createEmptyBorder()) {
						imgButtons.add("b");
						flower1.setBorder(BorderFactory.createLineBorder(
								Color.BLACK, 2));
					} else if (flower1.getBorder() != null) {
						imgButtons.remove("b");
						flower1.setBorder(BorderFactory.createEmptyBorder());
					}
				} else {
					imgButtons.remove("b");
					flower1.setBorder(BorderFactory.createEmptyBorder());
				}
			}
		});

		coin1 = new JButton();
		JLabel coin = new JLabel("");
		coin1.setIcon(new ImageIcon("image/coin.png"));
		coin1.setHorizontalAlignment(SwingConstants.CENTER);
		coin1.setBorder(BorderFactory.createEmptyBorder());
		FlowLayout f3 = new FlowLayout();
		f3.setVgap(30);
		coin1.setLayout(f3);
		coin1.setBounds(141, 53, 64, 46);
		coin1.add(coin);
		panel.add(coin1);
		coin1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (imgButtons.size() < 2) {
					if (coin1.getBorder() == BorderFactory.createEmptyBorder()) {
						imgButtons.add("c");
						coin1.setBorder(BorderFactory.createLineBorder(
								Color.BLACK, 2));
					} else if (coin1.getBorder() != null) {

						imgButtons.remove("c");
						coin1.setBorder(BorderFactory.createEmptyBorder());

					}
				} else {
					imgButtons.remove("c");
					coin1.setBorder(BorderFactory.createEmptyBorder());
				}
			}
		});

		enemy1 = new JButton();
		JLabel enemy = new JLabel("");
		enemy1.setIcon(new ImageIcon("image/enemy.png"));
		enemy1.setBorder(BorderFactory.createEmptyBorder());
		enemy1.setHorizontalAlignment(SwingConstants.CENTER);
		FlowLayout f4 = new FlowLayout();
		f4.setVgap(30);
		enemy1.setLayout(f4);
		enemy1.setBounds(141, 112, 64, 46);
		enemy1.add(enemy);
		panel.add(enemy1);
		enemy1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (imgButtons.size() < 2) {
					if (enemy1.getBorder() == BorderFactory.createEmptyBorder()) {
						imgButtons.add("d");
						enemy1.setBorder(BorderFactory.createLineBorder(
								Color.BLACK, 2));
					} else if (enemy1.getBorder() != null) {
						imgButtons.remove("d");
						enemy1.setBorder(BorderFactory.createEmptyBorder());

					}
				} else {
					imgButtons.remove("d");
					enemy1.setBorder(BorderFactory.createEmptyBorder());
				}
			}
		});

		JButton btnDone = new JButton("Done!");
		btnDone.setBounds(88, 171, 89, 23);
		btnDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				charList = new String[2];

				for (int i = 0; i < imgButtons.size(); i++) {
					charList[i] = imgButtons.get(i);

				}
				actionPanel.remove(panel);
				actionPanel.revalidate();
				actionPanel.repaint();
			}
		});
		panel.add(btnDone);

	}

	public void createNewWindow() {
		boolean isTriviaClicked = false;
		if (dfaBuilderController.isTriviaClicked) {
			isTriviaClicked = true;
		}
		dfaBuilderModel = new DFABuilderModel();
		DFABuilderView dfaBuilderView = new DFABuilderView(dfaBuilderModel);
		dfaBuilderController = new DFABuilderController(dfaBuilderModel,
				dfaBuilderView);
		dfaBuilderView.setController(dfaBuilderController);
		BlockBuilderModel.stateNo = 0;
		BlockBuilderModel.transitionNo = 0;
		if (isTriviaClicked) {
			BlockBuilderController.isTriviaClicked = true;
		}
		dispose();
	}

	public void removeFromPanel() {
		actionPanel.removeAll();
		actionPanel.revalidate();
	}

	private void creatHeaderMenu() {
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

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewWindow();
			}
		});
		mntmNew.setIcon(new ImageIcon("image/newIcon.png"));
		menuBar.add(mntmNew);
		JMenuItem mntmTrivia = new JMenuItem("Trivia");
		mntmTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFromPanel();
			}

		});
		mntmTrivia.setIcon(new ImageIcon("image/exclamationIcon.png"));
		menuBar.add(mntmTrivia);

		JMenuItem mntmTutorial = new JMenuItem("Tutorial");
		mntmTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mntmTutorial.setIcon(new ImageIcon("image/helpIcon.png"));
		menuBar.add(mntmTutorial);
	}

	private void createActionView() {
	}

	private void createTabbedView() {

	}

	public void loadpaint(BlockBuilderModel blockbuildermodel) {
		actionPanel.removeAll();
		for (Block block : blockbuildermodel.getBlockList()) {
			JLabel label = new JLabel();
			JLabel textLabel = new JLabel();
			textLabel.setHorizontalAlignment(SwingConstants.CENTER);
			if (block.getName().charAt(0) == 'q') {
				textLabel.setForeground(Color.white);
				textLabel.setFont(new Font("Serif", Font.BOLD, 12));
			} else {
				textLabel.setForeground(Color.BLACK);
				textLabel.setFont(new Font("Serif", Font.BOLD, 25));
			}
			label.setLayout(new FlowLayout());
			label.setTransferHandler(new TransferHandler("text"));
			label.setSize(30, 20);
			label.setIcon(new ImageIcon(block.getBlockLabelURL()));
			block.setBlockLabel(label);

			if (block.getName().charAt(0) == 'q') {
				textLabel.setText(block.getName());
			} else {
				textLabel.setText(block.getTransitionType());
			}

			label.setLayout(new BorderLayout());
			label.add(textLabel, BorderLayout.CENTER);

			label.setBounds(block.getX(), block.getY(), block.getWidth(),
					block.getHeight());
			actionPanel.add(label);
		}
		actionPanel.revalidate();
		actionPanel.repaint();
	}

	private void DisplayOutput() {

	}

	private void setResult(String result) {

		outputLabel = new JLabel(result);
		outputLabel.setBounds(181, 469, 169, 34);
		outputLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		playAgain = new JButton("Try Again!");
		playAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeFromPanel();
				//createLevel1View();
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

	public StateBlock getCurrentPathState() {
		return currentPathState;
	}

	public void setCurrentPathState(StateBlock currentPathState) {
		this.currentPathState = currentPathState;
	}

	private void createLeftPaneView() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(0, 3, 153, 662);
		contentPanel.add(panel);
		panel.setLayout(null);

		undo = new JLabel("undo");
		undo.setIcon(new ImageIcon("image/undo.png"));
		undo.setToolTipText("Undo");
		undo.setName("undo");
		undo.setBounds(30, 190, 75, 75);
		undo.setTransferHandler(new TransferHandler("text"));
		panel.add(undo);

		castle = new JLabel("");
		castle.setHorizontalAlignment(SwingConstants.CENTER);
		castle.setName("leftPanelState");
		castle.setToolTipText("");
		castle.setIcon(new ImageIcon("image/tunnel.png"));
		castle.setBounds(30, 42, 76, 96);
		castle.setTransferHandler(new TransferHandler("text"));
		panel.add(castle);

		redo = new JLabel("redo");
		redo.setToolTipText("Redo");
		redo.setIcon(new ImageIcon("image/redo.png"));
		redo.setBounds(30, 309, 75, 75);
		panel.add(redo);

		eraser = new JLabel("eraser");
		eraser.setToolTipText("Eraser\r\n");
		eraser.setIcon(new ImageIcon("image/delete.png"));
		eraser.setBounds(30, 428, 75, 75);
		eraser.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (isCursorflag() == false) {
					setCursorflag(true);
					// added check for MouseEvent.BUTTON1 which is left click
					if (e.isPopupTrigger()
							|| e.getButton() == MouseEvent.BUTTON1) {
						Toolkit kit = Toolkit.getDefaultToolkit();

						// Image image = null;
						String path = "image/delete.png";
						BufferedImage image = null;
						try {
							image = ImageIO.read(new File(path));
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						Point hotspot = new Point(0, 0);
						Cursor cursor = kit.createCustomCursor(image, hotspot,
								"Stone");
						setCursor(cursor);

					}
				} else {
					setCursorflag(false);
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		panel.add(eraser);

		JLabel play = new JLabel("Play Button");
		play.setToolTipText("Play\r\n");
		play.setIcon(new ImageIcon("image/run.png"));
		play.setBounds(30, 549, 75, 75);
		panel.add(play);
		colorPositionX = 12;
		colorPositionY = 34;
		transitionX = 12;
		transitionY = 34;
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showMessage(true, "Please enter your input string to test DFA");
				addTransitionPath();
				output.revalidate();
				repaint();

			}
		});

		listenerMini = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				dragSource = (JLabel) me.getSource();
				isMini = true;
				TransferHandler handler = dragSource.getTransferHandler();
				handler.exportAsDrag(dragSource, me, TransferHandler.COPY);
			}

		};

		hoverListener = new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				dragSource = (JLabel) me.getSource();
				dfaBlock = dfaBuilderController.getDFABlockObj(
						dragSource.getX(), dragSource.getY());
				if (dfaBlock != null && dfaBlock.isState()) {
					endStateBlock = (StateBlock) dfaBlock;
					DFABuilderView.this.isStartStateClicked = true;
				}
			}
		};

		listener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				dragSource = (JLabel) me.getSource();
				if (isCursorflag() == true) {
					if (dfaBuilderController.getDFABlockObject(
							dragSource.getX(), dragSource.getY()) == true) {
						dfaBlock = dfaBuilderController.getDFABlockObj(
								dragSource.getX(), dragSource.getY());
						dfaBuilderController
								.removeBlockFromListAndPanel(dfaBlock);
						// DisplayOutput();
					}
				} else {
					TransferHandler handler = dragSource.getTransferHandler();
					handler.exportAsDrag(dragSource, me, TransferHandler.COPY);
					dfaBlock = dfaBuilderController.getDFABlockObj(
							dragSource.getX(), dragSource.getY());

					if (dfaBlock != null && isStartStateClicked == false) {
						startStateBlock = (StateBlock) dfaBlock;
						if (me.getButton() == MouseEvent.BUTTON3) {
							popupForStateView.show(me.getComponent(),
									me.getX(), me.getY());
						}
					} else if (dfaBlock != null && isStartStateClicked == true) {
						finalEndStateBlock = (StateBlock) dfaBlock;
						addColorPallette(startStateBlock, finalEndStateBlock);
						actionPanel.revalidate();
						actionPanel.repaint();
						for (FABlock dfaBlockObj : dfaBuilderModel
								.getDfaBlockList()) {
							JLabel label = dfaBlockObj.getDfaLabel();
							label.removeMouseListener(hoverListener);
						}

						isStartStateClicked = false;
					}
				}
			}
		};
		undo.addMouseListener(undoMouseListener);
		redo.addMouseListener(redoMouseListener);
		castle.addMouseListener(listener);
	}

	protected void addTransitionPath() {
		inputPathLabels = new ArrayList<JLabel>();
		plus = new JButton();
		JLabel plus1 = new JLabel("");
		plus.setIcon(new ImageIcon("image/plus.png"));
		plus.setBorder(BorderFactory.createEmptyBorder());
		plus1.setHorizontalAlignment(SwingConstants.CENTER);
		FlowLayout f2 = new FlowLayout();
		f2.setVgap(30);
		plus.setLayout(f2);
		plus.setBounds(colorPositionX, colorPositionY, 64, 46);
		plus.add(plus1);
		output.add(plus);

		testTransitionButton = new JButton("Test");
		FlowLayout f3 = new FlowLayout();
		f3.setVgap(30);
		testTransitionButton.setLayout(f3);
		testTransitionButton.setBounds(colorPositionX + 600, colorPositionY,
				64, 46);
		output.add(testTransitionButton);

		testTransitionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// timer.stop();
				// Timer playTimer = new Timer(10, new PlayTimerTaskListener());
				// playTimer.start();

				isTestClicked = true;
				mario = new Mario();
				mario.setState("FALLING");
				marioLabel = mario.getMario();
				marioLabel.setBounds(runStartStateBlock.getX(),
						runStartStateBlock.getY() - 150, 65, 65);

			}
		});

		plus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMessage(false, "Please enter your input string to test DFA");
				colorPallete = new JPanel();
				colorPallete.setBorder(new TitledBorder("Select Transitions!"));
				mushroom1 = new JButton();
				FlowLayout f1 = new FlowLayout();
				mushroom1.setLayout(f1);
				if (charList[0].equals("a")) {
					mushroom1.setIcon(new ImageIcon("image/mushroom.png"));
				} else if (charList[0].equals("b")) {
					mushroom1.setIcon(new ImageIcon("image/flower.png"));
				} else if (charList[0].equals("c")) {
					mushroom1.setIcon(new ImageIcon("image/coin.png"));
				} else {
					mushroom1.setIcon(new ImageIcon("image/enemy.png"));
				}
				mushroom1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JLabel mushroomTransit1 = new JLabel();
						if (charList[0].equals("a")) {
							Icon = new ImageIcon("image/mushroom.png");
						} else if (charList[0].equals("b")) {
							Icon = new ImageIcon("image/flower.png");
						} else if (charList[0].equals("c")) {
							Icon = new ImageIcon("image/coin.png");
						} else {
							Icon = new ImageIcon("image/enemy.png");
						}
						// RotatedIcon ri = new RotatedIcon(Icon, degrees);
						mushroomTransit1.setIcon(Icon);
						// mushroomTransit1.setText(charList[0]);
						mushroomTransit1.setName(charList[0]);
						System.out.println("Icon: "
								+ mushroomTransit1.getIcon());
						// mushroomTransit1.setIcon(icon);
						mushroomTransit1.setLayout(new FlowLayout(
								FlowLayout.CENTER));
						mushroomTransit1
								.setHorizontalAlignment(SwingConstants.CENTER);
						mushroomTransit1.setFont(new Font("Serif", Font.BOLD,
								16));
						// mushroomTransit1.add(g, new
						// FlowLayout(FlowLayout.CENTER));
						mushroomTransit1.setBounds(colorPositionX,
								colorPositionY, 64, 46);
						output.remove(plus);
						output.add(mushroomTransit1);
						inputPathLabels.add(mushroomTransit1);
						output.remove(colorPallete);
						plus.setBounds(colorPositionX + 70, colorPositionY, 64,
								46);
						output.add(plus);
						output.revalidate();
						repaint();
					}
				});

				mushroom2 = new JButton();
				FlowLayout f2 = new FlowLayout(FlowLayout.CENTER);
				mushroom2.setLayout(f2);
				if (charList[1].equals("a")) {
					mushroom2.setIcon(new ImageIcon("image/mushroom.png"));
				} else if (charList[1].equals("b")) {
					mushroom2.setIcon(new ImageIcon("image/flower.png"));
				} else if (charList[1].equals("c")) {
					mushroom2.setIcon(new ImageIcon("image/coin.png"));
				} else {
					mushroom2.setIcon(new ImageIcon("image/enemy.png"));
				}
				mushroom2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						JLabel mushroomTransit = new JLabel();

						if (charList[1].equals("a")) {
							Icon = new ImageIcon("image/mushroom.png");
						} else if (charList[1].equals("b")) {
							Icon = new ImageIcon("image/flower.png");
						} else if (charList[1].equals("c")) {
							Icon = new ImageIcon("image/coin.png");
						} else {
							Icon = new ImageIcon("image/enemy.png");
						}
						// RotatedIcon ri = new RotatedIcon(Icon, degrees);
						mushroomTransit.setIcon(Icon);

						JLabel text = new JLabel();
						mushroomTransit.setBounds(colorPositionX,
								colorPositionY, 64, 46);
						// mushroomTransit.setText(charList[1]);
						mushroomTransit.setName(charList[1]);
						output.remove(plus);
						output.add(mushroomTransit);
						inputPathLabels.add(mushroomTransit);
						output.remove(colorPallete);
						plus.setBounds(colorPositionX + 70, colorPositionY, 64,
								46);
						output.add(plus);
						output.revalidate();
						repaint();
					}
				});

				colorPallete.setLayout(new GridLayout(1, 2));
				colorPallete.add(mushroom1);
				colorPallete.add(mushroom2);
				colorPallete.setBounds(transitionX + 120, colorPositionY - 30,
						120, 100);
				output.add(colorPallete);
				output.revalidate();
				repaint();
				colorPositionX += 50;
				transitionX = colorPositionX;
			}
		});

	}

	class PlayTimerTaskListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!marioLabel.getBounds().intersects(startStateBlock.getBounds())) {
				marioLabel.setBounds(marioLabel.getX(), marioLabel.getY()
						+ mario.getXspeed(), 65, 65);
				actionPanel.add(marioLabel);
			}

			actionPanel.revalidate();
			actionPanel.repaint();
		}
	}

	protected void playMario() {

		ArrayList<FABlock> dfaBlockList = new ArrayList<FABlock>();
		dfaBlockList = dfaBuilderModel.getDfaBlockList();

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("image/mario.png"));
		DFABuilderView.this.actionPanel.add(label);

		for (FABlock dfaBlock : dfaBlockList) {
			if (dfaBlock.isState()) {
				StateBlock stateBlock = (StateBlock) dfaBlock;

				// if(stateBlock.isInitial()){
				if (true) {

					HashMap<StateBlock, ArrayList<TransitionBlock>> stateTransitionList = stateBlock
							.getStateTransitionList();

					Iterator iter = stateTransitionList.keySet().iterator();
					while (iter.hasNext()) {
						StateBlock key = (StateBlock) iter.next();
						TransitionBlock value = stateTransitionList.get(key)
								.get(0);
						char check = value.getTransitionType().charAt(0);
						label.setBounds(stateBlock.getX(), stateBlock.getY(),
								50, 50);
						if (check == inputPathString.charAt(currentPathIndex)) { // matching
																					// transition
																					// found
							JLabel pathOutputLabel = new JLabel("");
							pathOutputLabel.setForeground(Color.GREEN);
							pathOutputLabel.setText(" " + check);
							output.add(pathOutputLabel);
							if (!dfaBuilderController.getTimerTask()
									.isRunning()) {

								dfaBuilderController.getTimerTask().run(label,
										stateBlock.getX(), stateBlock.getY(),
										key.getX(), key.getY());
								currentPathState = key;
							}
						}

						TransitionBlock val = (TransitionBlock) stateTransitionList
								.get(key).get(0);
					}
					break;
				}
			} else {

			}
		}
	}

	public void drawLine(String url2, JLabel stateTransit, int x1, int w1,
			int y1, int h1, int x2, int w2, int y2, int h2, Graphics g,
			String transitionType) {

		float[] dash1 = { 2f, 0f, 2f };
		BasicStroke bs1 = new BasicStroke(6, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER, 6.0f, dash1, 4f);

		g2d = (Graphics2D) g.create();
		Color myNewBlue = new Color(136, 69, 19); // creates your new color
		g2d.setColor(myNewBlue);

		g2d.setStroke(bs1);
		QuadCurve2D.Double curve = null;
		ArrayList<StateBlock> tempstartStateBlock = curves1
				.getStartStateBlock();
		ArrayList<StateBlock> tempfinalEndStateBlock = curves1
				.getFinalEndStateBlock();
		for (int i = 0; i < tempstartStateBlock.size(); i++) {
			StateBlock s = tempstartStateBlock.get(i);
			StateBlock f = tempfinalEndStateBlock.get(i);

			if (startStateBlock.equals(f) && (finalEndStateBlock.equals(s))) {
				isFinalState = true;
				if (x1 < x2) {
					curve = new QuadCurve2D.Double(x1 + w1, y1 + (h1 / 2),
							(x1 + x2) / 2, ((y2 + y1) / 2) + 120, x2, y2
									+ (h2 / 2));

				} else if (x1 > x2) {
					curve = new QuadCurve2D.Double(x2 + w2, y2 + (h2 / 2),
							(x1 + x2) / 2, ((y2 + y1) / 2) + 120, x1, y1
									+ (h1 / 2));

				} else if (x1 == x2 && y1 == y2) {
					curve = new QuadCurve2D.Double(x1, y1, (x1 + w1 - 20),
							y1 - 110, x1 + w1 - 5, y1);

				}

			}

			else {
				isFinalState = false;
			}

		}

		if (!isFinalState) {

			if (x1 < x2) {
				curve = new QuadCurve2D.Double(x1 + w1, y1 + (h1 / 2),
						(x1 + x2) / 2, ((y2 + y1) / 2) - 60, x2, y2 + (h2 / 2));

			} else if (x1 > x2) {
				curve = new QuadCurve2D.Double(x2 + w2, y2 + (h2 / 2),
						(x1 + x2) / 2, ((y2 + y1) / 2) - 60, x1, y1 + (h1 / 2));

			} else if (x1 == x2 && y1 == y2) {
				curve = new QuadCurve2D.Double(x1, y1, (x1 + w1 - 20),
						y1 - 110, x1 + w1 - 5, y1);

			}

		}
		int x = (int) curve.getCtrlX();
		int y = (int) curve.getCtrlY();
		int k = ((x1 - x2) < 0) ? -(x1 - x2) : (x1 - x2);
		if (k < 50) {
			y = y + 40;
		}

		stateTransit.setBounds(x, y, 50, 50);
		actionPanel.add(stateTransit);
		actionPanel.revalidate();
		actionPanel.repaint();
		db = dfaBuilderController.createBlockObj(transitURL, x, y, 50, 50,
				stateTransit, false, transitionType);
		dfaBuilderController.addTransitionBlocktoStateList(startStateBlock,
				finalEndStateBlock, (TransitionBlock) db);
		g2d.draw(curve);

		curves1.setCurves(curve);
		curves1.setStateTransits(stateTransit);

	}

	public String getInputPathString() {
		return inputPathString;
	}

	public void setInputPathString(String inputPathString) {
		this.inputPathString = inputPathString;
	}

	public void drawSelfLoop(Block b, Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		int x = b.getX();
		int y = b.getY() - b.getHeight() / 2;
		g2d.draw(new Arc2D.Double(x, y, 50, 50, 300, 300, Arc2D.OPEN));

	}

	public void addColorPallette(StateBlock firstBlock, StateBlock secondBlock) {
		db = new StateBlock();
		final int newX;
		final int newY;
		if (firstBlock == secondBlock) {
			newX = (((firstBlock.getX() + secondBlock.getX()) / 2) - 10);
			newY = (((firstBlock.getY() + firstBlock.getHeight())) + 20);
		} else {
			newX = ((firstBlock.getX() + secondBlock.getX() + firstBlock
					.getWidth()) / 2);
			newY = (((firstBlock.getY() + secondBlock.getY() + firstBlock
					.getHeight()) / 2) - 40);
		}
		colorPallete = new JPanel();
		colorPallete.setBorder(new TitledBorder("Select Transitions!"));
		mushroom1 = new JButton();
		/*
		 * g = new JLabel(); g.setHorizontalAlignment(SwingConstants.CENTER);
		 * g.setText(charList[0]); g.setFont(new Font("Serif", Font.BOLD, 16));
		 */
		FlowLayout f1 = new FlowLayout();
		mushroom1.setLayout(f1);
		if (charList[0].equals("a")) {
			mushroom1.setIcon(new ImageIcon("image/mushroom.png"));
			transitURL = "image/mushroom.png";

		} else if (charList[0].equals("b")) {
			mushroom1.setIcon(new ImageIcon("image/flower.png"));
			transitURL = "image/flower.png";
		} else if (charList[0].equals("c")) {
			mushroom1.setIcon(new ImageIcon("image/coin.png"));
			transitURL = "image/coin.png";
		} else {
			mushroom1.setIcon(new ImageIcon("image/enemy.png"));
			transitURL = "image/enemy.png";
		}
		// mushroom1.add(g);

		mushroom1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dfaBuilderController.checkErrors(startStateBlock,
						finalEndStateBlock, charList[0])) {
					noticeLabel.setVisible(false);
					degrees = dfaBuilderController.calculateSlope(
							startStateBlock.getX() + startStateBlock.getWidth(),
							startStateBlock.getY(), endStateBlock.getX(),
							endStateBlock.getY());

					stateTransit1 = new JLabel();
					if (charList[0].equals("a")) {
						// mushroom1.setIcon(new
						// ImageIcon("image/mushroom.png"));
						Icon = new ImageIcon("image/mushroom.png");
						transitURL = "image/mushroom.png";

					} else if (charList[0].equals("b")) {
						// mushroom1.setIcon(new ImageIcon("image/flower.png"));
						Icon = new ImageIcon("image/flower.png");
						transitURL = "image/flower.png";

					} else if (charList[0].equals("c")) {
						// mushroom1.setIcon(new ImageIcon("image/coin.png"));
						Icon = new ImageIcon("image/coin.png");
						transitURL = "image/coin.png";

					} else {
						// mushroom1.setIcon(new ImageIcon("image/enemy.png"));
						Icon = new ImageIcon("image/enemy.png");
						transitURL = "image/enemy.png";

					}
					// ImageIcon Icon = new ImageIcon("image/mushroom.png");
					RotatedIcon ri = new RotatedIcon(Icon, degrees);
					stateTransit1.setIcon(ri);
					// mushroomTransit1.setIcon(icon);
					stateTransit1.setLayout(new FlowLayout(FlowLayout.CENTER));
					stateTransit1.setHorizontalAlignment(SwingConstants.CENTER);
					stateTransit1.setFont(new Font("Serif", Font.BOLD, 16));
					actionPanel.remove(colorPallete);
					DFABuilderView.this.drawLine(transitURL, stateTransit1,
							startStateBlock.getX(), startStateBlock.getWidth(),
							startStateBlock.getY(),
							startStateBlock.getHeight(),
							finalEndStateBlock.getX(),
							finalEndStateBlock.getWidth(),
							finalEndStateBlock.getY(),
							finalEndStateBlock.getHeight(),
							actionPanel.getGraphics(), charList[0]);
					curves1.setStartStateBlock(startStateBlock);
					curves1.setFinalEndStateBlock(finalEndStateBlock);
					actionPanel.revalidate();
					repaint();
				} else {
					showMessage(true, "Cannot add similar transitions");
					colorPallete.remove(mushroom1);
					actionPanel.remove(colorPallete);
					actionPanel.revalidate();
					repaint();
				}
			}
		});
		mushroom2 = new JButton();

		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER);
		mushroom2.setLayout(f2);
		if (charList[1].equals("a")) {
			mushroom2.setIcon(new ImageIcon("image/mushroom.png"));
			transitURL = "image/mushroom.png";
		} else if (charList[1].equals("b")) {
			mushroom2.setIcon(new ImageIcon("image/flower.png"));
			transitURL = "image/flower.png";
		} else if (charList[1].equals("c")) {
			mushroom2.setIcon(new ImageIcon("image/coin.png"));
			transitURL = "image/coin.png";
		} else {
			mushroom2.setIcon(new ImageIcon("image/enemy.png"));
			transitURL = "image/enemy.png";
		}
		mushroom2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dfaBuilderController.checkErrors(startStateBlock,
						finalEndStateBlock, charList[1])) {
					noticeLabel.setVisible(false);
					degrees = dfaBuilderController.calculateSlope(
							startStateBlock.getX() + startStateBlock.getWidth(),
							startStateBlock.getY(), endStateBlock.getX(),
							endStateBlock.getY());

					stateTransit2 = new JLabel();

					// ImageIcon Icon = new ImageIcon("image/mushroom.png");
					if (charList[1].equals("a")) {
						// mushroom1.setIcon(new
						// ImageIcon("image/mushroom.png"));
						Icon = new ImageIcon("image/mushroom.png");
						transitURL = "image/mushrooom.png";
					} else if (charList[1].equals("b")) {
						// mushroom1.setIcon(new ImageIcon("image/flower.png"));
						Icon = new ImageIcon("image/flower.png");
						transitURL = "image/flower.png";
					} else if (charList[1].equals("c")) {
						// mushroom1.setIcon(new ImageIcon("image/coin.png"));
						Icon = new ImageIcon("image/coin.png");
						transitURL = "image/coin.png";
					} else {
						// mushroom1.setIcon(new ImageIcon("image/enemy.png"));
						Icon = new ImageIcon("image/enemy.png");
						transitURL = "image/enemy.png";
					}
					RotatedIcon ri = new RotatedIcon(Icon, degrees);
					stateTransit2.setIcon(ri);

					JLabel text = new JLabel();
					actionPanel.remove(colorPallete);
					DFABuilderView.this.drawLine(transitURL, stateTransit2,
							startStateBlock.getX(), startStateBlock.getWidth(),
							startStateBlock.getY(),
							startStateBlock.getHeight(),
							finalEndStateBlock.getX(),
							finalEndStateBlock.getWidth(),
							finalEndStateBlock.getY(),
							finalEndStateBlock.getHeight(),
							actionPanel.getGraphics(), charList[1]);
					curves1.setStartStateBlock(startStateBlock);
					curves1.setFinalEndStateBlock(finalEndStateBlock);
					actionPanel.revalidate();
					repaint();
				} else {
					showMessage(true, "Cannot add similar transitions");
					colorPallete.remove(mushroom2);
					actionPanel.remove(colorPallete);
					actionPanel.revalidate();
					repaint();
				}

			}
		});

		colorPallete.setLayout(new GridLayout(1, 2));
		colorPallete.add(mushroom1);
		colorPallete.add(mushroom2);
		colorPallete.setBounds(newX - 50, newY, 120, 100);
		actionPanel.add(colorPallete);

	}

	public void removeFromPanel(FABlock dfaBlock) {
		String name = dfaBlock.getName();
		JLabel msg = new JLabel(++activityNo + ". " + name
				+ " is removed from the panel");
		msg.setFont(new Font("Serif", Font.BOLD, 16));
		msg.setForeground(Color.black);
		panelActivity.add(msg);
		panelActivity.revalidate();
		panelActivity.repaint();

		dfaBlock.getDfaLabel().setBounds(0, 0, 0, 0);
		this.remove(dfaBlock.getDfaLabel());
		repaint();
	}

	public void addToPanel(FABlock dfaBlock) {
		dfaBlock.getDfaLabel().setBounds(dfaBlock.getX(), dfaBlock.getY(),
				dfaBlock.getWidth(), dfaBlock.getHeight());
		String name = dfaBlock.getName();
		JLabel msg = new JLabel(++activityNo + ". " + name
				+ " is added to the panel");
		msg.setFont(new Font("Serif", Font.BOLD, 16));
		msg.setForeground(Color.black);
		panelActivity.add(msg);
		panelActivity.revalidate();
		panelActivity.repaint();
		actionPanel.add(dfaBlock.getDfaLabel());
	}

	public boolean isCursorflag() {
		return cursorflag;
	}

	public void setCursorflag(boolean cursorflag) {
		this.cursorflag = cursorflag;
	}

	class DropTargetListener2 implements DropTargetListener {

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {

		}

		@Override
		public void dragExit(DropTargetEvent dtde) {
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			faBlock = new StateBlock();
			showMessage(false, "");

			if (dragSource.getName().equals("leftPanelState")) {
				actionstate = new JLabel("");

				actionstate.setName("actiontransition"
						+ BlockBuilderModel.stateNo);
				actionstate.setIcon(new ImageIcon("image/tunnel.png"));
				stateURL = "image/tunnel.png";
				actionstate.addMouseListener(listener);
				actionstate.setTransferHandler(new TransferHandler("text"));
				actionstate.setBounds(dtde.getLocation().x,
						dtde.getLocation().y, 50, 93);

				BoxLayout box = new BoxLayout(actionstate, BoxLayout.PAGE_AXIS);
				FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
				fl.setVgap(30);

				actionstate.setLayout(fl);

				JLabel text = new JLabel("q" + DFABuilderModel.stateNo);
				text.setForeground(Color.BLACK);

				text.setFont(new Font("Serif", Font.BOLD, 20));
				addicons(actionstate);

				actionstate.add(text);
				actionPanel.add(actionstate);

				faBlock = dfaBuilderController.createBlockObj(stateURL,
						dtde.getLocation().x, dtde.getLocation().y, 50, 93,
						actionstate, true, null);
				faBlock.setDfaLabelURL("image/tunnel.png");

			} else {

				faBlock = dfaBuilderController.updateBlockObj(dtde.getLocation().x,
						dtde.getLocation().y, dfaBlock);
				faBlock.setDfaLabelURL("image/tunnel.png");
				ArrayList<QuadCurve2D.Double> curves2 = curves1.getCurves();
				ArrayList<StateBlock> startStateBlock1 = curves1
						.getStartStateBlock();
				ArrayList<StateBlock> finalEndStateBlock1 = curves1
						.getFinalEndStateBlock();
				ArrayList<JLabel> sTransit = curves1.getStateTransits();
				int x1 = 0, w1 = 0, y1 = 0, h1 = 0, x2 = 0, w2 = 0, y2 = 0, h2 = 0;
				QuadCurve2D.Double curve = null;

				if (faBlock.isState()) {
					for (int i = 0; i < startStateBlock1.size(); i++) {
						StateBlock s = startStateBlock1.get(i);
						StateBlock f = finalEndStateBlock1.get(i);

						if (s.equals(faBlock)) {
							System.out.println("1");

							x1 = faBlock.getX();
							w1 = faBlock.getWidth();
							y1 = faBlock.getY();
							h1 = faBlock.getHeight();
							x2 = f.getX();
							w2 = f.getWidth();
							y2 = f.getY();
							h2 = f.getHeight();
							for (int i1 = 0; i1 < startStateBlock1.size(); i1++)

							{

								StateBlock s1 = startStateBlock1.get(i1);
								StateBlock f1 = finalEndStateBlock1.get(i1);

								if (s.equals(f1)) {

									if (s1.equals(f)) {
										depth = 120;

									}

									else {
										depth = -60;
									}
								}
							}

							if (x1 < x2) {
								curve = new QuadCurve2D.Double(x1 + w1, y1
										+ (h1 / 2), (x1 + x2) / 2,
										((y2 + y1) / 2) - 60, x2, y2 + (h2 / 2));

							} else if (x1 > x2) {
								curve = new QuadCurve2D.Double(x2 + w2, y2
										+ (h2 / 2), (x1 + x2) / 2,
										((y2 + y1) / 2) - 60, x1, y1 + (h1 / 2));

							} else if (x1 == x2 && y1 == y2) {
								curve = new QuadCurve2D.Double(x1, y1,
										(x1 + w1 - 20), y1 - 110, x1 + w1 - 5,
										y1);

							}
							curves1.getCurves().set(i, curve);
							JLabel transit = sTransit.get(i);

							degrees = dfaBuilderController.calculateSlope(
									s.getX() + s.getWidth(), s.getY(),
									f.getX(), f.getY());
							int x = (int) curve.getCtrlX();
							int y = (int) curve.getCtrlY();

							int k = ((x1 - x2) < 0) ? -(x1 - x2) : (x1 - x2);
							if (k < 50) {
								y = y + 40;
							}

							for (FABlock dfaBlockObj : dfaBuilderModel
									.getDfaBlockList()) {

								if (transit.equals(dfaBlockObj.getDfaLabel())) {

									JLabel temp = dfaBlockObj.getDfaLabel();
									ImageIcon icon = new ImageIcon(
											dfaBlockObj.getDfaLabelURL());
									RotatedIcon r = new RotatedIcon(icon,
											degrees);
									temp.setIcon(r);

									temp.setBounds(x, y, 50, 50);
									dfaBlockObj.setX(x);
									dfaBlockObj.setX(y);
									dfaBlockObj.setDfaLabel(temp);
									actionPanel.remove(transit);
									actionPanel.revalidate();
									actionPanel.repaint();

								}

							}

						} else if (f.equals(faBlock)) {
							System.out.println("2");
							x1 = s.getX();
							w1 = s.getWidth();
							y1 = s.getY();
							h1 = s.getHeight();
							x2 = faBlock.getX();
							w2 = faBlock.getWidth();
							y2 = faBlock.getY();
							h2 = faBlock.getHeight();
							JLabel transit1 = sTransit.get(i);
							for (int i1 = 0; i1 < startStateBlock1.size(); i1++)

							{

								StateBlock s1 = startStateBlock1.get(i1);
								StateBlock f1 = finalEndStateBlock1.get(i1);

								if (f.equals(s1)) {

									if (f1.equals(s)) {
										depth = 120;

									}

									else {
										depth = -60;
									}
								}
							}

							if (x1 < x2) {
								curve = new QuadCurve2D.Double(x1 + w1, y1
										+ (h1 / 2), (x1 + x2) / 2,
										((y2 + y1) / 2) + depth, x2, y2
												+ (h2 / 2));

							} else if (x1 > x2) {
								curve = new QuadCurve2D.Double(x2 + w2, y2
										+ (h2 / 2), (x1 + x2) / 2,
										((y2 + y1) / 2) + depth, x1, y1
												+ (h1 / 2));

							} else if (x1 == x2 && y1 == y2) {
								curve = new QuadCurve2D.Double(x1, y1,
										(x1 + w1 - 20), y1 - 110, x1 + w1 - 5,
										y1);

							}

							curves1.getCurves().set(i, curve);

							degrees = dfaBuilderController.calculateSlope(
									s.getX() + s.getWidth(), s.getY(),
									f.getX(), f.getY());
							int x = (int) curve.getCtrlX();
							int y = (int) curve.getCtrlY();

							int k = ((x1 - x2) < 0) ? -(x1 - x2) : (x1 - x2);
							if (k < 50) {
								y = y + 40;
							}

							for (FABlock dfaBlockObj : dfaBuilderModel
									.getDfaBlockList()) {

								if (transit1.equals(dfaBlockObj.getDfaLabel())) {

									JLabel temp = dfaBlockObj.getDfaLabel();
									ImageIcon icon = new ImageIcon(
											dfaBlockObj.getDfaLabelURL());
									RotatedIcon r = new RotatedIcon(icon,
											degrees);
									temp.setIcon(r);

									temp.setBounds(x, y, 50, 50);
									dfaBlockObj.setX(x);
									dfaBlockObj.setX(y);

									dfaBlockObj.setDfaLabel(temp);
									actionPanel.remove(transit1);
									actionPanel.revalidate();
									actionPanel.repaint();

								}

							}

						}
					}

				}
			}

			for (FABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {

				JLabel label = dfaBlockObj.getDfaLabel();

				if (dfaBlockObj.isState()) {
					if (((StateBlock) dfaBlockObj).isInitial()) {
						label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(),
								76, 96);

					}

					else if (((StateBlock) dfaBlockObj).isFinal()) {
						label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(),
								76, 106);

					}

					else {

						label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(),
								dfaBlockObj.getWidth(), dfaBlockObj.getHeight());

					}
				}

				label.setTransferHandler(new TransferHandler("text"));

				actionPanel.add(label);
			}

			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			dtde.getDropTargetContext().dropComplete(true);

			// DisplayOutput();

			actionPanel.revalidate();
			actionPanel.repaint();
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}
	}

	@Override
	public DFABuildViewInterface getViewType() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void moveState(JLabel label, int x1, int y1, int x2, int y2) {
		label.setBounds(x1, y1, 50, 50);
		if (x1 == x2 && y1 == y2) {
			dfaBuilderController.getTimerTask().stop();
			// if its not last state and mario has to move again
			dfaBuilderController.movetoNextState();
		}
		actionPanel.revalidate();
		repaint();
	}

	public void moveSelfloop(JLabel label, int x1, int y1, int x2, int y2) {

		label.setBounds(x1, y1, 50, 50);

		if (x1 == x2 && y1 == y2
				&& dfaBuilderController.getTimerTask().jumpUp == 0) {
			dfaBuilderController.getTimerTask().jumpUp = 1;
			dfaBuilderController.getTimerTask().y2 += 100;
		}

		else if (x1 == x2 && y1 == y2
				&& dfaBuilderController.getTimerTask().jumpUp == 1) {
			// dfaBuilderController.getTimerTask().jumpUp = 2;
			dfaBuilderController.getTimerTask().jumpUp = 0;
			dfaBuilderController.getTimerTask().stop();
			dfaBuilderController.movetoNextState();

		}
		actionPanel.revalidate();
	}

	public void switchToActionPanel(StateBlock stateBlock) {
		System.out.println("in switch toAction");
		contentPanel.remove(playPanel);
		contentPanel.add(actionPanel);

		currentPathState = stateBlock;
		mario.setState("FALLING");
		marioLabel = mario.getMario();
		marioLabel
				.setBounds(stateBlock.getX(), stateBlock.getY() - 150, 65, 65);

		if (stateBlock.isFinal()) {
			output.removeAll();
			JLabel finish = new JLabel();
			finish.setText("Final State Reached");
			finish.setFont(new Font("Tahoma", Font.PLAIN, 18));

			mario.setState("FINISH");
			isTestClicked = false; // so that he mario doesnt go to playview
									// again.
			marioLabel.setBounds(stateBlock.getX(), stateBlock.getY() - 50, 65,
					65);
		}

		timer.start();

		contentPanel.revalidate();
		contentPanel.repaint();

	}

}
