package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class StringEvaluator extends Evaluator {

	private String value;
	
	public StringEvaluator(String value) {
		super();
		this.value = value;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException {
		return new Type(value, "String");
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(t.getType().equals(String.class))
			throw new InvalidTypeException(t.getType(), "String");
	}

}
