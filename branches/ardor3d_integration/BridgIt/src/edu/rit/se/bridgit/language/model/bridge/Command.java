package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

import edu.rit.se.bridgit.language.model.Type;

public abstract class Command {

	protected String methodName;
	protected List<Type> arguments;
	protected GraphicalBridge bridge;
	
	public Command()
	{
		methodName = null;
		arguments = null;
		bridge = null;
	}
	
	public Command(String methodName, List<Type> arguments, GraphicalBridge inBridge)
	{
		this.methodName = methodName;
		this.arguments = arguments;
		this.bridge = inBridge;
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
	
	public GraphicalBridge getBridge()
	{
		return this.bridge;
	}
	
	public abstract Command clone(String inMethod, List<Type> inArguments, GraphicalBridge inBridge);
	
	public abstract boolean execute(double delta);

	public void setBridge(GraphicalBridge graphicalBridge) {
		bridge = graphicalBridge;
		
	}
}
