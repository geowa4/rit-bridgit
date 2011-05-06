package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class IntegerEvaluator implements Evaluator 
{

	private Integer value;
	
	public IntegerEvaluator(Integer value) 
	{
		super();
		this.value = value;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		Type t = new IntegerType(value);
		validateType(t);
		return t;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(t.getType() == null || !t.getType().equals(Integer.class))
			throw new InvalidTypeException(t.getType(), "Integer");
	}

}
