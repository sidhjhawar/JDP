package com.turingworld.command;

import com.turingworld.controller.DFABuilderController;
import com.turingworld.controller.NFABuilderController;
import com.turingworld.model.FABlock;

public class AddNFABlockCommand implements Command {

	private FABlock faBlock;
	private NFABuilderController nfaBuilderController;

	public AddNFABlockCommand(FABlock faBlock, NFABuilderController nfaBuilderController) {
		this.faBlock = faBlock;
		this.nfaBuilderController = nfaBuilderController;
	}

	@Override
	public void execute() {
		nfaBuilderController.addBlockToList(faBlock);
	}

	@Override
	public void unexecute() {
		nfaBuilderController.removeBlockFromListAndPanel(faBlock);
	}
}