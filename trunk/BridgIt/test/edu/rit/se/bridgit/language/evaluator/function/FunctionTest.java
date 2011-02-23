package edu.rit.se.bridgit.language.evaluator.function;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.MockBlockEvaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class FunctionTest {
	
	// Tests are not right
/*
	private Scope scope;

	@Before
	public void createScope() {
		scope = new Scope(null);
	}

	@Test
	public void functionDefinitionTest() throws InvalidTypeException,
			NameConflictException {

		GroupEvaluator group = new GroupEvaluator();
		group.addItem(new ParameterEvaluator("x", "Integer"));
		MockBlockEvaluator block = new MockBlockEvaluator();
		FunctionEvaluator functionEvaluator = new FunctionEvaluator("Test",
				group, "Integer");
		functionEvaluator.setFunctionBlock(block);
		functionEvaluator.evaluate(scope);
//		assertEquals("The block must execute once.", 1,
//				block.getNumTimesEvaluated());
	}

	@Test
	public void functionCallTest() throws InvalidTypeException,
			NameConflictException {

		GroupEvaluator group = new GroupEvaluator();
		group.addItem(new IntegerEvaluator(100));
		Function function = new Function();
		function.setFunctionName("Test");
		function.setReturnType("Integer");
		List<ParameterEvaluator> para = new LinkedList<ParameterEvaluator>();
		para.add(new ParameterEvaluator("x", "Integer"));
		function.setParameters(para);
		MockBlockEvaluator block = new MockBlockEvaluator();
		function.setFunctionBlock(block);
		scope.addFunction(function, function.getFunctionBlock().evaluate(scope));
		FunctionCallEvaluator functionCall = new FunctionCallEvaluator("Test",
				group);
		functionCall.evaluate(scope);
//		assertEquals("The block must execute once.", 1,
//				block.getNumTimesEvaluated());
	}
	
	@Test
	public void functionWithNoBlock_ParameterAndReturn() throws InvalidTypeException, NameConflictException{
		Function function = new Function();
		function.setFunctionName("Test");	
		function.setParameters(new LinkedList<Object>());
		function.setFunctionBlock(new MockBlockEvaluator());
		scope.addFunction(function, function.getFunctionBlock().evaluate(scope));
		
		GroupEvaluator group = new GroupEvaluator();
		FunctionCallEvaluator functionCall = new FunctionCallEvaluator("Test",
				group);
		functionCall.evaluate(scope);
		
		MockBlockEvaluator block = new MockBlockEvaluator();
		assertEquals("No block to execute.", 0,
				block.getNumTimesEvaluated());
	}
*/
}
