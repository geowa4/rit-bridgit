package edu.rit.se.bridgit.language.evaluator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class Scope 
{
	private static Scope currentScope;
	
	public static Scope getCurrentScope()
	{
		return currentScope;
	}
	
	public static void setCurrentScope(Scope scope)
	{
		currentScope = scope;
	}
	
	private Scope parent;
	
	private Map<String, Type> variables = new HashMap<String, Type>();
	private Map<String, Type> constants = new HashMap<String, Type>();
	private Map<String, Type> parameters = new HashMap<String, Type>();
	private Map<String, Function> functions = new HashMap<String, Function>();
	
	public Scope(Scope parent) 
	{
		this.parent = parent;
	}
	
	public Scope getParent()
	{
		return parent;
	}

	public void addVariable(String name, Type type) throws NameConflictException 
	{
		if(isVariable(name) || isConstant(name))
			throw new NameConflictException(name);
		variables.put(name, type);
	}
	
	public void addFunction(Function function)throws NameConflictException 
	{
		if(isFunction(function.getFunctionName()))
			throw new NameConflictException(function.getFunctionName());
		functions.put(function.getFunctionName(), function);
	}
	
	public int getFunctionParameterSize(String functionName){
				
		return 0;
	}
	
	public Type addParameter(String name, Type type) throws NameConflictException, InvalidTypeException{
		if(isParameter(name))
			throw new NameConflictException(name);
		parameters.put(name, type);
		return type;
	}
	
	public Boolean removeParameter(String name){
		if(isParameter(name)){
			parameters.remove(name);
			return true;
		}
		else 
			return false;
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
	
	public void addConstant(String name, Type type) throws NameConflictException 
	{
		if(isVariable(name) || isConstant(name))
			throw new NameConflictException(name);
		constants.put(name, type);
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
	
	public Type getFunctionValue(String name) throws InvalidTypeException 
	{
		if(functions.containsKey(name))
			return new Type(functions.get(name), "Function");
		else if(parent != null)
			return new Type(parent.getFunctionValue(name), "Function");
		else
			return null;
	}
	
	public Function getFunction(String name)
	{
		if(functions.containsKey(name))
			return functions.get(name);
		else if(parent != null)
			return parent.getFunction(name);
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
	
	public Set<String> getVariables()
	{
		Set<String> ret = variables.keySet();
		ret.addAll(parent.getVariables());
		return ret;
	}

	public Set<String> getConstants()
	{
		Set<String> ret = constants.keySet();
		ret.addAll(parent.getConstants());
		return ret;
	}
	
	public Set<String> getFunctions()
	{
		Set<String> ret = functions.keySet();
		ret.addAll(parent.getFunctions());
		return ret;
	}
}
