package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class EqualsEvaluator extends BinaryEvaluator 
{
	public EqualsEvaluator(Evaluator op1, Evaluator op2) throws InvalidTypeException 
	{
		super(op1, op2);
		this.operation = "Equals";
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
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type(((Integer) r1Val).equals((Integer) r2Val));
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Double)
			ret = new Type(((Double) r1Val).equals((Double) r2Val));
		
		else
			ret = new Type(((String) r1Val).equals((String) r2Val));
		
		return ret;
	}

}