package edu.rit.se.bridgit.language.evaluator.arithmetic;

import edu.rit.se.bridgit.language.evaluator.BinaryEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class PlusEvaluator extends BinaryEvaluator 
{
	
	public PlusEvaluator(Evaluator op1, Evaluator op2)
	{
		super(op1, op2);
		this.operation = "Addition";
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
		Type ret;
		if(r1Val instanceof Integer &&
				r2Val instanceof Integer)
			ret = new Type((Integer) r1Val + (Integer) r2Val, "Integer");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val + (Double) r2Val, "Double");
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof String)
			ret = new Type((Integer) r1Val + (String) r2Val, "String");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val + (Integer) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Double)
			ret = new Type((Double) r1Val + (Double) r2Val, "Double");
		
		else if(r1Val instanceof Double &&
				r2Val instanceof String)
			ret = new Type((Double) r1Val + (String) r2Val, "String");
		
		else if(r1Val instanceof String &&
				r2Val instanceof Integer)
			ret = new Type((String) r1Val + (Integer) r2Val, "String");
		
		else if(r1Val instanceof String &&
				r2Val instanceof Double)
			ret = new Type((String) r1Val + (Double) r2Val, "String");
		
		else
			ret = new Type((String) r1Val + (String) r2Val, "String");
		
		return ret;
	}

}
