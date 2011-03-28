package edu.rit.se.bridgit.language.model;


public class DoubleType extends Type
{
	protected Double value;

	public DoubleType(Double value) throws InvalidTypeException
	{
		super(value, Type.DOUBLE_TYPE);
		this.value = value;
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
		if(other.value instanceof Integer)
			return new DoubleType(value + (Integer) other.value);
		
		else if(other.value instanceof Double)
			return new DoubleType(value + (Double) other.value);
		
		else if(other.value instanceof String)
			return new StringType(value + other.value.toString());
		
		else
			throw new InvalidTypeException(other + " is an invlid type for addition with and Integer");
	}

	@Override
	public Type subtract(Type other) throws InvalidTypeException 
	{
		if(other.value instanceof Integer)
			return new DoubleType(value - (Integer) other.value);
		
		else if(other.value instanceof Double)
			return new DoubleType(value - (Double) other.value);
		
		else
			throw new InvalidTypeException(other + " is an invlid type for addition with and Integer");
	}

	@Override
	public Type multiply(Type other) throws InvalidTypeException 
	{
		if(other.value instanceof Integer)
			return new DoubleType(value * (Integer) other.value);
		
		else if(other.value instanceof Double)
			return new DoubleType(value * (Double) other.value);
		
		else
			throw new InvalidTypeException(other + " is an invlid type for addition with and Integer");
	}

	@Override
	public Type divide(Type other) throws InvalidTypeException 
	{
		if(other.value instanceof Integer)
			return new DoubleType(value / (Integer) other.value);
		
		else if(other.value instanceof Double)
			return new DoubleType(value / (Double) other.value);
		
		else
			throw new InvalidTypeException(other + " is an invlid type for addition with and Integer");
	}

	@Override
	public Type mod(Type other) throws InvalidTypeException 
	{
		if(other.value instanceof Integer)
			return new DoubleType(value % (Integer) other.value);
		
		else if(other.value instanceof Double)
			return new DoubleType(value % (Double) other.value);
		
		else
			throw new InvalidTypeException(other + " is an invlid type for addition with and Integer");
	}

	@Override
	public Type unary() throws InvalidTypeException 
	{
		return new DoubleType(-value);
	}

	@Override
	public Type and(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("Integer cannot be used as a term in and operation.");
	}

	@Override
	public Type eq(Type other) throws InvalidTypeException 
	{
		return new BooleanType(value.equals(other.value));
	}

	@Override
	public Type gt(Type other) throws InvalidTypeException 
	{
		if(other.value instanceof Number)
			return new BooleanType(((Number) value).doubleValue() > ((Number) other.value).doubleValue());
		else
			throw new InvalidTypeException(other + " is an invalid type for comparison with " + Type.INTEGER_TYPE);
	}

	@Override
	public Type lt(Type other) throws InvalidTypeException
	{
		if(other.value instanceof Number)
			return new BooleanType(((Number) value).doubleValue() < ((Number) other.value).doubleValue());
		else
			throw new InvalidTypeException(other + " is an invalid type for comparison with " + Type.INTEGER_TYPE);
	}

	@Override
	public Type negation() throws InvalidTypeException
	{
		throw new InvalidTypeException(Type.INTEGER_TYPE + " cannot be used as a term in negation operation.");
	}

	@Override
	public Type or(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("Integer cannot be used as a term in Boolean operation.");
	}
}
