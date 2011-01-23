package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class NegationEvaluator extends Evaluator {

	private Evaluator e;
	protected String operation;
	
	public NegationEvaluator(Evaluator e) {
		super();
		this.e = e;
		this.operation = "Boolean Negation";
	}
	
	protected void validateType(Type op) throws InvalidTypeException 
	{
		if(!(op.getValue() instanceof Boolean)) 
		{
			throw new InvalidTypeException(op.getType(), operation);
		}
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException {
		Type result = e.evaluate(scope);
		result = new Type(! (Boolean) result.getValue());
		validateType(result);
		return result;
	}

}
