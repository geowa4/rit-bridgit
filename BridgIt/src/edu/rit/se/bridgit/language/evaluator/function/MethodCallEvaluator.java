package edu.rit.se.bridgit.language.evaluator.function;

import org.apache.log4j.Logger;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.MemberLoadEvaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.VoidType;
import edu.rit.se.bridgit.language.model.bridge.Command;
import edu.rit.se.bridgit.language.model.bridge.GraphicalBridge;
import edu.rit.se.bridgit.language.model.bridge.NoMethodFoundException;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class MethodCallEvaluator implements Evaluator
{
	private MemberLoadEvaluator loader;
	private String methodName;
	private static final Logger log = Logger.getLogger(MethodCallEvaluator.class);
	
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
			((GraphicalBridge) ret.getValue()).sendMessage(new Command(methodName));
			return new VoidType();
		}
		catch(NoMethodFoundException e)
		{
			log.error(e);
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
