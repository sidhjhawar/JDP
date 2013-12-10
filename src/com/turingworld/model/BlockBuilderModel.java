package com.turingworld.model;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
/*
 * This class is another model class which is used in the Build and Learn functionality. Here , we store the list of 
 * blocks such as transition , states that are created by the user. Each of the arraylist has the list of all the 
 * blocks that are used by the user to build.
 */
import java.util.ArrayList;

public class BlockBuilderModel {
	public static int stateNo = 0;
	public static int transitionNo = 0;

	private ArrayList<Block> blockList;
	// The below arraylist is useful for storing the snapshot.
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
