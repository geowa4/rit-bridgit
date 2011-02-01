package edu.rit.se.bridgit.language.evaluator.arithmetic;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
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
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException {
		Type result = e.evaluate(scope);
		validateType(result);
		Object value = result.getValue();
		if(value instanceof Integer)
			return new Type(- (Integer) value, "Integer");
		else
			return new Type(- (Double) value, "Double");
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Integer.class) &&
				!t.getType().equals(Double.class))
			throw new InvalidTypeException(t.getType(), operation);
	}

}
