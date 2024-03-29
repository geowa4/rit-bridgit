package edu.rit.se.bridgit.language.evaluator.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

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
	public Type evaluate(Scope scope) throws PseudoException
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
		if(t.getPseudoType().equals(Type.NULL_TYPE))
		{
			t.setPseudoType(pseudoType);
		}
		else if(!t.getPseudoType().contains(pseudoType))
		{
			throw new InvalidTypeException(Function.class, "Return type of " + 
					function.getFunctionName() + " does not match declared return type.");
		}
	}

}
