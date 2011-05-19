package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.BinaryEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class LessThanEvaluator extends BinaryEvaluator 
{
	
	public LessThanEvaluator(Evaluator op1, Evaluator op2)
	{
		super(op1, op2);
		this.operation = "Less Than";
	}

	@Override
	public Type evaluate(Scope scope) throws PseudoException
	{
		Type result1 = op1.evaluate(scope);
		Type result2= op2.evaluate(scope);
		validateType(result1);
		validateType(result2);
		return result1.lt(result2);
	}

}
