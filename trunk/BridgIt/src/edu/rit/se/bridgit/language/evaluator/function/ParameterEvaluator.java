package edu.rit.se.bridgit.language.evaluator.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

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
		return new Type(null, pseudoType);
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		
	}
}
