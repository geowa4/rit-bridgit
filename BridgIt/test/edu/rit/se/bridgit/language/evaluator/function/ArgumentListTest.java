package edu.rit.se.bridgit.language.evaluator.function;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.rit.se.bridgit.language.builtin.function.TypeOfFunction;
import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.BlockEvaluator;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.conditional.WhileEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.StringEvaluator;
import edu.rit.se.bridgit.language.model.BooleanType;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ArgumentListTest
{
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	final States execCount = context.states("execCount").startsAs("1");
	@Mock private Evaluator arg0;
	@Mock private Evaluator arg1;
	@Mock private Evaluator whileCond;
	
	private ArgumentListEvaluator ale;
	private WhileEvaluator whileEval;
	private Block whileBlock;
	private Scope scope;
	
	@Before
	public void setUp()
	{
		scope = new Scope(null);
	}
	
	@Before
	public void createEvaluatoAndScope()
	{
		ale = new ArgumentListEvaluator();
		scope = new Scope(null);
	}
	
	/**
	 * This addresses an issue where the arguments list was not cleared for each use,
	 * making the number of arguments duplicate each time the function was called in a loop. 
	 * Obviously that would not match the parameters the second time around.
	 */
	@Test
	public void argumentsAreConsistentInLoop() throws InvalidTypeException, NameConflictException
	{
		whileEval = new WhileEvaluator();
		whileBlock = new BlockEvaluator() {{
			add(new FunctionCallEvaluator(TypeOfFunction.functionName) {{
				setArgumentsList(new ArgumentListEvaluator() {{
					addArg(new StringEvaluator("hi"));
				}});
			}});
		}};
		whileEval.setConditional(whileCond, whileBlock);
		context.checking(new Expectations() {{
			oneOf(whileCond).evaluate(with(any(Scope.class))); 
				will(returnValue(new BooleanType(true)));
				when(execCount.is("1"));
				then(execCount.is("2"));
			oneOf(whileCond).evaluate(with(any(Scope.class))); 
				will(returnValue(new BooleanType(true)));
				when(execCount.is("2"));
				then(execCount.is("3"));
			oneOf(whileCond).evaluate(with(any(Scope.class))); 
				will(returnValue(new BooleanType(false)));
				when(execCount.is("3"));
		}});
		whileEval.evaluate(scope);
	}
	
	@Test
	public void executesAllArguments() throws InvalidTypeException, NameConflictException
	{
		final Type t = new IntegerType(1);
		context.checking(new Expectations() {{
			oneOf(arg0).evaluate(scope); will(returnValue(t));
			oneOf(arg1).evaluate(scope); will(returnValue(t));
		}});
		ale.addArg(arg0);
		ale.addArg(arg1);
		ale.evaluate(scope);
		assertEquals("There must be two items in the args list.", 2, ale.getArgValues().size());
	}
}
