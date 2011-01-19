package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class DivideEvaluator extends BinaryEvaluator {

	public DivideEvaluator(Evaluator op1, Evaluator op2) throws InvalidTypeException {
		super(op1, op2);
		this.operation = "Division";
	}

	@Override
	protected void validateType(Type op) throws InvalidTypeException 
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
			ret = new Type((Integer) r1Val / (Integer) r2Val);
		
		else if(r1Val instanceof Integer &&
				r2Val instanceof Double)
			ret = new Type((Integer) r1Val / (Double) r2Val);
		
		else if(r1Val instanceof Double &&
				r2Val instanceof Integer)
			ret = new Type((Double) r1Val / (Integer) r2Val);
		
		else 
			ret = new Type((Double) r1Val / (Double) r2Val);
		
		return ret;
	}
}
