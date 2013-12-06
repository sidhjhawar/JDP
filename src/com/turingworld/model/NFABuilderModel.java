package com.turingworld.model;

import java.util.ArrayList;

public class NFABuilderModel {

	private ArrayList<FABlock> faBlockList;
	public static int stateNo = 0;

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
