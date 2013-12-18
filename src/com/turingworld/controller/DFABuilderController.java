package com.turingworld.controller;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
/* This class is the controller for DFABuilder view and model. 
 * When the states and transitions are being added to the action panel or undoed, 
 * methods of this class are being called. This class also contains methods which checks whether
 * there is only one outward transition from each state. 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JLabel;

import com.turingworld.command.AddDFABlockCommand;
import com.turingworld.command.Invoker;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.model.DFABuilderModel;
import com.turingworld.model.FABlock;
import com.turingworld.model.StateBlock;
import com.turingworld.model.TransitionBlock;
import com.turingworld.views.DFABuildViewInterface;
import com.turingworld.views.DFABuilderView;

public class DFABuilderController {
	public static boolean isTriviaClicked;
	private DFABuilderModel dfaBuilderModel;
	private DFABuilderView dfaBuilderView;
	private DFABuildViewInterface dfaBuildViewInterface;
	private AddDFABlockCommand addDFABlockCommand;
	private Invoker invoker;

	public DFABuildViewInterface getDfaBuildViewInterface() {
		return dfaBuildViewInterface;
	}

	public void setDfaBuildViewInterface(DFABuildViewInterface dfaBuildViewInterface) {
		this.dfaBuildViewInterface = dfaBuildViewInterface;
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

	// constructor
	public DFABuilderController(DFABuilderModel dfaBuilderModel, DFABuildViewInterface dfaBuildViewInterface) {
		this.dfaBuilderModel = dfaBuilderModel;
		this.dfaBuildViewInterface = dfaBuildViewInterface;
		isTriviaClicked = false;
		invoker = new Invoker();
	}

	// Returns the DFAblock which is being selected.
	public FABlock getDFABlockObj(int x, int y) {
		FABlock selectedBlock = null;
		if (dfaBuilderModel.getDfaBlockList().size() > 0) {
			for (FABlock dfaBlock : dfaBuilderModel.getDfaBlockList()) {

				if (dfaBlock.isState()) {
					if (((StateBlock) dfaBlock).getX() == x && ((StateBlock) dfaBlock).getY() == y) {
						selectedBlock = dfaBlock;
					}
				} else {
					if (((TransitionBlock) dfaBlock).getX() == x && ((TransitionBlock) dfaBlock).getY() == y) {
						selectedBlock = dfaBlock;
					}
				}
			}
		}
		return selectedBlock;
	}

	// Checks whether any DFAblock exists or not when the user clicks in the
	// action panel
	public Boolean getDFABlockObject(int x, int y) {
		Boolean selectedBlock = false;
		for (FABlock dfaBlock : dfaBuilderModel.getDfaBlockList()) {

			if (dfaBlock.isState()) {
				if (((StateBlock) dfaBlock).getX() == x && ((StateBlock) dfaBlock).getY() == y) {
					selectedBlock = true;
				}
			} else {
				if (((TransitionBlock) dfaBlock).getX() == x && ((TransitionBlock) dfaBlock).getY() == y) {
					selectedBlock = true;
				}
			}
		}
		return selectedBlock;
	}

	// removes the DFAblock from the list and the panel
	public void removeBlockFromListAndPanel(FABlock dfaBlock) {
		ArrayList<FABlock> dfaBlockList = dfaBuilderModel.getDfaBlockList();
		dfaBlockList.remove(dfaBlock);
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

	// This creates the DFAblock when a state or transition is dropped on the
	// panel.
	// Properties are been set to the block being dropped to the panel.
	public FABlock createBlockObj(String url, int x, int y, int width, int height, JLabel label, boolean isState, String transitionType) {
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

	// DFABlock is added to the arraylist. This is done when the state or
	// transition is dropped on the panel.
	public void addBlockToList(FABlock dfaBlock) {
		ArrayList<FABlock> dfaBlockList = dfaBuilderModel.getDfaBlockList();
		dfaBlockList.add(dfaBlock);
		dfaBuildViewInterface.addToPanel(dfaBlock);
	}

	// DFABlock x and y position are updated. This happens when the block is
	// being moved
	// from one position to other in the actionpanel.
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

					if (dfaBlock.getCollisionBounds().intersects(dfaBlockObj.getCollisionBounds())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// calculates slope between two states. This is needed when transition is
	// selected between two states.
	// A path is created between those two states
	public double calculateSlope(int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			return -(180 - ((Math.atan2((y2 - y1), (x2 - x1)) * 180) / 3.14));
		} else {
			return ((Math.atan2((y2 - y1), (x2 - x1)) * 180) / 3.14);
		}
	}

	/*
	 * fetches next state from the List for transition from the input string.
	 * returns the NextState
	 */
	public StateBlock getNextState() {
		ArrayList<FABlock> dfaBlockList = new ArrayList<FABlock>();
		dfaBlockList = dfaBuilderModel.getDfaBlockList();

		for (FABlock dfaBlock : dfaBlockList) {
			if (dfaBlock.isState()) {
				if (((StateBlock) dfaBlock).getName().equalsIgnoreCase(dfaBuilderView.getCurrentPathState().getName())) {
					// matchiing the states
					StateBlock stateBlock = (StateBlock) dfaBlock;
					HashMap<StateBlock, ArrayList<TransitionBlock>> stateTransitionList = stateBlock.getStateTransitionList();
					// if the statetransition list contains the state then check
					// if the transition is present
					Iterator iter = stateTransitionList.keySet().iterator();
					while (iter.hasNext()) {
						StateBlock key = (StateBlock) iter.next();
						TransitionBlock value = (TransitionBlock) stateTransitionList.get(key).get(0);
						char check = value.getTransitionType().charAt(0);
						// char check = 'a';
						if (check == dfaBuilderView.getInputPathString().charAt(dfaBuilderView.getCurrentPathIndex())) { // matching
							// transition
							// found
							return key;
						}
					} // end of while
				}
			}
		}// end of for loop
		return null;

	}

	// This checks if there is only one outward transition from any particular
	// state.
	public boolean checkErrors(StateBlock firstBlock, StateBlock secondBlock, String db) {
		HashMap<StateBlock, ArrayList<TransitionBlock>> stateTransitionList = firstBlock.getStateTransitionList();
		for (ArrayList<TransitionBlock> list : stateTransitionList.values()) {
			for (TransitionBlock transitionBlock : list) {
				if (transitionBlock != null) {
					if (transitionBlock.getTransitionType() != null) {
						if (transitionBlock.getTransitionType().equals(db)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

}
