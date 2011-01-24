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
			pseudoType = scope.getVariableValue(name).getPsuedoType();
		if(value != null)
		{
			Type eval = value != null ? value.evaluate(scope) : null;
			validateType(eval);
			eval.setPsuedoType(pseudoType);
			if(!isAssignment)
				scope.addVariable(name, eval);
			else
				scope.modifyVariableValue(name, eval);
			return eval;
		}
		else
		{
			scope.addVariable(name, null);
			return null;
		}
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{
		//TODO this needs to be flushed out more
		if(!t.getType().getName().contains(pseudoType))
		{
			throw new InvalidTypeException(t.getType(), "Variable Assignment");
		}
	}

}
