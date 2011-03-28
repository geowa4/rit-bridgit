package edu.rit.se.bridgit.language.model;


public class EmptyType extends Type
{
	public EmptyType(String pseudoType) throws InvalidTypeException
	{
		super(NullType.NULL_VALUE, pseudoType);
		validateTypes();
	}
	
	@Override
	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{
		this.pseudoType = pseudoType;
	}
	
	@Override
	public String toString()
	{
		return Type.NULL_TYPE;
	}

	@Override
	public Type add(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in an add operation.");
	}

	@Override
	public Type subtract(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in an add operation.");
	}

	@Override
	public Type multiply(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in an add operation.");
	}

	@Override
	public Type divide(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in an add operation.");
	}

	@Override
	public Type mod(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in an add operation.");
	}

	@Override
	public Type unary() throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in an add operation.");
	}

	@Override
	public Type and(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in an and operation.");
	}

	@Override
	public Type eq(Type other) throws InvalidTypeException 
	{
		return new BooleanType(value == other.value);
	}

	@Override
	public Type gt(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Null cannot be used as a term in a > operation.");
	}

	@Override
	public Type lt(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Null cannot be used as a term in a < operation.");
	}

	@Override
	public Type negation() throws InvalidTypeException
	{
		throw new InvalidTypeException("Null cannot be used as a term in a negation operation.");
	}

	@Override
	public Type or(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Null cannot be used as a term in an or operation.");
	}
}
