package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class IntegerEvaluator extends Evaluator {

	private Integer value;
	
	public IntegerEvaluator(Integer value) {
		super();
		this.value = value;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException {
		return new Type(value, "Integer");
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(t.getType().equals(Integer.class))
			throw new InvalidTypeException(t.getType(), "Integer");
	}

}
