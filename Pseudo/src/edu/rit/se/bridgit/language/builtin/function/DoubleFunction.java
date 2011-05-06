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

public class DoubleFunction extends Function
{
	private static final String parameterName = "object";
	public static final String functionName = "double";
	
	public DoubleFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.DOUBLE_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator(parameterName, Type.ANY_TYPE));
		setParameters(params);
		setReturnValue(new DoubleReturn());
	}
	
	private class DoubleReturn implements Evaluator
	{
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException,
				NameConflictException
		{
			Type t = scope.getVariableValue(parameterName);
			Object value = t.getValue();
			if(value instanceof String)
			{
				try
				{ 
					return new DoubleType(Double.parseDouble(value.toString()));
				}
				catch(NumberFormatException e)
				{
					throw new InvalidTypeException("String parameter to int() could not be cast to an integer.");
				}
			}
			else if(value instanceof Integer)
			{
				return new DoubleType(((Integer) value).doubleValue());
			}
			else if(value instanceof Boolean)
			{
				if((Boolean) value) return new DoubleType(1.0d); 
				else return new DoubleType(0.0d);
			}
			else if(value instanceof Double)
			{
				return t;
			}
			else
			{
				throw new InvalidTypeException("Parameter of type " + t.getPseudoType() + 
						" to " + functionName + "() could not be cast to a " + 
						Type.DOUBLE_TYPE + ".");
			}
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}
	}
}
