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
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ModTest
{
	private Scope scope;

	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}

	@Test
	public void integerModdedByIntegerIsAnInteger()
	throws InvalidTypeException, NameConflictException
	{
		ModEvaluator evaluator = new ModEvaluator(
				new IntegerEvaluator(1), new IntegerEvaluator(1));
		Type ret = evaluator.evaluate(scope);
		assertEquals("1%1 must be integer 0.", 0, ret.getValue());
	}
	
	@Test
	public void integerModdedByDoubleIsADouble()
	throws InvalidTypeException, NameConflictException
	{
		ModEvaluator evaluator = new ModEvaluator(
				new IntegerEvaluator(3), new DoubleEvaluator(2.0));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3%2.0 must be double 1.0.", 1.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleModdedByDoubleIsADouble()
	throws InvalidTypeException, NameConflictException
	{
		ModEvaluator evaluator = new ModEvaluator(
				new DoubleEvaluator(3.0), new DoubleEvaluator(4.0));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3.0%4.0 must be double 3.0.", 3.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleModdedByIntegerIsADouble()
	throws InvalidTypeException, NameConflictException
	{
		ModEvaluator evaluator = new ModEvaluator(
				new DoubleEvaluator(3.0), new IntegerEvaluator(3));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3.0%3 must be double 0.0.", 0.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringModdedByNumberIsNotAllowed()
	throws InvalidTypeException, NameConflictException
	{
		ModEvaluator evaluator = new ModEvaluator(
				new StringEvaluator("9"), new IntegerEvaluator(3));
		evaluator.evaluate(scope);
		fail("A string cannot be used in mod.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void numberModdedByStringIsNotAllowed()
	throws InvalidTypeException, NameConflictException
	{
		ModEvaluator evaluator = new ModEvaluator(
				new IntegerEvaluator(9), new StringEvaluator("3"));
		evaluator.evaluate(scope);
		fail("A string cannot be used in mod.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringModdedByStringIsNotAllowed()
	throws InvalidTypeException, NameConflictException
	{
		ModEvaluator evaluator = new ModEvaluator(
				new StringEvaluator("9"), new StringEvaluator("3"));
		evaluator.evaluate(scope);
		fail("A string cannot be used in mod.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nullIsNotAllowed() throws InvalidTypeException, NameConflictException
	{
		NullEvaluator op1 = new NullEvaluator();
		IntegerEvaluator op2 = new IntegerEvaluator(0);
		ModEvaluator evaluator = new ModEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Cannot perform arithmetic on Null.");
	}
}
