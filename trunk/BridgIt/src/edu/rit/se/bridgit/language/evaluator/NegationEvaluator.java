package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class NegationEvaluator extends Evaluator {

	private Type value;
	
	public NegationEvaluator(Type value) {
		super();
		this.value = value;
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
		validateType(value);
		return value;
	}

}
