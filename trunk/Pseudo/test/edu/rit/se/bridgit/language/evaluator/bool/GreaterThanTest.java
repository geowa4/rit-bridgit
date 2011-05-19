package edu.rit.se.bridgit.language.evaluator.bool;

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

public class GreaterThanTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void anyNumberTypeIsAllowed() throws PseudoException
	{
		IntegerEvaluator op1 = new IntegerEvaluator(3);
		DoubleEvaluator op2 = new DoubleEvaluator(3.0);
		GreaterThanEvaluator evaluator = new GreaterThanEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Java type must be Boolean.", false, t.getValue());
		assertEquals("Pseudo Type must be \"Boolean\"", Type.BOOLEAN_TYPE, t.getPseudoType());
	}
	
	@Test
	public void stringsAreAllowed() throws PseudoException
	{
		StringEvaluator op1 = new StringEvaluator("b");
		StringEvaluator op2 = new StringEvaluator("a");
		GreaterThanEvaluator evaluator = new GreaterThanEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Java type must be Boolean.", true, t.getValue());
		assertEquals("Pseudo Type must be \"Boolean\"", Type.BOOLEAN_TYPE, t.getPseudoType());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nullIsNotAllowed() throws PseudoException
	{
		NullEvaluator op1 = new NullEvaluator();
		StringEvaluator op2 = new StringEvaluator("a");
		GreaterThanEvaluator evaluator = new GreaterThanEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Null cannot be compared.");
	}
}
