package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class DoubleEvaluator extends Evaluator 
{

	private Double value;
	
	public DoubleEvaluator(Double value) 
	{
		super();
		this.value = value;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		Type t = new Type(value, "Double");
		validateType(t);
		return t;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{
		if(t.getType() == null || !t.getType().equals(Double.class))
			throw new InvalidTypeException(t.getType(), "Double");
	}

}
