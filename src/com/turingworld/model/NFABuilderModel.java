package com.turingworld.model;
/*
 * Like BlockBuilderModel and DFABuilder model, the NFABuilderModel is used to store all the NFA Blocks in the 
 * array list for further retrieval. 
 */
import java.util.ArrayList;

public class NFABuilderModel {

	private ArrayList<FABlock> faBlockList;
	public static int stateNo = 0;
	public static int transitionNo=0;

	public ArrayList<FABlock> getNfaBlockList() {
		return faBlockList;
	}

	public void setNfaBlockList(ArrayList<FABlock> faBlockList) {
		this.faBlockList = faBlockList;
	}

	public NFABuilderModel() {
		faBlockList = new ArrayList<FABlock>();
	}

}
