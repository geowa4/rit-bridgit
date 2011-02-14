package edu.rit.se.bridgit.language.evaluator;

import java.util.HashMap;
import java.util.Map;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class Scope 
{
	private Scope parent;
	
	private Map<String, Type> variables = new HashMap<String, Type>();
	private Map<String, Type> constants = new HashMap<String, Type>();
	private Map<String, Type> parameters = new HashMap<String, Type>();
	private Map<String, Type> functions = new HashMap<String, Type>();
	
	public Scope(Scope parent) 
	{
		this.parent = parent;
	}
	
	public Scope getParent()
	{
		return parent;
	}

	public Type addVariable(String name, Type type) throws NameConflictException 
	{
		if(isVariable(name) || isConstant(name))
			throw new NameConflictException(name);
		variables.put(name, type);
		return type;
	}
	
	public Type addFunction(String name, Type type)throws NameConflictException 
	{
		if(isFunction(name))
			throw new NameConflictException(name);
		functions.put(name, type);
		return type;
	}
	
	public Type addParameter(String name, Type type) throws NameConflictException, InvalidTypeException{
		if(isParameter(name))
			throw new NameConflictException(name);
		parameters.put(name, type);
		return type;
	}
	
	public boolean isVariable(String name)
	{
		if(variables.containsKey(name))
			return true;
		else if(parent != null)
			return parent.isVariable(name);
		else
			return false;
	}
	
	public boolean isFunction(String name)
	{
		if(functions.containsKey(name))
			return true;
		else if(parent != null)
			return parent.isFunction(name);
		else
			return false;
	}
	
	public boolean isParameter(String name)
	{
		if(parameters.containsKey(name))
			return true;
		else if(parent != null)
			return parent.isParameter(name);
		else
			return false;
	}
	
	public Type addConstant(String name, Type type) throws NameConflictException 
	{
		if(isVariable(name) || isConstant(name))
			throw new NameConflictException(name);
		constants.put(name, type);
		return type;
	}
	
	public boolean isConstant(String name)
	{
		if(constants.containsKey(name))
			return true;
		else if(parent != null)
			return parent.isConstant(name);
		else
			return false;
	}
	
	public Type modifyVariableValue(String name, Type type) throws InvalidTypeException
	{
		if(variables.containsKey(name))
		{
			checkSameType(variables.get(name), type);
			variables.put(name, type);
			return type;
		}
		else
		{
			return parent.modifyVariableValue(name, type);
		}
	}

	public void checkSameType(Type expected, Type actual) throws InvalidTypeException
	{
		if(!expected.getPseudoType().equals(actual.getPseudoType()))
			throw new InvalidTypeException(actual.getType(), "Assignment");
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
	
	public Type getParameterValue(String name) 
	{
		if(parameters.containsKey(name))
			return parameters.get(name);
		else if(parent != null)
			return parent.getParameterValue(name);
		else
			return null;
	}
	
	public Type getFunctionValue(String name) 
	{
		if(functions.containsKey(name))
			return functions.get(name);
		else if(parent != null)
			return parent.getFunctionValue(name);
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
