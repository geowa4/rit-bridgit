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
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class LessThanTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void anyNumberTypeIsAllowed() throws InvalidTypeException, NameConflictException
	{
		IntegerEvaluator op1 = new IntegerEvaluator(3);
		DoubleEvaluator op2 = new DoubleEvaluator(3.0);
		LessThanEvaluator evaluator = new LessThanEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Java type must be Boolean.", false, t.getValue());
		assertEquals("Pseudo Type must be \"Boolean\"", Type.BOOLEAN_TYPE, t.getPseudoType());
	}
	
	@Test
	public void stringsAreAllowed() throws InvalidTypeException, NameConflictException
	{
		StringEvaluator op1 = new StringEvaluator("b");
		StringEvaluator op2 = new StringEvaluator("a");
		LessThanEvaluator evaluator = new LessThanEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Java type must be Boolean.", false, t.getValue());
		assertEquals("Pseudo Type must be \"Boolean\"", Type.BOOLEAN_TYPE, t.getPseudoType());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nullIsNotAllowed() throws InvalidTypeException, NameConflictException
	{
		NullEvaluator op1 = new NullEvaluator();
		StringEvaluator op2 = new StringEvaluator("a");
		LessThanEvaluator evaluator = new LessThanEvaluator(op1, op2);
		evaluator.evaluate(scope);
		fail("Null cannot be compared.");
	}
}
