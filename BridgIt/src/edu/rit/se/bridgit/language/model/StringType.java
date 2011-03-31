package edu.rit.se.bridgit.language.model;

import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;


public class StringType extends Type
{
	public StringType(String value) throws InvalidTypeException
	{
		super(value, Type.STRING_TYPE);
		validateTypes();
	}
	
	@Override
	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{}
	
	@Override
	public String toString()
	{
		return "\"" + value.toString() + "\"";
	}

	@Override
	public Type add(Type other) throws InvalidTypeException 
	{
		return new StringType(value.toString().concat(other.getValue().toString()));
	}

	@Override
	public Type subtract(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("String cannot be used as a term in - operation.");
	}

	@Override
	public Type multiply(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("String cannot be used as a term in * operation.");
	}

	@Override
	public Type divide(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("String cannot be used as a term in % operation.");
	}

	@Override
	public Type mod(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("String cannot be used as a term in % operation.");
	}

	@Override
	public Type unary() throws InvalidTypeException 
	{
		throw new InvalidTypeException("String cannot be used as a term in unary operation.");
	}

	@Override
	public Type and(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("String cannot be used as a term in and operation.");
	}

	@Override
	public Type eq(Type other) throws InvalidTypeException 
	{
		return new BooleanType(value.equals(other.value));
	}

	@Override
	public Type gt(Type other) throws InvalidTypeException 
	{
		return new BooleanType(((String) value).compareTo((String) other.value) > 0);
	}

	@Override
	public Type lt(Type other) throws InvalidTypeException
	{
		return new BooleanType(((String) value).compareTo((String) other.value) < 0);
	}

	@Override
	public Type negation() throws InvalidTypeException
	{
		throw new InvalidTypeException("String cannot be used as a term in negation operation.");
	}

	@Override
	public Type or(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("String cannot be used as a term in Boolean operation.");
	}
}
