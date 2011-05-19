package edu.rit.se.bridgit.language.evaluator.bool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class OrTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void firstIsFalse() 
	throws PseudoException
	{
		BooleanEvaluator op1 = new BooleanEvaluator(false);
		BooleanEvaluator op2 = new BooleanEvaluator(true);
		OrEvaluator evaluator = new OrEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be true.",
				true, t.getValue());
	}
	
	@Test
	public void secondIsFalse() 
	throws PseudoException
	{
		BooleanEvaluator op2 = new BooleanEvaluator(false);
		BooleanEvaluator op1 = new BooleanEvaluator(true);
		OrEvaluator evaluator = new OrEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be true.",
				true, t.getValue());
	}
	
	@Test
	public void bothAreFalse() 
	throws PseudoException
	{
		BooleanEvaluator op1 = new BooleanEvaluator(false);
		BooleanEvaluator op2 = new BooleanEvaluator(false);
		OrEvaluator evaluator = new OrEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be false.",
				false, t.getValue());
	}
	
	@Test
	public void bothAreTrue() 
	throws PseudoException
	{
		BooleanEvaluator op1 = new BooleanEvaluator(true);
		BooleanEvaluator op2 = new BooleanEvaluator(true);
		OrEvaluator evaluator = new OrEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must be boolean, and that value must be true.",
				true, t.getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void firstIsNonBoolean() 
	throws PseudoException
	{
		IntegerEvaluator op1 = new IntegerEvaluator(1);
		BooleanEvaluator op2 = new BooleanEvaluator(true);
		OrEvaluator evaluator = new OrEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot use an Integer in a binary boolean expression.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void secondIsNonBoolean() 
	throws PseudoException
	{
		IntegerEvaluator op2 = new IntegerEvaluator(1);
		BooleanEvaluator op1 = new BooleanEvaluator(true);
		OrEvaluator evaluator = new OrEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot use an Integer in a binary boolean expression.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void neitherIsBoolean() 
	throws PseudoException
	{
		IntegerEvaluator op1 = new IntegerEvaluator(1);
		IntegerEvaluator op2 = new IntegerEvaluator(1);
		OrEvaluator evaluator = new OrEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot use an Integer in a binary boolean expression.");
	}
}
