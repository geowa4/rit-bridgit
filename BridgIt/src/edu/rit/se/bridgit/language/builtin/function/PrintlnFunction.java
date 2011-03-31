package edu.rit.se.bridgit.language.builtin.function;

import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.evaluator.function.ParameterEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.ParameterList;
import edu.rit.se.bridgit.language.evaluator.function.ParameterListEvaluator;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.VoidType;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class PrintlnFunction extends Function
{
	private static final String parameterName = "object";
	public static final String functionName = "println";
	
	public PrintlnFunction()
	{
		setFunctionName("println");
		setReturnType(Type.VOID_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator(parameterName, Type.ANY_TYPE));
		setParameters(params);
		setFunctionBlock(new PrintBlock());
	}
	
	private class PrintBlock implements Block
	{
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException,
				NameConflictException
		{
			System.out.println(scope.getVariableValue(parameterName));
			return new VoidType();
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}

		@Override
		public boolean add(Evaluator e)
		{
			return false;
		}
		
	}
}
