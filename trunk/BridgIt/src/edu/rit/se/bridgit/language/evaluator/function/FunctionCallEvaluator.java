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

	public FunctionCallEvaluator(String name)
	{
		this.name = name;
	}

	public void setArgumentsList(ArgumentListEvaluator arguments)
	{
		this.arguments = arguments;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException
	{
		List<Type> argVals = null;
		if(arguments != null)
		{
			arguments.evaluate(scope);
			argVals = arguments.getArgValues();
		}
		Function f = scope.getFunction(name);
		if(f.getReturnType().contains(Function.VOID_TYPE)) return f.apply(argVals);
		else return new Type(null, Function.VOID_TYPE);
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{}

}
