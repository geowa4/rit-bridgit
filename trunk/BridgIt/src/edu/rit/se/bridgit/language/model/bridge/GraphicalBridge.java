package edu.rit.se.bridgit.language.model.bridge;

import java.util.Vector;

public class GraphicalBridge
{
	String _pseudoType;
	Vector<String> _available_methods;
	
	public GraphicalBridge(String in_pseudoType, Vector<String> in_methods)
	{
		_pseudoType = in_pseudoType;
		_available_methods = in_methods;
	}
	
	public GraphicalBridge(GraphicalBridge in_graphicalBridge) 
	{
		this._pseudoType = in_graphicalBridge._pseudoType;
		this._available_methods = in_graphicalBridge._available_methods;
	}
	
	public Object sendMessage(String in_methodName) throws NoMethodFoundException
	{
		if(!_available_methods.contains(in_methodName))
		{
			throw new NoMethodFoundException(_pseudoType, in_methodName);
		}
		System.out.println(_pseudoType + "calls" + in_methodName);	
		return null;
	}
	
	public Vector<String> getAvailableMethods()
	{
		return _available_methods;
	}
	
	public String getPseudoType()
	{
		return _pseudoType;
	}
	
}
