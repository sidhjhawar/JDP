package com.turingworld.controller;

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

	public NFABuilderController(NFABuilderModel nfaBuilderModel, NFABuildViewInterface nfaBuildViewInterface) {
		this.nfaBuilderModel = nfaBuilderModel;
		this.nfaBuildViewInterface = nfaBuildViewInterface;
		invoker = new Invoker();
	}

	public void setNfaBuilderView(NFABuilderView nfaBuilderView) {

	}

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
			faBlock.setName("q" + DFABuilderModel.stateNo);
			DFABuilderModel.stateNo++;
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

	public void addBlockToList(FABlock dfaBlock) {
		ArrayList<FABlock> faBlockList = nfaBuilderModel.getNfaBlockList();
		faBlockList.add(dfaBlock);
		nfaBuildViewInterface.addToPanel(dfaBlock);
	}

	public void removeBlockFromListAndPanel(FABlock faBlock) {
		ArrayList<FABlock> nfaBlockList = nfaBuilderModel.getNfaBlockList();
		nfaBlockList.remove(faBlock);
	}

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

	public FABlock updateBlockObj(int x, int y, FABlock dfaBlock) {
		if (dfaBlock != null) {
			dfaBlock.setX(x);
			dfaBlock.setY(y);
		}
		return dfaBlock;
	}

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
