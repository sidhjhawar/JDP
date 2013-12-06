package com.turingworld.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.turingworld.controller.NFABuilderController;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.FABlock;
import com.turingworld.model.NFABuilderModel;
import com.turingworld.views.BlockBuilderView.DropTargetListener2;

public class NFABuilderView extends JFrame implements NFABuildViewInterface{
	
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
	private PopupForStateView popupForStateView;
	JScrollPane scrollPane_1;
	JPanel panel_1;
	NFABuilderModel nfaBuilderModel;
	boolean cursorflag;
	private MouseListener listener;
	private JLabel dragSource;
	private NFABuilderController nfaBuilderController;
	private DropTarget dropTarget;
	JLabel actionState;
	private MouseListener hoverListener;

	
	
	
	public NFABuilderView(NFABuilderModel nfaBuilderModel) {
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
	//	actionPanel = new ImagePanel(new ImageIcon("image/background.png").getImage());
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
		
		play = new JLabel("Play Button");
		play.setToolTipText("Play\r\n");
		play.setIcon(new ImageIcon("image/run.png"));
		play.setBounds(30, 549, 75, 75);
		
		
		panel.add(play);
		
		hoverListener = new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				dragSource = (JLabel) me.getSource();
				/*dfaBlock = dfaBuilderController.getDFABlockObj(dragSource.getX(), dragSource.getY());
				if (dfaBlock != null && dfaBlock.isState()) {
					endStateBlock = (StateBlock) dfaBlock;
					DFABuilderView.this.isStartStateClicked = true;
				}*/
			}
		};
		
		listener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				dragSource =(JLabel)me.getSource();
				TransferHandler handler = dragSource.getTransferHandler();
				handler.exportAsDrag(dragSource, me, TransferHandler.COPY);
				System.out.println("dragging");
				if (me.getButton() == MouseEvent.BUTTON3) {
					popupForStateView.show(me.getComponent(), me.getX(), me.getY());
				}
			
			
			}
			
			
			
			};
		
		
			stateCirle.addMouseListener(listener);

		
		
	}
	
	
	
	
	
	






	@Override
	public NFABuildViewInterface getViewType() {
		return null;
	}



	@Override
	public void moveState(JLabel label, int x1, int y1, int x2, int y2) {
		
	}



	public void setController(NFABuilderController nfaBuilderController) {
		
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
			
			
				System.out.println("State dragged");
				if (dragSource.getName().equals("leftPanelState")){
				actionState = new JLabel("state");
				actionState.setIcon(new ImageIcon("image/stateCirlce.png"));
				actionState.setName("actiontransition" + BlockBuilderModel.stateNo);
				actionState.addMouseListener(listener);
				actionState.setTransferHandler(new TransferHandler("text"));
				actionState.setBounds(dtde.getLocation().x,
						dtde.getLocation().y, 80, 80);
				FlowLayout fl = new FlowLayout();
				fl.setVgap(30);
				actionState.setLayout(fl);
				}
				else{
					actionState.setBounds(dtde.getLocation().x,
							dtde.getLocation().y, 80, 80);
					actionState.addMouseListener(listener);
				}
				
					
				
				
				
				
				
				actionPanel.add(actionState);
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
	
	class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Add Transition")){
				System.out.println("CLicked");
			}
			
			
			
			/*if (e.getActionCommand().equals("Add Initial State")) {

				for (DFABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {
					if (dfaBlockObj.isState()) {
						if (((StateBlock) dfaBlockObj).isInitial()) {

							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setInitial(false);
							JLabel label = new JLabel((new ImageIcon("image/tunnel.png")));
							dfaBlockObj.setDfaLabel(label);
							repaint();

						}

						if (dfaBlockObj.isState() && (dfaBlockObj.getX() == dragSource.getX())) {

							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setInitial(true);
							JLabel label = new JLabel((new ImageIcon("image/house.png")));
							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), 76, 96);
							dfaBlockObj.setDfaLabel(label);
							runStartStateBlock = (StateBlock) dfaBlockObj;
						}

					}
				}
				for (DFABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {

					if (dfaBlockObj.isState()) {

						JLabel label = dfaBlockObj.getDfaLabel();
						label.setName("");
						label.addMouseListener(listener);
						label.setTransferHandler(new TransferHandler("text"));
						if (((StateBlock) dfaBlockObj).isInitial()) {
							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), 76, 96);

						}

						else if (((StateBlock) dfaBlockObj).isFinal()) {
							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), 76, 106);

						}

						else {

							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), dfaBlockObj.getWidth(), dfaBlockObj.getHeight());

						}
						actionPanel.add(label);
					}
				}

				actionPanel.revalidate();
				actionPanel.repaint();
			} else if (e.getActionCommand().equals("Add Final State")) {
				for (DFABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {

					if (dfaBlockObj.isState()) {
						if (((StateBlock) dfaBlockObj).isFinal()) {
							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setFinal(false);
							JLabel label = new JLabel((new ImageIcon("image/tunnel.png")));
							dfaBlockObj.setDfaLabel(label);
							repaint();

						}

						if (dfaBlockObj.isState() && (dfaBlockObj.getX() == dragSource.getX())) {

							actionPanel.remove(dfaBlockObj.getDfaLabel());
							((StateBlock) dfaBlockObj).setFinal(true);

							JLabel label = new JLabel((new ImageIcon("image/princess.png")));
							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), 76, 96);

							dfaBlockObj.setDfaLabel(label);
							runEndStateBlock = (StateBlock) dfaBlockObj;

						}

					}
				}
				for (DFABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {
					if (dfaBlockObj.isState()) {

						JLabel label = dfaBlockObj.getDfaLabel();
						label.setName("");
						label.addMouseListener(listener);
						label.setTransferHandler(new TransferHandler("text"));
						if (((StateBlock) dfaBlockObj).isInitial()) {
							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), 76, 96);

						}

						else if (((StateBlock) dfaBlockObj).isFinal()) {
							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), 76, 106);

						}

						else {

							label.setBounds(dfaBlockObj.getX(), dfaBlockObj.getY(), dfaBlockObj.getWidth(), dfaBlockObj.getHeight());

						}

						actionPanel.add(label);
					}
				}
				actionPanel.revalidate();
				actionPanel.repaint();

			} else if (e.getActionCommand().equals("Add Transition")) {

				for (DFABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {
					JLabel label = dfaBlockObj.getDfaLabel();
					label.addMouseListener(hoverListener);
				}
			}*/
		}

		

	}

	@Override
	public void addToPanel(FABlock dfaBlock) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void removeFromPanel(FABlock dfaBlock) {
		// TODO Auto-generated method stub
		
	}
	

	
	
}