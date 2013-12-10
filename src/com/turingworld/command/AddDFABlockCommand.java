package com.turingworld.command;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
import com.turingworld.controller.DFABuilderController;
import com.turingworld.model.FABlock;

/*
 * This class is a concrete command that implements Command interface. It adds a
 * block(DFA) to the global block list that contains the list of all
 * the blocks
 */
public class AddDFABlockCommand implements Command {

	// Represents the DFA block
	private FABlock dfaBlock;

	// Reference to the dfaBuilderController
	private DFABuilderController dfaBuilderController;

	/*
	 * AddDFABlockCommand is the constructor that takes DFABlock and
	 * DFABuilderController as arguments and sets it to the local variables.
	 */
	public AddDFABlockCommand(FABlock dfaBlock, DFABuilderController dfaBuilderController) {
		this.dfaBlock = dfaBlock;
		this.dfaBuilderController = dfaBuilderController;
	}

	/*
	 * The execute method adds the block to the blocklist by calling the
	 * addBlockToList() of the blockBuilderController
	 */
	@Override
	public void execute() {
		dfaBuilderController.addBlockToList(dfaBlock);
	}

	/*
	 * The unexecute method removes block to the blocklist and the panel by
	 * calling the removeBlockFromListAndPanel() of the blockBuilderController
	 */
	@Override
	public void unexecute() {
		dfaBuilderController.removeBlockFromListAndPanel(dfaBlock);
	}
}