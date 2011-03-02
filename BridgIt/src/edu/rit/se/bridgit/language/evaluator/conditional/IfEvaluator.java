package edu.rit.se.bridgit.language.evaluator.conditional;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class IfEvaluator implements Evaluator 
{
	private List<ConditionalBlockPair> ladder = 
		new LinkedList<ConditionalBlockPair>();
	
	public boolean addConditional(Evaluator conditional, Evaluator block)
	{
		ConditionalBlockPair pair = new ConditionalBlockPair();
		pair.setConditional(conditional);
		pair.setBlock(block);
		return ladder.add(pair);
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		for(ConditionalBlockPair pair : ladder)
		{
			Type val = pair.getConditional().evaluate(scope);
			validateType(val);
			if((Boolean) val.getValue())
			{
				pair.getBlock().evaluate(scope);
				return val;
			}
		}
		return null;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(!t.getType().equals(Boolean.class))
		{
			throw new InvalidTypeException(t.getType(), "If");
		}
	}
}
