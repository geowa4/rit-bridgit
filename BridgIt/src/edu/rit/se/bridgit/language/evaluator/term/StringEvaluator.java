package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class StringEvaluator implements Evaluator 
{

	private String value;
	
	public StringEvaluator(String value) 
	{
		super();
		this.value = value;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		Type t = new Type(value, "String");
		validateType(t);
		return t;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(t.getType() == null || !t.getType().equals(String.class))
			throw new InvalidTypeException(t.getType(), "String");
	}

}
