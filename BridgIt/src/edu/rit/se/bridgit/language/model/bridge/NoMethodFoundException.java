package edu.rit.se.bridgit.language.model.bridge;

public class NoMethodFoundException extends Exception {
	private static final long serialVersionUID = -3894386344414091704L;
	
	public NoMethodFoundException(String type, String operation)
	{
		super(type + " does not contain the method:" + operation);
	}
}
