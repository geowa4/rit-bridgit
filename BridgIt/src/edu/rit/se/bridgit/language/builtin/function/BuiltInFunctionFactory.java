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
	}
	
	public static List<Function> getFunctions()
	{
		return functions;
	}
}
