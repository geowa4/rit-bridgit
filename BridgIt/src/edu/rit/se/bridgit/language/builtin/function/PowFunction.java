package edu.rit.se.bridgit.language.builtin.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.evaluator.function.ParameterEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.ParameterList;
import edu.rit.se.bridgit.language.evaluator.function.ParameterListEvaluator;
import edu.rit.se.bridgit.language.model.DoubleType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class PowFunction extends Function
{
	private static final String firstParameterName = "base";
	private static final String secondParameterName = "power";
	public static final String functionName = "power";
	
	public PowFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.DOUBLE_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator(firstParameterName, Type.ANY_TYPE));
		params.addParam(new ParameterEvaluator(secondParameterName, Type.ANY_TYPE));
		setParameters(params);
		setReturnValue(new PowReturn());
	}
	
	private class PowReturn implements Evaluator
	{
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException,
				NameConflictException
		{
			Type baseType = scope.getVariableValue(firstParameterName);
			Type exponentType = scope.getVariableValue(secondParameterName);
			validateType(baseType);
			validateType(exponentType);
			return new DoubleType(Math.pow(getDoubleValue(baseType), getDoubleValue(exponentType)));
		}

		private double getDoubleValue(Type t)
		{
			if(t.getPseudoType().equals(Type.INTEGER_TYPE))
			{
				return ((Integer) t.getValue()).doubleValue();
			}
			else
			{
				return ((Double) t.getValue()).doubleValue();
			}
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{
			if(!t.getPseudoType().equals(Type.DOUBLE_TYPE) && !t.getPseudoType().equals(Type.INTEGER_TYPE))
			{
				throw new InvalidTypeException("Both the base and power in exponentiation must be numbers.");
			}
		}
	}
}
