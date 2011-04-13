package edu.rit.se.bridgit.language.builtin.function;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.function.Function;

public class BuiltInFunctionFactory
{
	static {
		loadContent();
	}
	
	private static LinkedList<Function> functions;
	
	public static void loadContent()
	{
		functions = new LinkedList<Function>();
		functions.add(new PrintFunction());
		functions.add(new PrintlnFunction());
		functions.add(new StringFunction());
		functions.add(new DoubleFunction());
		functions.add(new IntegerFunction());
		functions.add(new TypeOfFunction());
		functions.add(new LenFunction());
		functions.add(new PowFunction());
	}
	
	public static List<Function> getFunctions()
	{
		return functions;
	}
}
