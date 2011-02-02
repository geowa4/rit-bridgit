package edu.rit.se.bridgit.language.evaluator.conditional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.MockBlockEvaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class IfTest
{
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
		MockBlockEvaluator block = new MockBlockEvaluator();
		evaluator.addConditional(new BooleanEvaluator(true), block);
		evaluator.evaluate(scope);
		assertEquals(1, block.getNumTimesEvaluated());
	}
	
	@Test
	public void singleIfWithFalseConditional() 
	throws InvalidTypeException, NameConflictException
	{
		MockBlockEvaluator block = new MockBlockEvaluator();
		evaluator.addConditional(new BooleanEvaluator(false), block);
		evaluator.evaluate(scope);
		assertEquals(0, block.getNumTimesEvaluated());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void singleIfWithInvalidConditional() 
	throws InvalidTypeException, NameConflictException
	{
		MockBlockEvaluator block = new MockBlockEvaluator();
		evaluator.addConditional(new IntegerEvaluator(0), block);
		evaluator.evaluate(scope);
		fail("Integer is not valid for a conditional.");
	}
	
	@Test
	public void ifElseWhereIfIsTrue() 
	throws InvalidTypeException, NameConflictException
	{
		MockBlockEvaluator ifBlock = new MockBlockEvaluator();
		MockBlockEvaluator elseBlock = new MockBlockEvaluator();
		evaluator.addConditional(new BooleanEvaluator(true), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(true), elseBlock);
		evaluator.evaluate(scope);
		assertEquals(1, ifBlock.getNumTimesEvaluated());
		assertEquals(0, elseBlock.getNumTimesEvaluated());
	}
	
	@Test
	public void ifElseWhereIfIsFalse() 
	throws InvalidTypeException, NameConflictException
	{
		MockBlockEvaluator ifBlock = new MockBlockEvaluator();
		MockBlockEvaluator elseBlock = new MockBlockEvaluator();
		evaluator.addConditional(new BooleanEvaluator(false), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(true), elseBlock);
		evaluator.evaluate(scope);
		assertEquals(0, ifBlock.getNumTimesEvaluated());
		assertEquals(1, elseBlock.getNumTimesEvaluated());
	}
	
	@Test
	public void ifElseIfWhereBothAreFalse() 
	throws InvalidTypeException, NameConflictException
	{
		MockBlockEvaluator ifBlock = new MockBlockEvaluator();
		MockBlockEvaluator elseIfBlock = new MockBlockEvaluator();
		evaluator.addConditional(new BooleanEvaluator(false), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(false), elseIfBlock);
		evaluator.evaluate(scope);
		assertEquals(0, ifBlock.getNumTimesEvaluated());
		assertEquals(0, elseIfBlock.getNumTimesEvaluated());
	}
	
	@Test
	public void ifElseIfElseWhereAllButLastAreFalse() 
	throws InvalidTypeException, NameConflictException
	{
		MockBlockEvaluator ifBlock = new MockBlockEvaluator();
		MockBlockEvaluator elseIfBlock = new MockBlockEvaluator();
		MockBlockEvaluator elseBlock = new MockBlockEvaluator();
		evaluator.addConditional(new BooleanEvaluator(false), ifBlock);
		evaluator.addConditional(new BooleanEvaluator(false), elseIfBlock);
		evaluator.addConditional(new BooleanEvaluator(true), elseBlock);
		evaluator.evaluate(scope);
		assertEquals(0, ifBlock.getNumTimesEvaluated());
		assertEquals(0, elseIfBlock.getNumTimesEvaluated());
		assertEquals(1, elseBlock.getNumTimesEvaluated());
	}
}
