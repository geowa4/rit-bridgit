package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class NegationEvaluator implements Evaluator {

	private Evaluator e;
	protected String operation;
	
	public NegationEvaluator(Evaluator e) {
		super();
		this.e = e;
		this.operation = "Boolean Negation";
	}
	
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(!(t.getPseudoType().equals(Type.BOOLEAN_TYPE))) 
		{
			throw new InvalidTypeException(t.getType(), operation);
		}
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException {
		Type result = e.evaluate(scope);
		validateType(result);
		return result.negation(); 
	}

}
