package com.turingworld.command;

import com.turingworld.controller.BlockBuilderController;
import com.turingworld.model.Block;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */

/*
 * This class is a concrete command that implements Command interface. It adds a
 * block(Build and learn) to the global block list that contains the list of all
 * the blocks
 */
public class AddBlockCommand implements Command {

	// Represents the block
	private Block block;

	// Reference to the blockBuilderController
	private BlockBuilderController blockBuilderController;

	/*
	 * AddBlockCommand is the constructor that takes Block and
	 * BlockBuilderController as arguments and sets it to the local variables.
	 */
	public AddBlockCommand(Block block, BlockBuilderController blockBuilderController) {
		this.block = block;
		this.blockBuilderController = blockBuilderController;
	}

	/*
	 * The execute method adds the block to the blocklist by calling the
	 * addBlockToList() of the blockBuilderController
	 */
	@Override
	public void execute() {
		blockBuilderController.addBlockToList(block);
	}

	/*
	 * The unexecute method removes block to the blocklist and the panel by
	 * calling the removeBlockFromListAndPanel() of the blockBuilderController
	 */
	@Override
	public void unexecute() {
		blockBuilderController.removeBlockFromListAndPanel(block);
	}
}