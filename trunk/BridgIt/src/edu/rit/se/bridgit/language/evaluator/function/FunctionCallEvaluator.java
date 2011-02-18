package edu.rit.se.bridgit.language.evaluator.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class FunctionCallEvaluator extends Evaluator {

	private String name;
	private Evaluator arguments;

	public FunctionCallEvaluator(String name, Evaluator arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		
		if (scope.isFunction(name) && arguments != null) {

			// Get the parameters, compare with arguments and then try to set
			// the value
			// evaluate the function set the return value and give that back
			// after evaluation
			List<?> para = scope.getFunction(name).getParameters();
			List<?> arg = (List<?>) arguments.evaluate(scope).getValue();
			GroupEvaluator group = new GroupEvaluator();
			if (para.size() == arg.size()) {
				for (int i = 0; i < arg.size(); i++) {
					scope.removeParameter(((ParameterEvaluator) para.get(i)).getName());
					group.addItem(new ParameterEvaluator(
							((ParameterEvaluator) para.get(i)).getName(),
							((Evaluator) arg.get(i)),
							((ParameterEvaluator) para.get(i)).getType()));
				}
			} else {
				throw new InvalidTypeException(FunctionCallEvaluator.class,
						"FunctionCall");
			}
			scope.getFunction(name).setParameters((List<?>)group.evaluate(scope).getValue());
			
			//Priya, the BlockEvaluator already has a list of internal evaluators. Just use that.
			scope.getFunction(name).getFunctionBlock().evaluate(scope);
			
			//Priya, the following threw a NullPointerException
			Type eval = null;// = scope.getFunction(name).getReturnValue().evaluate(scope.getFunction(name).getFunctionScope());
			
			if (eval != null) {
				return eval;
			}
		
		} else {
			throw new NameConflictException(name, "has not been defined");
		}
		return null;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {

	}

}
