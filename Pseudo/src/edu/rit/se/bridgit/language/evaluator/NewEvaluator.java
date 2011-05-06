package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.ObjectType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class NewEvaluator implements Evaluator
{
	private String pseudoType;

	public NewEvaluator(String pseudoType)
	{
		this.pseudoType = pseudoType;
	}

	@Override
	public Type evaluate(Scope scope) 
	throws InvalidTypeException, NameConflictException
	{
		return new ObjectType(pseudoType);
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException
	{/* no-op */}

}
