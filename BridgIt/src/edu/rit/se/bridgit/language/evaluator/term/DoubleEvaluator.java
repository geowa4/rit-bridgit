package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.DoubleType;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class DoubleEvaluator implements Evaluator 
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
		Type t = new DoubleType(value);
		validateType(t);
		return t;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(t.getType() == null || !t.getType().equals(Double.class))
			throw new InvalidTypeException(t.getType(), Type.DOUBLE_TYPE);
	}

}
