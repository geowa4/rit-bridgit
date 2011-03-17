package edu.rit.se.bridgit.language.builtin.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.evaluator.function.ParameterEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.ParameterList;
import edu.rit.se.bridgit.language.evaluator.function.ParameterListEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

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
					return new Type(Double.parseDouble(value.toString()), Type.DOUBLE_TYPE);
				}
				catch(NumberFormatException e)
				{
					throw new InvalidTypeException("String parameter to int() could not be cast to an integer.");
				}
			}
			else if(value instanceof Integer)
			{
				return new Type(((Integer) value).doubleValue(), Type.DOUBLE_TYPE);
			}
			else if(value instanceof Boolean)
			{
				if((Boolean) value) return new Type(1.0d, Type.DOUBLE_TYPE); 
				else return new Type(0.0d, Type.INTEGER_TYPE);
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
