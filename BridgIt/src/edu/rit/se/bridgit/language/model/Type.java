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
	
	private void validateTypes() throws InvalidTypeException
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

	public void setType(Class<?> type) throws InvalidTypeException 
	{
		this.type = type;
		validateTypes();
	}
	
	public Object getValue() 
	{
		return value;
	}
	
	public void setValue(Object value) throws InvalidTypeException 
	{
		this.value = value;
		setType(value.getClass());
	}

	public String getPseudoType() {
		return pseudoType;
	}

	public void setPseudoType(String pseudoType) throws InvalidTypeException 
	{
		this.pseudoType = pseudoType;
		validateTypes();
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
