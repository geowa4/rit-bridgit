package edu.rit.se.bridgit.language.builtin.function;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.ArgumentListEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.FunctionCallEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class StringFunctionTest
{
	private Scope scope;
	private FunctionCallEvaluator fnCallEval;
	
	@Before
	public void setUp()
	{
		scope = new Scope(null);
		fnCallEval = new FunctionCallEvaluator(StringFunction.functionName);
	}
	
	@Test
	public void alwaysReturnsString() throws PseudoException
	{
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new NullEvaluator());
		fnCallEval.setArgumentsList(arguments);
		Type str = fnCallEval.evaluate(scope);
		assertEquals("The return type must be \"String\" regardless of input", Type.STRING_TYPE, str.getPseudoType());
		assertEquals("The return value must be \"Null\"", Type.NULL_TYPE, str.getValue());
	}
}
