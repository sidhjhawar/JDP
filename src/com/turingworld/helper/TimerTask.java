package com.turingworld.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.turingworld.controller.BlockBuilderController;
import com.turingworld.controller.DFABuilderController;
import com.turingworld.views.BlockBuilderView;
import com.turingworld.views.BuildViewInterface;
import com.turingworld.views.DFABuilderView;

public class TimerTask {

	private final Timer t;
	private BlockBuilderController blockBuilderController;
	private DFABuilderController dfaBuilderController;
	private boolean finalswitchcall = false;
	public boolean selfloop = false;
	public int jumpUp = 0;
	// 0-> jumpUp , 1-> jumpDown ,2 ->timer stop
	public int x1, y1, x2, y2, c;
	float m;
	JLabel label;

	public TimerTask(BlockBuilderController controller) {
		this.blockBuilderController = controller;
		t = new Timer(10, new TimerTaskListener());
	}

	public TimerTask(DFABuilderController controller) {
		this.dfaBuilderController = controller;
		t = new Timer(10, new TimerTaskListener());
	}

	public void stop() {
		t.stop();
	}

	public void run(JLabel label, int x1, int y1, int x2, int y2) {

		if (x1 == x2 && y1 == y2) {
			selfloop = true;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2 - 100;
			this.label = label;
			m = 1;
			c = 0;
		} else {
			selfloop = false;
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			this.label = label;

			m = (float) (y2 - y1) / (x2 - x1);
			c = (int) (y1 - m * x1);

		}

		t.start();
	}

	public boolean isRunning() {
		return t.isRunning();
	}

	class TimerTaskListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			notifyObservers();
		}
	}

	public void notifyObservers() {

		BuildViewInterface blockBuilderView = null;
		DFABuilderView dfaBuilderView = null;

		if (dfaBuilderController.getDfaBuildViewInterface() instanceof DFABuilderView) {
			dfaBuilderView = (DFABuilderView) dfaBuilderController.getDfaBuildViewInterface();
		}

		if (selfloop == false)
			y1 = (int) ((float) m * x1 + c);

		if (x1 > x2 && selfloop == false) {
			dfaBuilderView.moveState(label, --x1, y1, x2, y2);
		} else if (x1 < x2) {
			dfaBuilderView.moveState(label, ++x1, y1, x2, y2);
		}

		else if (x1 == x2 && y1 > y2 && selfloop == false) {
			dfaBuilderView.moveState(label, x1, --y1, x2, y2);
		} else if (x1 == x2 && y1 < y2 && selfloop == false) {
			dfaBuilderView.moveState(label, x1, ++y1, x2, y2);
		}

		/*
		 * else if (jumpUp == true) { y1 += 100; }
		 */

		else if (selfloop == true && x1 == x2 && y1 > y2 && jumpUp == 0) {
			// jumpUp = 1;
			dfaBuilderView.moveSelfloop(label, x1, --y1, x2, y2);
		}

		else if (selfloop == true && x1 == x2 && y1 < y2 && jumpUp == 1) {
			dfaBuilderView.moveSelfloop(label, x1, ++y1, x2, y2);
		}

	}

}
