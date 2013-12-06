
package com.turingworld.command;

import com.turingworld.controller.BlockBuilderController;
import com.turingworld.model.Block;

public class AddBlockCommand implements Command {

	private Block block;
	private BlockBuilderController blockBuilderController;
	
	public AddBlockCommand(Block block, BlockBuilderController blockBuilderController)
	{
		this.block = block;
		this.blockBuilderController = blockBuilderController;
	}
	
	@Override
	public void execute() {
		blockBuilderController.addBlockToList(block);
	}

	@Override
	public void unexecute() {
		blockBuilderController.removeBlockFromListAndPanel(block);
	}
}