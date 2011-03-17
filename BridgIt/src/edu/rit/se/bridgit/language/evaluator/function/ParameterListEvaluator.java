package edu.rit.se.bridgit.language.evaluator.function;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ParameterListEvaluator implements ParameterList
{	
	private List<ParameterEvaluator> params = new LinkedList<ParameterEvaluator>();
	private List<Type> args;
	
	@Override
	public void addParam(ParameterEvaluator param)
	{
		params.add(param);
	}
	
	@Override
	public void setArgs(List<Type> args)
	{
		this.args = args;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		if((params.size() == 0 && args != null) || 
				(params.size() != 0 && args == null) || 
				(params.size() != args.size()))
		{
			throw new InvalidTypeException("The number of parameters and arguments do not match.");
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
			if(argType.getValue() == Type.NULL && !paramType.getPseudoType().equals(Type.ANY_TYPE))
			{
				argType.setPseudoType(paramType.getPseudoType());
			}
			if(paramType.getPseudoType().equals(Type.ANY_TYPE) || 
					paramType.getPseudoType().equals(argType.getPseudoType()))
			{
				scope.addVariable(param.getName(), argType);
			}
			else
			{
				throw new InvalidTypeException("The types of parameters and arguments do not match.");
			}
		}
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{}
}
