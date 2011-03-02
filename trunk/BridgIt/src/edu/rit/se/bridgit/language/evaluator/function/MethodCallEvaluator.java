package edu.rit.se.bridgit.language.evaluator.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.MemberLoadEvaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.bridge.GraphicalBridge;
import edu.rit.se.bridgit.language.model.bridge.NoMethodFoundException;

public class MethodCallEvaluator implements Evaluator
{
	private MemberLoadEvaluator loader;
	private String methodName;
	
	public MethodCallEvaluator(String variableName, String methodName)
	{
		this.loader = new MemberLoadEvaluator(variableName);
		this.methodName = methodName;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException
	{
		Type ret = loader.evaluate(scope);
		validateType(ret);
		try
		{
			return new Type(
					((GraphicalBridge) ret.getValue()).sendMessage(methodName), 
					ret.getPseudoType());
		}
		catch(NoMethodFoundException e)
		{
			System.err.println(e);
			return null;
		}
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException
	{
		if(!(t.getValue() instanceof GraphicalBridge))
		{
			throw new InvalidTypeException(t.getType(), 
					"Calling method " + methodName + " on " + t.getPseudoType());
		}
	}
}
