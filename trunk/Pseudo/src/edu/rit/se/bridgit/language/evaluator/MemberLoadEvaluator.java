package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class MemberLoadEvaluator implements Evaluator 
{
	private String name;
	
	public MemberLoadEvaluator(String name)
	{
		this.name = name;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		if(scope.isVariable(name))
			return scope.getVariableValue(name);
		else if(scope.isConstant(name))
			return scope.getConstantValue(name);
		else if(scope.isFunction(name))
			return scope.getFunctionValue(name);
		else
			throw new InvalidTypeException(null, "Member Load");
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{}

}
