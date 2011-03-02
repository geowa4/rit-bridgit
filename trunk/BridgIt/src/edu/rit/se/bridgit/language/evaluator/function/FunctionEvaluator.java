package edu.rit.se.bridgit.language.evaluator.function;

import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionEvaluator implements Evaluator
{
	private Function function;
	
	public FunctionEvaluator(String name)
	{
		function = new Function();
		function.setFunctionName(name);
	}
	
	public void setPseudoType(String pseudoType)
	{
		function.setReturnType("Function:" + pseudoType);
	}
	
	public void addReturnValue(Evaluator e){
		function.setReturnValue(e);
	}

	public void setFunctionBlock(Block block)
	{
		function.setFunctionBlock(block);
	}
	
	public void setParameters(ParameterList params)
	{
		function.setParameters(params);
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		Type type = new Type(function, function.getReturnType());
		function.setDefinitionScope(scope);
		scope.addFunction(function, type);
		return type;
	}

	@Override
	public void validateType(Type t) throws InvalidTypeException 
	{}
}

