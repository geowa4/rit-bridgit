package edu.rit.se.bridgit.language.model.exception;

public class NameConflictException extends Exception
{
	private static final long serialVersionUID = -2172881185726330078L;
	
	public NameConflictException(String name)
	{
		super(name + " has already been defined.");
	}
	
	public NameConflictException(String name, String text)
	{
			super(name + " " + text);
	}
}
