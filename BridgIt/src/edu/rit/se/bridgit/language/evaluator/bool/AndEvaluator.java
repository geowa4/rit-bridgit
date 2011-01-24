package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.BinaryEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class AndEvaluator extends BinaryEvaluator 
{

	public AndEvaluator(Evaluator op1, Evaluator op2)
	{
		super(op1, op2);
	}

	@Override
	protected void validateType(Type op) throws InvalidTypeException 
	{
		if(!op.getType().equals(Boolean.class))
		{
			throw new InvalidTypeException(op.getType(), "And");
		}
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		Type result1 = op1.evaluate(scope);
		Type result2= op2.evaluate(scope);
		validateType(result1);
		validateType(result2);
		Object r1Val = result1.getValue();
		Object r2Val = result2.getValue();
		return new Type((Boolean) r1Val && (Boolean) r2Val);
	}
}
