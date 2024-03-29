package edu.rit.se.bridgit.language.evaluator;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.model.BooleanType;
import edu.rit.se.bridgit.language.model.DoubleType;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class ScopeTest
{
	@Test
	public void constructorSetsNullParent()
	{
		Scope child = new Scope(null);
		assertThat("Parent must be null.", child.getParent(), nullValue());
	}
	
	@Test
	public void constructorSetsNonNullParent()
	{
		Scope parent = new Scope(null);
		Scope child = new Scope(parent);
		assertEquals("Parent must be non-null.", 
				parent, child.getParent());
	}
	
	@Test
	public void addVariable() 
	throws PseudoException
	{
		Scope scope = new Scope(null);
		Type type = new IntegerType(7);
		scope.addVariable("test", type);
		assertEquals("The \"test\" variable should be set in the current scope.", 
				type, scope.getVariableValue("test"));
		assertEquals("The value of the type should be unchanged.",
				7, type.getValue());
	}
	
	@Test
	public void addConstant() throws PseudoException
	{
		Scope scope = new Scope(null);
		Type type = new IntegerType(7);
		scope.addConstant("test", type);
		assertEquals("The \"test\" constant should be set in the current scope.", 
				type, scope.getConstantValue("test"));
		assertEquals("The value of the type should be unchanged.",
				7, type.getValue());
	}
	
	@Test(expected=NameConflictException.class)
	public void createNameConflictWithVariable() 
	throws PseudoException
	{
		Scope scope = new Scope(null);
		Type type1 = new DoubleType(8d);
		Type type2 = new IntegerType(8);
		scope.addConstant("x", type1);
		scope.addVariable("x", type2);
		fail("Declaring the same variable twice must generate an Exception.");
	}
	
	@Test(expected=NameConflictException.class)
	public void createNameConflictWithConstant() 
	throws PseudoException
	{
		Scope scope = new Scope(null);
		Type type1 = new DoubleType(8d);
		Type type2 = new IntegerType(8);
		scope.addVariable("x", type1);
		scope.addConstant("x", type2);
		fail("Declaring the same variable twice must generate an Exception.");
	}
	
	@Test(expected=NameConflictException.class)
	public void createNameConflictWithVariableInParent() 
	throws PseudoException
	{
		Scope parent = new Scope(null);
		Scope scope = new Scope(parent);
		Type type1 = new DoubleType(8d);
		Type type2 = new IntegerType(8);
		parent.addConstant("x", type1);
		scope.addVariable("x", type2);
		fail("Declaring the same variable twice must generate an Exception.");
	}
	
	@Test(expected=NameConflictException.class)
	public void createNameConflictWithConstantInParent() 
	throws PseudoException
	{
		Scope parent = new Scope(null);
		Scope scope = new Scope(parent);
		Type type1 = new DoubleType(8d);
		Type type2 = new IntegerType(8);
		parent.addVariable("x", type1);
		scope.addConstant("x", type2);
		fail("Declaring the same variable twice must generate an Exception.");
	}
	
	@Test
	public void constantsAreNotVariables() 
	throws PseudoException
	{
		Scope scope = new Scope(null);
		Type type = new IntegerType(7);
		scope.addVariable("var", type);
		assertThat("\"var\" should not be accessible as a constant.", 
				scope.getConstantValue("var"), nullValue());
	}
	
	@Test
	public void variablesAreNotConstants() 
	throws PseudoException
	{
		Scope scope = new Scope(null);
		Type type = new IntegerType(7);
		scope.addConstant("const", type);
		assertThat("\"const\" should not be accessible as a constant.", 
				scope.getVariableValue("const"), nullValue());
	}
	
	@Test
	public void modifyVariableToSameTypeInScopeWithNoParent() 
	throws PseudoException
	{
		Scope scope = new Scope(null);
		Type original = new StringType("value");
		Type modified = new StringType("modified");
		scope.addVariable("test", original);
		scope.modifyVariableValue("test", modified);
		assertThat("Variable \"test\" must not equal its original value.",
				scope.getVariableValue("test"), not(equalTo(original)));
		assertEquals("Variable \"test\" must have the correct value.", 
				modified, scope.getVariableValue("test"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void modifyVariableToDifferentTypeInScopeWithNoParent() 
	throws PseudoException
	{
		Scope scope = new Scope(null);
		Type original = new StringType("value");
		Type modified = new BooleanType(true);
		scope.addVariable("test", original);
		scope.modifyVariableValue("test", modified);
		fail("Modifying a variable to a different type is not allowed.");
	}
	
	@Test
	public void modifyVariableToSameTypeInParentScope() 
	throws PseudoException
	{
		Scope parent = new Scope(null);
		Scope scope = new Scope(parent);
		Type original = new StringType("value");
		Type modified = new StringType("modified");
		parent.addVariable("test", original);
		scope.modifyVariableValue("test", modified);
		assertThat("Variable \"test\" (acessed from child) must not equal its original value.",
				scope.getVariableValue("test"), not(equalTo(original)));
		assertEquals("Variable \"test\" (acessed from child) must have the correct value.", 
				modified, scope.getVariableValue("test"));
		assertThat("Variable \"test\" (acessed from parent) must not equal its original value.",
				parent.getVariableValue("test"), not(equalTo(original)));
		assertEquals("Variable \"test\" (acessed from parent) must have the correct value.", 
				modified, parent.getVariableValue("test"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void modifyVariableToDifferentTypeInParentScope() 
	throws PseudoException
	{
		Scope parent = new Scope(null);
		Scope scope = new Scope(parent);
		Type original = new StringType("value");
		Type modified = new BooleanType(true);
		parent.addVariable("test", original);
		scope.modifyVariableValue("test", modified);
		fail("Modifying a variable to a different type is not allowed.");
	}
}
