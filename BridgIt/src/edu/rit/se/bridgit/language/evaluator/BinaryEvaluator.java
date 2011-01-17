package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public abstract class BinaryEvaluator implements Evaluator {
	
	protected Evaluator op1;
	protected Evaluator op2;
	
	public BinaryEvaluator(Evaluator op1, Evaluator op2)
	{
		super();
		this.op1 = op1;
		this.op2 = op2;
	}

	protected void validateEvaluator(Type op) throws InvalidTypeException 
	{
		if(!(	op.getType().equals(Integer.class) 
			||	op.getType().equals(Double.class) 
			||	op.getType().equals(String.class)
			)
		) 
		{
			throw new InvalidTypeException(op.getType(), "Binary");
		}
	}

	@Override
	public abstract Type evaluate();

	public Evaluator getOp1() {
		return op1;
	}

	public void setOp1(Evaluator op1) {
		this.op1 = op1;
	}

	public Evaluator getOp2() {
		return op2;
	}

	public void setOp2(Evaluator op2) {
		this.op2 = op2;
	}
}
