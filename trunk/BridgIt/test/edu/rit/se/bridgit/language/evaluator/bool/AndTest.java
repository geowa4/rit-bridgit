package edu.rit.se.bridgit.language.evaluator.bool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class AndTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void firstIsFalse() 
	throws InvalidTypeException, NameConflictException
	{
		BooleanEvaluator op1 = new BooleanEvaluator(false);
		BooleanEvaluator op2 = new BooleanEvaluator(true);
		AndEvaluator evaluator = new AndEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be false.",
				false, t.getValue());
	}
	
	@Test
	public void secondIsFalse() 
	throws InvalidTypeException, NameConflictException
	{
		BooleanEvaluator op2 = new BooleanEvaluator(false);
		BooleanEvaluator op1 = new BooleanEvaluator(true);
		AndEvaluator evaluator = new AndEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be false.",
				false, t.getValue());
	}
	
	@Test
	public void bothAreFalse() 
	throws InvalidTypeException, NameConflictException
	{
		BooleanEvaluator op1 = new BooleanEvaluator(false);
		BooleanEvaluator op2 = new BooleanEvaluator(false);
		AndEvaluator evaluator = new AndEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be false.",
				false, t.getValue());
	}
	
	@Test
	public void bothAreTrue() 
	throws InvalidTypeException, NameConflictException
	{
		BooleanEvaluator op1 = new BooleanEvaluator(true);
		BooleanEvaluator op2 = new BooleanEvaluator(true);
		AndEvaluator evaluator = new AndEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be true.",
				true, t.getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void firstIsNonBoolean() 
	throws InvalidTypeException, NameConflictException
	{
		IntegerEvaluator op1 = new IntegerEvaluator(1);
		BooleanEvaluator op2 = new BooleanEvaluator(true);
		AndEvaluator evaluator = new AndEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot use an Integer in a binary boolean expression.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void secondIsNonBoolean() 
	throws InvalidTypeException, NameConflictException
	{
		IntegerEvaluator op2 = new IntegerEvaluator(1);
		BooleanEvaluator op1 = new BooleanEvaluator(true);
		AndEvaluator evaluator = new AndEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot use an Integer in a binary boolean expression.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void neitherIsBoolean() 
	throws InvalidTypeException, NameConflictException
	{
		IntegerEvaluator op1 = new IntegerEvaluator(1);
		IntegerEvaluator op2 = new IntegerEvaluator(1);
		AndEvaluator evaluator = new AndEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot use an Integer in a binary boolean expression.");
	}
}
