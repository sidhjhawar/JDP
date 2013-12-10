package com.turingworld.model;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
/*
 * This class is a model for the blocks that are used in Build and Learn. This model class stores attributes
 * of all the blocks that are used by the user to build simple blocks in Build and Learn functionality.
 */
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.google.gson.annotations.Expose;

public class Block {

	private String name;
	private int x;
	private int y;
	private int height;
	private int width;
	@Expose
	private JLabel blockLabel;
	private String blockLabelURL;
	private boolean isState;
	private String transitionType;

	public String getTransitionType() {
		return transitionType;
	}

	public void setTransitionType(String transitionType2) {
		this.transitionType = transitionType2;
	}

	public boolean isState() {
		return isState;
	}

	public void setState(boolean isState) {
		this.isState = isState;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - 25, y - 25, blockLabel.getIcon().getIconWidth() + 50, blockLabel.getIcon().getIconHeight() + 50);
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

	public JLabel getBlockLabel() {
		return blockLabel;
	}

	public void setBlockLabel(JLabel spriteLabel) {
		this.blockLabel = spriteLabel;
	}

	public String getBlockLabelURL() {
		return blockLabelURL;
	}

	public void setBlockLabelURL(String blockLabelURL) {
		this.blockLabelURL = blockLabelURL;
	}
}