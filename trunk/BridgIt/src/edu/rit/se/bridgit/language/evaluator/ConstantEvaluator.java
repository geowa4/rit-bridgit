package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ConstantEvaluator extends Evaluator 
{
	
	private String name;
	private String pseudoType;
	private Evaluator value;
	
	public ConstantEvaluator(String name, String pseudoType, Evaluator value) 
	{
		this.name = name;
		this.pseudoType = pseudoType;
		this.value = value;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		if(value == null)
			throw new InvalidTypeException(null, "Constant Assignment to null");
		Type ret = value.evaluate(scope);
		validateType(ret);
		ret.setPseudoType(pseudoType);
		scope.addConstant(name, ret);
		return ret;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{
		if(!t.getPseudoType().contains(pseudoType))
		{
			throw new InvalidTypeException(t.getType(), 
					"Constant Assignment to " + pseudoType);
		}
	}

}
