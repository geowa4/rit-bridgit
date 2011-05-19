package edu.rit.se.bridgit.language.evaluator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.rit.se.bridgit.language.bridge.PseudoBridge;
import edu.rit.se.bridgit.language.builtin.function.BuiltInFunctionFactory;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.model.FunctionType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class Scope 
{
	private static Scope currentScope;
	private static PseudoBridge pseudoBridge;
	
	public static Scope getCurrentScope()
	{
		return currentScope;
	}
	
	public static void setCurrentScope(Scope scope)
	{
		currentScope = scope;
	}
	
	public static PseudoBridge getPseudoBridge()
	{
		return pseudoBridge;
	}

	public static void setPseudoBridge(PseudoBridge pseudoBridge)
	{
		Scope.pseudoBridge = pseudoBridge;
	}

	private Scope parent;
	
	private Map<String, Type> variables = new HashMap<String, Type>();
	private Map<String, Type> constants = new HashMap<String, Type>();
	private Map<String, Function> functions = new HashMap<String, Function>();
	
	public Scope(Scope parent) 
	{
		this.parent = parent;
		if(parent == null) loadBuiltInFunctions();
	}
	
	private void loadBuiltInFunctions()
	{
		for(Function f : BuiltInFunctionFactory.getFunctions())
		{
			try
			{
				addFunction(f);
			} 
			catch (NameConflictException e)
			{
				System.err.println("Skipped function " + f.getFunctionName() + 
						" as it has already been added.");
			}
		}
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
	
	public Type removeVariable(String name)
	{
		if(variables.containsKey(name))
			return variables.remove(name);
		else if(parent != null)
			return parent.removeVariable(name);
		else
			return null;
	}
	
	public Type getFunctionValue(String name) throws InvalidTypeException 
	{
		if(functions.containsKey(name))
			return new FunctionType(functions.get(name));
		else if(parent != null)
			return parent.getFunctionValue(name);
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
