package com.turingworld.controller;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.turingworld.command.AddBlockCommand;
import com.turingworld.command.AddDFABlockCommand;
import com.turingworld.command.Invoker;
import com.turingworld.helper.TimerTask;
import com.turingworld.model.Block;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.FABlock;
import com.turingworld.model.DFABuilderModel;
import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;
import com.turingworld.views.DFABuildViewInterface;
import com.turingworld.views.DFABuilderView;

public class DFABuilderController {
	public static boolean isTriviaClicked;

	private TimerTask timerTask;

	private DFABuilderModel dfaBuilderModel;
	private DFABuilderView dfaBuilderView;
	private DFABuildViewInterface dfaBuildViewInterface;
	private AddDFABlockCommand addDFABlockCommand;
	private Invoker invoker;

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public DFABuildViewInterface getDfaBuildViewInterface() {
		return dfaBuildViewInterface;
	}

	public void setDfaBuildViewInterface(
			DFABuildViewInterface dfaBuildViewInterface) {
		this.dfaBuildViewInterface = dfaBuildViewInterface;
	}

	public void setTimerTask(TimerTask timerTask) {
		this.timerTask = timerTask;
	}

	public DFABuilderModel getDfaBuilderModel() {
		return dfaBuilderModel;
	}

	public void setDfaBuilderModel(DFABuilderModel dfaBuilderModel) {
		this.dfaBuilderModel = dfaBuilderModel;
	}

	public DFABuilderView getDfaBuilderView() {
		return dfaBuilderView;
	}

	public void setDfaBuilderView(DFABuilderView dfaBuilderView) {
		this.dfaBuilderView = dfaBuilderView;
	}

	public DFABuilderController(DFABuilderModel dfaBuilderModel,
			DFABuildViewInterface dfaBuildViewInterface) {
		this.dfaBuilderModel = dfaBuilderModel;
		this.dfaBuildViewInterface = dfaBuildViewInterface;
		this.timerTask = new TimerTask(this);
		isTriviaClicked = false;
		invoker = new Invoker();
	}

	public FABlock getDFABlockObj(int x, int y) {
		FABlock selectedBlock = null;
		if (dfaBuilderModel.getDfaBlockList().size() > 0) {
			for (FABlock dfaBlock : dfaBuilderModel.getDfaBlockList()) {

				if (dfaBlock.isState()) {
					if (((StateBlock) dfaBlock).getX() == x
							&& ((StateBlock) dfaBlock).getY() == y) {
						selectedBlock = dfaBlock;
					}
				} else {
					if (((TransitionBlock) dfaBlock).getX() == x
							&& ((TransitionBlock) dfaBlock).getY() == y) {
						selectedBlock = dfaBlock;
					}
				}
			}
		}
		return selectedBlock;
	}

	public Boolean getDFABlockObject(int x, int y) {
		Boolean selectedBlock = false;
		for (FABlock dfaBlock : dfaBuilderModel.getDfaBlockList()) {

			if (dfaBlock.isState()) {
				if (((StateBlock) dfaBlock).getX() == x
						&& ((StateBlock) dfaBlock).getY() == y) {
					selectedBlock = true;
				}
			} else {
				if (((TransitionBlock) dfaBlock).getX() == x
						&& ((TransitionBlock) dfaBlock).getY() == y) {
					selectedBlock = true;
				}
			}
		}
		return selectedBlock;
	}

	public void removeBlockFromListAndPanel(FABlock dfaBlock) {
		ArrayList<FABlock> dfaBlockList = dfaBuilderModel.getDfaBlockList();
		dfaBlockList.remove(dfaBlock);
	}

	public void addTransitionBlocktoStateList(StateBlock startStateBlock,
			StateBlock finalStateBlcok, TransitionBlock b) {
		ArrayList<TransitionBlock> transitionList = null;
		if (startStateBlock.getStateTransitionList().get(finalStateBlcok) == null) {
			transitionList = new ArrayList<TransitionBlock>();
		} else {
			transitionList = startStateBlock.getStateTransitionList().get(
					finalStateBlcok);
		}
		transitionList.add(b);
		startStateBlock.getStateTransitionList().put(finalStateBlcok,
				transitionList);
	}

	public FABlock createBlockObj(String url, int x, int y, int width, int height,
			JLabel label, boolean isState, String transitionType) {
		FABlock dfaBlock;
		if (isState == true) {
			dfaBlock = new StateBlock();
		} else {
			dfaBlock = new TransitionBlock();
		}
	

		dfaBlock.setX(x);
		dfaBlock.setY(y);
		dfaBlock.setWidth(width);
		dfaBlock.setHeight(height);
		dfaBlock.setDfaLabel(label);
		dfaBlock.setTransitionType(transitionType);
		dfaBlock.setDfaLabelURL(url);

		if (isState) {
			dfaBlock.setName("q" + DFABuilderModel.stateNo);
			DFABuilderModel.stateNo++;
			dfaBlock.setState(true);
		} else {
			dfaBlock.setName("t" + BlockBuilderModel.transitionNo);
			BlockBuilderModel.transitionNo++;
			dfaBlock.setState(false);
		}
		addDFABlockCommand = new AddDFABlockCommand(dfaBlock, this);
		invoker.setCommand(addDFABlockCommand);
		invoker.invoke();
		return dfaBlock;
	}

	public void addBlockToList(FABlock dfaBlock) {
		System.out.println(dfaBlock.getName());
		ArrayList<FABlock> dfaBlockList = dfaBuilderModel.getDfaBlockList();
		dfaBlockList.add(dfaBlock);
		dfaBuildViewInterface.addToPanel(dfaBlock);
	}

	public FABlock updateBlockObj(int x, int y, FABlock dfaBlock) {
		if (dfaBlock != null) {
			dfaBlock.setX(x);
			dfaBlock.setY(y);
		}
		return dfaBlock;
	}

	public boolean isAdjacentToSimilarBlock(FABlock dfaBlock) {
		for (FABlock dfaBlockObj : dfaBuilderModel.getDfaBlockList()) {
			if (dfaBlockObj != dfaBlock) {
				if (dfaBlock.isState() == dfaBlockObj.isState()) {

					if (dfaBlock.getCollisionBounds().intersects(
							dfaBlockObj.getCollisionBounds())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public double calculateSlope(int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			return -(180 - ((Math.atan2((y2 - y1), (x2 - x1)) * 180) / 3.14));
		} else {
			return ((Math.atan2((y2 - y1), (x2 - x1)) * 180) / 3.14);
		}
	}


}
