package edu.rit.se.bridgit.language.evaluator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

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
	
	@Test
	public void retrieveNonExistentMember() throws InvalidTypeException
	{
		MemberLoadEvaluator evaluator = new MemberLoadEvaluator("z");
		Type val = evaluator.evaluate(scope);
		assertThat("Since the member does not exist, null must be returned.",
				val, nullValue());
	}
}
