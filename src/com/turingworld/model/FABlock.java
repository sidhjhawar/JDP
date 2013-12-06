package com.turingworld.model;

import java.awt.Rectangle;

import javax.swing.JLabel;

public interface FABlock {
	public Rectangle getBounds();

	public Rectangle getCollisionBounds();

	public boolean isState();

	public void setState(boolean isState);

	public String getName();

	public void setName(String name);

	public int getX();

	public void setX(int x);

	public int getY();

	public void setY(int y);

	public int getHeight();

	public void setHeight(int height);

	public int getWidth();

	public void setWidth(int width);

	public JLabel getDfaLabel();

	public void setDfaLabel(JLabel dfaLabel);

	public String getDfaLabelURL();

	public void setDfaLabelURL(String dfaLabelURL);

	public void setTransitionType(String transitionType);
}
