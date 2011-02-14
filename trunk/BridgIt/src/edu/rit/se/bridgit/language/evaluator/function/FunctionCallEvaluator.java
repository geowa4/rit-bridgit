package edu.rit.se.bridgit.language.evaluator.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionCallEvaluator extends Evaluator {

	private String name;
	private Evaluator arguments;

	public FunctionCallEvaluator(String name, Evaluator arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		if (scope.isFunction(name) && arguments != null) {
			
			Type eval = arguments.evaluate(scope);
			validateType(eval);
			if (eval.getValue() != null) {
				return eval;
			}
		} else {
			throw new NameConflictException(name, "has not been defined");
		}
		return null;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {

	}

}
