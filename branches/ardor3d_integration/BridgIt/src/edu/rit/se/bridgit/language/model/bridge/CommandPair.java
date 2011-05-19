package edu.rit.se.bridgit.language.model.bridge;

public class CommandPair {

	Command command;
	GraphicalBridge graphicalBridge;
	
	public CommandPair(Command inCommand, GraphicalBridge in_graphicalBridge)
	{
		command = inCommand;
		graphicalBridge = in_graphicalBridge;
	}
}
