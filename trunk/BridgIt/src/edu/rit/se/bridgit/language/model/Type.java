package edu.rit.se.bridgit.language.model;

public class Type 
{
	private Class<?> type;
	private Object value;
	private String pseudoType;
	
	public Type(Object value, String pseudoType) throws InvalidTypeException 
	{
		super();
		this.value = value;
		this.type = value == null ? null : value.getClass();
		this.pseudoType = pseudoType;
		validateTypes();
	}
	
	private void validateTypes() throws InvalidTypeException
	{
		//This method will likely need to change
		if(type != null && !type.getName().contains(pseudoType))
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
