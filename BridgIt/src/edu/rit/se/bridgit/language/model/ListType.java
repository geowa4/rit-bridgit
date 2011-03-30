package edu.rit.se.bridgit.language.model;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ListType extends Type
{
	public ListType(List<Type> list) throws InvalidTypeException
	{
		super(list, Type.LIST_TYPE);
		validateTypes();
	}
	
	@Override
	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString()
	{
		Iterator<Type> iter = ((List<Type>) getValue()).iterator();
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		while(iter.hasNext())
		{
			Type t = iter.next();
			sb.append(t.toString());
			if(iter.hasNext()) sb.append(',');
		}
		sb.append(']');
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type add(Type other) throws InvalidTypeException 
	{
		Object val = this.getValue();
		List<Type> list = new ArrayList<Type>();
		if(val instanceof List<?>)
		{
			list.addAll((List<Type>) val);
			list.add(other);
		}
		return new ListType(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type subtract(Type other) throws InvalidTypeException 
	{
		Object val = this.getValue();
		List<Type> list = new ArrayList<Type>();
		list.addAll((List<Type>) val);
		list.remove(other);
		return new ListType(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type multiply(Type other) throws InvalidTypeException 
	{
		Object otherVal = other.getValue();
		if(!(otherVal instanceof Integer))
		{
			throw new InvalidTypeException("List can only be multiplied by an Integer.");
		}
		Object val = this.getValue();
		List<Type> list = new ArrayList<Type>();
		Integer times = (Integer) otherVal;
		for(int i = 0; i < times; ++i)
		{
			list.addAll((List<Type>) val);
		}
		return new ListType(list);
	}

	@Override
	public Type divide(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("List cannot be used as a term in % operation.");
	}

	@Override
	public Type mod(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("List cannot be used as a term in % operation.");
	}

	@Override
	public Type unary() throws InvalidTypeException 
	{
		throw new InvalidTypeException("List cannot be used as a term in unary operation.");
	}

	@Override
	public Type and(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("List cannot be used as a term in and operation.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type eq(Type other) throws InvalidTypeException 
	{
		List<Type> r1Val = (List<Type>) this.getValue();
		List<Type> r2Val = (List<Type>) other.getValue();
		return new BooleanType(r1Val.equals(r2Val));
	}

	@Override
	public Type gt(Type other) throws InvalidTypeException 
	{
		throw new InvalidTypeException("List cannot be used as a term in > operation.");
	}

	@Override
	public Type lt(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("List cannot be used as a term in < operation.");
	}

	@Override
	public Type negation() throws InvalidTypeException
	{
		throw new InvalidTypeException("List cannot be used as a term in negation operation.");
	}

	@Override
	public Type or(Type other) throws InvalidTypeException
	{
		throw new InvalidTypeException("List cannot be used as a term in Boolean operation.");
	}
}
