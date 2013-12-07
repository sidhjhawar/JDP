package com.turingworld.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.turingworld.controller.NFABuilderController;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.DFABuilderModel;
import com.turingworld.model.FABlock;
import com.turingworld.model.NFABuilderModel;
import com.turingworld.model.StateBlock;

import com.turingworld.views.DFABuilderView.MenuActionListener;

public class NFABuilderView extends JFrame implements NFABuildViewInterface {

	JPanel contentPanel;
	JLabel undo;
	JLabel stateCirle;
	JLabel redo;
	JLabel eraser;
	JLabel play;
	JPanel actionPanel;
	JScrollPane scrollPane;
	JPanel panel_2;
	JPanel panelActivity;
	JScrollPane scrollPane_1;
	JPanel panel_1;
	NFABuilderModel nfaBuilderModel;
	boolean cursorflag;
	private MouseListener listener;
	private JLabel dragSource;
	private NFABuilderController nfaBuilderController;
	private DropTarget dropTarget;
	private FABlock b;
	private String stateURL;
	private FABlock faBlock;
	JLabel actionState;
	private PopupForStateView popupForStateView;
	private MouseListener hoverListener;
	protected boolean isStartStateClicked;
	private FABlock nfaBlock;
	private StateBlock startStateBlock;
	private StateBlock endStateBlock;
	private StateBlock finalEndStateBlock;
	private ArrayList<Line2D.Double> lines;
	private Graphics2D g2;
	private Timer timer;

	public NFABuilderView(NFABuilderModel nfaBuilderModel) {

		timer = new Timer(5, blinker);
		timer.start();

		lines = new ArrayList<Line2D.Double>();
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

		panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(939, 0, 296, 681);
		contentPanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		scrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Recent Activity", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);

		panelActivity = new JPanel();
		scrollPane.setViewportView(panelActivity);
		panelActivity.setLayout(new BoxLayout(panelActivity, BoxLayout.Y_AXIS));
		// actionPanel = new ImagePanel(new
		// ImageIcon("image/background.png").getImage());
		actionPanel = new JPanel();
		actionPanel.setBounds(155, 3, 785, 514);
		contentPanel.add(actionPanel);
		actionPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		actionPanel.setBackground(new Color(211, 211, 211));
		actionPanel.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBounds(155, 515, 785, 166);
		contentPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setViewportBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(scrollPane_1);

		createLeftPaneView();
		popupForStateView.registerActionListner(new MenuActionListener());

		dropTarget = new DropTarget(this.actionPanel, new DropTargetListener2());
		repaint();
		paintLines();

		startStateBlock = new StateBlock();
		endStateBlock = new StateBlock();
		finalEndStateBlock = new StateBlock();

	}

	private void createLeftPaneView() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(0, 3, 153, 678);
		contentPanel.add(panel);
		panel.setLayout(null);

		undo = new JLabel("undo");
		undo.setIcon(new ImageIcon("image/undo.png"));
		undo.setToolTipText("Undo");
		undo.setName("undo");
		undo.setBounds(30, 190, 75, 75);
		undo.setTransferHandler(new TransferHandler("text"));
		panel.add(undo);

		stateCirle = new JLabel("");
		stateCirle.setHorizontalAlignment(SwingConstants.CENTER);
		stateCirle.setName("leftPanelState");
		stateCirle.setToolTipText("");
		stateCirle.setIcon(new ImageIcon("image/stateCirlce.png"));
		stateCirle.setBounds(30, 42, 80, 80);
		stateCirle.setTransferHandler(new TransferHandler("text"));
		panel.add(stateCirle);

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
		panel.add(eraser);

		play = new JLabel("Play Button");
		play.setToolTipText("Play\r\n");
		play.setIcon(new ImageIcon("image/run.png"));
		play.setBounds(30, 549, 75, 75);

		panel.add(play);

		hoverListener = new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				dragSource = (JLabel) me.getSource();
				System.out.println("Listener");
				nfaBlock = nfaBuilderController.getNFABlockObj(dragSource.getX(), dragSource.getY());
				if (nfaBlock != null /* && nfaBlock.isState() */) {
					endStateBlock = (StateBlock) nfaBlock;
					NFABuilderView.this.isStartStateClicked = true;
					
					g2 = (Graphics2D) actionPanel.getGraphics().create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					Line2D.Double line = new Line2D.Double(startStateBlock.getX(), startStateBlock.getY(), endStateBlock.getX(), endStateBlock.getY());
					lines.add(line);
					paintLines();
					for (FABlock nfaBlockObj : nfaBuilderModel.getNfaBlockList()) {
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
				faBlock = nfaBuilderController.getNFABlockObj(dragSource.getX(), dragSource.getY());
				if (me.getButton() == MouseEvent.BUTTON3) {
					popupForStateView.show(me.getComponent(), me.getX(), me.getY());
					nfaBlock = nfaBuilderController.getNFABlockObj(dragSource.getX(), dragSource.getY());
					startStateBlock = (StateBlock) nfaBlock;
					System.out.println(nfaBlock.getX());
				}

			}

		};

		stateCirle.addMouseListener(listener);

	}

	void paintLines() {
		double theta;
		for (Line2D.Double line : lines) {
			g2.draw(line);
			theta = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());
			drawArrow(theta, line.getX2(), line.getY2());
		}

	}

	private void drawArrow(double theta, double x0, double y0) {
		int barb = 20; // barb length
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

		}
	};

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

				actionState.setName("actiontransition" + BlockBuilderModel.stateNo);
				actionState.setIcon(new ImageIcon("image/stateCirlce.png"));
				stateURL = "image/stateCirlce.png";
				actionState.addMouseListener(listener);
				actionState.setTransferHandler(new TransferHandler("text"));
				actionState.setBounds(dtde.getLocation().x, dtde.getLocation().y, 80, 80);
				FlowLayout fl = new FlowLayout();
				fl.setVgap(30);
				actionState.setLayout(fl);

				actionState.setBounds(dtde.getLocation().x, dtde.getLocation().y, 80, 80);
				b = nfaBuilderController.createBlockObj(stateURL, dtde.getLocation().x, dtde.getLocation().y, 50, 93, actionState, true, null);

				actionPanel.add(actionState);

			} else {
				// Update block
				b = nfaBuilderController.updateBlockObj(dtde.getLocation().x, dtde.getLocation().y, faBlock);
				actionState = b.getDfaLabel();
				actionState.setBounds(dtde.getLocation().x, dtde.getLocation().y, 80, 80);
				// actionState.setBounds(b.getX(), b.getY(), 80, 80);
				actionState.addMouseListener(listener);
			}
			actionPanel.revalidate();
			actionPanel.repaint();

			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			dtde.getDropTargetContext().dropComplete(true);

			g2 = (Graphics2D) actionPanel.getGraphics().create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			paintLines();

		}

		@Override
		public void dropActionChanged(DropTargetDragEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Add Transition")) {
				for (FABlock nfaBlockObj : nfaBuilderModel.getNfaBlockList()) {
					if (!nfaBlockObj.equals(startStateBlock)) {
						JLabel label = nfaBlockObj.getDfaLabel();
						label.addMouseListener(hoverListener);
					}
				}
			}
		}

	}

}