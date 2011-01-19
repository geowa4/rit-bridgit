package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class NegationEvaluator extends Evaluator {

	private Evaluator e;
	
	public NegationEvaluator(Evaluator e) {
		super();
		this.e = e;
	}
	
	protected void validateType(Type op) throws InvalidTypeException 
	{
		if(!(op.getValue() instanceof Boolean)) 
		{
			throw new InvalidTypeException(op.getType(), "Negation");
		}
	}

	@Override
	public Type evaluate() throws InvalidTypeException {
		Type result = e.evaluate();
		result = new Type(! (Boolean) result.getValue());
		validateType(result);
		return result;
	}

}
