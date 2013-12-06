package com.turingworld.views;

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

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.turingworld.controller.BlockBuilderController;
import com.turingworld.model.Block;
import com.turingworld.model.BlockBuilderModel;

public class BlockBuilderView extends JFrame implements BuildViewInterface {

	private JPanel contentPane;
	private JPanel actionPanel;
	private DropTarget dropTarget;
	private MouseListener listener;
	private MouseListener undoMouseListener;
	private MouseListener redoMouseListener;
	private MouseListener snapshotMouseListener;
	private JLabel transition;
	private JLabel undo;
	private JLabel state;
	private JLabel redo;
	private JLabel eraser;
	private JLabel snapshot;
	private JLabel dragSource;
	private JLabel noticeLabel;
	private JLabel snapLabel;
	private int activityNo = 0;
	private JScrollPane scrollPane;
	private JPanel panelActivity;
	private JTextField field;
	private int transitionX;
	private int transitionY;
	private Block b;
	private JLabel actiontransition;
	private JLabel actionstate;
	private JPanel colorPallete;
	private String textFieldValue = null;
	public Collection<JLabel> actionicons = new ArrayList<JLabel>();
	public Collection<JLabel> outputicons = new ArrayList<JLabel>();
	public int count = 0;
	private boolean cursorflag = false;
	private BlockBuilderModel blockBuilderModel;
	private Block block;
	private BlockBuilderController blockBuilderController;
	private JLabel outputLabel;
	private JButton playAgain;
	private JPanel panel_1;
	private JScrollPane scrollPane_1;
	private JPanel output;
	private JPanel panel_2;
	private JPanel snapShotPanel;
	private JLabel g;
	private JLabel r;
	private JLabel c;
	private JLabel y;

	/**
	 * Launch the application.
	 */
	public void addoutputicons(JLabel icon) {
		outputicons.add(icon);
	}

	public void addicons(JLabel icon) {
		actionicons.add(icon);
	}

	public void setController(BlockBuilderController blockBuilderController) {
		this.blockBuilderController = blockBuilderController;
	}

	public void playSound(String audioFile)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File(audioFile));

			Clip clip = AudioSystem.getClip();

			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException uae) {
			System.out.println(uae);
		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (LineUnavailableException lua) {
			System.out.println(lua);
		}
	}

	/**
	 * Create the frame.
	 */
	public BlockBuilderView(BlockBuilderModel blockBuilderModel) {
		this.blockBuilderModel = blockBuilderModel;
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

		this.setVisible(true);
		setTitle("Welcome - Build your Blocks!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 0, 1251, 719);

		// method to create the view
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setToolTipText("");
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		creatHeaderMenu();
		createTabbedView();
		createLeftPaneView();
		createActionView();
		panel_1 = new JPanel();
		panel_1.setBounds(155, 515, 785, 150);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setViewportBorder(new TitledBorder(null, "",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(scrollPane_1);

		output = new JPanel();

		output.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Output",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		scrollPane_1.setViewportView(output);

		panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		panel_2.setBounds(939, 0, 296, 517);
		contentPane.add(panel_2);
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

		snapShotPanel = new JPanel();
		snapShotPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Snap Shot ",
				TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, null));
		snapShotPanel.setLayout(new BorderLayout());
		snapShotPanel.setBounds(939, 515, 296, 148);
		contentPane.add(snapShotPanel);
		actionPanel = new JPanel();
		actionPanel.setBounds(155, 3, 785, 514);
		contentPane.add(actionPanel);
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

		dropTarget = new DropTarget(this.actionPanel, new DropTargetListener2());

		repaint();
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

	public void createNewWindow() {
		boolean isTriviaClicked = false;
		if (blockBuilderController.isTriviaClicked) {
			isTriviaClicked = true;
		}
		blockBuilderModel = new BlockBuilderModel();
		BlockBuilderView blockBuilderView = new BlockBuilderView(
				blockBuilderModel);
		blockBuilderController = new BlockBuilderController(blockBuilderModel,
				blockBuilderView);
		blockBuilderView.setController(blockBuilderController);
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

	public void drawCurve(int x1, int y1, int x2, int y2, Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		QuadCurve2D.Double curve = new QuadCurve2D.Double(x1, y1,
				(x1 + x2) / 2, ((y2 + y1) / 2) - 60, x2, y2);
		g2d.draw(curve);
	}

	public void drawSelfLoop(Block b, Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		int x = b.getX();
		int y = b.getY() - b.getHeight() / 2;
		g2d.draw(new Arc2D.Double(x, y, 50, 50, 300, 300, Arc2D.OPEN));

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

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				blockBuilderController.saveTuringWorld();
			}
		});

		mntmSave.setIcon(new ImageIcon("image/SaveIcon.png"));
		menuBar.add(mntmSave);

		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String choosertitle = null;
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(choosertitle);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(BlockBuilderView.this);

				File file = null;
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
				} else {
				}

				blockBuilderModel = blockBuilderController
						.loadTuringWorld(file);
				loadpaint(blockBuilderModel);

			}
		});
		mntmLoad.setIcon(new ImageIcon("image/loadIcon.jpg"));
		menuBar.add(mntmLoad);

		JMenuItem mntmTrivia = new JMenuItem("Trivia");
		mntmTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFromPanel();
				createTriviaView();
			}

		});
		mntmTrivia.setIcon(new ImageIcon("image/exclamationIcon.png"));
		menuBar.add(mntmTrivia);

		JMenuItem mntmTutorial = new JMenuItem("Tutorial");
		mntmTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Block b1 = blockBuilderModel.getBlockList().get(0);
				Block b2 = blockBuilderModel.getBlockList().get(1);
				boolean switched = false;

				int x1 = 0;
				int x2 = 0;
				int y1 = 0;
				int y2 = 0;

				if (b1.getY() < b2.getY() && b1.getX() < b2.getX()) {
					x1 = b1.getX() + b1.getWidth();
					x2 = b2.getX();
					y1 = b1.getY();
					y2 = b2.getY();
					System.out.println("Normal routine");
				}

				else {
					x1 = b2.getX() + b2.getWidth();
					x2 = b1.getX();
					y1 = b2.getY();
					y2 = b1.getY();
					switched = true;
					System.out.println("Switched routine");
				}

				drawCurve(x1, y1, x2, y2, actionPanel.getGraphics());
				drawSelfLoop(b1, actionPanel.getGraphics());
				JLabel label = new JLabel();
				label.setIcon(new ImageIcon("image/mario.png"));
				BlockBuilderView.this.actionPanel.add(label);
				label.setBounds(x1, y1, 50, 50);
				/*
				 * blockBuilderController.getTimerTask() .run(label, x1, y1, x2,
				 * y2, switched);
				 */
				// moveState(x1, y1, x2, y2);

			}
		});
		mntmTutorial.setIcon(new ImageIcon("image/helpIcon.png"));
		menuBar.add(mntmTutorial);
	}

	public void moveState(JLabel label, int x1, int y1, int x2, int y2) {

		label.setBounds(x1, y1, 50, 50);
		if (x1 == x2 || y1 == y2)
			blockBuilderController.getTimerTask().stop();
		actionPanel.revalidate();
		// repaint();

	}
	
	private void createLevel1View() {
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
		txtrCanYouBuild
				.setText("Can you build a machine with equal number of A's and B's ?");
		txtrCanYouBuild.setEditable(false);
		txtrCanYouBuild.setBounds(51, 40, 273, 46);
		panelAmateur.add(txtrCanYouBuild);

		JButton yesBtn = new JButton("Lets Do it!");

		yesBtn.setBounds(150, 97, 89, 23);
		yesBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BlockBuilderController.isTriviaClicked = true;
				removeFromPanel();
				createNewWindow();
				actionPanel.revalidate();
				repaint();

			}
		});
		panelAmateur.add(yesBtn);

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

	private void createActionView() {
	}

	private void createTabbedView() {

	}

	public void loadpaint(BlockBuilderModel blockbuildermodel1) {
		actionPanel.removeAll();
		// this.blockBuilderModel.setBlockList(blockbuildermodel1.getBlockList());
		blockBuilderController.setBlockBuilderModel(blockbuildermodel1);
		for (Block block : blockbuildermodel1.getBlockList()) {
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
			label.setName("transition0");
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
			label.addMouseListener(listener);
			actionPanel.add(label);
		}

		actionPanel.revalidate();
		actionPanel.repaint();
	}

	private void DisplayOutput() {
		output.removeAll();
		output.revalidate();
		output.repaint();
		StringBuilder str = blockBuilderController.getOutputString();

		FlowLayout fl = new FlowLayout();
		fl.setVgap(10);
		for (int i = 0; i < str.length(); i++) {

			JLabel tempicon = new JLabel();
			tempicon.setLayout(fl);
			String temp = Character.toString(str.charAt(i));
			JLabel text = new JLabel(temp);
			text.setForeground(Color.BLACK);
			text.setFont(new Font("Serif", Font.BOLD, 25));

			tempicon.add(text);
			tempicon.setIcon(new ImageIcon("image/outputBlock.png"));
			actionicons.add(tempicon);
			output.add(tempicon);
		}
		if (BlockBuilderController.isTriviaClicked) {
			int noOfAs = 0;
			int noOfBs = 0;
			for (int index = 0; index < str.length(); index++) {
				if (str.charAt(index) == 'A') {
					noOfAs++;
				}
				if (str.charAt(index) == 'B') {
					noOfBs++;
				}
			}

			if (noOfAs == noOfBs) {
				setResult("Correct!");

			} else {
				setResult("Sorry. Wrong Answer!");
			}
		}
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

	private void createLeftPaneView() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(0, 3, 153, 662);
		contentPane.add(panel);
		panel.setLayout(null);

		transition = new JLabel("TransitionA");
		transition.setName("leftPanelTransitionA");
		transition.setToolTipText("Transition\r\n\r\nA");
		transition.setIcon(new ImageIcon("image/transition.png"));
		transition.setBounds(41, 110, 75, 73);
		transition.setTransferHandler(new TransferHandler("text"));
		panel.add(transition);

		undo = new JLabel("undo");
		undo.setIcon(new ImageIcon("image/undo.png"));
		undo.setToolTipText("Undo");
		undo.setName("undo");
		undo.setBounds(41, 212, 75, 73);
		undo.setTransferHandler(new TransferHandler("text"));
		panel.add(undo);

		state = new JLabel("state");
		state.setName("leftPanelState");
		state.setToolTipText("State");
		state.setIcon(new ImageIcon("image/state1.png"));
		state.setBounds(41, 11, 75, 73);
		state.setTransferHandler(new TransferHandler("text"));
		panel.add(state);

		redo = new JLabel("redo");
		redo.setToolTipText("Redo");
		redo.setIcon(new ImageIcon("image/redo.png"));
		redo.setBounds(41, 313, 75, 75);
		panel.add(redo);

		eraser = new JLabel("eraser");
		eraser.setToolTipText("Eraser\r\n");
		eraser.setIcon(new ImageIcon("image/delete.png"));
		eraser.setBounds(41, 429, 75, 75);
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

		snapshot = new JLabel("snapShot");
		snapshot.setToolTipText("Shap Shot");
		snapshot.setIcon(new ImageIcon("image/camera.png"));
		snapshot.setBounds(41, 544, 75, 75);
		panel.add(snapshot);

		undoMouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				blockBuilderController.removeBlock();
				blockBuilderController.sortTransitions();
				DisplayOutput();
			}
		};

		redoMouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				blockBuilderController.redoBlock();

				blockBuilderController.sortTransitions();

				DisplayOutput();
			}
		};

		snapshotMouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {

				if (actionPanel.getComponentCount() == 0) {
					JLabel msg = new JLabel(
							++activityNo
									+ ". Please create the blocks before taking a snaps");
					msg.setFont(new Font("Serif", Font.BOLD, 16));
					msg.setForeground(Color.red);
					panelActivity.add(msg);

				}

				else {
					snapShotPanel.removeAll();

				}

				actionPanel.removeAll();
				actionPanel.revalidate();
				actionPanel.repaint();
				snapShotPanel.revalidate();
				snapShotPanel.repaint();
				try {
					playSound("./audio/camera.wav");
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				snapLabel = new JLabel();
				snapLabel.setName("mini");
				snapLabel.setLayout(new FlowLayout());
				snapLabel.setTransferHandler(new TransferHandler("text"));
				snapLabel.addMouseListener(listener);
				snapShotPanel.add(snapLabel, BorderLayout.CENTER);

				int stateNo = 0;

				for (Block block : blockBuilderModel.getBlockList()) {

					JLabel blockLabel = new JLabel();
					blockLabel.setLayout(new FlowLayout());
					blockLabel.setTransferHandler(new TransferHandler("text"));
					blockLabel.setSize(30, 20);

					JLabel textLabel = new JLabel();
					textLabel.setHorizontalAlignment(SwingConstants.CENTER);
					textLabel.setFont(new Font("Serif", Font.BOLD, 12));

					if (block.isState()) {
						blockLabel
								.setIcon(new ImageIcon("image/mini_state.png"));
						textLabel.setText("q" + stateNo++);
						textLabel.setForeground(Color.white);
						blockLabel.setLayout(new BorderLayout());
						blockLabel.add(textLabel, BorderLayout.CENTER);
					} else {
						if (block.getTransitionType().equals("A")) {
							blockLabel.setIcon(new ImageIcon(
									"image/mini_transitionA.png"));
							textLabel.setText("A");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("B")) {
							blockLabel.setIcon(new ImageIcon(
									"image/mini_transitionB.png"));
							textLabel.setText("B");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("C")) {
							blockLabel.setIcon(new ImageIcon(
									"image/mini_transitionC.png"));
							textLabel.setText("C");
							blockLabel.add(textLabel);
						} else {
							blockLabel.setIcon(new ImageIcon(
									"image/mini_transitionD.png"));
							textLabel.setText("D");
							blockLabel.add(textLabel);
						}
					}
					snapLabel.add(blockLabel);
					blockBuilderModel.getMiniBlockList().add(block);

					blockBuilderController.setOutputString(new StringBuilder());

					DisplayOutput();

					snapShotPanel.revalidate();
					snapShotPanel.repaint();
				}
				blockBuilderModel.getBlockList().clear();
			}
		};

		listener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {

				dragSource = (JLabel) me.getSource();

				if (isCursorflag() == true) {
					if (blockBuilderController.getBlockObject(
							dragSource.getX(), dragSource.getY()) == true) {
						block = blockBuilderController.getBlockObj(
								dragSource.getX(), dragSource.getY());
						blockBuilderController
								.removeBlockFromListAndPanel(block);
						blockBuilderController.sortTransitions();
						DisplayOutput();
					}
				} else {
					TransferHandler handler = dragSource.getTransferHandler();
					handler.exportAsDrag(dragSource, me, TransferHandler.COPY);
					block = blockBuilderController.getBlockObj(
							dragSource.getX(), dragSource.getY());

				}
			}
		};

		transition.addMouseListener(listener);
		undo.addMouseListener(undoMouseListener);
		snapshot.addMouseListener(snapshotMouseListener);
		redo.addMouseListener(redoMouseListener);
		state.addMouseListener(listener);
	}

	public void addColorPallette(Block b2) {
		colorPallete = new JPanel();
		colorPallete.setBorder(new TitledBorder("Color your Block!"));
		JButton green = new JButton();
		g = new JLabel();
		g.setHorizontalAlignment(SwingConstants.CENTER);
		g.setText("D");
		g.setFont(new Font("Serif", Font.BOLD, 30));
		green.setLayout(new BorderLayout());
		green.add(g, BorderLayout.CENTER);
		green.setBackground(Color.green);
		green.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actiontransition
						.setIcon(new ImageIcon("image/transitionD.png"));
				actiontransition.add(g, BorderLayout.CENTER);
				b.setTransitionType("D");
				b.setBlockLabelURL("image/transitionD.png");
				actionPanel.remove(colorPallete);
				actionPanel.revalidate();
				repaint();

			}
		});
		JButton red = new JButton();
		r = new JLabel();
		r.setText("B");
		r.setHorizontalAlignment(SwingConstants.CENTER);
		r.setFont(new Font("Serif", Font.BOLD, 30));
		red.setLayout(new BorderLayout());
		red.add(r, BorderLayout.CENTER);
		red.setBackground(Color.red);
		red.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actiontransition
						.setIcon(new ImageIcon("image/transitionB.png"));
				actiontransition.add(r, BorderLayout.CENTER);
				b.setTransitionType("B");
				b.setBlockLabelURL("image/transitionB.png");
				actionPanel.remove(colorPallete);
				actionPanel.revalidate();
				repaint();
			}
		});
		JButton yellow = new JButton();
		yellow.setBackground(Color.yellow);
		y = new JLabel();
		y.setText("A");
		y.setFont(new Font("Serif", Font.BOLD, 30));
		y.setHorizontalAlignment(SwingConstants.CENTER);
		yellow.setLayout(new BorderLayout());
		yellow.add(y, BorderLayout.CENTER);
		yellow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actiontransition
						.setIcon(new ImageIcon("image/transitionA.png"));
				actiontransition.add(y, BorderLayout.CENTER);
				b.setTransitionType("A");
				b.setBlockLabelURL("image/transitionA.png");
				actionPanel.remove(colorPallete);
				actionPanel.revalidate();
				repaint();
			}
		});
		JButton cyan = new JButton();
		c = new JLabel();
		c.setText("C");
		c.setHorizontalAlignment(SwingConstants.CENTER);
		c.setFont(new Font("Serif", Font.BOLD, 30));
		cyan.setLayout(new BorderLayout());
		cyan.add(c, BorderLayout.CENTER);

		cyan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actiontransition
						.setIcon(new ImageIcon("image/transitionC.png"));
				b.setTransitionType("C");
				b.setBlockLabelURL("image/transitionC.png");
				JLabel msg = new JLabel(++activityNo
						+ ". C is added to the panel");
				msg.setFont(new Font("Serif", Font.BOLD, 16));
				msg.setForeground(Color.black);
				panelActivity.add(msg);
				actiontransition.add(c, BorderLayout.CENTER);
				actionPanel.remove(colorPallete);
				actionPanel.revalidate();
				repaint();
			}
		});
		cyan.setBackground(Color.CYAN);

		colorPallete.setLayout(new GridLayout(2, 2));
		colorPallete.add(yellow);
		colorPallete.add(red);
		colorPallete.add(cyan);
		colorPallete.add(green);
		colorPallete.setBounds(transitionX + 80, transitionY, 180, 120);
		actionPanel.add(colorPallete);
	}

	public void removeFromPanel(Block block) {

		String name = block.getName();
		JLabel msg = new JLabel(++activityNo + ". " + name
				+ " is removed from the panel");
		msg.setFont(new Font("Serif", Font.BOLD, 16));
		msg.setForeground(Color.black);
		panelActivity.add(msg);
		panelActivity.revalidate();
		panelActivity.repaint();
		block.getBlockLabel().setBounds(0, 0, 0, 0);
		this.remove(block.getBlockLabel());
		repaint();
	}

	public void addToPanel(Block block) {
		block.getBlockLabel().setBounds(block.getX(), block.getY(),
				block.getWidth(), block.getHeight());
		String name = block.getName();
		JLabel msg = new JLabel(++activityNo + ". " + name
				+ " is added to the panel");
		msg.setFont(new Font("Serif", Font.BOLD, 16));
		msg.setForeground(Color.black);
		panelActivity.add(msg);
		panelActivity.revalidate();
		panelActivity.repaint();
		actionPanel.add(block.getBlockLabel());

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
			b = new Block();
			showMessage(false, "");
			int stateNo = 0;
			if (dragSource.getName().contains("mini")) {
				b = blockBuilderModel.getMiniBlockList().get(0);
				int x = blockBuilderModel.getBlockList()
						.get(blockBuilderModel.getBlockList().size() - 1)
						.getX();
				int y = blockBuilderModel.getBlockList()
						.get(blockBuilderModel.getBlockList().size() - 1)
						.getY();

				int miniX = x;
				int miniY = y;

				if (blockBuilderModel.getBlockList()
						.get(blockBuilderModel.getBlockList().size() - 1)
						.isState() != blockBuilderModel.getMiniBlockList()
						.get(0).isState()) {
					miniX = x + 60;
				} else {
					miniX = x + 300;
				}

				for (Block block : blockBuilderModel.getMiniBlockList()) {
					JLabel blockLabel = new JLabel();
					blockLabel.setLayout(new FlowLayout());
					blockLabel.setTransferHandler(new TransferHandler("text"));
					blockLabel.setSize(75, 73);

					JLabel textLabel = new JLabel();
					textLabel.setHorizontalAlignment(SwingConstants.CENTER);

					blockLabel.setLayout(new BorderLayout());

					if (block.isState()) {
						blockLabel.setIcon(new ImageIcon("image/state.png"));
						textLabel.setText("q" + stateNo++);
						textLabel.setFont(new Font("Serif", Font.BOLD, 16));
						textLabel.setForeground(Color.white);

					} else {
						textLabel.setFont(new Font("Serif", Font.BOLD, 30));
						if (block.getTransitionType().equals("A")) {
							blockLabel.setIcon(new ImageIcon(
									"image/transitionA.png"));
							textLabel.setText("A");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("B")) {
							blockLabel.setIcon(new ImageIcon(
									"image/transitionB.png"));
							textLabel.setText("B");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("C")) {
							blockLabel.setIcon(new ImageIcon(
									"image/transitionC.png"));
							textLabel.setText("C");
							blockLabel.add(textLabel);
						} else {
							blockLabel.setIcon(new ImageIcon(
									"image/transitionD.png"));
							textLabel.setText("D");
							blockLabel.add(textLabel);
						}

					}
					blockLabel.add(textLabel, BorderLayout.CENTER);
					blockLabel.setBounds(miniX, miniY, 75, 73);
					block.setX(miniX);
					block.setY(miniY);
					blockBuilderModel.getBlockList().add(block);
					actionPanel.revalidate();
					actionPanel.repaint();
					miniX += 60;
				}
				addLabels();
			}

			else {

				if (dragSource.getName().equals("leftPanelTransitionA")) {

					if (blockBuilderModel.getBlockList().size() < 1) {
						showMessage(true,
								"First Block should be a State Block!");
					}
					transitionX = dtde.getLocation().x;
					transitionY = dtde.getLocation().y;
					actiontransition = new JLabel("transitionA");
					actiontransition.setName("actiontransition"
							+ (BlockBuilderModel.transitionNo));
					actiontransition.setIcon(new ImageIcon(
							"image/transition.png"));
					actiontransition
							.setBounds(transitionX, transitionY, 75, 73);

					actiontransition.addMouseListener(listener);
					actiontransition.setTransferHandler(new TransferHandler(
							"text"));
					addicons(actiontransition);
					actiontransition.setLayout(new BorderLayout());
					actionPanel.add(actiontransition);
					addColorPallette(b);
					b = blockBuilderController.createBlockObj(transitionX,
							transitionY, 75, 73, actiontransition, false,
							textFieldValue);

				}

				else if (dragSource.getName().equals("leftPanelState")) {
					actionstate = new JLabel("state");

					actionstate.setName("actiontransition"
							+ BlockBuilderModel.stateNo);
					actionstate.setIcon(new ImageIcon("image/state.png"));
					actionstate.addMouseListener(listener);
					actionstate.setTransferHandler(new TransferHandler("text"));
					actionstate.setBounds(dtde.getLocation().x,
							dtde.getLocation().y, 75, 73);
					FlowLayout fl = new FlowLayout();
					fl.setVgap(30);
					actionstate.setLayout(fl);
					JLabel text = new JLabel("q" + BlockBuilderModel.stateNo);
					text.setForeground(Color.white);
					text.setFont(new Font("Serif", Font.BOLD, 16));
					actionstate.add(text);
					addicons(actionstate);
					actionPanel.add(actionstate);
					b = blockBuilderController.createBlockObj(
							dtde.getLocation().x, dtde.getLocation().y, 75, 73,
							actionstate, true, null);
					b.setBlockLabelURL("image/state.png");

				} else {

					b = blockBuilderController.updateBlockObj(
							dtde.getLocation().x, dtde.getLocation().y, block);
					snapShotPanel.removeAll();

					// b.setBlockLabelURL("image/state.png");
				}

				Block adjacentBlock;

				if (blockBuilderController.isAdjacentToSimilarBlock(b)) {
					b.setX(b.getX() + 50);
					b.getBlockLabel().setLocation(b.getX(), b.getY());
					showMessage(true, " Cannot attach two similar blocks");
					try {
						playSound("./audio/repel.wav");
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				} else if (blockBuilderController
						.isAdjacentToDissimilarBlock(b) != null) {

					adjacentBlock = blockBuilderController
							.isAdjacentToDissimilarBlock(b);
					if (b.getX() > adjacentBlock.getX()) {
						b.setX(adjacentBlock.getX() + adjacentBlock.getWidth()
								- 15);
						b.setY(adjacentBlock.getY());
					} else {
						b.setX(adjacentBlock.getX() + adjacentBlock.getWidth()
								- 135);
						b.setY(adjacentBlock.getY());

					}
					try {
						playSound("./audio/attach.wav");
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				} else {
					try {
						playSound("./audio/beep_normal.wav");
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				}

				addLabels();

			}

			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			dtde.getDropTargetContext().dropComplete(true);

			blockBuilderController.sortTransitions();
			DisplayOutput();

			actionPanel.revalidate();
			actionPanel.repaint();
		}

		public void addLabels() {
			for (Block blockObj : blockBuilderModel.getBlockList()) {

				JLabel label = blockObj.getBlockLabel();
				label.setTransferHandler(new TransferHandler("text"));
				label.setBounds(blockObj.getX(), blockObj.getY(),
						blockObj.getWidth(), blockObj.getHeight());
				actionPanel.add(label);
			}
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}
	}

	@Override
	public BuildViewInterface getViewType() {
		// TODO Auto-generated method stub
		return this;
	}
}
