package edu.rit.se.bridgit.language.evaluator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class WhileTest
{
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void neverExecutesWhenConditionalIsFalse() 
	throws InvalidTypeException, NameConflictException
	{
		WhileEvaluator evaluator = new WhileEvaluator();
		BooleanEvaluator conditional = new BooleanEvaluator(false);
		MockBlockEvaluator block = new MockBlockEvaluator();
		evaluator.setConditional(conditional, block);
		evaluator.evaluate(scope);
		assertEquals("The block of a while loop with a false conditional must never execute.", 
				0, block.getNumTimesEvaluated());
	}
	
	@Test
	public void blockCanChangeTheConditional() 
	throws InvalidTypeException, NameConflictException
	{
		WhileEvaluator evaluator = new WhileEvaluator();
		VariableEvaluator conditionalValue = new VariableEvaluator(
				"conditional", "Boolean", new BooleanEvaluator(true));
		conditionalValue.evaluate(scope);
		MemberLoadEvaluator conditional = new MemberLoadEvaluator("conditional");
		BlockEvaluator block = new BlockEvaluator() {{
			add(new VariableEvaluator(
				"conditional", new BooleanEvaluator(false)));
		}};
		evaluator.setConditional(conditional, block);
		evaluator.evaluate(scope);
	}
}
