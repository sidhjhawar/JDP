package com.turingworld.model;

import java.util.ArrayList;

public class BlockBuilderModel {
	public static int stateNo = 0;
	public static int transitionNo = 0;

	private ArrayList<Block> blockList;
	private ArrayList<Block> miniBlockList;

	public ArrayList<Block> getMiniBlockList() {
		return miniBlockList;
	}

	public void setMiniBlockList(ArrayList<Block> miniBlockList) {
		this.miniBlockList = miniBlockList;
	}

	public BlockBuilderModel() {
		blockList = new ArrayList<Block>();
		miniBlockList = new ArrayList<Block>();
	}

	public ArrayList<Block> getBlockList() {
		return blockList;
	}

	public void setBlockList(ArrayList<Block> blockList) {
		this.blockList = blockList;
	}
}
