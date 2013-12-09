package com.turingworld.views;

import javax.swing.JLabel;

import com.turingworld.model.Block;
import com.turingworld.model.FABlock;

public interface DFABuildViewInterface {

	public void addToPanel(FABlock dfaBlock);

	public void removeFromPanel(FABlock dfaBlock);
	
	public DFABuildViewInterface getViewType();
	

}
