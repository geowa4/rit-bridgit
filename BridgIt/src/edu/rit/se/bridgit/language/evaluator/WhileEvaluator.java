package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class WhileEvaluator extends Evaluator
{
	private ConditionalBlockPair pair;
	
	public WhileEvaluator()
	{
		pair = new ConditionalBlockPair();
	}
	
	public void setConditional(Evaluator conditional, Evaluator block)
	{
		pair.setConditional(conditional);
		pair.setBlock(block);
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		while(executeConditional(scope))
		{
			pair.getBlock().evaluate(scope);
		}
		return null;
	}
	
	private Boolean executeConditional(Scope scope) 
	throws InvalidTypeException, NameConflictException
	{
		Type ret = pair.getConditional().evaluate(scope);
		validateType(ret);
		return (Boolean) ret.getValue();
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Boolean.class))
		{
			throw new InvalidTypeException(t.getType(), "While");
		}		
	}
}

