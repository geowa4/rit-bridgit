package edu.rit.se.bridgit.language.evaluator.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.BlockEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;

public class Function{
	private List<?> parameters;
	private BlockEvaluator functionBlock;
	private String returnType;
	private String functionName;
	private Scope functionScope;
	private Evaluator returnValue;
	
	public List<?> getParameters() {
		return parameters;
	}
	public void setParameters(List<?> parameters) {
		this.parameters = parameters;
	}
	
	public BlockEvaluator getFunctionBlock() {
		return functionBlock;
	}
	public void setFunctionBlock(BlockEvaluator block) {
		this.functionBlock = block;
	}
	
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public Scope getFunctionScope() {
		return functionScope;
	}
	public void setFunctionScope(Scope functionScope) {
		this.functionScope = functionScope;
	}
	
	public Evaluator getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Evaluator returnValue) {
		this.returnValue = returnValue;
	}
	
}

