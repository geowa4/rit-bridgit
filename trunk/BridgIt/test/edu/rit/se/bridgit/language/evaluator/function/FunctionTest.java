package edu.rit.se.bridgit.language.evaluator.function;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionTest
{
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	@Mock ParameterList params;
	@Mock Block block;
	@Mock Evaluator returnExpr;
	
	private Scope scope;
	private Function function;
	
	@Before
	public void createFunction()
	{
		scope = new Scope(null);
		function = new Function();
		function.setFunctionName("test");
		function.setDefinitionScope(scope);
		function.setFunctionBlock(block);
		function.setReturnValue(returnExpr);
	}
	
	@Test
	public void voidFunctionReturnsVoidType() throws InvalidTypeException, NameConflictException
	{
		function.setReturnType(Function.VOID_TYPE);
		context.checking(new Expectations() {{
			oneOf(block).evaluate(with(any(Scope.class)));
			never(returnExpr).evaluate(with(any(Scope.class)));
		}});
		assertEquals("Type must be void.", Function.VOID_TYPE, function.apply(null).getPseudoType());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void nonNullParameterListIsEvaluated() throws InvalidTypeException, NameConflictException
	{
		function.setReturnType(Function.VOID_TYPE);
		function.setParameters(params);
		context.checking(new Expectations() {{
			oneOf(params).setArgs(with(aNull(List.class)));
			oneOf(params).evaluate(with(any(Scope.class)));
			oneOf(block).evaluate(with(any(Scope.class)));
			never(returnExpr).evaluate(with(any(Scope.class)));
		}});
		function.apply(null);
	}
	
	@Test
	public void typeOfFunctionReturnsSameType() throws InvalidTypeException, NameConflictException
	{
		function.setReturnType("Integer");
		context.checking(new Expectations() {{
			oneOf(block).evaluate(with(any(Scope.class)));
			oneOf(returnExpr).evaluate(with(any(Scope.class))); will(returnValue(new Type(1, "Integer")));
		}});
		assertEquals("Type should have Pseudo type of \"Integer\".", "Integer", function.apply(null).getPseudoType());
	}
}
