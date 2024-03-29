package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.BinaryEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class EqualsEvaluator extends BinaryEvaluator 
{
	public EqualsEvaluator(Evaluator op1, Evaluator op2)
	{
		super(op1, op2);
		this.operation = "Equals";
	}

	@Override
	public Type evaluate(Scope scope) throws PseudoException
	{
		Type result1 = op1.evaluate(scope);
		Type result2= op2.evaluate(scope);
		return result1.eq(result2);
	}
}
