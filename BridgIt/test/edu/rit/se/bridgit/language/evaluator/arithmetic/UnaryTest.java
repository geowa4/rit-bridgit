package edu.rit.se.bridgit.language.evaluator.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.DoubleEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class UnaryTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void negatingAPositiveIntegerMakesANegativeInteger() 
	throws InvalidTypeException, NameConflictException
	{
		UnaryEvaluator evaluator = new UnaryEvaluator(new IntegerEvaluator(1));
		Type t = evaluator.evaluate(scope);
		assertEquals("Integer must be negative.", -1, t.getValue());
	}
	
	@Test
	public void negatingAPositiveDoubleMakesANegativeDouble() 
	throws InvalidTypeException, NameConflictException
	{
		UnaryEvaluator evaluator = new UnaryEvaluator(new DoubleEvaluator(1.0));
		Type t = evaluator.evaluate(scope);
		assertEquals("Double must be negative.", -1.0, (Double) t.getValue(), 0.01);
	}
	
	@Test
	public void negatingANegativeIntegerMakesAPositiveInteger() 
	throws InvalidTypeException, NameConflictException
	{
		UnaryEvaluator evaluator = new UnaryEvaluator(new IntegerEvaluator(-1));
		Type t = evaluator.evaluate(scope);
		assertEquals("Integer must be positive.", 1, t.getValue());
	}
	
	@Test
	public void negatingANegativeDoubleMakesAPositiveDouble() 
	throws InvalidTypeException, NameConflictException
	{
		UnaryEvaluator evaluator = new UnaryEvaluator(new DoubleEvaluator(-1.0));
		Type t = evaluator.evaluate(scope);
		assertEquals("Double must be positive.", 1.0, (Double) t.getValue(), 0.01);
	}
	
	@Test
	public void negatingYourselfYieldsNothing() 
	throws InvalidTypeException, NameConflictException
	{
		UnaryEvaluator nested = new UnaryEvaluator(new DoubleEvaluator(1.0));
		UnaryEvaluator evaluator = new UnaryEvaluator(nested);
		Type t = evaluator.evaluate(scope);
		assertEquals("Double-negation of a positive must be positive.", 
				1.0, (Double) t.getValue(), 0.01);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nullIsNotAllowed() throws InvalidTypeException, NameConflictException
	{
		NullEvaluator op1 = new NullEvaluator();
		UnaryEvaluator evaluator = new UnaryEvaluator(op1);
		evaluator.evaluate(scope);
		fail("Cannot perform arithmetic on Null.");
	}
}
