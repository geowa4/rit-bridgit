package edu.rit.se.bridgit.language.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class StringTypeTest
{
	private static final String baseWord = "hello";
	private static final String testWord = "world";
	private StringType type;
	
	@Before
	public void setUp() throws InvalidTypeException
	{
		type = new StringType(baseWord);
	}
	
	@Test
	public void stringsCanBeAdded() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands concatenated.", 
				baseWord.concat(testWord), type.add(new StringType("world")).getValue());
	}
	
	@Test
	public void integersCanBeAddedToStrings() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands concatenated.", 
				baseWord.concat("1"), type.add(new IntegerType(1)).getValue());
	}
	
	@Test
	public void doublesCanBeAddedToStrings() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands concatenated.", 
				baseWord.concat("1.0"), type.add(new DoubleType(1d)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeSubtracted() throws InvalidTypeException
	{
		type.subtract(new StringType("world"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeMultiplied() throws InvalidTypeException
	{
		type.multiply(new StringType("world"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeDivided() throws InvalidTypeException
	{
		type.divide(new StringType("world"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeModded() throws InvalidTypeException
	{
		type.mod(new StringType("world"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeUnaryNegated() throws InvalidTypeException
	{
		type.unary();
	}
	
	@Test
	public void stringsCanBeGreaterThanOtherStrings() throws InvalidTypeException
	{
		assertEquals("Comparison should allow another String.", 
				baseWord.compareTo(testWord) > 0, type.gt(new StringType("world")).getValue());
	}
	
	@Test
	public void stringsCanBeLessThanOtherStrings() throws InvalidTypeException
	{
		assertEquals("Comparison should allow another String.", 
				baseWord.compareTo(testWord) < 0, type.lt(new StringType("world")).getValue());
	}
	
	@Test
	public void stringsCanBeEqualToOtherStringOfSameValue() throws InvalidTypeException
	{
		assertTrue("", (Boolean) type.eq(new StringType(baseWord)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeNegated() throws InvalidTypeException
	{
		type.negation();
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeAnded() throws InvalidTypeException
	{
		type.and(new StringType("world"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeOred() throws InvalidTypeException
	{
		type.or(new StringType("world"));
	}
}
