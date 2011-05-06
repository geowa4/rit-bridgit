package edu.rit.se.bridgit.language.model.exception;

public class InvalidTypeException extends Exception {
	private static final long serialVersionUID = -3894386344414091704L;
	
	public InvalidTypeException(String text)
	{
		super(text);
	}
	
	public InvalidTypeException(Class<?> type, String operation)
	{
		super(type + " is an invalid type for " + operation);
	}
}
