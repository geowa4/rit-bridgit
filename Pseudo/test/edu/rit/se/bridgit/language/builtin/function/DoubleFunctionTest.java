package edu.rit.se.bridgit.language.builtin.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.ArgumentListEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.FunctionCallEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.StringEvaluator;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class DoubleFunctionTest
{
	private Scope scope;
	private FunctionCallEvaluator fnCallEval;
	
	@Before
	public void setUp()
	{
		scope = new Scope(null);
		fnCallEval = new FunctionCallEvaluator(DoubleFunction.functionName);
	}
	
	@Test
	public void stringsCanBeCastToDouble() throws InvalidTypeException, NameConflictException
	{
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new StringEvaluator("42"));
		fnCallEval.setArgumentsList(arguments);
		Type ret = fnCallEval.evaluate(scope);
		assertEquals("Double must be returned.", 42.0d, ret.getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void nonNumericStringsAreNotAllowed() throws InvalidTypeException, NameConflictException
	{
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new StringEvaluator("forty-two"));
		fnCallEval.setArgumentsList(arguments);
		fnCallEval.evaluate(scope);
		fail("\"forty-two\" is not allowed.");
	}
	
	@Test
	public void booleansCanBeCastToDouble() throws InvalidTypeException, NameConflictException
	{
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new BooleanEvaluator(true));
		fnCallEval.setArgumentsList(arguments);
		Type ret = fnCallEval.evaluate(scope);
		assertEquals("Double must be returned.", 1.0d, ret.getValue());
	}
	
	@Test
	public void intsCanBeCastToDouble() throws InvalidTypeException, NameConflictException
	{
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new IntegerEvaluator(1));
		fnCallEval.setArgumentsList(arguments);
		Type ret = fnCallEval.evaluate(scope);
		assertEquals("Double must be returned.", 1.0d, ret.getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void otherTypesAreDisallowed() throws InvalidTypeException, NameConflictException
	{
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new NullEvaluator());
		fnCallEval.setArgumentsList(arguments);
		fnCallEval.evaluate(scope);
		fail("\"Null\" is not allowed.");
	}
}