package edu.rit.se.bridgit.language.evaluator;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.Type;

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
	{
		Scope scope = new Scope(null);
		Type type = new Type(7);
		scope.addVariable("test", type);
		assertEquals("The \"test\" variable should be set in the current scope.", 
				type, scope.getVariableValue("test"));
		assertEquals("The value of the type should be unchanged.",
				7, type.getValue());
	}
	
	@Test
	public void addConstant()
	{
		Scope scope = new Scope(null);
		Type type = new Type(7);
		scope.addConstant("test", type);
		assertEquals("The \"test\" constant should be set in the current scope.", 
				type, scope.getConstantValue("test"));
		assertEquals("The value of the type should be unchanged.",
				7, type.getValue());
	}
	
	@Test
	public void constantsAreNotVariables()
	{
		Scope scope = new Scope(null);
		Type type = new Type(7);
		scope.addVariable("var", type);
		assertThat("\"var\" should not be accessible as a constant.", 
				scope.getConstantValue("var"), nullValue());
	}
	
	@Test
	public void variablesAreNotConstants()
	{
		Scope scope = new Scope(null);
		Type type = new Type(7);
		scope.addConstant("const", type);
		assertThat("\"const\" should not be accessible as a constant.", 
				scope.getVariableValue("const"), nullValue());
	}
	
	@Test
	public void modifyVariableToSameTypeInScopeWithNoParent() 
	throws InvalidTypeException
	{
		Scope scope = new Scope(null);
		Type original = new Type("value");
		Type modified = new Type("modified");
		original.setPseudoType("String");
		modified.setPseudoType("String");
		scope.addVariable("test", original);
		scope.modifyVariableValue("test", modified);
		assertThat("Variable \"test\" must not equal its original value.",
				scope.getVariableValue("test"), not(equalTo(original)));
		assertEquals("Variable \"test\" must have the correct value.", 
				modified, scope.getVariableValue("test"));
	}
	
	@Test(expected=InvalidTypeException.class)
	public void modifyVariableToDifferentTypeInScopeWithNoParent() 
	throws InvalidTypeException
	{
		Scope scope = new Scope(null);
		Type original = new Type("value");
		Type modified = new Type(true);
		original.setPseudoType("String");
		original.setPseudoType("Boolean");
		scope.addVariable("test", original);
		scope.modifyVariableValue("test", modified);
		fail("Modifying a variable to a different type is not allowed.");
	}
	
	@Test
	public void modifyVariableToSameTypeInParentScope() 
	throws InvalidTypeException
	{
		Scope parent = new Scope(null);
		Scope scope = new Scope(parent);
		Type original = new Type("value");
		Type modified = new Type("modified");
		original.setPseudoType("String");
		modified.setPseudoType("String");
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
	throws InvalidTypeException
	{
		Scope parent = new Scope(null);
		Scope scope = new Scope(parent);
		Type original = new Type("value");
		Type modified = new Type(true);
		original.setPseudoType("String");
		original.setPseudoType("Boolean");
		parent.addVariable("test", original);
		scope.modifyVariableValue("test", modified);
		fail("Modifying a variable to a different type is not allowed.");
	}
}
