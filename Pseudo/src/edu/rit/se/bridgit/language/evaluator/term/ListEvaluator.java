package edu.rit.se.bridgit.language.evaluator.term;

import java.util.ArrayList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.ListType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class ListEvaluator implements Evaluator 
{
	List<Evaluator> terms = new ArrayList<Evaluator>();
	
	public ListEvaluator() 
	{
		super();
	}
	
	public void addTerm(Evaluator term)
	{
		terms.add(term);
	}

	@Override
	public Type evaluate(Scope scope) throws PseudoException
	{
		List<Type> types = new ArrayList<Type>();
		for(Evaluator e : terms)
		{
			types.add(e.evaluate(scope));
		}
		return new ListType(types);
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{}

}
