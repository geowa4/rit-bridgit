package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.Type;

public class UnaryEvaluator implements Evaluator {

	private Integer value;
	
	public UnaryEvaluator(Integer value) {
		super();
		this.value = value;
	}

	@Override
	public Type evaluate() {
		return new Type(value);
	}

}
