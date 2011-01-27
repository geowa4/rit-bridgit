package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class VariableEvaluator extends Evaluator 
{
	
	private String name;
	private String pseudoType;
	private Evaluator value;
	private boolean isAssignment;
	
	public VariableEvaluator(String name, Evaluator value)
	{
		this.name = name;
		this.value = value;
		this.isAssignment = true;
	}
	
	public VariableEvaluator(String name, String pseudoType, Evaluator value) 
	{
		this.name = name;
		this.pseudoType = pseudoType;
		this.value = value;
		this.isAssignment = false;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		if(pseudoType == null)
			pseudoType = scope.getVariableValue(name).getPseudoType();
		if(value != null)
		{
			Type eval = value.evaluate(scope);
			eval.setPseudoType(pseudoType);
			validateType(eval);
			if(!isAssignment)
				scope.addVariable(name, eval);
			else
				scope.modifyVariableValue(name, eval);
			return eval;
		}
		else
		{
			scope.addVariable(name, new Type(null, pseudoType));
			return null;
		}
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{
		if(!t.getPseudoType().equals(pseudoType))
		{
			throw new InvalidTypeException(t.getType(), 
					"Variable Assignment to " + pseudoType);
		}
	}

}
