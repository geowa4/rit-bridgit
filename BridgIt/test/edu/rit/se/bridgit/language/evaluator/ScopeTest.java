package edu.rit.se.bridgit.language.evaluator;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

public class ScopeTest
{
	@Test
	public void constructorSetsNullParent()
	{
		Scope child = new Scope(null);
		assertEquals("Parent must be null.", null, child.getParent());
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
		
	}
}
