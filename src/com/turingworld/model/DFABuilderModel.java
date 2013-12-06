package com.turingworld.model;

import java.util.ArrayList;

public class DFABuilderModel {

	private ArrayList<FABlock> dfaBlockList;
	public static int stateNo = 0;

	public ArrayList<FABlock> getDfaBlockList() {
		return dfaBlockList;
	}

	public void setDfaBlockList(ArrayList<FABlock> dfaBlockList) {
		this.dfaBlockList = dfaBlockList;
	}

	public DFABuilderModel() {
		dfaBlockList = new ArrayList<FABlock>();
	}

}
