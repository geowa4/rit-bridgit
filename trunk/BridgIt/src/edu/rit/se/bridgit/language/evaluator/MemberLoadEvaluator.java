package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class MemberLoadEvaluator extends Evaluator 
{
	private String name;
	
	public MemberLoadEvaluator(String name)
	{
		this.name = name;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		return scope.getVariableValue(name);
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{}

}
