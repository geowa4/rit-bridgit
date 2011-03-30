package edu.rit.se.bridgit.language.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class IntegerTypeTest
{
	private static final int baseInt = 7;
	private static final int testInt = 3;
	private IntegerType type;
	
	@Before
	public void setUp() throws InvalidTypeException
	{
		type = new IntegerType(baseInt);
	}
	
	@Test
	public void integersCanBeAdded() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands added together.", 
				baseInt + testInt, type.add(new IntegerType(testInt)).getValue());
	}
	
	@Test
	public void stringCanBeAddedToIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands concatenated.", 
				baseInt + "1", type.add(new StringType("1")).getValue());
	}
	
	@Test
	public void doublesCanBeAddedToIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands added together.", 
				baseInt + 1.0, type.add(new DoubleType(1d)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansAndOtherTypesCannotBeAddedToIntegers() throws InvalidTypeException
	{
		type.add(new BooleanType(true));
	}
	
	@Test
	public void doublesCanBeSubtractedFromIntegers() throws InvalidTypeException
	{
		assertEquals("Resultatnt value should be a double.", 
				baseInt - 1d, type.subtract(new DoubleType(1d)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void stringsCannotBeSubtractedFromIntegers() throws InvalidTypeException
	{
		type.subtract(new StringType("1"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansAndOtherTypesCannotBeSubtractedFromIntegers() throws InvalidTypeException
	{
		type.subtract(new BooleanType(true));
	}
	
	@Test
	public void integersCanBeMultipliedByIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together.",
				baseInt * testInt, type.multiply(new IntegerType(testInt)).getValue());
	}
	
	@Test
	public void integersCanBeMultipliedByDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together.",
				baseInt * 5d, type.multiply(new DoubleType(5d)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void integersCannotBeMultipliedByStrings() throws InvalidTypeException
	{
		type.multiply(new StringType("1"));
	}
	
	@Test
	public void integersCanBeDividedByIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands divided.",
				baseInt / testInt, type.divide(new IntegerType(testInt)).getValue());
	}
	
	@Test
	public void integersCanBeDividedByDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together.",
				baseInt / 5d, type.divide(new DoubleType(5d)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void integersCannotBeDividedByStrings() throws InvalidTypeException
	{
		type.divide(new StringType("1"));
	}
	
	@Test
	public void integersCanBeModdedByIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together",
				baseInt % testInt, type.mod(new IntegerType(testInt)).getValue());
	}
	
	@Test
	public void integersCanBeModdedByDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together",
				baseInt % 5d, type.mod(new DoubleType(5d)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void integersCannotBeModdedByStrings() throws InvalidTypeException
	{
		type.mod(new StringType("1"));
	}
	
	@Test
	public void integersCannotBeUnaryNegated() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together",
				-baseInt, type.unary().getValue());
	}
	
	@Test
	public void integersCanBeGreaterThanOtherStrings() throws InvalidTypeException
	{
		assertEquals("Comparison should allow another Integer.", 
				((Integer) baseInt).compareTo(testInt) > 0, type.gt(new IntegerType(testInt)).getValue());
	}
	
	@Test
	public void integersCanBeLessThanOtherStrings() throws InvalidTypeException
	{
		assertEquals("Comparison should allow another Integer.", 
				((Integer) baseInt).compareTo(testInt) < 0, type.lt(new IntegerType(testInt)).getValue());
	}
	
	@Test
	public void integersCanBeEqualToOtherStringOfSameValue() throws InvalidTypeException
	{
		assertTrue("", (Boolean) type.eq(new IntegerType(baseInt)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void integersCannotBeNegated() throws InvalidTypeException
	{
		type.negation();
	}
	
	@Test(expected=InvalidTypeException.class)
	public void integersCannotBeAnded() throws InvalidTypeException
	{
		type.and(new StringType("world"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void integersCannotBeOred() throws InvalidTypeException
	{
		type.or(new StringType("world"));
	}
}
