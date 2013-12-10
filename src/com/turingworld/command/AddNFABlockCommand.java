package com.turingworld.command;

import com.turingworld.controller.NFABuilderController;
import com.turingworld.model.FABlock;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 *
 */

/*
 * This class is a concrete command that implements Command interface. It adds a
 * block(NFA) to the global block list that contains the list of all
 * the blocks
 */
public class AddNFABlockCommand implements Command {

	// Represents the fablock
	private FABlock faBlock;
	
	// Reference to the nfaBuilderController
	private NFABuilderController nfaBuilderController;

	/*
	 * AddNFABlockCommand is the constructor that takes FABlock and
	 * NFABuilderController as arguments and sets it to the local variables.
	 */
	public AddNFABlockCommand(FABlock faBlock, NFABuilderController nfaBuilderController) {
		this.faBlock = faBlock;
		this.nfaBuilderController = nfaBuilderController;
	}

	/*
	 * The execute method adds the block to the blocklist by calling the
	 * addBlockToList() of the nfaBuilderController
	 */
	@Override
	public void execute() {
		nfaBuilderController.addBlockToList(faBlock);
	}

	/*
	 * The unexecute method removes block to the blocklist and the panel by
	 * calling the removeBlockFromListAndPanel() of the blockBuilderController
	 */
	@Override
	public void unexecute() {
		nfaBuilderController.removeBlockFromListAndPanel(faBlock);
	}
}