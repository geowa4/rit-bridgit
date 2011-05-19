package edu.rit.se.bridgit.language.evaluator;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.DoubleEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.StringEvaluator;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.NullType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class VariableTest 
{
	@Test
	public void declarationWithValidInitialization() 
	throws PseudoException
	{
		Evaluator value = new IntegerEvaluator(7);
		Evaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Variable must be set to 7.",
				value.evaluate(scope).getValue(), 
				scope.getVariableValue("x").getValue());
	}
	
	@Test
	public void declareAsDoulbeAssignAsInteger() 
	throws PseudoException
	{
		Evaluator value = new IntegerEvaluator(7);
		Evaluator evaluator = new VariableEvaluator(
				"x", Type.DOUBLE_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Variable must be set to 7.0.",
				7.0, 
				(Double) scope.getVariableValue("x").getValue(),
				0.01);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void declareWithInvalidInitialization() 
	throws PseudoException
	{
		Evaluator value = new BooleanEvaluator(true);
		Evaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		fail("Variable cannot be declared as one type and set to another.");
	}
	
	@Test
	public void declareWithoutInitialization() 
	throws PseudoException
	{
		Evaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertThat("Variable value must be null.",
				scope.getVariableValue("x").getValue(), sameInstance(NullType.NULL_VALUE));
	}
	
	@Test
	public void assignToValidTypeAfterValidInitialization() 
	throws PseudoException
	{
		Evaluator value = new IntegerEvaluator(7);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new IntegerEvaluator(8);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to 8.",
				new IntegerType(8).getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void assignToInvalidTypeAfterValidInitialization() 
	throws PseudoException
	{
		Evaluator value = new IntegerEvaluator(7);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new BooleanEvaluator(false);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		fail("Variable cannot be assigned to a different type.");
	}
	
	@Test
	public void assignToNullAfterValidInitialization() 
	throws PseudoException
	{
		Evaluator value = new IntegerEvaluator(7);
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, value);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new NullEvaluator();
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to null.",
				new NullType().getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test
	public void assignToValidTypeAfterNoInitialization() 
	throws PseudoException
	{
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new IntegerEvaluator(8);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to 8.",
				new IntegerType(8).getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void assignToInValidTypeAfterNoInitialization() 
	throws PseudoException
	{
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new DoubleEvaluator(8d);
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		fail("Variable cannot be assigned to a different type.");
	}
	
	@Test
	public void assignToNullAfterNoInitialization() 
	throws PseudoException
	{
		VariableEvaluator evaluator = new VariableEvaluator(
				"x", Type.INTEGER_TYPE, null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		
		Evaluator newValue = new NullEvaluator();
		VariableEvaluator assignment = new VariableEvaluator("x", newValue);
		assignment.evaluate(scope);
		
		assertEquals("Variable must be changed to null.",
				new NullType().getValue(), scope.getVariableValue("x").getValue());
	}
	
	@Test
	public void checkStringVariableDefinition() throws PseudoException
	{
		
		Evaluator e = new StringEvaluator("Test");
		VariableEvaluator evaluator = new VariableEvaluator("a", Type.STRING_TYPE, e);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		assertEquals("Variable must be equal to Test.",
				e.evaluate(scope).getValue(), scope.getVariableValue("a").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void cannotDeclareWithNullPseudoType() throws PseudoException
	{
		Evaluator evaluator = new VariableEvaluator(
				"x", null, null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		fail("Variable cannot be declared with null Pseudo Type.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void cannotDeclareWithEmptyPseudoType() throws PseudoException
	{
		Evaluator evaluator = new VariableEvaluator(
				"x", "", null);
		Scope scope = new Scope(null);
		evaluator.evaluate(scope);
		fail("Variable cannot be declared with null Pseudo Type.");
	}
}
