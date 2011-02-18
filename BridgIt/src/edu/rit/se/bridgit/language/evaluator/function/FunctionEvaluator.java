package edu.rit.se.bridgit.language.evaluator.function;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionEvaluator extends Evaluator{

	
	private Function function;
	private Evaluator parameters;
	private List<Evaluator> functionBlock = new LinkedList<Evaluator>();
	
	public FunctionEvaluator(String name, Evaluator parameters)
	{
		function = new Function();
		function.setFunctionName(name);
		this.parameters = parameters;

	}
	
	public FunctionEvaluator(String name, Evaluator parameters, String type)
	{
		function = new Function();
		function.setFunctionName(name);
		this.parameters = parameters;
		function.setReturnType(type);

	}
	
	public void addFunctionBlock(Evaluator e){
		functionBlock.add(e);
	}
	
	public void addReturnValue(Evaluator e){
		function.setReturnValue(e);
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {	
		List<?> parameter = null;
		Scope functionScope = new Scope(scope);

		if(parameters != null){
			parameter = (List<?>) parameters.evaluate(functionScope).getValue();		
		} 		
		function.setParameters(parameter);
		function.setFunctionScope(functionScope);
		function.setFunctionBlock(functionBlock);
		Type type = new Type(function, "Function");
		scope.addFunction(function , type);
		return type;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(t.getType() == null || !t.getType().equals(Function.class))
			throw new InvalidTypeException(t.getType(), "Function");
	}

}

