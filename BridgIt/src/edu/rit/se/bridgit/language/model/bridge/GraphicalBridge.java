package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;
import java.util.Queue;

public class GraphicalBridge
{
	String pseudoType;
	List<String> availableMethods;
	Queue<?> actionQueue;
	
	public GraphicalBridge(String pseudoType, List<String> methods, Queue<?> actionQueue)
	{
		this.pseudoType = pseudoType;
		this.availableMethods = methods;
		this.actionQueue = actionQueue;
	}
	
	public GraphicalBridge(GraphicalBridge other) 
	{
		this(other.pseudoType, other.availableMethods, other.actionQueue);
	}
	
	public Object sendMessage(String methodName) throws NoMethodFoundException
	{
		if(!availableMethods.contains(methodName))
		{
			throw new NoMethodFoundException(pseudoType, methodName);
		}
		actionQueue.add(null); //TODO: this should add something real
		return new Boolean(true); //TODO: must return something real
	}
	
	public List<String> getAvailableMethods()
	{
		return availableMethods;
	}
	
	public String getPseudoType()
	{
		return pseudoType;
	}
	
}
