package edu.rit.se.bridgit.language.evaluator.term;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class BooleanTest
{
	@Test
	public void onlyReturnsBooleanTypesWhenGivenBooleanValues() 
	throws InvalidTypeException
	{
		BooleanEvaluator evaluator = new BooleanEvaluator(true);
		Type ret = evaluator.evaluate(null);
		assertEquals("Value must be true.", true, ret.getValue());
		assertEquals("Pseudo type must be Boolean.", Type.BOOLEAN_TYPE, ret.getPseudoType());
		assertEquals("Java type must be Boolean", Boolean.class, ret.getType());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doesNotAllowNull() throws InvalidTypeException
	{
		BooleanEvaluator evaluator = new BooleanEvaluator(null);
		evaluator.evaluate(null);
		fail("Null is not allowed in a Boolean evaluator.");
	}
}
