package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.BinaryEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class EqualsEvaluator extends BinaryEvaluator 
{
	public EqualsEvaluator(Evaluator op1, Evaluator op2)
	{
		super(op1, op2);
		this.operation = "Equals";
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		Type result1 = op1.evaluate(scope);
		Type result2= op2.evaluate(scope);
		Object r1Val = result1.getValue();
		Object r2Val = result2.getValue();
		return new Type(r1Val.equals(r2Val), "Boolean");
	}
}
