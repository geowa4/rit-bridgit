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

public class MultTest
{
	private Scope scope;

	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}

	@Test
	public void integerMultipliedByIntegerIsAnInteger()
	throws PseudoException
	{
		MultEvaluator evaluator = new MultEvaluator(
				new IntegerEvaluator(1), new IntegerEvaluator(1));
		Type ret = evaluator.evaluate(scope);
		assertEquals("1*1 must be integer 1.", 1, ret.getValue());
	}
	
	@Test
	public void integerMultipliedByDoubleIsADouble()
	throws PseudoException
	{
		MultEvaluator evaluator = new MultEvaluator(
				new IntegerEvaluator(0), new DoubleEvaluator(0.0));
		Type ret = evaluator.evaluate(scope);
		assertEquals("0*0.0 must be double 0.0.", 0.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleMultipliedByDoubleIsADouble()
	throws PseudoException
	{
		MultEvaluator evaluator = new MultEvaluator(
				new DoubleEvaluator(3.0), new DoubleEvaluator(4.0));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3.0*4.0 must be double 12.0.", 12.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleMultipliedByIntegerIsADouble()
	throws PseudoException
	{
		MultEvaluator evaluator = new MultEvaluator(
				new DoubleEvaluator(3.0), new IntegerEvaluator(3));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3.0*3 must be double 9.0.", 9.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringMultipliedByNumberIsNotAllowed()
	throws PseudoException
	{
		MultEvaluator evaluator = new MultEvaluator(
				new StringEvaluator("9"), new IntegerEvaluator(3));
		evaluator.evaluate(scope);
		fail("A string cannot be used in division.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void numberMultipliedByStringIsNotAllowed()
	throws PseudoException
	{
		MultEvaluator evaluator = new MultEvaluator(
				new IntegerEvaluator(9), new StringEvaluator("3"));
		evaluator.evaluate(scope);
		fail("A string cannot be used in division.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringMultipliedByStringIsNotAllowed()
	throws PseudoException
	{
		MultEvaluator evaluator = new MultEvaluator(
				new StringEvaluator("9"), new StringEvaluator("3"));
		evaluator.evaluate(scope);
		fail("A string cannot be used in division.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nullIsNotAllowed() throws PseudoException
	{
		NullEvaluator op1 = new NullEvaluator();
		IntegerEvaluator op2 = new IntegerEvaluator(0);
		MultEvaluator evaluator = new MultEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot perform arithmetic on Null.");
	}
}
