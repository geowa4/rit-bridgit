package edu.rit.se.bridgit.language.evaluator.function;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.NullType;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class ParameterListTest
{
	private ParameterListEvaluator ple;
	private Scope scope;
	
	@Before
	public void createEvaluatorAndScope()
	{
		ple = new ParameterListEvaluator();
		scope = new Scope(null);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nullArgsWhenExpectingParamsIsNotAllowed() throws PseudoException
	{
		ple.addParam(new ParameterEvaluator("test", Type.STRING_TYPE));
		ple.evaluate(scope);
		fail("Arguments are null when at least one parameter is expected.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nonNullArgsWhenNotExpectingParamsIsNotAllowed() throws PseudoException
	{
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = 4920464986871390479L;
		{
			add(new NullType());
		}});
		ple.evaluate(scope);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void argsLenAndParamsLenCannotBeDifferent() throws PseudoException
	{
		ple.addParam(new ParameterEvaluator("test", Type.STRING_TYPE));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new StringType("hi"));
			add(new StringType("hi"));
		}});
		ple.evaluate(scope);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void pseudoTypesOfArgsAndParamsMustMatch() throws PseudoException
	{
		ple.addParam(new ParameterEvaluator("test", Type.STRING_TYPE));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new IntegerType(1));
		}});
		ple.evaluate(scope);
	}
	
	@Test
	public void parametersAreAddedAsVariablesToScope() throws PseudoException
	{
		ple.addParam(new ParameterEvaluator("test", Type.STRING_TYPE));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new StringType("hi"));
		}});
		ple.evaluate(scope);
		assertEquals("Parameter must be added as a variable.", scope.getVariableValue("test").getValue(), "hi");
	}
	
	@Test
	public void parameterCanBeSetToNullValue() throws PseudoException
	{
		ple.addParam(new ParameterEvaluator("test", Type.STRING_TYPE));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new NullType());
		}});
		ple.evaluate(scope);
		assertThat("Parameter must be set to Null.", scope.getVariableValue("test").getValue(), sameInstance(NullType.NULL_VALUE));
	}
}
