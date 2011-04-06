package edu.rit.se.bridgit.language.evaluator.function;

import org.apache.log4j.Logger;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.bridge.GraphicalBridge;
import edu.rit.se.bridgit.language.model.bridge.NoMethodFoundException;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class MethodCallEvaluator implements Evaluator
{
	private static final Logger log = Logger.getLogger(MethodCallEvaluator.class);
	
	private Evaluator booleanExpression;
	private String methodName;
	private ArgumentListEvaluator arguments;
	
	public MethodCallEvaluator(Evaluator booleanExpression)
	{
		this.booleanExpression = booleanExpression;
	}

	public void setMethodNameAndParameters(String methodName, ArgumentListEvaluator arguments)
	{
		this.methodName = methodName;
		this.arguments = arguments;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException
	{
		Type target = booleanExpression.evaluate(scope);
		if(methodName != null && arguments != null) 
		{
			validateType(target);
			GraphicalBridge bridge = (GraphicalBridge) target.getValue();
			arguments.evaluate(scope);
			try
			{
				bridge.sendMessage(methodName, arguments.getArgValues());
			}
			catch (NoMethodFoundException e)
			{
				log.error("Method \"" + methodName + "\" does not exist for type " + target.getPseudoType());
			}
		}
		return target;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException
	{
		if(!(t.getValue() instanceof GraphicalBridge))
		{
			throw new InvalidTypeException("It is not correct to call method \"" + 
					methodName + "\" on " + t.getPseudoType());
		}
	}
}
