package edu.rit.se.bridgit.language.model;

public class Type 
{
	private Class<?> type;
	private Object value;
	private String psuedoType;
	
	public Type(Object value) 
	{
		super();
		this.value = value;
		this.type = value.getClass();
	}
	
	public Class<?> getType() 
	{
		return type;
	}

	public void setType(Class<?> type) 
	{
		this.type = type;
	}
	
	public Object getValue() 
	{
		return value;
	}
	
	public void setValue(Object value) 
	{
		this.value = value;
	}

	public String getPsuedoType() {
		return psuedoType;
	}

	public void setPsuedoType(String psuedoType) {
		this.psuedoType = psuedoType;
	}
}
