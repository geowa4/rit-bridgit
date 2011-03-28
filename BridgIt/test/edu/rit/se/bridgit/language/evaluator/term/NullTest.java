package edu.rit.se.bridgit.language.evaluator.term;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NullType;
import edu.rit.se.bridgit.language.model.Type;

public class NullTest
{
	@Test
	public void onlyAllowsNull() 
	throws InvalidTypeException
	{
		NullEvaluator evaluator = new NullEvaluator();
		Type ret = evaluator.evaluate(null);
		assertEquals("Value must be null.", NullType.NULL_VALUE, ret.getValue());
		assertEquals("Pseudo type must be Null.", "Null", ret.getPseudoType());
		assertThat("Java type must not be \"null\"", ret.getType(), notNullValue());
	}
}
