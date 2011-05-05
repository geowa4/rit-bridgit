package edu.rit.se.bridgit.language.builtin.function;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.evaluator.function.ParameterEvaluator;
import edu.rit.se.bridgit.language.evaluator.function.ParameterList;
import edu.rit.se.bridgit.language.evaluator.function.ParameterListEvaluator;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class InputFunction extends Function
{
	public static final String functionName = "input";
	private static final String parameterName = "prompt";

	public InputFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.STRING_TYPE);
		ParameterList params = new ParameterListEvaluator();
		params.addParam(new ParameterEvaluator(parameterName, Type.ANY_TYPE));
		setParameters(params);
		setReturnValue(new InputReturn());
	}
	
	private class InputReturn implements Evaluator
	{
		
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException
		{
			IWorkbenchWindow workbench = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			InputDialog dialog = new InputDialog(workbench.getShell(), "The application requests your input", 
					scope.getVariableValue(parameterName).getValue().toString(), "", null);
			dialog.open();
			String value = dialog.getValue() == null ? "" : dialog.getValue();
			return new StringType(value);
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}
	}
}
