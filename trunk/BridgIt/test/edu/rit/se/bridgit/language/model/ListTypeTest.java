package edu.rit.se.bridgit.language.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ListTypeTest
{
	private ListType type;
	
	@Before
	public void setUp() throws InvalidTypeException
	{
		type = new ListType(new ArrayList<Type>());
	}
	
	@Test
	public void allTypesCanBeAdded() throws InvalidTypeException
	{
		type = (ListType) type
			.add(new StringType("1"))
			.add(new IntegerType(2))
			.add(new DoubleType(3d))
			.add(new BooleanType(true));
		assertEquals("Resultant value must have four elements.", 4, ((List<?>) type.getValue()).size());
	}
	
	@Test
	public void allTypesCanBeRemoved() throws InvalidTypeException
	{
		type= (ListType) type
			.add(new StringType("1"))
			.add(new IntegerType(2))
			.add(new DoubleType(3d))
			.add(new BooleanType(true))
			.subtract(new BooleanType(true));
		assertEquals("Resultant value must have three elements.", 3, ((List<?>) type.getValue()).size());
	}
	
	@Test
	public void listsCanBeRemovedFromOtherLists() throws InvalidTypeException
	{
		type= (ListType) type
			.add(new StringType("1"))
			.add(new IntegerType(2))
			.add(new DoubleType(3d))
			.add(new BooleanType(true))
			.subtract(new ListType(new ArrayList<Type>() {
				private static final long serialVersionUID = -8836246130098780274L;
			{
				add(new BooleanType(true));
				add(new DoubleType(3d));
			}}));
		assertEquals("Resultant value must have three elements.", 2, ((List<?>) type.getValue()).size());
	}
	
	@Test
	public void listsCanBeMultipliedByIntegers() throws InvalidTypeException
	{
		type = (ListType) type.add(new StringType("1"));
		type = (ListType) type.multiply(new IntegerType(3));
		assertEquals("Resultant value must have three elements.", 3, ((List<?>) type.getValue()).size());
	}
	
	@Test
	public void listsCanBeCheckedForEquality() throws InvalidTypeException
	{
		type = (ListType) type.add(new StringType("1"));
		Type other = new ListType(new ArrayList<Type>() {
			private static final long serialVersionUID = -6766391370251778184L;
		{
			add(new StringType("1"));
		}});
		assertTrue("Both types must be equal.", (Boolean) type.eq(other).getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeMultipliedByNonIntegerTypes() throws InvalidTypeException
	{
		type = (ListType) type.add(new StringType("1"));
		type = (ListType) type.multiply(new DoubleType(3d));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeModded() throws InvalidTypeException
	{
		type.mod(new IntegerType(3));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeUsedInAUnaryOperation() throws InvalidTypeException
	{
		type.unary();
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeUsedInAndOperation() throws InvalidTypeException
	{
		type.and(new ListType(new ArrayList<Type>()));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeUsedInOrOperation() throws InvalidTypeException
	{
		type.or(new ListType(new ArrayList<Type>()));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeUsedInGtOperation() throws InvalidTypeException
	{
		type.gt(new ListType(new ArrayList<Type>()));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeUsedInLtOperation() throws InvalidTypeException
	{
		type.lt(new ListType(new ArrayList<Type>()));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void listsCannotBeUsedInNegationOperation() throws InvalidTypeException
	{
		type.negation();
	}
}