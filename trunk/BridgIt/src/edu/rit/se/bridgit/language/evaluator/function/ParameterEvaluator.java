package edu.rit.se.bridgit.language.evaluator.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ParameterEvaluator extends Evaluator{

	
	private String name;
	private String type;
	
	public ParameterEvaluator(String name, String pseudoType)
	{
		this.name = name;
		this.type = pseudoType;
	}
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		
		//scope.addParameter(name, new Type(null, type));
		return new Type(null, type);
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Boolean.class))
		{
			throw new InvalidTypeException(t.getType(), "Parameter");
		}
	}

}
