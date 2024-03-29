package edu.rit.se.bridgit.language.model;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class ObjectType extends Type
{
	public ObjectType(String pseudoType) throws InvalidTypeException
	{
		super(Scope.getPseudoBridge().buildInstanceBridge(pseudoType), pseudoType);
	}
	
	@Override
	protected void validateClassType()
	{/*ignore class types for models*/}
	
	@Override
	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{}
	
	@Override
	public String toString()
	{
		return "{" + pseudoType + "}";
	}

	@Override
	public Type add(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an add operation.");
	}

	@Override
	public Type subtract(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an add operation.");
	}

	@Override
	public Type multiply(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an add operation.");
	}

	@Override
	public Type divide(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an add operation.");
	}

	@Override
	public Type mod(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an add operation.");
	}

	@Override
	public Type unary() throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an add operation.");
	}

	@Override
	public Type and(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an and operation.");
	}

	@Override
	public Type eq(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in an == operation.");
	}

	@Override
	public Type gt(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Object cannot be used as a term in a > operation.");
	}

	@Override
	public Type lt(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Object cannot be used as a term in a < operation.");
	}

	@Override
	public Type negation() throws InvalidTypeException
	{
		throw new InvalidTypeException("Object cannot be used as a term in a negation operation.");
	}

	@Override
	public Type or(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Object cannot be used as a term in an or operation.");
	}
}
