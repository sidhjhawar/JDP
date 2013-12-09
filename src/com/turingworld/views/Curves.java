package com.turingworld.views;
/*
 * The class is used for storing all the curves or quadcurve which are drawn between any two states and state
 * itself. We are storing them to continuously maintain them on screen.
 * Since, its a graphics component , it is hard to continuously maintain them on screen and so we need to iterate 
 * over the data structure in the class and continuously plot on screen.
 */
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.turingworld.model.StateBlock;

public class Curves {
	private ArrayList<StateBlock> startStateBlock;
	private ArrayList<StateBlock> finalEndStateBlock;
	private ArrayList<QuadCurve2D.Double> curves;
	private QuadCurve2D.Double curve;
	private ArrayList<JLabel> stateTransits;
	
public Curves()
	{
		curves = new ArrayList<QuadCurve2D.Double>();
		startStateBlock = new ArrayList<StateBlock>();
		finalEndStateBlock = new ArrayList<StateBlock>();
		stateTransits= new ArrayList<JLabel>();
	}
	
	public ArrayList<QuadCurve2D.Double> getCurves() {
		return curves;
	}
	public void setCurves(QuadCurve2D.Double curves) {
		this.curves.add(curves);
	}
	public QuadCurve2D.Double getCurve() {
		return curve;
	}
	public void setCurve(QuadCurve2D.Double curve) {
		this.curve = curve;
	}

	public ArrayList<StateBlock> getStartStateBlock() {
		return startStateBlock;
	}

	public void setStartStateBlock(StateBlock startStateBlock1) {
		this.startStateBlock.add(startStateBlock1);
	}

	public ArrayList<StateBlock> getFinalEndStateBlock() {
		return finalEndStateBlock;
	}

	public void setFinalEndStateBlock(StateBlock finalEndStateBlock1) {
		this.finalEndStateBlock.add(finalEndStateBlock1);
	}

	public ArrayList<JLabel> getStateTransits() {
		return stateTransits;
	}

	public void setStateTransits(JLabel stateTransits) {
		this.stateTransits.add(stateTransits);
	}

}
