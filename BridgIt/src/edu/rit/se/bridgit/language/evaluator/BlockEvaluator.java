package edu.rit.se.bridgit.language.evaluator;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

/**
 * Evaluates a list of Evaluators. Useful for `setup`, `main`, and `function` blocks.
 * 
 * @author student
 *
 */
public class BlockEvaluator extends Evaluator 
{
	private List<Evaluator> evaluators = new LinkedList<Evaluator>();
	
	public boolean add(Evaluator e)
	{
		return evaluators.add(e);
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
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
