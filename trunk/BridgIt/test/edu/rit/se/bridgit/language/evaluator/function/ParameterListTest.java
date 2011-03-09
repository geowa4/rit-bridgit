package edu.rit.se.bridgit.language.evaluator.function;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

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
	public void nullArgsWhenExpectingParamsIsNotAllowed() throws InvalidTypeException, NameConflictException
	{
		ple.addParam(new ParameterEvaluator("test", "String"));
		ple.evaluate(scope);
		fail("Arguments are null when at least one parameter is expected.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nonNullArgsWhenNotExpectingParamsIsNotAllowed() throws InvalidTypeException, NameConflictException
	{
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = 4920464986871390479L;
		{
			add(new Type(null, "Null"));
		}});
		ple.evaluate(scope);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void argsLenAndParamsLenCannotBeDifferent() throws InvalidTypeException, NameConflictException
	{
		ple.addParam(new ParameterEvaluator("test", "String"));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new Type("hi", "String"));
			add(new Type("hi", "String"));
		}});
		ple.evaluate(scope);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void pseudoTypesOfArgsAndParamsMustMatch() throws InvalidTypeException, NameConflictException
	{
		ple.addParam(new ParameterEvaluator("test", "String"));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new Type(1, "Integer"));
		}});
		ple.evaluate(scope);
	}
	
	@Test
	public void parametersAreAddedAsVariablesToScope() throws InvalidTypeException, NameConflictException
	{
		ple.addParam(new ParameterEvaluator("test", "String"));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new Type("hi", "String"));
		}});
		ple.evaluate(scope);
		assertEquals("Parameter must be added as a variable.", scope.getVariableValue("test").getValue(), "hi");
	}
	
	@Test
	public void parameterCanBeSetToNullValue() throws InvalidTypeException, NameConflictException
	{
		ple.addParam(new ParameterEvaluator("test", "String"));
		ple.setArgs(new LinkedList<Type>() {
			private static final long serialVersionUID = -1267528789248589583L;
		{
			add(new Type(Type.NULL, NullEvaluator.NULL_TYPE));
		}});
		ple.evaluate(scope);
		assertThat("Parameter must be set to Null.", scope.getVariableValue("test").getValue(), sameInstance(Type.NULL));
	}
}
