package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
/*
 * This class is for setting the following options to a state:
 * 1. Initial State.
 * 2. Final State.
 * 3. Transition between two states.
 */
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class PopupForStateView extends JPopupMenu {
	private JMenuItem addInitialState;
	private JMenuItem addFinalState;
	private JMenuItem addTransition;

	public PopupForStateView() {
		addInitialState = new JMenuItem("Add Initial State");
		addFinalState = new JMenuItem("Add Final State");
		addTransition = new JMenuItem("Add Transition");

		add(addInitialState);
		add(addFinalState);
		add(addTransition);
	}

	public void registerActionListner(ActionListener actionListener) {
		addInitialState.addActionListener(actionListener);
		addFinalState.addActionListener(actionListener);
		addTransition.addActionListener(actionListener);
	}
}
