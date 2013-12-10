package com.turingworld.views;

/*
 * This class like DFABuilderView is used for creating NFA's the implementation is very much similar to the one
 * in DFA.Most of the method used here are there in dfa as well with slight modifications.
 * We have stick to traditional circle and arrow notation for NFA. We have separate visualization when the user
 * clicks on the play button
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.turingworld.controller.NFABuilderController;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.FABlock;
import com.turingworld.model.NFABuilderModel;
import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;

@SuppressWarnings("serial")
public class NFABuilderView extends JFrame implements NFABuildViewInterface {

	JPanel contentPanel;
	JLabel undo;
	JLabel stateCirle;
	JLabel redo;
	JLabel eraser;
	JLabel play;
	JPanel actionPanel;
	JScrollPane recentScrollPanel;
	JPanel recentActivityPanel;
	JPanel panelActivity;
	JScrollPane outputScrollPanel;
	JPanel outputActivityPanel;
	NFABuilderModel nfaBuilderModel;
	boolean cursorflag;
	private MouseListener listener;
	private JLabel dragSource;
	private NFABuilderController nfaBuilderController;
	private DropTarget dropTarget;
	private FABlock b;
	private String stateURL;
	private FABlock faBlock;
	private JLabel transition;
	private JButton test;
	JLabel actionState;
	private PopupForStateView popupForStateView;
	private MouseListener hoverListener;
	protected boolean isStartStateClicked;
	private FABlock nfaBlock;
	private StateBlock startStateBlock;
	private StateBlock endStateBlock;
	private StateBlock finalEndStateBlock;
	private ArrayList<Line2D.Double> lines;
	private ArrayList<StateBlock> startStateBlockList;
	private ArrayList<StateBlock> endStateBlockList;
	private Graphics2D g2;
	private Timer timer;
	private JPanel colorPallete;
	private JTextField transit;
	private String tranValue;
	private JPanel output;
	private ArrayList<QuadCurve2D.Double> curves;
	private boolean same;

	public NFABuilderView(NFABuilderModel nfaBuilderModel) {

		timer = new Timer(5, blinker);
		timer.start();
		curves = new ArrayList<QuadCurve2D.Double>();

		lines = new ArrayList<Line2D.Double>();
		startStateBlockList = new ArrayList<StateBlock>();
		endStateBlockList = new ArrayList<StateBlock>();
		popupForStateView = new PopupForStateView();
		this.nfaBuilderModel = nfaBuilderModel;
		setVisible(true);
		setTitle("Welcome - Build your Blocks!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 0, 1251, 719);

		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setToolTipText("");
		contentPanel.setBorder(null);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		recentActivityPanel = new JPanel();
		recentActivityPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		recentActivityPanel.setBounds(939, 0, 296, 665);
		contentPanel.add(recentActivityPanel);
		recentActivityPanel.setLayout(new BorderLayout(0, 0));

		recentScrollPanel = new JScrollPane();
		recentActivityPanel.add(recentScrollPanel);
		recentScrollPanel.setViewportBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Recent Activity",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		recentScrollPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		recentScrollPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		panelActivity = new JPanel();
		recentScrollPanel.setViewportView(panelActivity);
		panelActivity.setLayout(new BoxLayout(panelActivity, BoxLayout.Y_AXIS));
		actionPanel = new JPanel();
		actionPanel.setBounds(155, 3, 785, 514);
	//	actionPanel.setBounds(155, 3, 785, 514);
		contentPanel.add(actionPanel);
		actionPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		actionPanel.setBackground(new Color(211, 211, 211));
		actionPanel.setLayout(null);

		outputActivityPanel = new JPanel();
		outputActivityPanel.setBounds(155, 515, 785, 166);
		contentPanel.add(outputActivityPanel);
		outputActivityPanel.setLayout(new BorderLayout(0, 0));

		outputScrollPanel = new JScrollPane();
		outputScrollPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		outputScrollPanel.setViewportBorder(new TitledBorder(null, "",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		outputActivityPanel.add(outputScrollPanel);
		
		output = new JPanel();
		output.setLayout(null);
		output.setLayout(null);
		outputScrollPanel.setViewportView(output);
		creatHeaderMenu();
		
		

		createLeftPaneView();
		popupForStateView.registerActionListner(new MenuActionListener());

		dropTarget = new DropTarget(this.actionPanel, new DropTargetListener2());
		repaint();
		paintLines();

		startStateBlock = new StateBlock();
		endStateBlock = new StateBlock();
		finalEndStateBlock = new StateBlock();

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
	public void removeFromPanel() {
		actionPanel.removeAll();
		actionPanel.revalidate();
	}
	
	public void createNewWindow() {
		boolean isTriviaClicked = false;
		if (nfaBuilderController.isTriviaClicked) {
			isTriviaClicked = true;
		}
		nfaBuilderModel = new NFABuilderModel();
		NFABuilderView nfaBuilderView = new NFABuilderView(nfaBuilderModel);
		nfaBuilderController = new NFABuilderController(nfaBuilderModel, nfaBuilderView);
		nfaBuilderView.setController(nfaBuilderController);
		NFABuilderModel.stateNo = 0;
		NFABuilderModel.transitionNo = 0;
		if (isTriviaClicked) {
			NFABuilderController.isTriviaClicked = true;
		}
		dispose();
	}

	private void createLeftPaneView() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		leftPanel.setBackground(new Color(211, 211, 211));
		leftPanel.setBounds(0, 3, 153, 662);
		contentPanel.add(leftPanel);
		leftPanel.setLayout(null);

		undo = new JLabel("undo");
		undo.setIcon(new ImageIcon("image/undo.png"));
		undo.setToolTipText("Undo");
		undo.setName("undo");
		undo.setBounds(30, 190, 75, 75);
		undo.setTransferHandler(new TransferHandler("text"));
		leftPanel.add(undo);

		stateCirle = new JLabel("");
		stateCirle.setHorizontalAlignment(SwingConstants.CENTER);
		stateCirle.setName("leftPanelState");
		stateCirle.setToolTipText("");
		stateCirle.setIcon(new ImageIcon("image/stateCirlce.png"));
		stateCirle.setBounds(30, 42, 80, 80);
		stateCirle.setTransferHandler(new TransferHandler("text"));
		leftPanel.add(stateCirle);

		redo = new JLabel("redo");
		redo.setToolTipText("Redo");
		redo.setIcon(new ImageIcon("image/redo.png"));
		redo.setBounds(30, 309, 75, 75);
		leftPanel.add(redo);

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
		leftPanel.add(eraser);

		play = new JLabel("Play Button");
		play.setToolTipText("Play\r\n");
		play.setIcon(new ImageIcon("image/run.png"));
		play.setBounds(30, 549, 75, 75);
		play.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				for (FABlock faBlock : nfaBuilderModel.getNfaBlockList()) {
					if (faBlock.isState() == true) {
						if (((StateBlock) faBlock).isInitial() == true) {
							startStateBlock = (StateBlock) faBlock;
						}
					}
				}
				test = new JButton();
				test.setText("Test");
				test.setBounds(250, 31, 85, 41);
				textField_1 = new JTextField();
				textField_1.setBounds(82, 31, 165, 41);
				output.add(textField_1);
				output.add(test);
				textField_1.setColumns(10);
				output.revalidate();
				output.repaint();
				test.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						NFARunWindow nfaRun = new NFARunWindow(startStateBlock);
						
					}
				});


			
			}

		});

		leftPanel.add(play);

		hoverListener = new MouseAdapter() {
			private boolean shift;
			

			public void mouseEntered(MouseEvent me) {
				int x1 = 0, x2 = 0, y1 = 0, y2 = 02;
				dragSource = (JLabel) me.getSource();
				FABlock sblock;
				FABlock fblock;
				nfaBlock = nfaBuilderController.getNFABlockObj(
						dragSource.getX(), dragSource.getY());
				if (nfaBlock != null /* && nfaBlock.isState() */) {

					endStateBlock = (StateBlock) nfaBlock;
					for (int i = 0; i < startStateBlockList.size(); i++)
					{
						sblock = startStateBlockList.get(i);
						fblock = endStateBlockList.get(i);
						
						
						if(startStateBlock.equals(sblock)&&endStateBlock.equals(fblock))
						{
							same = true;
							
							break;
						}
						else
						{
							same=false;
						}
					}
					if (startStateBlock.equals(endStateBlock)) {
						QuadCurve2D.Double curve = new QuadCurve2D.Double(
								startStateBlock.getX() + 16,
								startStateBlock.getY() + 7,
								startStateBlock.getX() + 32,
								startStateBlock.getY() - 70,
								startStateBlock.getX() + 48,
								startStateBlock.getY() + 7);
						g2 = (Graphics2D) actionPanel.getGraphics();
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
						startStateBlockList.add(startStateBlock);
						endStateBlockList.add(endStateBlock);
						curves.add(curve);
						addColorPallette(curve.getCtrlX(), curve.getCtrlY());
					}

					else {
						NFABuilderView.this.isStartStateClicked = true;
						g2 = (Graphics2D) actionPanel.getGraphics();
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
						
						
						startStateBlockList.add(startStateBlock);
						endStateBlockList.add(endStateBlock);
						for (int i = 0; i < startStateBlockList.size(); i++) {
							sblock = startStateBlockList.get(i);
							fblock = endStateBlockList.get(i);
							if (endStateBlock.equals(sblock)
									&& startStateBlock.equals(fblock)) {
								
								shift = true;
							}
							
							
						}

						if ((startStateBlock.getX() < endStateBlock.getX())
								&& (startStateBlock.getY() > endStateBlock
										.getY())) {
							if (!shift) {
								x1 = startStateBlock.getX() + 48;
								y1 = startStateBlock.getY() + 7;
								x2 = endStateBlock.getX() + 7;
								y2 = endStateBlock.getY() + 48;
							} else

							{

								x1 = startStateBlock.getX() + 66;
								y1 = startStateBlock.getY() + 16;
								x2 = endStateBlock.getX() + 16;
								y2 = endStateBlock.getY() + 66;
								shift = false;

							}

						} else if ((startStateBlock.getX() > endStateBlock
								.getX())
								&& (startStateBlock.getY() < endStateBlock
										.getY())) {
							if (!shift) {
								x1 = startStateBlock.getX() + 7;
								y1 = startStateBlock.getY() + 48;
								x2 = endStateBlock.getX() + 48;
								y2 = endStateBlock.getY() + 7;
							}

							else {
								x1 = startStateBlock.getX() + 16;
								y1 = startStateBlock.getY() + 66;
								x2 = endStateBlock.getX() + 66;
								y2 = endStateBlock.getY() + 16;
								shift = false;

							}

						}

						else if ((startStateBlock.getX() < endStateBlock.getX())
								&& (startStateBlock.getY() < endStateBlock
										.getY())) {
							if (!shift) {
								x1 = startStateBlock.getX() + 66;
								y1 = startStateBlock.getY() + 48;
								x2 = endStateBlock.getX() + 16;
								y2 = endStateBlock.getY() + 7;
							}

							else {
								x1 = startStateBlock.getX() + 48;
								y1 = startStateBlock.getY() + 66;
								x2 = endStateBlock.getX() + 7;
								y2 = endStateBlock.getY() + 16;
								shift = false;

							}

						} else if ((startStateBlock.getX() > endStateBlock
								.getX())
								&& (startStateBlock.getY() > endStateBlock
										.getY())) {
							if (!shift) {
								x1 = startStateBlock.getX() + 16;
								y1 = startStateBlock.getY() + 7;
								x2 = endStateBlock.getX() + 76; // y =66
																// (Remdodified)
								y2 = endStateBlock.getY() + 48;
							}

							else {
								x1 = startStateBlock.getX() + 7;
								y1 = startStateBlock.getY() + 16;
								x2 = endStateBlock.getX() + 66; // x = 48
								y2 = endStateBlock.getY() + 66;
								shift = false;

							}

						}

						Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
						lines.add(line);
						paintLines();
						addColorPallette(x1, x2, y1, y2);
					}
					for (FABlock nfaBlockObj : nfaBuilderModel
							.getNfaBlockList()) {
						JLabel label = nfaBlockObj.getDfaLabel();
						label.removeMouseListener(hoverListener);
					}
				}
			}
		};
		listener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				dragSource = (JLabel) me.getSource();
				TransferHandler handler = dragSource.getTransferHandler();
				handler.exportAsDrag(dragSource, me, TransferHandler.COPY);
				faBlock = nfaBuilderController.getNFABlockObj(
						dragSource.getX(), dragSource.getY());
				if (me.getButton() == MouseEvent.BUTTON3) {
					popupForStateView.show(me.getComponent(), me.getX(),
							me.getY());
					nfaBlock = nfaBuilderController.getNFABlockObj(
							dragSource.getX(), dragSource.getY());
					startStateBlock = (StateBlock) nfaBlock;
				}

			}

		};

		stateCirle.addMouseListener(listener);

	}
/*
 * The method below pops out a small TEXTField which allows user to enter a character as a transition
 * between two states.
 * 
 */
	public void addColorPallette(double d, double e) {

		int x = (int) d;
		int y = (int) e;
		transition = new JLabel();
		transition.setBounds(x, y + 10, 40, 20);
		colorPallete = new JPanel();
		colorPallete.setBorder(new TitledBorder("Enter Transitions!"));
		colorPallete.setBounds(x, y - 40, 130, 80);
		transit = new JTextField(4);
		transit.setPreferredSize(new Dimension(50, 24));
		colorPallete.add(transit);
		actionPanel.add(colorPallete);
		transit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tranValue = transit.getText();
				actionPanel.remove(colorPallete);
				transition.setFont(new Font("Serif", Font.BOLD, 18));
				if(same)
				{
				transition.setText("  ,"+tranValue);
				
				}
				else
				{
					transition.setText(tranValue);
				}
				actionPanel.add(transition);
				actionPanel.revalidate();
				actionPanel.repaint();
				TransitionBlock transitionBlock = new TransitionBlock();
				transitionBlock.setName(tranValue);
				nfaBuilderController.addTransitionBlocktoStateList(
						startStateBlock, endStateBlock, transitionBlock);
			}
		});

		actionPanel.revalidate();
		actionPanel.repaint();

	}

	public void addColorPallette(int x1, int x2, int y1, int y2) {

		transition = new JLabel();
		transition.setBounds((x1 + x2) / 2, ((y1 + y2) / 2)-20, 40, 20);
		colorPallete = new JPanel();
		colorPallete.setBorder(new TitledBorder("Enter Transitions!"));
		colorPallete.setBounds((x1 + x2) / 2, (y1 + y2) / 2, 130, 80);
		transit = new JTextField(4);
		transit.setPreferredSize(new Dimension(50, 24));

		colorPallete.add(transit);
		actionPanel.add(colorPallete);
		transit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tranValue = transit.getText();
				actionPanel.remove(colorPallete);
				transition.setFont(new Font("Serif", Font.BOLD, 18));
				if(same)
				{
				transition.setText("  ,"+tranValue);
				
				}
				else
				{
					transition.setText(tranValue);
				}
				actionPanel.add(transition);
				actionPanel.revalidate();
				actionPanel.repaint();
				TransitionBlock transitionBlock = new TransitionBlock();
				transitionBlock.setName(tranValue);
				nfaBuilderController.addTransitionBlocktoStateList(
						startStateBlock, endStateBlock, transitionBlock);
			}
		});

		actionPanel.revalidate();
		actionPanel.repaint();

	}
// The method iterates over all the transition that were drawn by the user and continuously draws them on the screen.
	void paintLines() {
		double theta;

		for (Line2D.Double line : lines) {

			g2.draw(line);
			theta = Math.atan2(line.getY2() - line.getY1(),
					line.getX2() - line.getX1());
			drawArrow(theta, line.getX2(), line.getY2());
		}
		for (QuadCurve2D.Double curve : curves) {
			g2.draw(curve);

		}

	}
// This method is used to draw arrow head on the transitions.
	private void drawArrow(double theta, double x0, double y0) {
		int barb = 13; // barb length
		double phi = Math.PI / 6;
		double x = x0 - barb * Math.cos(theta + phi);
		double y = y0 - barb * Math.sin(theta + phi);
		g2.draw(new Line2D.Double(x0, y0, x, y));
		x = x0 - barb * Math.cos(theta - phi);
		y = y0 - barb * Math.sin(theta - phi);
		g2.draw(new Line2D.Double(x0, y0, x, y));
	}

	ActionListener blinker = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			paintLines();
		}
	};
	private JTextField textField_1;

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
		return null;
	}

	@Override
	public void moveState(JLabel label, int x1, int y1, int x2, int y2) {

	}

	public void setController(NFABuilderController nfaBuilderController) {
		this.nfaBuilderController = nfaBuilderController;
	}

	public boolean isCursorflag() {
		return cursorflag;
	}

	public void setCursorflag(boolean cursorflag) {
		this.cursorflag = cursorflag;
	}
// The method below is the part of Drag and Drop library which is used to drag and drop states from left panel
// to the main window.
	class DropTargetListener2 implements DropTargetListener {

		@Override
		public void dragEnter(DropTargetDragEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dragExit(DropTargetEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dragOver(DropTargetDragEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			paintLines();
			b = new StateBlock();

			if (dragSource.getName().equals("leftPanelState")) {
				// Add block

				actionState = new JLabel("state");

				actionState.setName("actiontransition"
						+ BlockBuilderModel.stateNo);
				actionState.setIcon(new ImageIcon("image/stateCirlce.png"));
				stateURL = "image/stateCirlce.png";
				actionState.addMouseListener(listener);
				actionState.setTransferHandler(new TransferHandler("text"));
				actionState.setBounds(dtde.getLocation().x,
						dtde.getLocation().y, 80, 80);
				FlowLayout fl = new FlowLayout();
				fl.setVgap(30);
				actionState.setLayout(fl);

				actionState.setBounds(dtde.getLocation().x,
						dtde.getLocation().y, 80, 80);
				b = nfaBuilderController.createBlockObj(stateURL,
						dtde.getLocation().x, dtde.getLocation().y, 50, 93,
						actionState, true, null);

				actionPanel.add(actionState);

			} else {
				// Update block
				b = nfaBuilderController.updateBlockObj(dtde.getLocation().x,
						dtde.getLocation().y, faBlock);
				actionState = b.getDfaLabel();
				actionState.setBounds(dtde.getLocation().x,
						dtde.getLocation().y, 80, 80);
				// actionState.setBounds(b.getX(), b.getY(), 80, 80);
				actionState.addMouseListener(listener);
			}
			actionPanel.revalidate();
			actionPanel.repaint();

			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			dtde.getDropTargetContext().dropComplete(true);

		}

		@Override
		public void dropActionChanged(DropTargetDragEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
// The class below is used to implement the right click menu.
	class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Add Transition")) {
				for (FABlock nfaBlockObj : nfaBuilderModel.getNfaBlockList()) {
					// if (!nfaBlockObj.equals(startStateBlock))
					{
						JLabel label = nfaBlockObj.getDfaLabel();
						label.addMouseListener(hoverListener);
					}
				}
			}

			else if (e.getActionCommand().equals("Add Initial State")) {

				for (FABlock nfaBlockObj : nfaBuilderModel.getNfaBlockList()) {
					if (nfaBlockObj.equals(startStateBlock)) {
						((StateBlock) nfaBlockObj).setInitial(true);
						actionPanel.remove(nfaBlockObj.getDfaLabel());
						JLabel initial = new JLabel();
						initial.setName("");
						initial.setIcon(new ImageIcon(
								"image/initialStateCirlce.png"));
						initial.setBounds(nfaBlockObj.getX(),
								nfaBlockObj.getY(), 80, 80);
						initial.setTransferHandler(new TransferHandler("text"));
						initial.addMouseListener(listener);
						;
						nfaBlockObj.setDfaLabel(initial);

						actionPanel.add(nfaBlockObj.getDfaLabel());
						actionPanel.revalidate();
						actionPanel.repaint();

					}
				}

			}

			else if (e.getActionCommand().equals("Add Final State")) {

				for (FABlock nfaBlockObj : nfaBuilderModel.getNfaBlockList()) {
					if (nfaBlockObj.equals(startStateBlock)) {
						((StateBlock) nfaBlockObj).setFinal(true);
						actionPanel.remove(nfaBlockObj.getDfaLabel());
						JLabel initial = new JLabel();
						initial.setName("");
						initial.setIcon(new ImageIcon(
								"image/finalStateCirlce.png"));
						initial.setBounds(nfaBlockObj.getX(),
								nfaBlockObj.getY(), 80, 80);
						initial.setTransferHandler(new TransferHandler("text"));
						initial.addMouseListener(listener);
						nfaBlockObj.setDfaLabel(initial);

						actionPanel.add(nfaBlockObj.getDfaLabel());
						actionPanel.revalidate();
						actionPanel.repaint();

					}
				}

			}
		}

	}
}
