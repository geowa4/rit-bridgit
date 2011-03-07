package edu.rit.se.bridgit.language.evaluator.function;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ArgumentListTest
{
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	@Mock private Evaluator arg0;
	@Mock private Evaluator arg1;
	
	private ArgumentListEvaluator ale;
	private Scope scope;
	
	@Before
	public void createEvaluatoAndScope()
	{
		ale = new ArgumentListEvaluator();
		scope = new Scope(null);
	}
	
	@Test
	public void executesAllArguments() throws InvalidTypeException, NameConflictException
	{
		final Type t = new Type(1, "Integer");
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
