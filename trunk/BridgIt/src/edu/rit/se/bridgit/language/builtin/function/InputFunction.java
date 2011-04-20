package edu.rit.se.bridgit.language.builtin.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class InputFunction extends Function
{
	public static final String functionName = "input";
	
	public InputFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.STRING_TYPE);
		setReturnValue(new StrReturn());
	}
	
	private class StrReturn implements Evaluator
	{
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException
		{
			return new StringType("");
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}
	}
}
