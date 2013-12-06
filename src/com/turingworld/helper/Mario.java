package com.turingworld.helper;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Mario extends JLabel {

	JLabel marioLabel;
	private int xSpeed = 0;
	private int ySpeed = 0;

	public Mario() {

		marioLabel = new JLabel("");
	}

	/*
	 * depending on what state the state is mario Labels are changed accordingly
	 */
	public void setState(String state) {

		if (state.equalsIgnoreCase("RUNNING")) {
			xSpeed = 1;
			ySpeed = 0;
			marioLabel.setIcon(new ImageIcon("image/mario_run.gif"));
		} else if (state.equalsIgnoreCase("JUMPING")) {
			xSpeed = 0;
			ySpeed = -1;
			marioLabel.setIcon(new ImageIcon("image/mario.png"));
		} else if (state.equalsIgnoreCase("FALLING")) {
			xSpeed = 0;
			ySpeed = 1;
			marioLabel.setIcon(new ImageIcon("image/mario.png"));
		} else if (state.equalsIgnoreCase("FINISH")) {
			xSpeed = 0;
			ySpeed = 0;
			marioLabel.setIcon(new ImageIcon("image/mario.png"));
		} else {
			marioLabel.setIcon(new ImageIcon("image/mario.png"));
		}
		// return marioLabel;

	}

	public JLabel getMario() {
		return marioLabel;
	}

	public int getXspeed() {
		return xSpeed;
	}

	public void setXspeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

}
