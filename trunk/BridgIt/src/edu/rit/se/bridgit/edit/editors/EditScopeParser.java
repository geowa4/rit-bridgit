package edu.rit.se.bridgit.edit.editors;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditScopeParser
{
	/**
	 * Returns a parsed list of valued which are within the program text given.
	 * 
	 * @param p_ProgramText - The text for the program.
	 * 
	 * @return The list of values parsed from the text.
	 */
	public ArrayList<ArrayList<String>> parseScope(String p_ProgramText)
	{
		// The return value
		ArrayList<ArrayList<String>> retVal = new ArrayList<ArrayList<String>>();

		// Clear out comments
		p_ProgramText = p_ProgramText.replaceAll("//.*\n*", "");
		p_ProgramText = p_ProgramText.replaceAll("/\\*(.|\\s)*?\\*/", "");

		// Declare the pattern and matcher for regex parsing
		Pattern variablePattern = Pattern
				.compile("\\s*var\\s+\\w+\\s*:\\s*\\w+\\s*");
		Pattern constantPattern = Pattern
				.compile("\\s*constant\\s+\\w+\\s*:\\s*\\w+\\s*");
		Pattern functionPattern = Pattern
				.compile("\\s*function\\s+\\w+\\s*(.*)\\s*:\\s*\\w+\\s*");

		// Declare the matches for each
		Matcher variableMatcher = variablePattern.matcher(p_ProgramText);
		Matcher constantMatcher = constantPattern.matcher(p_ProgramText);
		Matcher functionMatcher = functionPattern.matcher(p_ProgramText);

		// The lists for the values
		ArrayList<String> variables = new ArrayList<String>();
		ArrayList<String> constants = new ArrayList<String>();
		ArrayList<String> functions = new ArrayList<String>();

		// Add all the variables
		while (variableMatcher.find())
			variables.add(variableMatcher.group().replaceAll("\\s*var\\s*", "")
					.trim());

		// Add all the constants
		while (constantMatcher.find())
			constants.add(constantMatcher.group()
					.replaceAll("\\s*constant\\s*", "").trim());

		// Add all the functions
		while (functionMatcher.find())
			functions.add(functionMatcher.group()
					.replaceAll("\\s*function\\s*", "").trim());

		// Add the values
		retVal.add(variables);
		retVal.add(constants);
		retVal.add(functions);

		// Return
		return retVal;
	}
}
