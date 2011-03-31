package edu.rit.se.bridgit.language.evaluator.term;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class DoubleTest
{
	@Test
	public void onlyReturnsDoubleTypesWhenGivenDoubleValues() 
	throws InvalidTypeException
	{
		DoubleEvaluator evaluator = new DoubleEvaluator(1.1);
		Type ret = evaluator.evaluate(null);
		assertEquals("Value must be 1.1.", 1.1, ret.getValue());
		assertEquals("Pseudo type must be Double.", Type.DOUBLE_TYPE, ret.getPseudoType());
		assertEquals("Java type must be Double", Double.class, ret.getType());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doesNotAllowNull() throws InvalidTypeException
	{
		DoubleEvaluator evaluator = new DoubleEvaluator(null);
		evaluator.evaluate(null);
		fail("Null is not allowed in a Double evaluator.");
	}
}
