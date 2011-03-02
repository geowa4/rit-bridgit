package edu.rit.se.bridgit.language.evaluator.bool;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

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
		if(!(t.getPseudoType().equals("Boolean"))) 
		{
			throw new InvalidTypeException(t.getType(), operation);
		}
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException {
		Type result = e.evaluate(scope);
		validateType(result);
		result = new Type(! (Boolean) result.getValue(), "Boolean");
		return result;
	}

}
