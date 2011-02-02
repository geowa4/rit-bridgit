package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionEvaluator extends Evaluator{

	private String name;
	private Evaluator parameters;
	private String returnType;
	private Evaluator functionBlock;
	private boolean nestScope; 
	
	
	public FunctionEvaluator(String name, Evaluator parameters, boolean nestScope)
	{
		this.name = name;
		this.parameters = parameters;
		this.nestScope = nestScope;
	}
	
	public FunctionEvaluator(String name, Evaluator parameters, String type, boolean nestScope)
	{
		this.name = name;
		this.parameters = parameters;
		this.returnType = type;
		this.nestScope = nestScope;
	}
	
	public void addFunctionBlock(Evaluator functionBlock){
		this.functionBlock = functionBlock;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		
		if(nestScope)
			scope = new Scope(scope);
		
		if(parameters != null){
			parameters.evaluate(scope);
		}	
		if(functionBlock != null){
			functionBlock.evaluate(scope);
		}
		return null;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		
	}

}
