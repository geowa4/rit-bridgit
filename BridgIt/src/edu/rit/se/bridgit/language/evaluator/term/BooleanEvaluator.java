package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.BooleanType;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class BooleanEvaluator implements Evaluator 
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
		Type t = new BooleanType(value);
		validateType(t);
		return t;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(t.getType() == null || !t.getType().equals(Boolean.class))
			throw new InvalidTypeException(t.getType(), Type.BOOLEAN_TYPE);
	}

}
