package com.turingworld.command;

import java.util.LinkedList;
public class Invoker {

	private Command command;
	public LinkedList<Command> undocommandHistory = new LinkedList<Command>();
	public LinkedList<Command> redocommandHistory = new LinkedList<Command>();

	public void setCommand(Command command) {
		this.command = command;
	
	}

	public void invoke() {
		//Add command to commandHistory before executing it.
		command.execute();
		undocommandHistory.add(command);
		redocommandHistory.clear();
		
	}
	
	public void revoke()
	{
			command.unexecute();
			redocommandHistory.add(command);
		
	}
	
	public void redo(){
			command.execute();
			undocommandHistory.add(command);
	}
}
