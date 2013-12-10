package com.turingworld.model;
/*
 * The class StateBlock is of type FABlock. The StateBlocks that are created by the user are stored and retreived
 * using this class. All the StateBlock's attributes and methods are implemented here. 
 */
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;

public class StateBlock implements FABlock {
	private String name;
	private int x;
	private int y;
	private int height;
	private int width;
	private JLabel dfaLabel;
	private String dfaLabelURL;
	//isInitial is the flag that is set when the user marks the state as the initial state
	private boolean isInitial;
	//isFinal is the flag that is set when the user marks the state as final state.
	private boolean isFinal;
	//FABlock has this boolean field to mark the difference between state and transition. True in this class.
	private boolean isState;
	// The hashmap stateTransitionList stores a state and its various transitions in the form of key,value pair
	private HashMap<StateBlock, ArrayList<TransitionBlock>> stateTransitionList;

	public StateBlock() {
		stateTransitionList = new HashMap<StateBlock, ArrayList<TransitionBlock>>();
	}

	public JLabel getDfaLabel() {
		return dfaLabel;
	}

	public void setDfaLabel(JLabel dfaLabel) {
		this.dfaLabel = dfaLabel;
	}

	public String getDfaLabelURL() {
		return dfaLabelURL;
	}

	public void setDfaLabelURL(String dfaLabelURL) {
		this.dfaLabelURL = dfaLabelURL;
	}

	public boolean isState() {
		return isState;
	}

	public void setState(boolean isState) {
		this.isState = isState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isInitial() {
		return isInitial;
	}

	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public HashMap<StateBlock, ArrayList<TransitionBlock>> getStateTransitionList() {
		return stateTransitionList;
	}

	public void setStateTransitionList(HashMap<StateBlock, ArrayList<TransitionBlock>> stateTransitionList) {
		this.stateTransitionList = stateTransitionList;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, dfaLabel.getIcon().getIconWidth(), dfaLabel.getIcon().getIconHeight());
	}

	@Override
	public Rectangle getCollisionBounds() {
		return new Rectangle(x - 50, y - 50, dfaLabel.getIcon().getIconWidth() + 100, dfaLabel.getIcon().getIconHeight() + 100);
	}

	@Override
	public void setTransitionType(String transitionType) {
		// TODO Auto-generated method stub

	}
}
