package edu.rit.se.bridgit.language.evaluator.conditional;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class WhileEvaluator implements Evaluator
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
	public Type evaluate(Scope scope) throws PseudoException
	{
		while(executeConditional(scope))
		{
			pair.getBlock().evaluate(scope);
		}
		return null;
	}
	
	private Boolean executeConditional(Scope scope) 
	throws PseudoException
	{
		Type ret = pair.getConditional().evaluate(scope);
		validateType(ret);
		return (Boolean) ret.getValue();
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Boolean.class))
		{
			throw new InvalidTypeException(t.getType(), "While");
		}		
	}
}

