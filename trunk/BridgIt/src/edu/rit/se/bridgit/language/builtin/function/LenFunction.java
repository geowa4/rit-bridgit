package edu.rit.se.bridgit.language.builtin.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.evaluator.function.ParameterEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.ParameterList;
import edu.rit.se.bridgit.language.evaluator.function.ParameterListEvaluator;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class LenFunction extends Function
{
	private static final String parameterName = "object";
	public static final String functionName = "len";
	
	public LenFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.INTEGER_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator(parameterName, Type.ANY_TYPE));
		setParameters(params);
		setReturnValue(new TypeOfReturn());
	}
	
	private class TypeOfReturn implements Evaluator
	{
		@SuppressWarnings("unchecked")
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException,
				NameConflictException
		{
			Type type = scope.getVariableValue(parameterName);
			String pseudoType = type.getPseudoType();
			if(pseudoType.equals(Type.STRING_TYPE))
				return new IntegerType(((String) type.getValue()).length());
			
			else if(pseudoType.equals(Type.LIST_TYPE))
				return new IntegerType(((List<Type>) type.getValue()).size());
			
			else
				throw new InvalidTypeException("Length of " + pseudoType + " cannot be determined.");
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}
	}
}
