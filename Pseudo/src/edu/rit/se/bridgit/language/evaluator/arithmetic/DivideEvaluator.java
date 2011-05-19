package edu.rit.se.bridgit.language.evaluator.arithmetic;

import edu.rit.se.bridgit.language.evaluator.BinaryEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class DivideEvaluator extends BinaryEvaluator 
{
	public DivideEvaluator(Evaluator op1, Evaluator op2)
	{
		super(op1, op2);
		this.operation = "Division";
	}

	@Override
	public void validateType(Type op) throws InvalidTypeException 
	{
		if(!(	op.getType().equals(Integer.class) 
			||	op.getType().equals(Double.class)
			)
		) 
		{
			throw new InvalidTypeException(op.getType(), operation);
		}
	}
	
	@Override
	public Type evaluate(Scope scope) throws PseudoException
	{
		Type result1 = op1.evaluate(scope);
		Type result2= op2.evaluate(scope);
		validateType(result1);
		validateType(result2);
		return result1.divide(result2);
	}
}
