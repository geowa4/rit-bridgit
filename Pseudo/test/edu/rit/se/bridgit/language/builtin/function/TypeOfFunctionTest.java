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

public class TypeOfFunctionTest
{
	private Scope scope;
	private FunctionCallEvaluator fnCallEval;
	
	@Before
	public void setUp()
	{
		scope = new Scope(null);
		fnCallEval = new FunctionCallEvaluator(TypeOfFunction.functionName);
	}
	
	@Test
	public void nullValueReturnsNullType() throws PseudoException
	{
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new NullEvaluator());
		fnCallEval.setArgumentsList(arguments);
		Type ret = fnCallEval.evaluate(scope);
		assertEquals("The return type must be \"String\" regardless of input", Type.STRING_TYPE, ret.getPseudoType());
		assertEquals("The return value must be \"Null\"", "Null", ret.getValue());
	}
}
