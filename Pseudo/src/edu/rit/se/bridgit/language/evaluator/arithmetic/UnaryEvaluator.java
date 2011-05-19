package edu.rit.se.bridgit.language.evaluator.arithmetic;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class UnaryEvaluator implements Evaluator {

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
	public Type evaluate(Scope scope) throws PseudoException 
	{
		Type result = e.evaluate(scope);
		validateType(result);
		return result.unary();
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Integer.class) &&
				!t.getType().equals(Double.class))
			throw new InvalidTypeException(t.getType(), operation);
	}

}
