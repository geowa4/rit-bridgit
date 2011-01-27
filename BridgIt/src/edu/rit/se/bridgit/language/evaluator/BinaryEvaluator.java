package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public abstract class BinaryEvaluator extends Evaluator {
	
	protected Evaluator op1;
	protected Evaluator op2;
	
	protected String operation = "Binary";
	
	public BinaryEvaluator(Evaluator op1, Evaluator op2)
	{
		super();
		this.op1 = op1;
		this.op2 = op2;
	}

	protected void validateType(Type op) throws InvalidTypeException 
	{
		if(!(	op.getType().equals(Integer.class) 
			||	op.getType().equals(Double.class) 
			||	op.getType().equals(String.class)
			)
		) 
		{
			throw new InvalidTypeException(op.getType(), operation);
		}
	}

	@Override
	public abstract Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException;

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
