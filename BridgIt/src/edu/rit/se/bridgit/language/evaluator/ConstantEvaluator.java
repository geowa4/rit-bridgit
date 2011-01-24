package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class ConstantEvaluator extends Evaluator 
{
	
	private String name;
	private String type;
	private Evaluator value;
	
	public ConstantEvaluator(String name, String type, Evaluator value) 
	{
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException 
	{
		Type ret = value.evaluate(scope);
		validateType(ret);
		ret.setPsuedoType(type);
		scope.addConstant(name, ret);
		return ret;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException 
	{
		//TODO this needs to be flushed out more
		if(!t.getType().getName().contains(type))
		{
			throw new InvalidTypeException(t.getType(), "Constant Assignment");
		}
	}

}
