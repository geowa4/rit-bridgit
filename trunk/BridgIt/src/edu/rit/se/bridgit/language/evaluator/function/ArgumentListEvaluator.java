package edu.rit.se.bridgit.language.evaluator.function;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ArgumentListEvaluator implements Evaluator
{	
	private List<Evaluator> args = new LinkedList<Evaluator>();
	private List<Type> argValues = new LinkedList<Type>();
	
	public void addArg(Evaluator arg)
	{
		args.add(arg);
	}
	
	public List<Type> getArgValues()
	{
		return argValues;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		for(Evaluator e : args)
		{
			argValues.add(e.evaluate(scope));
		}
		return null;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{}
}
