package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
import com.turingworld.model.FABlock;

public interface DFABuildViewInterface {

	public void addToPanel(FABlock dfaBlock);

	public void removeFromPanel(FABlock dfaBlock);

	public DFABuildViewInterface getViewType();

}
