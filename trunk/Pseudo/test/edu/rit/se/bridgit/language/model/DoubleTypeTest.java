package edu.rit.se.bridgit.language.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;

public class DoubleTypeTest
{
	private static final double baseDouble = 7d;
	private static final double testDouble= 3d;
	private DoubleType type;
	
	@Before
	public void setUp() throws InvalidTypeException
	{
		type = new DoubleType(baseDouble);
	}
	
	@Test
	public void doublesCanBeAdded() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands added together.", 
				baseDouble + testDouble, type.add(new DoubleType(testDouble)).getValue());
	}
	
	@Test
	public void stringCanBeAddedToDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands concatenated.", 
				baseDouble + "1", type.add(new StringType("1")).getValue());
	}
	
	@Test
	public void integersCanBeAddedToDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value should be the two operands added together.", 
				baseDouble + 1, type.add(new IntegerType(1)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void booleansAndOtherTypesCannotBeAddedToDoubles() throws InvalidTypeException
	{
		type.add(new BooleanType(true));
	}
	
	@Test
	public void doublesCanBeSubtractedFromDoubes() throws InvalidTypeException
	{
		assertEquals("Resultatnt value should be a double.", 
				baseDouble - testDouble, type.subtract(new DoubleType(testDouble)).getValue());
	}
	
	@Test
	public void integersCanBeSubtractedFromDoubes() throws InvalidTypeException
	{
		assertEquals("Resultatnt value should be a double.", 
				baseDouble - 1, type.subtract(new IntegerType(1)).getValue());
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
	public void doublesCanBeMultipliedByIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together.",
				baseDouble * 5, type.multiply(new IntegerType(5)).getValue());
	}
	
	@Test
	public void doublesCanBeMultipliedByDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together.",
				baseDouble * testDouble, type.multiply(new DoubleType(testDouble)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doublesCannotBeMultipliedByStrings() throws InvalidTypeException
	{
		type.multiply(new StringType("1"));
	}
	
	@Test
	public void doublesCanBeDividedByIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands divided.",
				baseDouble / 5, type.divide(new IntegerType(5)).getValue());
	}
	
	@Test
	public void doublesCanBeDividedByDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together.",
				baseDouble / testDouble, type.divide(new DoubleType(testDouble)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doublesCannotBeDividedByStrings() throws InvalidTypeException
	{
		type.divide(new StringType("1"));
	}
	
	@Test
	public void doublesCanBeModdedByIntegers() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together",
				baseDouble % 5, type.mod(new IntegerType(5)).getValue());
	}
	
	@Test
	public void doublesCanBeModdedByDoubles() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together",
				baseDouble % testDouble, type.mod(new DoubleType(testDouble)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doublesCannotBeModdedByStrings() throws InvalidTypeException
	{
		type.mod(new StringType("1"));
	}
	
	@Test
	public void doublesCanBeUnaryNegated() throws InvalidTypeException
	{
		assertEquals("Resultant value must be the two operands multiplied together",
				-baseDouble, type.unary().getValue());
	}
	
	@Test
	public void doublesCanBeGreaterThanOtherDoubles() throws InvalidTypeException
	{
		assertEquals("Comparison should allow another Integer.", 
				((Double) baseDouble).compareTo(testDouble) > 0, type.gt(new DoubleType(testDouble)).getValue());
	}
	
	@Test
	public void doublesCanBeLessThanOtherDoubles() throws InvalidTypeException
	{
		assertEquals("Comparison should allow another Integer.", 
				((Double) baseDouble).compareTo(testDouble) < 0, type.lt(new DoubleType(testDouble)).getValue());
	}
	
	@Test
	public void doublesCanBeEqualToOtherDoubleOfSameValue() throws InvalidTypeException
	{
		assertTrue("Resultant value must be true.", (Boolean) type.eq(new DoubleType(baseDouble)).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doublesCannotBeNegated() throws InvalidTypeException
	{
		type.negation();
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doublesCannotBeAnded() throws InvalidTypeException
	{
		type.and(new DoubleType(testDouble));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void doublesCannotBeOred() throws InvalidTypeException
	{
		type.or(new DoubleType(testDouble));
	}
}
