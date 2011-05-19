package edu.rit.se.bridgit.language.builtin.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.evaluator.function.ParameterEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.ParameterList;
import edu.rit.se.bridgit.language.evaluator.function.ParameterListEvaluator;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class TypeOfFunction extends Function
{
	private static final String parameterName = "object";
	public static final String functionName = "typeOf";
	
	public TypeOfFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.STRING_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator(parameterName, Type.ANY_TYPE));
		setParameters(params);
		setReturnValue(new TypeOfReturn());
	}
	
	private class TypeOfReturn implements Evaluator
	{
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException,
				NameConflictException
		{
			return new StringType(scope.getVariableValue(parameterName).getPseudoType());
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}
	}
}
