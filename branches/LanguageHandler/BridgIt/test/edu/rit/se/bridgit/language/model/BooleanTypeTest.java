package edu.rit.se.bridgit.language.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class BooleanTypeTest
{
	private BooleanType type;
	
	@Before
	public void setUp() throws InvalidTypeException
	{
		type = new BooleanType(true);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeAdded() throws InvalidTypeException
	{
		type.add(new BooleanType(false));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeSubtracted() throws InvalidTypeException
	{
		type.subtract(new BooleanType(false));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeMultiplied() throws InvalidTypeException
	{
		type.multiply(new BooleanType(false));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeDivided() throws InvalidTypeException
	{
		type.divide(new BooleanType(false));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeModded() throws InvalidTypeException
	{
		type.mod(new BooleanType(false));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeUnaryNegated() throws InvalidTypeException
	{
		type.unary();
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeGreaterThanAnything() throws InvalidTypeException
	{
		type.gt(new BooleanType(false));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansCannotBeLessThanAnything() throws InvalidTypeException
	{
		type.lt(new BooleanType(false));
	}
	
	@Test
	public void booleansCanBeEqualToOtherBooleanOfSameValue() throws InvalidTypeException
	{
		assertFalse("True cannot equals false.", (Boolean) type.eq(new BooleanType(false)).getValue());
		assertTrue("True must equal true.", (Boolean) type.eq(new BooleanType(true)).getValue());
	}
	
	@Test
	public void booleansCanBeNegated() throws InvalidTypeException
	{
		assertEquals("Resultant value must be false.", false, type.negation().getValue());
	}
	
	@Test
	public void booleansCanBeAnded() throws InvalidTypeException
	{
		assertEquals("Resultant value must be false.", false, type.and(new BooleanType(false)).getValue());
	}
	
	@Test
	public void booleansCanBeOred() throws InvalidTypeException
	{
		assertEquals("Resultant value must be true.", true, type.or(new BooleanType(false)).getValue());
	}
}
