package edu.rit.se.bridgit.language.evaluator.term;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class IntegerTest
{
	@Test
	public void onlyReturnsIntegerTypesWhenGivenIntegerValues() 
	throws InvalidTypeException
	{
		IntegerEvaluator evaluator = new IntegerEvaluator(1);
		Type ret = evaluator.evaluate(null);
		assertEquals("Value must be 1.", 1, ret.getValue());
		assertEquals("Pseudo type must be Integer.", Type.INTEGER_TYPE, ret.getPseudoType());
		assertEquals("Java type must be Integer", Integer.class, ret.getType());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doesNotAllowNull() throws InvalidTypeException
	{
		IntegerEvaluator evaluator = new IntegerEvaluator(null);
		evaluator.evaluate(null);
		fail("Null is not allowed in a Integer evaluator.");
	}
}
