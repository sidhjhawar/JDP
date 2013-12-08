package com.turingworld.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.turingworld.controller.BlockBuilderController;
import com.turingworld.controller.ControllerHelper;
import com.turingworld.model.Block;
import com.turingworld.model.BlockBuilderModel;

@SuppressWarnings("serial")
public class BlockBuilderView extends JFrame implements BuildViewInterface {

	private DropTarget dropTarget;
	private MouseListener listener;
	private MouseListener undoMouseListener;
	private MouseListener redoMouseListener;
	private MouseListener snapshotMouseListener;

	private JLabel transitionLabel;
	private JLabel undoLabel;
	private JLabel stateLabel;
	private JLabel redoLabel;
	private JLabel eraserLabel;
	private JLabel snapshotLabel;
	private JLabel dragSource;
	private JLabel snapLabel;
	private JLabel actiontransition;
	private JLabel actionstate;
	private JLabel greenLabel;
	private JLabel redLabel;
	private JLabel cyanLabel;
	private JLabel yellowLabel;
	private JLabel outputLabel;

	private int activityNo = 0;
	private int transitionX;
	private int transitionY;

	private JPanel colorPallete;
	private String textFieldValue = null;
	public Collection<JLabel> actionicons = new ArrayList<JLabel>();
	public Collection<JLabel> outputicons = new ArrayList<JLabel>();
	public int count = 0;
	private boolean cursorflag = false;

	private JButton playAgain;

	private Block b;
	private BlockBuilderModel blockBuilderModel;
	private Block block;
	private BlockBuilderController blockBuilderController;

	private ControllerHelper controllerHelper;
	private ViewHelper viewHelper;

	public boolean isCursorflag() {
		return cursorflag;
	}

	public void setCursorflag(boolean cursorflag) {
		this.cursorflag = cursorflag;
	}

	public void addoutputicons(JLabel icon) {
		outputicons.add(icon);
	}

	public void addicons(JLabel icon) {
		actionicons.add(icon);
	}

	public void setController(BlockBuilderController blockBuilderController) {
		this.blockBuilderController = blockBuilderController;
	}

	/**
	 * Create the frame.
	 */
	public BlockBuilderView(BlockBuilderModel blockBuilderModel) {
		this.blockBuilderModel = blockBuilderModel;

		this.setVisible(true);
		setTitle("Welcome - Build your Blocks!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 0, 1251, 719);

		viewHelper = new ViewHelper();
		viewHelper.setContentPane();
		setContentPane(viewHelper.getContentPane());
		viewHelper.createWindow(viewHelper.getContentPane());

		creatHeaderMenu();
		createLeftPaneView();

		controllerHelper = new ControllerHelper();
		dropTarget = new DropTarget(viewHelper.getActionPanel(), new DropListener());

		repaint();
	}

	public void createNewWindow() {
		boolean isTriviaClicked = false;
		if (blockBuilderController.isTriviaClicked) {
			isTriviaClicked = true;
		}
		blockBuilderModel = new BlockBuilderModel();
		BlockBuilderView blockBuilderView = new BlockBuilderView(blockBuilderModel);
		blockBuilderController = new BlockBuilderController(blockBuilderModel, blockBuilderView);
		blockBuilderView.setController(blockBuilderController);
		BlockBuilderModel.stateNo = 0;
		BlockBuilderModel.transitionNo = 0;
		if (isTriviaClicked) {
			BlockBuilderController.isTriviaClicked = true;
		}
		dispose();
	}

	private void creatHeaderMenu() {

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		viewHelper.creatHeaderMenu(menuBar);

		JMenuItem newMenu = new JMenuItem("New");
		newMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewWindow();
			}
		});
		newMenu.setIcon(new ImageIcon("image/newIcon.png"));
		menuBar.add(newMenu);
	}

	public void createLevel1View() {
		JPanel panelAmateur = viewHelper.createLevel1View();
		JButton yesBtn = new JButton("Lets Do it!");
		yesBtn.setBounds(150, 97, 89, 23);
		yesBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BlockBuilderController.isTriviaClicked = true;
				viewHelper.removeFromPanel();
				createNewWindow();
				viewHelper.getActionPanel().revalidate();
				repaint();

			}
		});
		panelAmateur.add(yesBtn);
	}

	private void DisplayOutput() {
		viewHelper.getOutput().removeAll();
		viewHelper.getOutput().revalidate();
		viewHelper.getOutput().repaint();
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
			viewHelper.getOutput().add(tempicon);
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
				viewHelper.setResult("Correct!");

			} else {
				viewHelper.setResult("Sorry. Wrong Answer!");
			}
		}
	}

	private void createLeftPaneView() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftPanel.setBackground(new Color(211, 211, 211));
		leftPanel.setBounds(0, 3, 153, 662);
		viewHelper.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);

		transitionLabel = new JLabel("TransitionA");
		transitionLabel.setName("leftPanelTransitionA");
		transitionLabel.setToolTipText("Transition\r\n\r\nA");
		transitionLabel.setIcon(new ImageIcon("image/transition.png"));
		transitionLabel.setBounds(41, 110, 75, 73);
		transitionLabel.setTransferHandler(new TransferHandler("text"));
		leftPanel.add(transitionLabel);

		undoLabel = new JLabel("undo");
		undoLabel.setIcon(new ImageIcon("image/undo.png"));
		undoLabel.setToolTipText("Undo");
		undoLabel.setName("undo");
		undoLabel.setBounds(41, 212, 75, 73);
		undoLabel.setTransferHandler(new TransferHandler("text"));
		leftPanel.add(undoLabel);

		stateLabel = new JLabel("state");
		stateLabel.setName("leftPanelState");
		stateLabel.setToolTipText("State");
		stateLabel.setIcon(new ImageIcon("image/state1.png"));
		stateLabel.setBounds(41, 11, 75, 73);
		stateLabel.setTransferHandler(new TransferHandler("text"));
		leftPanel.add(stateLabel);

		redoLabel = new JLabel("redo");
		redoLabel.setToolTipText("Redo");
		redoLabel.setIcon(new ImageIcon("image/redo.png"));
		redoLabel.setBounds(41, 313, 75, 75);
		leftPanel.add(redoLabel);

		eraserLabel = new JLabel("eraser");
		eraserLabel.setToolTipText("Eraser\r\n");
		eraserLabel.setIcon(new ImageIcon("image/delete.png"));
		eraserLabel.setBounds(41, 429, 75, 75);
		eraserLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (isCursorflag() == false) {
					setCursorflag(true);
					// added check for MouseEvent.BUTTON1 which is left click
					if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON1) {
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
						Cursor cursor = kit.createCustomCursor(image, hotspot, "Stone");
						setCursor(cursor);

					}
				} else {
					setCursorflag(false);
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		leftPanel.add(eraserLabel);

		snapshotLabel = new JLabel("snapShot");
		snapshotLabel.setToolTipText("Shap Shot");
		snapshotLabel.setIcon(new ImageIcon("image/camera.png"));
		snapshotLabel.setBounds(41, 544, 75, 75);
		leftPanel.add(snapshotLabel);

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

				if (viewHelper.getActionPanel().getComponentCount() == 0) {
					JLabel msg = new JLabel(++activityNo + ". Please create the blocks before taking a snaps");
					msg.setFont(new Font("Serif", Font.BOLD, 16));
					msg.setForeground(Color.red);
					viewHelper.getPanelActivity().add(msg);

				}

				else {
					viewHelper.getSnapShotPanel().removeAll();
				}

				viewHelper.getActionPanel().removeAll();
				viewHelper.getActionPanel().revalidate();
				viewHelper.getActionPanel().repaint();
				viewHelper.getSnapShotPanel().revalidate();
				viewHelper.getSnapShotPanel().repaint();
				try {
					controllerHelper.playSound("./audio/camera.wav");
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}

				snapLabel = new JLabel();
				snapLabel.setName("mini");
				snapLabel.setLayout(new FlowLayout());
				snapLabel.setTransferHandler(new TransferHandler("text"));
				snapLabel.addMouseListener(listener);
				viewHelper.getSnapShotPanel().add(snapLabel, BorderLayout.CENTER);

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
						blockLabel.setIcon(new ImageIcon("image/mini_state.png"));
						textLabel.setText("q" + stateNo++);
						textLabel.setForeground(Color.white);
						blockLabel.setLayout(new BorderLayout());
						blockLabel.add(textLabel, BorderLayout.CENTER);
					} else {
						if (block.getTransitionType().equals("A")) {
							blockLabel.setIcon(new ImageIcon("image/mini_transitionA.png"));
							textLabel.setText("A");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("B")) {
							blockLabel.setIcon(new ImageIcon("image/mini_transitionB.png"));
							textLabel.setText("B");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("C")) {
							blockLabel.setIcon(new ImageIcon("image/mini_transitionC.png"));
							textLabel.setText("C");
							blockLabel.add(textLabel);
						} else {
							blockLabel.setIcon(new ImageIcon("image/mini_transitionD.png"));
							textLabel.setText("D");
							blockLabel.add(textLabel);
						}
					}
					snapLabel.add(blockLabel);
					blockBuilderModel.getMiniBlockList().add(block);

					blockBuilderController.setOutputString(new StringBuilder());

					DisplayOutput();

					viewHelper.getSnapShotPanel().revalidate();
					viewHelper.getSnapShotPanel().repaint();
				}
				blockBuilderModel.getBlockList().clear();
			}
		};

		listener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {

				dragSource = (JLabel) me.getSource();

				if (isCursorflag() == true) {
					if (blockBuilderController.getBlockObject(dragSource.getX(), dragSource.getY()) == true) {
						block = blockBuilderController.getBlockObj(dragSource.getX(), dragSource.getY());
						blockBuilderController.removeBlockFromListAndPanel(block);
						blockBuilderController.sortTransitions();
						DisplayOutput();
					}
				} else {
					TransferHandler handler = dragSource.getTransferHandler();
					handler.exportAsDrag(dragSource, me, TransferHandler.COPY);
					block = blockBuilderController.getBlockObj(dragSource.getX(), dragSource.getY());

				}
			}
		};

		transitionLabel.addMouseListener(listener);
		undoLabel.addMouseListener(undoMouseListener);
		snapshotLabel.addMouseListener(snapshotMouseListener);
		redoLabel.addMouseListener(redoMouseListener);
		stateLabel.addMouseListener(listener);
	}

	public void addColorPallette() {

		colorPallete = new JPanel();
		colorPallete.setBorder(new TitledBorder("Color your Block!"));

		greenLabel = new JLabel();
		greenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		greenLabel.setText("D");
		greenLabel.setFont(new Font("Serif", Font.BOLD, 30));

		JButton greenButton = new JButton();
		greenButton.setLayout(new BorderLayout());
		greenButton.add(greenLabel, BorderLayout.CENTER);
		greenButton.setBackground(Color.green);
		greenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actiontransition.setIcon(new ImageIcon("image/transitionD.png"));
				actiontransition.add(greenLabel, BorderLayout.CENTER);
				b.setTransitionType("D");
				b.setBlockLabelURL("image/transitionD.png");
				viewHelper.getActionPanel().remove(colorPallete);
				viewHelper.getActionPanel().revalidate();
				repaint();

			}
		});

		JButton redButton = new JButton();
		redLabel = new JLabel();
		redLabel.setText("B");
		redLabel.setHorizontalAlignment(SwingConstants.CENTER);
		redLabel.setFont(new Font("Serif", Font.BOLD, 30));
		redButton.setLayout(new BorderLayout());
		redButton.add(redLabel, BorderLayout.CENTER);
		redButton.setBackground(Color.red);
		redButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actiontransition.setIcon(new ImageIcon("image/transitionB.png"));
				actiontransition.add(redLabel, BorderLayout.CENTER);
				b.setTransitionType("B");
				b.setBlockLabelURL("image/transitionB.png");
				viewHelper.getActionPanel().remove(colorPallete);
				viewHelper.getActionPanel().revalidate();
				repaint();
			}
		});

		JButton yellowButton = new JButton();
		yellowButton.setBackground(Color.yellow);
		yellowLabel = new JLabel();
		yellowLabel.setText("A");
		yellowLabel.setFont(new Font("Serif", Font.BOLD, 30));
		yellowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yellowButton.setLayout(new BorderLayout());
		yellowButton.add(yellowLabel, BorderLayout.CENTER);
		yellowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actiontransition.setIcon(new ImageIcon("image/transitionA.png"));
				actiontransition.add(yellowLabel, BorderLayout.CENTER);
				b.setTransitionType("A");
				b.setBlockLabelURL("image/transitionA.png");
				viewHelper.getActionPanel().remove(colorPallete);
				viewHelper.getActionPanel().revalidate();
				repaint();
			}
		});

		JButton cyanButton = new JButton();
		cyanLabel = new JLabel();
		cyanLabel.setText("C");
		cyanLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cyanLabel.setFont(new Font("Serif", Font.BOLD, 30));
		cyanButton.setLayout(new BorderLayout());
		cyanButton.add(cyanLabel, BorderLayout.CENTER);

		cyanButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actiontransition.setIcon(new ImageIcon("image/transitionC.png"));
				b.setTransitionType("C");
				b.setBlockLabelURL("image/transitionC.png");
				JLabel msg = new JLabel(++activityNo + ". C is added to the panel");
				msg.setFont(new Font("Serif", Font.BOLD, 16));
				msg.setForeground(Color.black);
				viewHelper.getPanelActivity().add(msg);
				actiontransition.add(cyanLabel, BorderLayout.CENTER);
				viewHelper.getActionPanel().remove(colorPallete);
				viewHelper.getActionPanel().revalidate();
				repaint();
			}
		});
		cyanButton.setBackground(Color.CYAN);

		colorPallete.setLayout(new GridLayout(2, 2));
		colorPallete.add(yellowButton);
		colorPallete.add(redButton);
		colorPallete.add(cyanButton);
		colorPallete.add(greenButton);
		colorPallete.setBounds(transitionX + 80, transitionY, 180, 120);
		viewHelper.getActionPanel().add(colorPallete);
	}

	public void removeBlockFromPanel(Block block) {

		String name = block.getName();
		JLabel msg = new JLabel(++activityNo + ". " + name + " is removed from the panel");
		msg.setFont(new Font("Serif", Font.BOLD, 16));
		msg.setForeground(Color.black);
		viewHelper.getPanelActivity().add(msg);
		viewHelper.getPanelActivity().revalidate();
		viewHelper.getPanelActivity().repaint();
		block.getBlockLabel().setBounds(0, 0, 0, 0);
		this.remove(block.getBlockLabel());
		repaint();
	}

	public void addBlockToPanel(Block block) {
		block.getBlockLabel().setBounds(block.getX(), block.getY(), block.getWidth(), block.getHeight());
		String name = block.getName();
		JLabel msg = new JLabel(++activityNo + ". " + name + " is added to the panel");
		msg.setFont(new Font("Serif", Font.BOLD, 16));
		msg.setForeground(Color.black);
		viewHelper.getPanelActivity().add(msg);
		viewHelper.getPanelActivity().revalidate();
		viewHelper.getPanelActivity().repaint();
		viewHelper.getActionPanel().add(block.getBlockLabel());

	}

	class DropListener extends DropTargetAdapter {

		@Override
		public void drop(DropTargetDropEvent dtde) {

			b = new Block();
			viewHelper.showMessage(false, "");
			int stateNo = 0;

			if (dragSource.getName().contains("mini")) {
				b = blockBuilderModel.getMiniBlockList().get(0);
				int x = blockBuilderModel.getBlockList().get(blockBuilderModel.getBlockList().size() - 1).getX();
				int y = blockBuilderModel.getBlockList().get(blockBuilderModel.getBlockList().size() - 1).getY();

				int miniX = x;
				int miniY = y;

				if (blockBuilderModel.getBlockList().get(blockBuilderModel.getBlockList().size() - 1).isState() != blockBuilderModel.getMiniBlockList().get(0).isState()) {
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
							blockLabel.setIcon(new ImageIcon("image/transitionA.png"));
							textLabel.setText("A");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("B")) {
							blockLabel.setIcon(new ImageIcon("image/transitionB.png"));
							textLabel.setText("B");
							blockLabel.add(textLabel);
						} else if (block.getTransitionType().equals("C")) {
							blockLabel.setIcon(new ImageIcon("image/transitionC.png"));
							textLabel.setText("C");
							blockLabel.add(textLabel);
						} else {
							blockLabel.setIcon(new ImageIcon("image/transitionD.png"));
							textLabel.setText("D");
							blockLabel.add(textLabel);
						}

					}
					blockLabel.add(textLabel, BorderLayout.CENTER);
					blockLabel.setBounds(miniX, miniY, 75, 73);
					block.setX(miniX);
					block.setY(miniY);
					blockBuilderModel.getBlockList().add(block);
					viewHelper.getActionPanel().revalidate();
					viewHelper.getActionPanel().repaint();
					miniX += 60;
				}
				addLabels();
			}

			else {

				if (dragSource.getName().equals("leftPanelTransitionA")) {

					if (blockBuilderModel.getBlockList().size() < 1) {
						viewHelper.showMessage(true, "First Block should be a State Block!");
					}
					transitionX = dtde.getLocation().x;
					transitionY = dtde.getLocation().y;
					actiontransition = new JLabel("transitionA");
					actiontransition.setName("actiontransition" + (BlockBuilderModel.transitionNo));
					actiontransition.setIcon(new ImageIcon("image/transition.png"));
					actiontransition.setBounds(transitionX, transitionY, 75, 73);

					actiontransition.addMouseListener(listener);
					actiontransition.setTransferHandler(new TransferHandler("text"));
					addicons(actiontransition);
					actiontransition.setLayout(new BorderLayout());
					viewHelper.getActionPanel().add(actiontransition);

					addColorPallette();

					b = blockBuilderController.createBlockObj(transitionX, transitionY, 75, 73, actiontransition, false, textFieldValue);

				}

				else if (dragSource.getName().equals("leftPanelState")) {
					actionstate = new JLabel("state");

					actionstate.setName("actiontransition" + BlockBuilderModel.stateNo);
					actionstate.setIcon(new ImageIcon("image/state.png"));
					actionstate.addMouseListener(listener);
					actionstate.setTransferHandler(new TransferHandler("text"));
					actionstate.setBounds(dtde.getLocation().x, dtde.getLocation().y, 75, 73);
					FlowLayout fl = new FlowLayout();
					fl.setVgap(30);
					actionstate.setLayout(fl);
					JLabel text = new JLabel("q" + BlockBuilderModel.stateNo);
					text.setForeground(Color.white);
					text.setFont(new Font("Serif", Font.BOLD, 16));
					actionstate.add(text);
					addicons(actionstate);
					viewHelper.getActionPanel().add(actionstate);
					b = blockBuilderController.createBlockObj(dtde.getLocation().x, dtde.getLocation().y, 75, 73, actionstate, true, null);
					b.setBlockLabelURL("image/state.png");

				} else {

					b = blockBuilderController.updateBlockObj(dtde.getLocation().x, dtde.getLocation().y, block);
					viewHelper.getSnapShotPanel().removeAll();

					// b.setBlockLabelURL("image/state.png");
				}

				Block adjacentBlock;

				if (blockBuilderController.isAdjacentToSimilarBlock(b)) {
					b.setX(b.getX() + 50);
					b.getBlockLabel().setLocation(b.getX(), b.getY());
					viewHelper.showMessage(true, " Cannot attach two similar blocks");
					try {
						controllerHelper.playSound("./audio/repel.wav");
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				} else if (blockBuilderController.isAdjacentToDissimilarBlock(b) != null) {

					adjacentBlock = blockBuilderController.isAdjacentToDissimilarBlock(b);
					if (b.getX() > adjacentBlock.getX()) {
						b.setX(adjacentBlock.getX() + adjacentBlock.getWidth() - 15);
						b.setY(adjacentBlock.getY());
					} else {
						b.setX(adjacentBlock.getX() + adjacentBlock.getWidth() - 135);
						b.setY(adjacentBlock.getY());

					}
					try {
						controllerHelper.playSound("./audio/attach.wav");
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				} else {
					try {
						controllerHelper.playSound("./audio/beep_normal.wav");
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

			viewHelper.getActionPanel().revalidate();
			viewHelper.getActionPanel().repaint();
		}

		public void addLabels() {
			for (Block blockObj : blockBuilderModel.getBlockList()) {

				JLabel label = blockObj.getBlockLabel();
				label.setTransferHandler(new TransferHandler("text"));
				label.setBounds(blockObj.getX(), blockObj.getY(), blockObj.getWidth(), blockObj.getHeight());
				viewHelper.getActionPanel().add(label);
			}
		}
	}
}
