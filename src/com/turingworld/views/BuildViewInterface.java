package com.turingworld.views;

import javax.swing.JLabel;

import com.turingworld.model.Block;

public interface BuildViewInterface {

	public void addBlockToPanel(Block block);

	public void removeBlockFromPanel(Block block);
	
	//public BuildViewInterface getViewType();
	//public void moveState(JLabel label, int x1, int y1, int x2, int y2);
}
