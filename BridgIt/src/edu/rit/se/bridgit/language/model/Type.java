package edu.rit.se.bridgit.language.model;

public class Type 
{
	public static final Object VOID = new Void();
	public static final Object NULL = new Null();
	
	public static final String ANY_TYPE = "Any";
	public static final String VOID_TYPE = "Void";
	public static final String NULL_TYPE = "Null";
	public static final String STRING_TYPE = "String";
	public static final String INTEGER_TYPE = "Integer";
	public static final String DOUBLE_TYPE = "Double";
	public static final String LIST_TYPE = "List";
	
	private Class<?> type;
	private Object value;
	private String pseudoType;
	
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
		int index = pseudoType.indexOf(":");
		String finalPseudoType = index < 0 ? pseudoType : pseudoType.substring(0, index);
		if(value == NULL || value == VOID)
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
	
	public Type add(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type((Integer) r1Val + (Integer) r2Val, "Integer");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val + (Double) r2Val, "Double");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof String)
			ret = new Type((Integer) r1Val + (String) r2Val, "String");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val + (Integer) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Double)
			ret = new Type((Double) r1Val + (Double) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof String)
			ret = new Type((Double) r1Val + (String) r2Val, "String");
		
		else if(r1Val instanceof String &&
				r2Val instanceof Integer)
			ret = new Type((String) r1Val + (Integer) r2Val, "String");
		
		else if(r1Val instanceof String &&
				r2Val instanceof Double)
			ret = new Type((String) r1Val + (Double) r2Val, "String");
		
		else
			ret = new Type(this.toString() + other.toString(), "String");
		
		return ret;
	}

	public Type subtract(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type((Integer) r1Val - (Integer) r2Val, "Integer");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val - (Double) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val - (Integer) r2Val, "Double");
		
		else
			ret = new Type((Double) r1Val - (Double) r2Val, "Double");
		
		return ret;
	}

	public Type multiply(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type((Integer) r1Val * (Integer) r2Val, "Integer");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val * (Double) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val * (Integer) r2Val, "Double");
		
		else 
			ret = new Type((Double) r1Val * (Double) r2Val, "Double");
		
		return ret;
	}
	
	public Type divide(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type((Integer) r1Val / (Integer) r2Val, "Integer");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val / (Double) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val / (Integer) r2Val, "Double");
		
		else 
			ret = new Type((Double) r1Val / (Double) r2Val, "Double");
		
		return ret;
	}
	
	public Type mod(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type((Integer) r1Val % (Integer) r2Val, "Integer");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val % (Double) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val % (Integer) r2Val, "Double");
		
		else 
			ret = new Type((Double) r1Val % (Double) r2Val, "Double");
		
		return ret;
	}
	
	public Type unary() throws InvalidTypeException 
	{
		Object value = this.getValue();
		if(value instanceof Integer)
			return new Type(- (Integer) value, "Integer");
		else
			return new Type(- (Double) value, "Double");
	}

	public Type and(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		return new Type((Boolean) r1Val && (Boolean) r2Val, "Boolean");
	}
	
	public Type eq(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		return new Type(r1Val.equals(r2Val), "Boolean");
	}

	public Type gt(Type other) throws InvalidTypeException 
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		Type ret;
		if(r1Val instanceof Number &&
				r2Val instanceof Number)
			ret = new Type(((Number) r1Val).doubleValue() > ((Number) r2Val).doubleValue(), "Boolean");
		
		else if(r1Val instanceof String &&
				r2Val instanceof String)
			ret = new Type(((String) r1Val).compareTo((String) r2Val) > 0, "Boolean");
		
		else
			throw new InvalidTypeException("Both Pseudo Types must be a " +
					"Number or String to perform > operation");
		
		return ret;
	}

	public Type lt(Type other) throws InvalidTypeException
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		Type ret;
		if(r1Val instanceof Number &&
				r2Val instanceof Number)
			ret = new Type(((Number) r1Val).doubleValue() < ((Number) r2Val).doubleValue(), "Boolean");
		
		else if(r1Val instanceof String &&
				r2Val instanceof String)
			ret = new Type(((String) r1Val).compareTo((String) r2Val) < 0, "Boolean");
		
		else
			throw new InvalidTypeException("Both Pseudo Types must be a " +
					"Number or Boolean to perform < operation");
		
		return ret;
	}

	public Type negation() throws InvalidTypeException
	{
		return new Type(! (Boolean) this.getValue(), "Boolean");
	}

	public Type or(Type other) throws InvalidTypeException
	{
		Object r1Val = this.getValue();
		Object r2Val = other.getValue();
		return new Type((Boolean) r1Val || (Boolean) r2Val, "Boolean");
	}
	
	private static class Void {
		@Override
		public String toString() { return Type.VOID_TYPE; }
	}
	private static class Null {
		@Override
		public String toString() { return Type.NULL_TYPE; }
	}
}
