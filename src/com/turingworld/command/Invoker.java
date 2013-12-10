package com.turingworld.command;

import java.util.LinkedList;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */

/*
 * This is the invoker class. It is used to store the history of the commands
 * executed. It has the implementation of execute and unexecute method.
 */
public class Invoker {

	// Command class reference.
	private Command command;

	// Linkedlist of Commands to store the undo hoistory
	public LinkedList<Command> undocommandHistory = new LinkedList<Command>();

	// Linkedlist of commands to store the redo history
	public LinkedList<Command> redocommandHistory = new LinkedList<Command>();

	/*
	 * setCommand method takes in Command as parameter and sets it to the
	 * command variable
	 */
	public void setCommand(Command command) {
		this.command = command;
	}

	/*
	 * invoke() executes the command, adds it to the history of
	 * undoCommandHistory and redoCommandHistory
	 */
	public void invoke() {
		// Add command to commandHistory before executing it.
		command.execute();

		// Add the command to the undoCommandHistory
		undocommandHistory.add(command);

		// Clear the redoCommandHistory
		redocommandHistory.clear();
	}

	/*
	 * revoke() unexecutes the command and adds the command to the redo history
	 */
	public void revoke() {
		// Unexecutes the commands
		command.unexecute();

		// Adds the command to the redoCommandHistory
		redocommandHistory.add(command);
	}

	/*
	 * redo() executes the command and adds the command to the
	 * undoCommandHistory
	 */
	public void redo() {
		// Execute the command
		command.execute();

		// Adds the command to the undoCommandHistory
		undocommandHistory.add(command);
	}
}
