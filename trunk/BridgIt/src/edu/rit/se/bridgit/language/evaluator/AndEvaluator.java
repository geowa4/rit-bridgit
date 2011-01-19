package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class AndEvaluator extends BinaryEvaluator {

	public AndEvaluator(Evaluator op1, Evaluator op2) throws InvalidTypeException {
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
	public Type evaluate() throws InvalidTypeException 
	{
		Type result1 = op1.evaluate();
		Type result2= op2.evaluate();
		validateType(result1);
		validateType(result2);
		Object r1Val = result1.getValue();
		Object r2Val = result2.getValue();
		return new Type((Boolean) r1Val && (Boolean) r2Val);
	}
}
