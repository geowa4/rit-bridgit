package edu.rit.se.bridgit.language.evaluator;

import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class ConstantTest
{
	@Test
	public void declarationWithValidInitialization() 
	throws PseudoException
	{
		Evaluator value = new IntegerEvaluator(7);
		Evaluator evaluator = new ConstantEvaluator(
				"x", Type.INTEGER_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Constant must be set to 7.",
				value.evaluate(scope).getValue(), 
				scope.getConstantValue("x").getValue());
	}
	
	@Test
	public void declarationAsDoubleAssignAsInteger() 
	throws PseudoException
	{
		Evaluator value = new IntegerEvaluator(7);
		Evaluator evaluator = new ConstantEvaluator(
				"x", Type.DOUBLE_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Constant must be set to 7.0.",
				7.0, 
				(Double) scope.getConstantValue("x").getValue(),
				0.01);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void declareWithInvalidInitialization() 
	throws PseudoException
	{
		Evaluator value = new BooleanEvaluator(true);
		Evaluator evaluator = new ConstantEvaluator(
				"x", Type.INTEGER_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		fail("Constant cannot be declared as one type and set to another.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void declareWithoutInitialization() 
	throws PseudoException
	{
		Evaluator evaluator = new ConstantEvaluator(
				"x", Type.INTEGER_TYPE, null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertThat("Variable must be set to 7.",
				scope.getVariableValue("x").getValue(), nullValue());
	}
}
