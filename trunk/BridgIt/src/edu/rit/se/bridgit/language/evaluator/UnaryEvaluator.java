package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class UnaryEvaluator extends Evaluator {

	private Integer value;
	
	/**
	 * Only create me if you want to perform a arithmetic negation. 
	 * i.e. -1, -2345, or -67;
	 * not 1, +23
	 * 
	 * @param value
	 */
	public UnaryEvaluator(Integer value) {
		super();
		this.value = value;
	}

	@Override
	public Type evaluate() {
		return new Type(-value);
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		//no-op
	}

}
