package edu.rit.se.bridgit.language.evaluator.function;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
	private FunctionEvaluator fnEval;
	
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
	
	@Before
	public void createEvaluator()
	{
		fnEval = new FunctionEvaluator("test");
	}
	
	@Test
	public void voidFunctionReturnsVoidType() throws InvalidTypeException, NameConflictException
	{
		function.setReturnType(Type.VOID_TYPE);
		context.checking(new Expectations() {{
			oneOf(block).evaluate(with(any(Scope.class)));
			never(returnExpr).evaluate(with(any(Scope.class)));
		}});
		assertEquals("Type must be void.", Type.VOID_TYPE, function.apply(null).getPseudoType());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void nonNullParameterListIsEvaluated() throws InvalidTypeException, NameConflictException
	{
		function.setReturnType(Type.VOID_TYPE);
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
	public void functionDoesNotCareAboutReturnType() throws InvalidTypeException, NameConflictException
	{//the executing evaluator must check this
		function.setReturnType("String");
		context.checking(new Expectations() {{
			oneOf(block).evaluate(with(any(Scope.class)));
			oneOf(returnExpr).evaluate(with(any(Scope.class))); will(returnValue(new Type(1, "Integer")));
		}});
		assertEquals("Type should have Pseudo type of \"Integer\" " +
				"even though function declares \"String\".", "Integer", 
				function.apply(null).getPseudoType());
	}
	
	@Test
	public void functionIsAddedToScopeWithDefinitionScopeSet() throws InvalidTypeException, NameConflictException
	{
		function.setReturnType("Function:" + Type.VOID_TYPE);
		fnEval.setFunction(function);
		assertEquals("Type should be \"void\".", "Function:" + Type.VOID_TYPE,
				fnEval.evaluate(scope).getPseudoType());
		assertThat("Function must be same.", function, sameInstance(scope.getFunction("test")));
		assertThat("Scope must be same.", scope, sameInstance(function.getDefinitionScope()));
	}
}
