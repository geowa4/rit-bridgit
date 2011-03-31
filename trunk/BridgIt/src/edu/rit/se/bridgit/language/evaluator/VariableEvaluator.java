package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.EmptyType;
import edu.rit.se.bridgit.language.model.NullType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class VariableEvaluator implements Evaluator 
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
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException 
	{
		if(pseudoType == null || pseudoType.equals(""))
		{
			if(!isAssignment)
			{
				throw new InvalidTypeException("Psuedo Type must be specified for declaration.");
			}
			pseudoType = scope.getVariableValue(name).getPseudoType();
		}
		if(value != null)
		{
			Type eval = value.evaluate(scope);
			if(eval.getPseudoType().equals(Type.NULL_TYPE)) eval.setPseudoType(pseudoType);
			validateType(eval);
			if(!isAssignment)
			{
				scope.addVariable(name, eval);
			}
			else
			{
				scope.modifyVariableValue(name, eval);
			}
			return eval;
		}
		else
		{
			scope.addVariable(name, new EmptyType(pseudoType));
			return null;
		}
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(!(t instanceof NullType) && !t.getPseudoType().equals(pseudoType))
		{
			throw new InvalidTypeException(t.getType(), 
					"Variable Assignment to " + pseudoType);
		}
	}

}
