package edu.rit.se.bridgit.language.evaluator.function;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ParameterListEvaluator extends Evaluator
{	
	private List<ParameterEvaluator> params = new LinkedList<ParameterEvaluator>();
	private List<Type> args;
	
	public void addParam(ParameterEvaluator param)
	{
		params.add(param);
	}
	
	public void setArgs(List<Type> args)
	{
		this.args = args;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		if(params.size() != args.size())
		{
			throw new InvalidTypeException(null, "The number of parameters and arguments do not match.");
		}
		else
		{
			validateAndAddArgumentsToScope(scope);
		}
		return null;
	}

	private void validateAndAddArgumentsToScope(Scope scope)
			throws InvalidTypeException, NameConflictException
	{
		Iterator<ParameterEvaluator> paramsIter = params.iterator();
		Iterator<Type> argsIter = args.iterator();
		while(paramsIter.hasNext())
		{
			ParameterEvaluator param = paramsIter.next();
			Type argType = argsIter.next();
			Type paramType = param.evaluate(scope);
			if(paramType.getPseudoType().equals(argType.getPseudoType()))
			{
				scope.addVariable(param.getName(), argType);
			}
			else
			{
				throw new InvalidTypeException(null, "The types of parameters and arguments do not match.");
			}
		}
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{}
}