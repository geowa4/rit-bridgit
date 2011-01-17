package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.Type;

public class IntegerEvaluator implements Evaluator {

	private Integer value;
	
	public IntegerEvaluator(Integer value) {
		super();
		this.value = value;
	}

	@Override
	public Type evaluate() {
		return new Type(value);
	}

}
