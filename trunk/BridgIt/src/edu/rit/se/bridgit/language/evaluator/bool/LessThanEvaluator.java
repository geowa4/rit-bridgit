package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.BinaryEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class LessThanEvaluator extends BinaryEvaluator 
{
	
	public LessThanEvaluator(Evaluator op1, Evaluator op2)
	{
		super(op1, op2);
		this.operation = "Less Than";
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		Type result1 = op1.evaluate(scope);
		Type result2= op2.evaluate(scope);
		validateType(result1);
		validateType(result2);
		Object r1Val = result1.getValue();
		Object r2Val = result2.getValue();
		Type ret;
		if(r1Val instanceof Number &&
				r2Val instanceof Number)
			ret = new Type(((Number) r1Val).doubleValue() < ((Number) r2Val).doubleValue(), "Boolean");
		
		else if(r1Val instanceof String &&
				r2Val instanceof String)
			ret = new Type(((String) r1Val).compareTo((String) r2Val) < 0, "Boolean");
		
		else
			throw new InvalidTypeException("Both Pseudo Types must be a " +
					"Number or Boolean to perform " + operation);
		
		return ret;
	}

}
