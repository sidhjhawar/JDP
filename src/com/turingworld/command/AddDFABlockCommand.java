package com.turingworld.command;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
import com.turingworld.controller.DFABuilderController;
import com.turingworld.model.FABlock;

public class AddDFABlockCommand implements Command {

	private FABlock dfaBlock;
	private DFABuilderController dfaBuilderController;

	public AddDFABlockCommand(FABlock dfaBlock, DFABuilderController dfaBuilderController) {
		this.dfaBlock = dfaBlock;
		this.dfaBuilderController = dfaBuilderController;
	}

	@Override
	public void execute() {
		dfaBuilderController.addBlockToList(dfaBlock);
	}

	@Override
	public void unexecute() {
		dfaBuilderController.removeBlockFromListAndPanel(dfaBlock);
	}
}