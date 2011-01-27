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
		return new Type(value, "Boolean");
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{
		if(t.getType().equals(Boolean.class))
			throw new InvalidTypeException(t.getType(), "Boolean");
	}

}
