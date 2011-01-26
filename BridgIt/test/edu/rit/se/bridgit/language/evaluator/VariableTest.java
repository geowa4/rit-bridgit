package edu.rit.se.bridgit.language.evaluator;

import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class VariableTest 
{
	@Test
	public void declarationWithValidInitialization() 
	throws InvalidTypeException
	{
		Evaluator value = new IntegerEvaluator(7);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Variable must be set to 7.",
				value.evaluate(scope).getValue(), 
				scope.getVariableValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void declareWithInvalidInitialization() 
	throws InvalidTypeException
	{
		Evaluator value = new BooleanEvaluator(true);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		fail("Variable cannot be declared as one type and set to another.");
	}
	
	@Test
	public void declareWithoutInitialization() 
	throws InvalidTypeException
	{
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertThat("Variable must be set to 7.",
				scope.getVariableValue("x"), nullValue());
	}
	
	@Test
	public void assignToValidTypeAfterValidInitialization() 
	throws InvalidTypeException
	{
		Evaluator value = new IntegerEvaluator(7);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new IntegerEvaluator(8);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to 8.",
				new Type(8).getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void assignToInvalidTypeAfterValidInitialization() 
	throws InvalidTypeException
	{
		//TODO
		throw new InvalidTypeException(Integer.class, "Assignment");
	}
	
	@Test
	public void assignToNullAfterValidInitialization() 
	throws InvalidTypeException
	{
		//TODO
	}
	
	@Test
	public void assignToValidTypeAfterNoInitialization() 
	throws InvalidTypeException
	{
		//TODO
	}
	
	@Test(expected=InvalidTypeException.class)
	public void assignToInValidTypeAfterNoInitialization() 
	throws InvalidTypeException
	{
		//TODO
		throw new InvalidTypeException(Integer.class, "Assignment");
	}
	
	@Test
	public void assignToNullAfterNoInitialization() 
	throws InvalidTypeException
	{
		//TODO
	}
}
