package edu.rit.se.bridgit.language.evaluator.arithmetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.DoubleEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.StringEvaluator;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class DivideTest
{
	private Scope scope;

	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void integerDivisionWithRoundoff() 
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new IntegerEvaluator(1), new IntegerEvaluator(3));
		Type ret = evaluator.evaluate(scope);
		assertEquals("1/3 must be 0, not 0.333...", 0, ret.getValue());
	}
	
	@Test
	public void integerDivisionWithoutRoundoff() 
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new IntegerEvaluator(9), new IntegerEvaluator(3));
		Type ret = evaluator.evaluate(scope);
		assertEquals("9/3 must be integer 3", 3, ret.getValue());
	}
	
	@Test
	public void integerDividedByDoubleIsADouble()
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new IntegerEvaluator(9), new DoubleEvaluator(4.5));
		Type ret = evaluator.evaluate(scope);
		assertEquals("9/4.5 must be double 2.0.", 2.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleDividedByDoubleIsADouble()
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new DoubleEvaluator(1.0), new DoubleEvaluator(0.33333));
		Type ret = evaluator.evaluate(scope);
		assertEquals("1.0/0.33333 must be double 3.0.", 3.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleDividedByIntegerIsADouble()
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new DoubleEvaluator(10.0), new IntegerEvaluator(2));
		Type ret = evaluator.evaluate(scope);
		assertEquals("10.0/2 must be double 5.0.", 5.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringDividedByNumberIsNotAllowed()
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new StringEvaluator("9"), new IntegerEvaluator(3));
		evaluator.evaluate(scope);
		fail("A string cannot be used in division.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void numberDividedByStringIsNotAllowed()
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new IntegerEvaluator(9), new StringEvaluator("3"));
		evaluator.evaluate(scope);
		fail("A string cannot be used in division.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringDividedByStringIsNotAllowed()
	throws PseudoException
	{
		DivideEvaluator evaluator = new DivideEvaluator(
				new StringEvaluator("9"), new StringEvaluator("3"));
		evaluator.evaluate(scope);
		fail("A string cannot be used in division.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nullIsNotAllowed() throws PseudoException
	{
		NullEvaluator op1 = new NullEvaluator();
		IntegerEvaluator op2 = new IntegerEvaluator(0);
		DivideEvaluator evaluator = new DivideEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot perform arithmetic on Null.");
	}
}
