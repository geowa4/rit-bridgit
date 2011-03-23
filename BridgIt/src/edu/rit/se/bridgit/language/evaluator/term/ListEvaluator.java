package edu.rit.se.bridgit.language.evaluator.term;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.ListType;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ListEvaluator implements Evaluator 
{
	List<Evaluator> terms = new LinkedList<Evaluator>();
	
	public ListEvaluator() 
	{
		super();
	}
	
	public void addTerm(Evaluator term)
	{
		terms.add(term);
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		List<Type> types = new LinkedList<Type>();
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
