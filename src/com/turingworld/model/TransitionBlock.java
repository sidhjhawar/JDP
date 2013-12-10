package com.turingworld.model;
/*
 * The class TransitionBlock is of type FABlock. The TransitionBlocks that are created by the user are stored 
 * and retrieved using this class. All the TransitionBlock's attributes and methods are implemented here. 
 */
import java.awt.Rectangle;

import javax.swing.JLabel;

public class TransitionBlock implements FABlock {

	private String name;
	private int x;
	private int y;
	private int height;
	private int width;
	private JLabel dfaLabel;
	private String dfaLabelURL;
	private String transitionType;
	private boolean isState;

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

	public String getTransitionType() {
		return transitionType;
	}

	public void setTransitionType(String transitionType) {
		this.transitionType = transitionType;
	}

	// This method returns the bounds as a Rectangle.
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, dfaLabel.getIcon().getIconWidth(), dfaLabel.getIcon().getIconHeight());
	}

	
	@Override
	public Rectangle getCollisionBounds() {
		return new Rectangle(x - 50, y - 50, dfaLabel.getIcon().getIconWidth() + 100, dfaLabel.getIcon().getIconHeight() + 100);
	}
}
