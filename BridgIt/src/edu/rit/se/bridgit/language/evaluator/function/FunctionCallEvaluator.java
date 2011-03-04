package edu.rit.se.bridgit.language.evaluator.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionCallEvaluator implements Evaluator {

	private String name;
	private ArgumentListEvaluator arguments;
	private Function function;

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
		function = scope.getFunction(name);
		Type ret = function.apply(argVals);
		validateType(ret);
		return ret;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		String pseudoType = function.getReturnType();
		int index = pseudoType.indexOf(":");
		String finalPseudoType = index < 0 ? pseudoType : pseudoType.substring(index + 1);
		if(!t.getPseudoType().contains(finalPseudoType))
			throw new InvalidTypeException(Function.class, "Return type of " + 
					function.getFunctionName() + " does not match declared return type.");
	}

}
