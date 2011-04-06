package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

import edu.rit.se.bridgit.language.model.Type;

public class Command {

	private String methodName;
	private List<Type> arguments;
	
	public Command(String methodName, List<Type> arguments)
	{
		this.methodName = methodName;
		this.arguments = arguments;
	}

	public String getMethodName()
	{
		return methodName;
	}

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	public List<Type> getArguments()
	{
		return arguments;
	}

	public void setArguments(List<Type> arguments)
	{
		this.arguments = arguments;
	}
}
