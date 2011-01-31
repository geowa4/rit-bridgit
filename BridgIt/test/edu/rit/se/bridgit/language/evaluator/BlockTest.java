package edu.rit.se.bridgit.language.evaluator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class BlockTest
{
	private BlockEvaluator evaluator;
	private Scope scope;
	
	@Before
	public void createEvaluator()
	{
		evaluator = new BlockEvaluator(false);
		scope = new Scope(null);
	}
	
	@Test
	public void executeSingleNestedEvaluator() 
	throws InvalidTypeException, NameConflictException
	{
		MockBlockEvaluator mock = new MockBlockEvaluator();
		evaluator.add(mock);
		evaluator.evaluate(scope);
		assertEquals("The contained Evaluator must be evaluated exactly once.", 
				1, mock.getNumTimesEvaluated());
	}
	
	@Test
	public void executeNNestedEvaluators() 
	throws InvalidTypeException, NameConflictException 
	{
		MockBlockEvaluator mock1 = new MockBlockEvaluator();
		MockBlockEvaluator mock2 = new MockBlockEvaluator();
		MockBlockEvaluator mock3 = new MockBlockEvaluator();
		evaluator.add(mock1);
		evaluator.add(mock2);
		evaluator.add(mock3);
		evaluator.evaluate(scope);
		assertEquals("The contained Evaluators must be evaluated exactly once.", 
				1, mock1.getNumTimesEvaluated());
		assertEquals("The contained Evaluators must be evaluated exactly once.", 
				1, mock2.getNumTimesEvaluated());
		assertEquals("The contained Evaluators must be evaluated exactly once.", 
				1, mock3.getNumTimesEvaluated());
	}
	
	@Test
	public void scopeOfParentEvaluatorReusedInSetup() 
	throws InvalidTypeException, NameConflictException 
	{
		BlockEvaluator setup = new BlockEvaluator(false);
		setup.add(
				new ConstantEvaluator("a", "Integer", 
						new IntegerEvaluator(1)
				));
		BlockEvaluator main = new BlockEvaluator();
		main.add(
				new MemberLoadEvaluator("a")
				);
		evaluator.add(setup);
		evaluator.add(main);
		evaluator.evaluate(scope);
		assertEquals("Top-level scope contains \"a\" and it equals 1.",
				1, scope.getConstantValue("a").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void localScopeIsNotAccessibleFromOutside() 
	throws InvalidTypeException, NameConflictException 
	{
		BlockEvaluator setup = new BlockEvaluator();
		setup.add(
				new ConstantEvaluator("a", "Integer", 
						new IntegerEvaluator(1)
				));
		BlockEvaluator main = new BlockEvaluator();
		main.add(
				new MemberLoadEvaluator("a")
				);
		evaluator.add(setup);
		evaluator.add(main);
		evaluator.evaluate(scope);
		fail("What is defined in a nested block cannot be accessed outside of it.");
	}
}
