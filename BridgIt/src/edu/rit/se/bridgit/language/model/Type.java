package edu.rit.se.bridgit.language.model;

public class Type 
{
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
	
	public Type(Type in_type) throws InvalidTypeException
	{
		this.value = in_type.value;
		this.type = value == null ? null : value.getClass();
		this.pseudoType = in_type.pseudoType;
		validateTypes();
	}


	private void validateTypes() throws InvalidTypeException
	{
		int index = pseudoType.indexOf(":");
		String finalPseudoType = index < 0 ? pseudoType : pseudoType.substring(0, index);
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
}
