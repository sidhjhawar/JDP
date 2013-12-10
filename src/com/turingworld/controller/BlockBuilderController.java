package com.turingworld.controller;

/* This class is the controller for BlockBuilder view and model. 
 * When the states and transitions are being added to the action panel or undoed, 
 * methods of this class are being called. This class also contains methods which check whether
 * two blocks are similar or not. 
 */

import java.io.File;
import java.util.ArrayList;
import javax.swing.JLabel;
import com.turingworld.command.AddBlockCommand;
import com.turingworld.command.Invoker;
import com.turingworld.model.Block;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.views.BuildViewInterface;

public class BlockBuilderController {

	private BlockBuilderModel blockBuilderModel;
	private Block block;
	private AddBlockCommand addBlockCommand;
	private Invoker invoker;
	private BuildViewInterface buildViewInterface;
	private StringBuilder outputString;
	public static boolean isTriviaClicked;
	private ControllerHelper blockBuilderControllerHelper;

	public BlockBuilderModel getBlockBuilderModel() {
		return blockBuilderModel;
	}

	public void setBlockBuilderModel(BlockBuilderModel blockBuilderModel) {
		this.blockBuilderModel = blockBuilderModel;
	}

	public StringBuilder getOutputString() {
		return outputString;
	}

	public void setOutputString(StringBuilder outputString) {
		this.outputString = outputString;
	}

	public BuildViewInterface getBuildViewInterface() {
		return buildViewInterface;
	}

	public void setBuildViewInterface(BuildViewInterface buildViewInterface) {
		this.buildViewInterface = buildViewInterface;
	}

	// constructor
	public BlockBuilderController(BlockBuilderModel blockBuilderModel, BuildViewInterface buildViewInterface) {

		this.blockBuilderModel = blockBuilderModel;
		this.buildViewInterface = buildViewInterface;
		this.blockBuilderControllerHelper = new ControllerHelper();
		isTriviaClicked = false;
		invoker = new Invoker();
	}

	// This creates the block when a state or transition is dropped on the
	// panel.
	// Properties are been set to the block being dropped to the panel.
	public Block createBlockObj(int x, int y, int width, int height, JLabel label, boolean isState, String transitionType) {
		block = new Block();
		block.setX(x);
		block.setY(y);
		block.setWidth(width);
		block.setHeight(height);
		block.setBlockLabel(label);
		block.setTransitionType(transitionType);

		if (isState) {
			block.setName("q" + BlockBuilderModel.stateNo);
			BlockBuilderModel.stateNo++;
			block.setState(true);
		} else {
			block.setName("t" + BlockBuilderModel.transitionNo);
			BlockBuilderModel.transitionNo++;
			block.setState(false);
		}
		addBlockCommand = new AddBlockCommand(block, this);
		invoker.setCommand(addBlockCommand);
		invoker.invoke();

		return block;
	}

	// Block x and y position are updated. This happens when the block is being
	// moved
	// from one position to other in the actionpanel.
	public Block updateBlockObj(int x, int y, Block block) {
		if (block != null) {
			block.setX(x);
			block.setY(y);
		}
		return block;
	}

	// Returns the block which is being selected.
	public Block getBlockObj(int x, int y) {
		Block selectedBlock = null;
		for (Block block : blockBuilderModel.getBlockList()) {
			if (block.getX() == x && block.getY() == y) {

				selectedBlock = block;
			}
		}
		return selectedBlock;
	}

	// Checks whether any block exists or not when the user clicks in the action
	// panel
	public Boolean getBlockObject(int x, int y) {
		Boolean selectedBlock = false;
		for (Block block : blockBuilderModel.getBlockList()) {
			if (block.getX() == x && block.getY() == y) {
				selectedBlock = true;
			}
		}
		return selectedBlock;
	}

	// Checks if the two blocks are similar or not. If both are states or
	// transitions.
	// This is checked when a state or transition is dropped on the panel.
	// It checks if the adjacent block is similar or not
	public boolean isAdjacentToSimilarBlock(Block block) {
		for (Block blockObj : blockBuilderModel.getBlockList()) {
			if (blockObj != block) {
				if (block.isState() == blockObj.isState()) {

					if (block.getBounds().intersects(blockObj.getBounds())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// returns the block if the adjacent block is not similar
	public Block isAdjacentToDissimilarBlock(Block block) {
		for (Block blockObj : blockBuilderModel.getBlockList()) {
			if (blockObj != block) {
				if (block.isState() != blockObj.isState()) {
					if (block.getBounds().intersects(blockObj.getBounds())) {
						return blockObj;
					}
				}
			}
		}
		return null;
	}

	// Block is added to the arraylist. This is done when the state or
	// transition is dropped on the panel.
	public void addBlockToList(Block block) {
		ArrayList<Block> blockList = blockBuilderModel.getBlockList();
		blockList.add(block);
		buildViewInterface.addBlockToPanel(block);
	}

	// last block is being removed from the undo command history when undo
	// button is clicked.
	public void removeBlock() {
		if (invoker.undocommandHistory.size() > 0) {
			AddBlockCommand addBlockCommand = (AddBlockCommand) invoker.undocommandHistory.removeLast();
			invoker.setCommand(addBlockCommand);
			invoker.revoke();
		}
	}

	// last block is being removed from the redo command history when undo
	// button is clicked.
	public void redoBlock() {
		if (invoker.redocommandHistory.size() > 0) {
			AddBlockCommand addBlockCommand = (AddBlockCommand) invoker.redocommandHistory.removeLast();
			invoker.setCommand(addBlockCommand);
			invoker.redo();
		}
	}

	// Block is being removed from the list and panel when undo button is
	// clicked
	public void removeBlockFromListAndPanel(Block block) {
		ArrayList<Block> blockList = blockBuilderModel.getBlockList();
		blockList.remove(block);
		buildViewInterface.removeBlockFromPanel(block);
	}

	// This happens when the snapshot of blocks are being taken from the action
	// panel
	public void sortTransitions() {
		outputString = new StringBuilder();
		ArrayList<Block> blockList = blockBuilderModel.getBlockList();
		int length = blockList.size();
		int counter, index;
		Block tempBlock = null;

		for (counter = 0; counter < length; counter++) {
			for (index = counter; index < length - 1; index++) {
				if (blockList.get(index).getX() > blockList.get(index + 1).getX()) {
					tempBlock = blockList.get(index);
					blockList.set(index, blockList.get(index + 1));
					blockList.set(index + 1, tempBlock);
				}
			}
		}

		for (counter = 0; counter < length; counter++) {
			if (blockList.get(counter).isState() == false) {
				if (blockList.get(counter).getTransitionType() != null) {
					outputString.append(blockList.get(counter).getTransitionType());
				}
			}
		}
	}

	/*
	 * method invoked on clicking save in the BlockBuilder View. Method
	 * serializes the BlockBuilderModel and saves them
	 */

	public void saveTuringWorld() {
		blockBuilderControllerHelper.convertToJSON(blockBuilderModel);
	}

	public BlockBuilderModel loadTuringWorld(File file) {
		return blockBuilderControllerHelper.importJSON(file);
	}

	/*
	 * Method to calculate the y coordinate of the parabola for any x
	 * coordinate(x) between two points (x1,y1) and (x2,y2)
	 */
	public int getYforTrajectory(int x, int x1, int x2, int y1, int y2) {
		// (h,k)
		int h = x1 + (x2 - x1) / 2;
		int k = y1 - 50;
		int sq = (int) (Math.pow(((x - h) / 10), 2));
		int y = sq + k;
		return y;
	}
}
