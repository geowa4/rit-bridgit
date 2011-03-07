package edu.rit.se.bridgit.language.evaluator.conditional;

import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class IfTest
{
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	@Mock private Block block;
	@Mock private Block ifBlock;
	@Mock private Block elseIfBlock;
	@Mock private Block elseBlock;
	
	private IfEvaluator evaluator;
	private Scope scope;
	
	@Before
	public void createEvaluator()
	{
		evaluator = new IfEvaluator();
		scope = new Scope(null);
	}
	
	@Test
	public void singleIfWithTrueConditional() 
	throws InvalidTypeException, NameConflictException
	{
		context.checking(new Expectations() {{
			oneOf(block).evaluate(scope);
		}});
		evaluator.addConditional(new BooleanEvaluator(true), block);
		evaluator.evaluate(scope);
	}
	
	@Test
	public void singleIfWithFalseConditional() 
	throws InvalidTypeException, NameConflictException
	{
		context.checking(new Expectations() {{
			never(block).evaluate(scope);
		}});
		evaluator.addConditional(new BooleanEvaluator(false), block);
		evaluator.evaluate(scope);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void singleIfWithInvalidConditional() 
	throws InvalidTypeException, NameConflictException
	{
		context.checking(new Expectations() {{
			never(block).evaluate(scope);
		}});
		evaluator.addConditional(new IntegerEvaluator(0), block);
		evaluator.evaluate(scope);
		fail("Integer is not valid for a conditional.");
	}
	
	@Test
	public void ifElseWhereIfIsTrue() 
	throws InvalidTypeException, NameConflictException
	{
		context.checking(new Expectations() {{
			oneOf(ifBlock).evaluate(scope);
			never(elseBlock).evaluate(scope);
		}});
		evaluator.addConditional(new BooleanEvaluator(true), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(true), elseBlock);
		evaluator.evaluate(scope);
	}
	
	@Test
	public void ifElseWhereIfIsFalse() 
	throws InvalidTypeException, NameConflictException
	{
		context.checking(new Expectations() {{
			never(ifBlock).evaluate(scope);
			oneOf(elseBlock).evaluate(scope);
		}});
		evaluator.addConditional(new BooleanEvaluator(false), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(true), elseBlock);
		evaluator.evaluate(scope);
	}
	
	@Test
	public void ifElseIfWhereBothAreFalse() 
	throws InvalidTypeException, NameConflictException
	{
		context.checking(new Expectations() {{
			never(ifBlock).evaluate(scope);
			never(elseIfBlock).evaluate(scope);
		}});
		evaluator.addConditional(new BooleanEvaluator(false), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(false), elseIfBlock);
		evaluator.evaluate(scope);
	}
	
	@Test
	public void ifElseIfElseWhereAllButLastAreFalse() 
	throws InvalidTypeException, NameConflictException
	{
		context.checking(new Expectations() {{
			never(ifBlock).evaluate(scope);
			never(elseIfBlock).evaluate(scope);
			oneOf(elseBlock).evaluate(scope);
		}});
		evaluator.addConditional(new BooleanEvaluator(false), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(false), elseIfBlock);
		evaluator.addConditional(new BooleanEvaluator(true), elseBlock);
		evaluator.evaluate(scope);
	}
}
