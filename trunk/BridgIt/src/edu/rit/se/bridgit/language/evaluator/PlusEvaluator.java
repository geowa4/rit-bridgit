package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class PlusEvaluator extends BinaryEvaluator 
{
	
	public PlusEvaluator(Evaluator op1, Evaluator op2) throws InvalidTypeException 
	{
		super(op1, op2);
	}

	@Override
	public Type evaluate() 
	{
		Type result1 = op1.evaluate();
		Object r1Val = result1.getValue();
		Type result2= op2.evaluate();
		Object r2Val = result2.getValue();
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type((Integer) r1Val + (Integer) r2Val);
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val + (Double) r2Val);
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof String)
			ret = new Type((Integer) r1Val + (String) r2Val);
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val + (Integer) r2Val);
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Double)
			ret = new Type((Double) r1Val + (Double) r2Val);
		
		else if(r1Val instanceof Double &&
				r2Val instanceof String)
			ret = new Type((Double) r1Val + (String) r2Val);
		
		else if(r1Val instanceof String &&
				r2Val instanceof Integer)
			ret = new Type((String) r1Val + (Integer) r2Val);
		
		else if(r1Val instanceof String &&
				r2Val instanceof Double)
			ret = new Type((String) r1Val + (Double) r2Val);
		
		else
			ret = new Type((String) r1Val + (String) r2Val);
		
		return ret;
	}

}
