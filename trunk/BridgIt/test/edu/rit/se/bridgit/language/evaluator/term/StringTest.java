package edu.rit.se.bridgit.language.evaluator.term;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class StringTest
{
	@Test
	public void onlyReturnsStringTypesWhenGivenStringValues() 
	throws InvalidTypeException
	{
		StringEvaluator evaluator = new StringEvaluator("not empty");
		Type ret = evaluator.evaluate(null);
		assertEquals("Value must be \"not empty\".", "not empty", ret.getValue());
		assertEquals("Pseudo type must be String.", "String", ret.getPseudoType());
		assertEquals("Java type must be String", String.class, ret.getType());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doesNotAllowNull() throws InvalidTypeException
	{
		StringEvaluator evaluator = new StringEvaluator(null);
		evaluator.evaluate(null);
		fail("Null is not allowed in a String evaluator.");
	}
}
