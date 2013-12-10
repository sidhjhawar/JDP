package com.turingworld.model;
/*
 * As the name suggests, this model class serves as the storage for storing the blocks that are created in the 
 * DFA functionality. The data structure is same as that of the BlockBuilderModel.
 */

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
	
	public DFABuilderModel()
	{
		dfaBlockList = new ArrayList<FABlock>();
	}


}
