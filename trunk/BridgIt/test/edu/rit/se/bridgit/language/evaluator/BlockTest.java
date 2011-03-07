package edu.rit.se.bridgit.language.evaluator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class BlockTest
{
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	@Mock private Block block1;
	@Mock private Block block2;
	@Mock private Block block3;
	
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
		context.checking(new Expectations() {{
			oneOf(block1).evaluate(scope);
		}});
		evaluator.add(block1);
		evaluator.evaluate(scope);
	}
	
	@Test
	public void executeNNestedEvaluators() 
	throws InvalidTypeException, NameConflictException 
	{
		context.checking(new Expectations() {{
			oneOf(block1).evaluate(scope);
			oneOf(block2).evaluate(scope);
			oneOf(block3).evaluate(scope);
		}});
		evaluator.add(block1);
		evaluator.add(block2);
		evaluator.add(block3);
		evaluator.evaluate(scope);
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
