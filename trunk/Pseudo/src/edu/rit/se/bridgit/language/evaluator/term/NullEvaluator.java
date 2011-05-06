package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.NullType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class NullEvaluator implements Evaluator
{
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException
	{
		return new NullType();
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException
	{}

}
