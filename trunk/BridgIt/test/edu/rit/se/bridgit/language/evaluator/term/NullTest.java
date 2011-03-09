package edu.rit.se.bridgit.language.evaluator.term;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

public class NullTest
{
	@Test
	public void onlyAllowsNull() 
	throws InvalidTypeException
	{
		NullEvaluator evaluator = new NullEvaluator();
		Type ret = evaluator.evaluate(null);
		assertEquals("Value must be null.", Type.NULL, ret.getValue());
		assertEquals("Pseudo type must be Null.", "Null", ret.getPseudoType());
		assertEquals("Java type must be Object as null is not allowed.", Object.class, ret.getType());
	}
}
