package com.turingworld.controller;

/* This class is the controller for NFABuilder view and model. 
 * When the states and transitions are being added to the action panel or undoed, 
 * methods of this class are being called. 
 */

import java.util.ArrayList;

import javax.swing.JLabel;

import com.turingworld.command.AddNFABlockCommand;
import com.turingworld.command.Invoker;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.DFABuilderModel;
import com.turingworld.model.FABlock;
import com.turingworld.model.NFABuilderModel;
import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;
import com.turingworld.views.NFABuildViewInterface;
import com.turingworld.views.NFABuilderView;

public class NFABuilderController {
	private NFABuilderModel nfaBuilderModel;
	private NFABuildViewInterface nfaBuildViewInterface;
	private AddNFABlockCommand addNFABlockCommand;
	private Invoker invoker;
	public static boolean isTriviaClicked;


	// constructor
	public NFABuilderController(NFABuilderModel nfaBuilderModel, NFABuildViewInterface nfaBuildViewInterface) {
		this.nfaBuilderModel = nfaBuilderModel;
		this.nfaBuildViewInterface = nfaBuildViewInterface;
		invoker = new Invoker();
	}

	public void setNfaBuilderView(NFABuilderView nfaBuilderView) {

	}

	// This creates the NFAblock when a state or transition is dropped on the
	// panel.
	// Properties are been set to the block being dropped to the panel.
	public FABlock createBlockObj(String url, int x, int y, int width, int height, JLabel label, boolean isState, String transitionType) {
		FABlock faBlock;
		if (isState == true) {
			faBlock = new StateBlock();
		} else {
			faBlock = new TransitionBlock();
		}

		faBlock.setX(x);
		faBlock.setY(y);
		faBlock.setWidth(width);
		faBlock.setHeight(height);
		faBlock.setDfaLabel(label);
		faBlock.setTransitionType(transitionType);
		faBlock.setDfaLabelURL(url);

		if (isState) {
			faBlock.setName("q" + NFABuilderModel.stateNo);
			NFABuilderModel.stateNo++;
			faBlock.setState(true);
		} else {
			faBlock.setName("t" + BlockBuilderModel.transitionNo);
			BlockBuilderModel.transitionNo++;
			faBlock.setState(false);
		}
		addNFABlockCommand = new AddNFABlockCommand(faBlock, this);
		invoker.setCommand(addNFABlockCommand);
		invoker.invoke();
		return faBlock;
	}

	// NFABlock is added to the arraylist. This is done when the state or
	// transition is dropped on the panel.
	public void addBlockToList(FABlock dfaBlock) {
		ArrayList<FABlock> faBlockList = nfaBuilderModel.getNfaBlockList();
		faBlockList.add(dfaBlock);
		nfaBuildViewInterface.addToPanel(dfaBlock);
	}

	// removes the NFAblock from the list and the panel
	public void removeBlockFromListAndPanel(FABlock faBlock) {
		ArrayList<FABlock> nfaBlockList = nfaBuilderModel.getNfaBlockList();
		nfaBlockList.remove(faBlock);
	}

	// Returns the NFAblock which is being selected.
	public FABlock getNFABlockObj(int x, int y) {
		FABlock selectedBlock = null;
		if (nfaBuilderModel.getNfaBlockList().size() > 0) {
			for (FABlock faBlock : nfaBuilderModel.getNfaBlockList()) {

				if (faBlock.isState()) {
					if (((StateBlock) faBlock).getX() == x && ((StateBlock) faBlock).getY() == y) {
						selectedBlock = faBlock;
					}
				} else {
					if (((TransitionBlock) faBlock).getX() == x && ((TransitionBlock) faBlock).getY() == y) {
						selectedBlock = faBlock;
					}
				}
			}
		}
		return selectedBlock;
	}

	// NFABlock x and y position are updated. This happens when the block is
	// being moved
	// from one position to other in the actionpanel.
	public FABlock updateBlockObj(int x, int y, FABlock dfaBlock) {
		if (dfaBlock != null) {
			dfaBlock.setX(x);
			dfaBlock.setY(y);
		}
		return dfaBlock;
	}

	// when transition is been added between two states, that particular
	// transition is added to the state list
	public void addTransitionBlocktoStateList(StateBlock startStateBlock, StateBlock finalStateBlcok, TransitionBlock b) {
		ArrayList<TransitionBlock> transitionList = null;
		if (startStateBlock.getStateTransitionList().get(finalStateBlcok) == null) {
			transitionList = new ArrayList<TransitionBlock>();
		} else {
			transitionList = startStateBlock.getStateTransitionList().get(finalStateBlcok);
		}
		transitionList.add(b);
		startStateBlock.getStateTransitionList().put(finalStateBlcok, transitionList);
	}
}
