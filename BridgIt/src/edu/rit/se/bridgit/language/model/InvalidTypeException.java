package edu.rit.se.bridgit.language.model;

public class InvalidTypeException extends Exception {
	private static final long serialVersionUID = -3894386344414091704L;
	
	public InvalidTypeException(Class<?> type, String operation)
	{
		super(type + " is an invalid type for " + operation + " operation");
	}
}
