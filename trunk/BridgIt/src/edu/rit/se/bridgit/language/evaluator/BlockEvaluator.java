package edu.rit.se.bridgit.language.evaluator;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

/**
 * Evaluates a list of Evaluators. Useful for `setup`, `main`, and `function` blocks.
 */
public class BlockEvaluator extends Evaluator 
{
	private boolean nestScope;
	private List<Evaluator> evaluators;
	
	public BlockEvaluator()
	{
		this(true);
	}
	
	public BlockEvaluator(boolean nestScope)
	{
		this.nestScope = nestScope;
		this.evaluators = new LinkedList<Evaluator>();
	}
	
	public boolean add(Evaluator e)
	{
		return evaluators.add(e);
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		if(nestScope)
			scope = new Scope(scope);
		for(Evaluator e : evaluators) 
		{
			e.evaluate(scope);
		}
		return null;
	}

	/**
	 * no-op
	 */
	@Override
	protected void validateType(Type t) throws InvalidTypeException {}
	
}