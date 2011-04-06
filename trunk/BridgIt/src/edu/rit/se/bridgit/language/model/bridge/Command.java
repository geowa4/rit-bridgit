package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

public class Command {

	public String methodName;
	public String[] parameters;
	
	public Command(String methodName, String...params)
	{
		this.methodName = methodName;
		parameters = params;
	}
	
}
