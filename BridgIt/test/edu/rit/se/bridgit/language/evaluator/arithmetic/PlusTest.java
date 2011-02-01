package edu.rit.se.bridgit.language.evaluator.arithmetic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.DoubleEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.StringEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class PlusTest
{
	private Scope scope;

	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}

	@Test
	public void integerAddedToIntegerIsAnInteger()
	throws InvalidTypeException, NameConflictException
	{
		PlusEvaluator evaluator = new PlusEvaluator(
				new IntegerEvaluator(1), new IntegerEvaluator(1));
		Type ret = evaluator.evaluate(scope);
		assertEquals("1+1 must be integer 2.", 2, ret.getValue());
	}
	
	@Test
	public void integerAddedToDoubleIsADouble()
	throws InvalidTypeException, NameConflictException
	{
		PlusEvaluator evaluator = new PlusEvaluator(
				new IntegerEvaluator(3), new DoubleEvaluator(2.0));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3+2.0 must be double 5.0.", 5.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleAddedToDoubleIsADouble()
	throws InvalidTypeException, NameConflictException
	{
		PlusEvaluator evaluator = new PlusEvaluator(
				new DoubleEvaluator(3.0), new DoubleEvaluator(4.0));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3.0+4.0 must be double 7.0.", 7.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void doubleAddedToIntegerIsADouble()
	throws InvalidTypeException, NameConflictException
	{
		PlusEvaluator evaluator = new PlusEvaluator(
				new DoubleEvaluator(3.0), new IntegerEvaluator(3));
		Type ret = evaluator.evaluate(scope);
		assertEquals("3.0+3 must be double 6.0.", 6.0, (Double) ret.getValue(), 0.01);
	}
	
	@Test
	public void stringAddedToIntegerIsAString()
	throws InvalidTypeException, NameConflictException
	{
		PlusEvaluator evaluator = new PlusEvaluator(
				new StringEvaluator("hello"), new IntegerEvaluator(3));
		Type ret = evaluator.evaluate(scope);
		assertEquals("\"hello\"+3 must be \"hello3\".", "hello3", ret.getValue());
	}
	
	@Test
	public void stringAddedToDoubleIsAString()
	throws InvalidTypeException, NameConflictException
	{
		PlusEvaluator evaluator = new PlusEvaluator(
				new StringEvaluator("hello"), new DoubleEvaluator(3.0));
		Type ret = evaluator.evaluate(scope);
		assertEquals("\"hello\"+3 must be \"hello3.0\".", "hello3.0", ret.getValue());
	}
	
	@Test
	public void stringAddedToStringIsAString()
	throws InvalidTypeException, NameConflictException
	{
		PlusEvaluator evaluator = new PlusEvaluator(
				new StringEvaluator("hello"), new StringEvaluator("3"));
		Type ret = evaluator.evaluate(scope);
		assertEquals("\"hello\"+\"3\" must be \"hello3\".", "hello3", ret.getValue());
	}
}
