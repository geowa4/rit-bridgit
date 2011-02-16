package edu.rit.se.bridgit.language.evaluator;

import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.DoubleEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.StringEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class VariableTest 
{
	@Test
	public void declarationWithValidInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator value = new IntegerEvaluator(7);
		Evaluator evaluator = new VariableEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Variable must be set to 7.",
				value.evaluate(scope).getValue(), 
				scope.getVariableValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void declareWithInvalidInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator value = new BooleanEvaluator(true);
		Evaluator evaluator = new VariableEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		fail("Variable cannot be declared as one type and set to another.");
	}
	
	@Test
	public void declareWithoutInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator evaluator = new VariableEvaluator(
				"x", "Integer", null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertThat("Variable must be set to 7.",
				scope.getVariableValue("x").getValue(), nullValue());
	}
	
	@Test
	public void assignToValidTypeAfterValidInitialization() 
	throws InvalidTypeException, NameConflictException
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
				new Type(8, "Integer").getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void assignToInvalidTypeAfterValidInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator value = new IntegerEvaluator(7);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new BooleanEvaluator(false);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		fail("Variable cannot be assigned to a different type.");
	}
	
	@Test
	public void assignToNullAfterValidInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator value = new IntegerEvaluator(7);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new NullEvaluator();
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to null.",
				new Type(null, "Integer").getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test
	public void assignToValidTypeAfterNoInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new IntegerEvaluator(8);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to 8.",
				new Type(8, "Integer").getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void assignToInValidTypeAfterNoInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new DoubleEvaluator(8d);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		fail("Variable cannot be assigned to a different type.");
	}
	
	@Test
	public void assignToNullAfterNoInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", "Integer", null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new NullEvaluator();
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to null.",
				new Type(null, "Integer").getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test
	public void checkStringVariableDefinition() throws InvalidTypeException, NameConflictException{
		
		Evaluator e = new StringEvaluator("Test");
		VariableEvaluator evaluator = new VariableEvaluator("a", "String", e);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Variable must be equal to Test.",
				e.evaluate(scope).getValue(), scope.getVariableValue("a").getValue());
	}
	
}
