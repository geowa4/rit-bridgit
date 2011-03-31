package edu.rit.se.bridgit.language.evaluator.term;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;
import edu.rit.se.bridgit.language.model.exception.UnsupportedMemberException;

public class IndexedAccessEvaluator implements Evaluator
{
	private Evaluator term;
	private Evaluator index;
	
	public IndexedAccessEvaluator(Evaluator term)
	{
		this.term = term;
	}
	
	public void setIndex(Evaluator index)
	{
		this.index = index;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException
	{
		Type termType = term.evaluate(scope);
		if(index != null)
		{
			Type indexType = index.evaluate(scope);
			if(indexType.getPseudoType().equals(Type.INTEGER_TYPE))
			{
				try
				{
					return retrieveElementAt(termType, indexType);
				}
				catch(IndexOutOfBoundsException e)
				{
					throw new UnsupportedMemberException("Object does not have an element at index " + indexType + ".");
				}
			}
			else throw new InvalidTypeException("Index must be an Integer.");
		}
		else return termType;
	}

	private Type retrieveElementAt(Type termType, Type indexType)
			throws InvalidTypeException
	{
		if(termType.getPseudoType().equals(Type.LIST_TYPE))
		{
			return (Type) 
				((List<?>) termType.getValue())
				.get((Integer) indexType.getValue());
		}
		else if(termType.getPseudoType().equals(Type.STRING_TYPE))
		{
			return new StringType("" +
				((String) termType.getValue())
				.charAt((Integer) indexType.getValue()));
		}
		throw new InvalidTypeException("Only Lists and Strings can be indexed.");
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException
	{}

}
