package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public abstract class BinaryEvaluator implements Evaluator {
	
	protected Evaluator op1;
	protected Evaluator op2;
	
	protected String operation = "Binary";
	
	public BinaryEvaluator(Evaluator op1, Evaluator op2)
	{
		super();
		this.op1 = op1;
		this.op2 = op2;
	}

	public void validateType(Type op) throws InvalidTypeException 
	{}

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
