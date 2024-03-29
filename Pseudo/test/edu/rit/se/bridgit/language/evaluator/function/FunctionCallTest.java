package edu.rit.se.bridgit.language.evaluator.function;

import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Block;
import edu.rit.se.bridgit.language.evaluator.BlockEvaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.VariableEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.model.IntegerType;
import edu.rit.se.bridgit.language.model.NullType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class FunctionCallTest
{
	private static final String fnName = "test";
	private Scope definitionScope;
	private Scope callScope;
	private FunctionEvaluator fnEval;
	private Block fnBlock;
	private FunctionCallEvaluator fnCallEval;
	
	@Before
	public void setUp()
	{
		definitionScope = new Scope(null);
		callScope = new Scope(definitionScope);
		fnEval = new FunctionEvaluator(fnName);
		fnBlock = new BlockEvaluator(false);
		fnEval.setFunctionBlock(fnBlock);
		fnCallEval = new FunctionCallEvaluator(fnName);
	}
	
	@Test
	public void functionBlockCanAlterVariablesInDefinitionScope() throws PseudoException
	{
		definitionScope.addVariable("variable", new IntegerType(1));
		fnBlock.add(new VariableEvaluator("variable", new IntegerEvaluator(2)));
		fnEval.setPseudoType(Type.VOID_TYPE);
		fnEval.evaluate(definitionScope);
		fnCallEval.evaluate(callScope);
		assertEquals("\"variable\" must be changed to 2.", 2, definitionScope.getVariableValue("variable").getValue());
	}
	
	@Test(expected=NullPointerException.class)
	public void functionBlockCannotAlterVariablesInCallScope() throws PseudoException
	{
		callScope.addVariable("variable", new IntegerType(1));
		fnBlock.add(new VariableEvaluator("variable", new IntegerEvaluator(2)));
		fnEval.setPseudoType(Type.VOID_TYPE);
		fnEval.evaluate(definitionScope);
		fnCallEval.evaluate(callScope);
		fail("\"variable\" cannot be accesible.");
	}
	
	@Test(expected=InvalidTypeException.class)
	public void variableCannotBeAssignedToVoidReturnValue() throws PseudoException
	{
		definitionScope.addVariable("variable", new IntegerType(1));
		fnEval.setPseudoType(Type.VOID_TYPE);
		fnEval.evaluate(definitionScope);
		fnCallEval.evaluate(callScope);
		VariableEvaluator assigner = new VariableEvaluator("variable", fnCallEval);
		assigner.evaluate(callScope);
		fail("\"variable\" cannot be assigned to a void type.");
	}
	
	@Test
	public void variableCanBeAssignedMatchingFunctionReturnValue() throws PseudoException
	{
		definitionScope.addVariable("variable", new IntegerType(1));
		fnEval.setPseudoType("Integer");
		fnEval.addReturnValue(new IntegerEvaluator(2));
		fnEval.evaluate(definitionScope);
		fnCallEval.evaluate(callScope);
		VariableEvaluator assigner = new VariableEvaluator("variable", fnCallEval);
		assigner.evaluate(callScope);
		assertEquals("\"variable\" must be changed to 2.", 2, definitionScope.getVariableValue("variable").getValue());
	}
	
	@Test(expected=InvalidTypeException.class)
	public void variableCannotBeAssignedMismatchedFunctionReturnValue() throws PseudoException
	{
		definitionScope.addVariable("variable", new IntegerType(1));
		fnEval.setPseudoType(Type.BOOLEAN_TYPE);
		fnEval.addReturnValue(new BooleanEvaluator(true));
		fnEval.evaluate(definitionScope);
		fnCallEval.evaluate(callScope);
		VariableEvaluator assigner = new VariableEvaluator("variable", fnCallEval);
		assigner.evaluate(callScope);
		fail("\"variable\" cannot be assigned to a Boolean type.");
	}
	
	@Test
	public void parametersAreNotVisibleOutsideOfFunction() throws PseudoException
	{
		fnEval.setPseudoType(Type.VOID_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator("arg0", Type.INTEGER_TYPE));
		fnEval.setParameters(params);
		fnEval.evaluate(definitionScope);
		ArgumentListEvaluator arguments = new ArgumentListEvaluator();
		arguments.addArg(new IntegerEvaluator(1));
		fnCallEval.setArgumentsList(arguments);
		fnCallEval.evaluate(callScope);
		assertThat("\"arg0\" must not be accessible.", callScope.getVariableValue("arg0"), nullValue());
	}
	
	@Test
	public void functionCanReturnNull() throws PseudoException
	{
		fnEval.setPseudoType(Type.INTEGER_TYPE);
		fnEval.addReturnValue(new NullEvaluator());
		fnEval.evaluate(definitionScope);
		Type t = fnCallEval.evaluate(callScope);
		assertThat("Value must be Null.", t.getValue(), sameInstance(NullType.NULL_VALUE));
		assertEquals("Pseudo Type must be the same as the function.", Type.INTEGER_TYPE, t.getPseudoType());
	}
}
