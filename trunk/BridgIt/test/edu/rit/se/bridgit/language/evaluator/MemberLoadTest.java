package edu.rit.se.bridgit.language.evaluator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class MemberLoadTest
{
	private Scope scope;
	private Type varType;
	private Type constType;
	
	@Before
	public void createScope() throws InvalidTypeException, NameConflictException
	{
		this.scope = new Scope(null);
		this.varType = new Type(7, "Integer");
		this.constType = new Type(true, "Boolean");
		this.scope.addVariable("x", this.varType);
		this.scope.addConstant("y", this.constType);
	}
	
	@Test
	public void retrieveExistingVariable() throws InvalidTypeException
	{
		MemberLoadEvaluator evaluator = new MemberLoadEvaluator("x");
		Type val = evaluator.evaluate(scope);
		assertEquals("The value 7 must be loaded.",
				varType.getValue(), val.getValue());
	}
	
	@Test
	public void retrieveExistingConstant() throws InvalidTypeException
	{
		MemberLoadEvaluator evaluator = new MemberLoadEvaluator("y");
		Type val = evaluator.evaluate(scope);
		assertEquals("The value true must be loaded.",
				constType.getValue(), val.getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void retrieveNonExistentMember() 
	throws InvalidTypeException
	{
		MemberLoadEvaluator evaluator = new MemberLoadEvaluator("z");
		evaluator.evaluate(scope);
		fail("Since the member does not exist, an exception must be thrown.");
	}
}