package edu.rit.se.bridgit.language.evaluator.term;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;

public class StringEvaluator implements Evaluator 
{

	private String value;
	
	public StringEvaluator(String value) 
	{
		super();
		this.value = value == null ? value : value.replaceAll("^\"|\"$", "");
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		return new StringType(value);
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{}

}
