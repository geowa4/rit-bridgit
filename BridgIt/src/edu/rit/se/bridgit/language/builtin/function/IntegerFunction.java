package edu.rit.se.bridgit.language.builtin.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.evaluator.function.ParameterEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.ParameterList;
import edu.rit.se.bridgit.language.evaluator.function.ParameterListEvaluator;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class IntegerFunction extends Function
{
	private static final String parameterName = "object";
	public static final String functionName = "integer";
	
	public IntegerFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.INTEGER_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator(parameterName, Type.ANY_TYPE));
		setParameters(params);
		setReturnValue(new IntegerReturn());
	}
	
	private class IntegerReturn implements Evaluator
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
					return new IntegerType(Integer.parseInt(value.toString()));
				}
				catch(NumberFormatException e)
				{
					throw new InvalidTypeException("String parameter to int() could not be cast to an integer.");
				}
			}
			else if(value instanceof Boolean)
			{
				if((Boolean) value) return new IntegerType(1); 
				else return new IntegerType(0);
			}
			else if(value instanceof Double)
			{
				return new IntegerType(((Double) value).intValue());
			}
			else if(value instanceof Integer)
			{
				return t;
			}
			else
			{
				throw new InvalidTypeException("Parameter of type " + t.getPseudoType() + 
						" passed to function " + functionName + "() could not be cast to an " + 
						Type.INTEGER_TYPE + ".");
			}
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}
	}
}
