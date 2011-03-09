package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class NullEvaluator implements Evaluator
{
	public static String NULL_TYPE = "Null";
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException
	{
		return new Type(Type.NULL, NULL_TYPE);
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException
	{}

}
