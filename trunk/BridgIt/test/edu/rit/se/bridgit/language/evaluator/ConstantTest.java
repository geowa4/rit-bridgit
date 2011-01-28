package edu.rit.se.bridgit.language.evaluator;

import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class ConstantTest
{
	@Test
	public void declarationWithValidInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator value = new IntegerEvaluator(7);
		Evaluator evaluator = new ConstantEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Constant must be set to 7.",
				value.evaluate(scope).getValue(), 
				scope.getConstantValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void declareWithInvalidInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator value = new BooleanEvaluator(true);
		Evaluator evaluator = new ConstantEvaluator(
				"x", "Integer", value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		fail("Constant cannot be declared as one type and set to another.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void declareWithoutInitialization() 
	throws InvalidTypeException, NameConflictException
	{
		Evaluator evaluator = new ConstantEvaluator(
				"x", "Integer", null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertThat("Variable must be set to 7.",
				scope.getVariableValue("x").getValue(), nullValue());
	}
}
