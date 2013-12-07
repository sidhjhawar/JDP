package com.turingworld.controller;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.turingworld.command.AddBlockCommand;
import com.turingworld.command.Invoker;
import com.turingworld.helper.TimerTask;
import com.turingworld.model.Block;
import com.turingworld.model.BlockBuilderModel;
import com.turingworld.views.BuildViewInterface;

public class BlockBuilderController {

	private BlockBuilderModel blockBuilderModel;

	public BlockBuilderModel getBlockBuilderModel() {
		return blockBuilderModel;
	}

	public void setBlockBuilderModel(BlockBuilderModel blockBuilderModel) {
		this.blockBuilderModel = blockBuilderModel;
	}

	private Block block;
	private AddBlockCommand addBlockCommand;
	private Invoker invoker;
	// private BlockBuilderView blockBuilderView;
	private BuildViewInterface buildViewInterface;

	private StringBuilder outputString;
	public static boolean isTriviaClicked;
	private ControllerHelper blockBuilderControllerHelper;
	private TimerTask timerTask;

	public StringBuilder getOutputString() {
		return outputString;
	}

	public void setOutputString(StringBuilder outputString) {
		this.outputString = outputString;
	}

	public BlockBuilderController(BlockBuilderModel blockBuilderModel, BuildViewInterface buildViewInterface) {

		this.blockBuilderModel = blockBuilderModel;
		this.buildViewInterface = buildViewInterface;
		this.blockBuilderControllerHelper = new ControllerHelper();
		this.timerTask = new TimerTask(this);

		isTriviaClicked = false;
		invoker = new Invoker();
	}

	public Block createBlockObj(int x, int y, int width, int height, JLabel label, boolean isState, String transitionType) {
		block = new Block();
		block.setX(x);
		block.setY(y);
		block.setWidth(width);
		block.setHeight(height);
		block.setBlockLabel(label);
		block.setTransitionType(transitionType);
		// System.out.println("In side the create block");

		if (isState) {
			// System.out.println("In side State");
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

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public BuildViewInterface getBuildViewInterface() {
		return buildViewInterface;
	}

	public void setBuildViewInterface(BuildViewInterface buildViewInterface) {
		this.buildViewInterface = buildViewInterface;
	}

	public void setTimerTask(TimerTask timerTask) {
		this.timerTask = timerTask;
	}

	public Block updateBlockObj(int x, int y, Block block) {
		if (block != null) {
			block.setX(x);
			block.setY(y);
		}
		return block;
	}

	public Block getBlockObj(int x, int y) {
		Block selectedBlock = null;
		for (Block block : blockBuilderModel.getBlockList()) {
			if (block.getX() == x && block.getY() == y) {

				selectedBlock = block;
			}
		}
		return selectedBlock;
	}

	public Boolean getBlockObject(int x, int y) {
		Boolean selectedBlock = false;
		for (Block block : blockBuilderModel.getBlockList()) {
			if (block.getX() == x && block.getY() == y) {
				selectedBlock = true;
			}
		}
		return selectedBlock;
	}

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

	public void addBlockToList(Block block) {
		System.out.println(block.getName());
		ArrayList<Block> blockList = blockBuilderModel.getBlockList();
		blockList.add(block);
		buildViewInterface.addBlockToPanel(block);
	}

	public void removeBlock() {
		if (invoker.undocommandHistory.size() > 0) {
			AddBlockCommand addBlockCommand = (AddBlockCommand) invoker.undocommandHistory.removeLast();
			invoker.setCommand(addBlockCommand);
			invoker.revoke();
		}

	}

	public void redoBlock() {

		// invoker.redo();
		if (invoker.redocommandHistory.size() > 0) {

			AddBlockCommand addBlockCommand = (AddBlockCommand) invoker.redocommandHistory.removeLast();
			invoker.setCommand(addBlockCommand);
			invoker.redo();

		}

	}

	public void removeBlockFromListAndPanel(Block block) {
		ArrayList<Block> blockList = blockBuilderModel.getBlockList();
		blockList.remove(block);
		buildViewInterface.removeBlockFromPanel(block);
	}

	public void sortTransitions()

	{
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

		// System.out.println(outputString);
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
		System.out.println("sq:" + sq);
		int y = sq + k;
		return y;
	}

}
