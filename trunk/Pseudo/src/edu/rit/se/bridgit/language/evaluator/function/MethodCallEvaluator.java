package edu.rit.se.bridgit.language.evaluator.function;

import org.apache.log4j.Logger;

import edu.rit.se.bridgit.language.bridge.PseudoInstanceBridge;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.ParallelBlockEvaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NoMethodFoundException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

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

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	public void setArguments(ArgumentListEvaluator arguments)
	{
		this.arguments = arguments;
	}
	
	@Override
	public Type evaluate(Scope scope) throws PseudoException
	{
		Type target = booleanExpression.evaluate(scope);
		if(methodName != null && arguments != null) 
		{
			validateType(target);
			PseudoInstanceBridge bridge = (PseudoInstanceBridge) target.getValue();
			arguments.evaluate(scope);
			try
			{
				Type parallelId = scope.getVariableValue(ParallelBlockEvaluator.PARALLEL_ID);
				if(parallelId != null)
					bridge.sendMessage(methodName, arguments.getArgValues(), (Integer) parallelId.getValue());
				else
					bridge.sendMessage(methodName, arguments.getArgValues());
			}
			catch(NoMethodFoundException e)
			{
				log.error("Method \"" + methodName + "\" does not exist for type " + target.getPseudoType());
			}
		}
		return target;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException
	{
		if(!(t.getValue() instanceof PseudoInstanceBridge))
		{
			throw new InvalidTypeException("It is not correct to call method \"" + 
					methodName + "\" on " + t.getPseudoType());
		}
	}
}
