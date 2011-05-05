package edu.rit.se.bridgit.language.evaluator.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.EmptyType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class ParameterEvaluator implements Evaluator
{
	private String name;
	private String pseudoType;
	
	public ParameterEvaluator(String name, String pseudoType)
	{
		this.name = name;
		this.pseudoType = pseudoType;
	}

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		return new EmptyType(pseudoType);
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		
	}
}
