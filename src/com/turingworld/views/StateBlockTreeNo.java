package com.turingworld.views;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JLabel;

import com.turingworld.model.StateBlock;

public class StateBlockTreeNo {
	private StateBlock stateBlock;
	private int treeNo;
	private JLabel treeLabel;
	private HashMap<StateBlockTreeNo, String> treeConnectionList;
	private int forestPosition;

	public int getForestPosition() {
		return forestPosition;
	}

	public void setForestPosition(int forestPosition) {
		this.forestPosition = forestPosition;
	}

	public StateBlockTreeNo() {
		treeConnectionList = new LinkedHashMap<StateBlockTreeNo, String>();
	}

	public HashMap<StateBlockTreeNo, String> getTreeConnectionList() {
		return treeConnectionList;
	}

	public void setTreeConnectionList(HashMap<StateBlockTreeNo, String> treeConnectionList) {
		this.treeConnectionList = treeConnectionList;
	}

	public JLabel getTreeLabel() {
		return treeLabel;
	}

	public void setTreeLabel(JLabel treeLabel) {
		this.treeLabel = treeLabel;
	}

	public StateBlock getStateBlock() {
		return stateBlock;
	}

	public void setStateBlock(StateBlock stateBlock) {
		this.stateBlock = stateBlock;
	}

	public int getTreeNo() {
		return treeNo;
	}

	public void setTreeNo(int treeNo) {
		this.treeNo = treeNo;
	}

}
