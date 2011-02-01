package edu.rit.se.bridgit.language.evaluator;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class WhileEvaluator extends Evaluator
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
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException {
		scope = new Scope(scope);
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
	protected void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Boolean.class))
		{
			throw new InvalidTypeException(t.getType(), "While");
		}		
	}

}

