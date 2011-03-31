package edu.rit.se.bridgit.language.evaluator;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

/**
 * Evaluates a list of Evaluators. Useful for `setup`, `main`, and `function` blocks.
 */
public class BlockEvaluator implements Block 
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
	
	@Override
	public boolean add(Evaluator e)
	{
		return evaluators.add(e);
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		if(nestScope)
		{
			scope = new Scope(scope);
			Scope.setCurrentScope(scope);
		}
		for(Evaluator e : evaluators) e.evaluate(scope);
		if(nestScope) Scope.setCurrentScope(scope.getParent());
		return null;
	}

	/**
	 * no-op
	 */
	@Override
	public void validateType(Type t) throws InvalidTypeException {}
	
}
