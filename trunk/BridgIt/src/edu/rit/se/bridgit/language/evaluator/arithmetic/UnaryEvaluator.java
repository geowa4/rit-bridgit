package edu.rit.se.bridgit.language.evaluator.arithmetic;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class UnaryEvaluator extends Evaluator {

	private Evaluator e;
	protected String operation;
	
	/**
	 * Only create me if you want to perform a arithmetic negation. 
	 * i.e. -1, -2345, or -67;
	 * not 1, +23
	 * 
	 * @param value
	 */
	public UnaryEvaluator(Evaluator e) {
		super();
		this.e = e;
		this.operation = "Unary Negation";
	}

	@Override
	public Type evaluate() throws InvalidTypeException {
		Type result = e.evaluate();
		validateType(result);
		Object value = result.getValue();
		return new Type(- (Integer) value);
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Integer.class))
			throw new InvalidTypeException(t.getType(), operation);
	}

}
