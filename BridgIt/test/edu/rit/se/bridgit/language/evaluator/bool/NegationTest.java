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

public class NegationTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void negatingABooleanIsTypeConsistent() 
	throws InvalidTypeException, NameConflictException
	{
		BooleanEvaluator b = new BooleanEvaluator(true);
		NegationEvaluator evaluator = new NegationEvaluator(b);
		Type t = evaluator.evaluate(scope);
		assertEquals("Type must still be boolean, and it must be false.",
				false, t.getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void cannotNegateSomethingThatIsNotBoolean() 
	throws InvalidTypeException, NameConflictException
	{
		IntegerEvaluator i = new IntegerEvaluator(1);
		NegationEvaluator evaluator = new NegationEvaluator(i);
		evaluator.evaluate(scope);
		fail("Cannot negate anything that is not a Boolean.");
	}
}
