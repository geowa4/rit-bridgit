package edu.rit.se.bridgit.language.evaluator.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.VoidType;

public class Function
{
	private ParameterList parameters;
	private Block functionBlock;
	private String returnType;
	private String functionName;
	private Evaluator returnValue;
	private Scope definitionScope;
	
	public Type apply(List<Type> args) throws InvalidTypeException, NameConflictException 
	{
		Scope executionScope = new Scope(definitionScope);
		if(parameters != null) 
		{
			parameters.setArgs(args);
			parameters.evaluate(executionScope);
		}
		if(functionBlock != null)
		{
			functionBlock.evaluate(executionScope);
		}
		if(getReturnType().contains(Type.VOID_TYPE))
		{
			return new VoidType();
		}
		else 
		{
			return returnValue.evaluate(executionScope);
		}
	}
	
	public Scope getDefinitionScope()
	{
		return this.definitionScope;
	}
	
	public void setDefinitionScope(Scope definitionScope)
	{
		this.definitionScope = definitionScope;
	}
	
	public void setParameters(ParameterList parameters) 
	{
		this.parameters = parameters;
	}
	
	public Block getFunctionBlock() 
	{
		return functionBlock;
	}
	
	public void setFunctionBlock(Block block) 
	{
		this.functionBlock = block;
	}
	
	public String getReturnType() 
	{
		return returnType;
	}
	
	public void setReturnType(String returnType) 
	{
		this.returnType = returnType;
	}
	
	public String getFunctionName() 
	{
		return functionName;
	}
	
	public void setFunctionName(String functionName) 
	{
		this.functionName = functionName;
	}
	
	public void setReturnValue(Evaluator returnValue) 
	{
		this.returnValue = returnValue;
	}
	
	@Override
	public String toString()
	{
		return this.functionName + ":" + this.returnType;
	}
}

