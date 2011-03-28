package edu.rit.se.bridgit.language.evaluator.bool;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class EqualsTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void resultIsBoolean() 
	throws InvalidTypeException, NameConflictException
	{
		IntegerEvaluator op1 = new IntegerEvaluator(0);
		BooleanEvaluator op2 = new BooleanEvaluator(true);
		EqualsEvaluator evaluator = new EqualsEvaluator(op1, op2);
		Type t = evaluator.evaluate(scope);
		assertEquals("Java type must be Boolean.", Boolean.class, t.getType());
		assertEquals("Pseudo Type must be \"Boolean\"", Type.BOOLEAN_TYPE, t.getPseudoType());
	}
}
