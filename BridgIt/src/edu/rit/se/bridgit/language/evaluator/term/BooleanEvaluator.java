package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class BooleanEvaluator extends Evaluator 
{

	private Boolean value;
	
	public BooleanEvaluator(Boolean value) 
	{
		super();
		this.value = value;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		Type t = new Type(value, "Boolean");
		validateType(t);
		return t;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{
		if(t.getType() == null || !t.getType().equals(Boolean.class))
			throw new InvalidTypeException(t.getType(), "Boolean");
	}

}