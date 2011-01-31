package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class NullEvaluator extends Evaluator
{
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException
	{
		return new Type(null, "Null");
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException
	{}

}
