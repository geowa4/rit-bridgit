package edu.rit.se.bridgit.language.evaluator;

import org.apache.log4j.Logger;

import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class ParallelBlockEvaluator extends BlockEvaluator
{
	private static final Logger log = Logger.getLogger(ParallelBlockEvaluator.class);
	public static final String PARALLEL_ID = "$parallelId";
	
	@Override
	public Type evaluate(Scope scope) throws PseudoException
	{
		Type t = scope.getVariableValue(PARALLEL_ID);
		if(t == null)
		{
			Long time = System.currentTimeMillis();
			scope.addVariable(PARALLEL_ID, new IntegerType(time.hashCode()));
			Type superReturn = super.evaluate(scope);
			scope.removeVariable(PARALLEL_ID);
			return superReturn;
		}
		else
		{
			log.warn("Nested paraller blocks are ignored.");
			return null;
		}
	}
}
