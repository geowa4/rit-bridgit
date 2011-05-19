package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class ConstantEvaluator implements Evaluator 
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
	public Type evaluate(Scope scope) throws PseudoException 
	{
		if(value == null)
			throw new InvalidTypeException(null, "Constant Assignment to null");
		Type eval = value.evaluate(scope);
		if(pseudoType.equals(Type.DOUBLE_TYPE) && eval.getPseudoType().equals(Type.INTEGER_TYPE))
			eval = ((IntegerType) eval).convertToDoubleType();
		validateType(eval);
		eval.setPseudoType(pseudoType);
		scope.addConstant(name, eval);
		return eval;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{
		if(!t.getPseudoType().contains(pseudoType))
		{
			throw new InvalidTypeException(t.getType(), 
					"Constant Assignment to " + pseudoType);
		}
	}

}
