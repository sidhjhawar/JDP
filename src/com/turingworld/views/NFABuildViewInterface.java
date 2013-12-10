package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
import javax.swing.JLabel;

import com.turingworld.model.FABlock;

public interface NFABuildViewInterface {
	public void addToPanel(FABlock dfaBlock);

	public void removeFromPanel(FABlock dfaBlock);

	public NFABuildViewInterface getViewType();

	public void moveState(JLabel label, int x1, int y1, int x2, int y2);
}
