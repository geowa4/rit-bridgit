package edu.rit.se.bridgit.language.evaluator.conditional;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.BlockEvaluator;
import edu.rit.se.bridgit.language.evaluator.MemberLoadEvaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.VariableEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class WhileTest
{
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	@Mock private Block block;
	
	private Scope scope;
	
	@Before
	public void createScope()
	{
		scope = new Scope(null);
	}
	
	@Test
	public void neverExecutesWhenConditionalIsFalse() 
	throws PseudoException
	{
		context.checking(new Expectations() {{
			never(block).evaluate(scope);
		}});
		WhileEvaluator evaluator = new WhileEvaluator();
		BooleanEvaluator conditional = new BooleanEvaluator(false);
		evaluator.setConditional(conditional, block);
		evaluator.evaluate(scope);
	}
	
	@Test
	public void blockCanChangeTheConditional() 
	throws PseudoException
	{
		WhileEvaluator evaluator = new WhileEvaluator();
		VariableEvaluator conditionalValue = new VariableEvaluator(
				"conditional", Type.BOOLEAN_TYPE, new BooleanEvaluator(true));
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
