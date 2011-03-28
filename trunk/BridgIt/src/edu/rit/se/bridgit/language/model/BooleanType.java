package edu.rit.se.bridgit.language.model;


public class BooleanType extends Type
{
	public BooleanType(Boolean value) throws InvalidTypeException
	{
		super(value, Type.BOOLEAN_TYPE);
		validateTypes();
	}
	
	@Override
	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{}
	
	@Override
	public String toString()
	{
		return value.toString();
	}

	@Override
	public Type add(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in an add operation.");
	}

	@Override
	public Type subtract(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in an add operation.");
	}

	@Override
	public Type multiply(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in an add operation.");
	}

	@Override
	public Type divide(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in an add operation.");
	}

	@Override
	public Type mod(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in an add operation.");
	}

	@Override
	public Type unary() throws InvalidTypeException 
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in an add operation.");
	}

	@Override
	public Type and(Type other) throws InvalidTypeException 
	{
		return new BooleanType((Boolean) value && (Boolean) other.value);
	}

	@Override
	public Type eq(Type other) throws InvalidTypeException 
	{
		return new BooleanType((Boolean) value == (Boolean) other.value);
	}

	@Override
	public Type gt(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in a > operation.");
	}

	@Override
	public Type lt(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Boolean cannot be used as a term in a < operation.");
	}

	@Override
	public Type negation() throws InvalidTypeException
	{
		return new BooleanType(!(Boolean) value);
	}

	@Override
	public Type or(Type other) throws InvalidTypeException
	{
		return new BooleanType((Boolean) value || (Boolean) other.value);
	}
}
