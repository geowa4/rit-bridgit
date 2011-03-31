package edu.rit.se.bridgit.language.model;

import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public abstract class Type
{
	public static final String ANY_TYPE = "Any";
	public static final String VOID_TYPE = "Void";
	public static final String NULL_TYPE = "Null";
	public static final String STRING_TYPE = "String";
	public static final String INTEGER_TYPE = "Integer";
	public static final String DOUBLE_TYPE = "Double";
	public static final String BOOLEAN_TYPE = "Boolean";
	public static final String LIST_TYPE = "List";
	public static final String FUNCTION_TYPE = "Function";
	
	protected Class<?> type;
	protected Object value;
	protected String pseudoType;
	
	public Type(Object value, String pseudoType) throws InvalidTypeException 
	{
		this.value = value;
		this.type = value == null ? null : value.getClass();
		this.pseudoType = pseudoType;
		validateTypes();
	}
	
	protected void validateTypes() throws InvalidTypeException
	{
		if(value == null) throw new InvalidTypeException("Value of a Type cannot be null.");
		else if(pseudoType == null) throw new InvalidTypeException("Invalid Pseudo type.");
		int index = pseudoType.indexOf(":");
		String finalPseudoType = index < 0 ? pseudoType : pseudoType.substring(0, index);
		if(value == NullType.NULL_VALUE || value == VoidType.VOID_VALUE)
			return;
		if(type != null && !type.getName().contains(finalPseudoType))
			throw new InvalidTypeException(type, "Assignment");
	}
	
	public Class<?> getType() 
	{
		return type;
	}

	public Object getValue() 
	{
		return value;
	}

	public String getPseudoType() {
		return pseudoType;
	}

	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{
		this.pseudoType = pseudoType;
		validateTypes();
	}
	
	@Override
	public String toString()
	{
		return value.toString();
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Type)
		{
			Type o = (Type) other;
			return o.pseudoType.equals(this.pseudoType) && 
				o.value.equals(this.value);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public int hashCode()
	{
		return this.value.hashCode() * this.pseudoType.hashCode();
	}
	
	public abstract Type add(Type other) throws InvalidTypeException;

	public abstract Type subtract(Type other) throws InvalidTypeException;

	public abstract Type multiply(Type other) throws InvalidTypeException;
	
	public abstract Type divide(Type other) throws InvalidTypeException;
	
	public abstract Type mod(Type other) throws InvalidTypeException;
	
	public abstract Type unary() throws InvalidTypeException;

	public abstract Type and(Type other) throws InvalidTypeException;
	
	public abstract Type eq(Type other) throws InvalidTypeException;

	public abstract Type gt(Type other) throws InvalidTypeException;

	public abstract Type lt(Type other) throws InvalidTypeException;

	public abstract Type negation() throws InvalidTypeException;

	public abstract Type or(Type other) throws InvalidTypeException;
}
