package edu.rit.se.bridgit.language.model;

import edu.rit.se.bridgit.language.evaluator.function.Function;


public class FunctionType extends Type
{
	private Function value;
	
	public FunctionType(Function func) throws InvalidTypeException
	{
		super(func, Type.FUNCTION_TYPE);
		value = func;
		validateTypes();
	}
	
	@Override
	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{}
	
	@Override
	public String getPseudoType()
	{
		return value.getReturnType();
	}
	
	@Override
	public String toString()
	{
		return value.toString();
	}

	@Override
	public Type add(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an add operation.");
	}

	@Override
	public Type subtract(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an add operation.");
	}

	@Override
	public Type multiply(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an add operation.");
	}

	@Override
	public Type divide(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an add operation.");
	}

	@Override
	public Type mod(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an add operation.");
	}

	@Override
	public Type unary() throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an add operation.");
	}

	@Override
	public Type and(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an and operation.");
	}

	@Override
	public Type eq(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in an == operation.");
	}

	@Override
	public Type gt(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Function cannot be used as a term in a > operation.");
	}

	@Override
	public Type lt(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Function cannot be used as a term in a < operation.");
	}

	@Override
	public Type negation() throws InvalidTypeException
	{
		throw new InvalidTypeException("Function cannot be used as a term in a negation operation.");
	}

	@Override
	public Type or(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Function cannot be used as a term in an or operation.");
	}
}
