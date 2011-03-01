package edu.rit.se.bridgit.language.evaluator.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionCallEvaluator extends Evaluator {

	private String name;
	private ArgumentListEvaluator arguments;

	public FunctionCallEvaluator(String name, ArgumentListEvaluator arguments) 
	{
		this.name = name;
		this.arguments = arguments;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		arguments.evaluate(scope);
		List<Type> argVals = arguments.getArgValues();
		return scope.getFunction(name).apply(argVals);
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{}

}
