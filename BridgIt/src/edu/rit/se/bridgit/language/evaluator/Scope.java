package edu.rit.se.bridgit.language.evaluator;

import java.util.HashMap;
import java.util.Map;

import edu.rit.se.bridgit.language.model.Type;

public class Scope 
{
	private Scope parent;
	
	private Map<String, Type> variables = new HashMap<String, Type>();
	private Map<String, Type> constants = new HashMap<String, Type>();
	
	public Scope(Scope parent) 
	{
		this.parent = parent;
	}
	
	public Scope getParent()
	{
		return parent;
	}

	public Type addVariable(String name, Type type) 
	{
		variables.put(name, type);
		return type;
	}
	
	public Type addConstant(String name, Type type) 
	{
		constants.put(name, type);
		return type;
	}
	
	public Type modifyVariableValue(String name, Type type)
	{
		if(variables.containsKey(name))
		{
			variables.put(name, type);
			return type;
		}
		else
		{
			return parent.modifyVariableValue(name, type);
		}
	}
	
	public Type getVariableValue(String name) 
	{
		if(variables.containsKey(name))
			return variables.get(name);
		else if(parent != null)
			return parent.getVariableValue(name);
		else
			return null;
	}
	
	public Type getConstantValue(String name) 
	{
		if(constants.containsKey(name))
			return constants.get(name);
		else if(parent != null)
			return parent.getConstantValue(name);
		else
			return null;
	}
}
