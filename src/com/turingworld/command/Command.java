package com.turingworld.command;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 * 
 */

/*
 * This interface is the command interface. It defines two methods execute() and
 * unexecute().
 */
public interface Command {
	
	//execute method definition
	public void execute();

	//unexecute method definition
	public void unexecute();
}
