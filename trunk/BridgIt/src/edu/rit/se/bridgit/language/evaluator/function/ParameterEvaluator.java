package edu.rit.se.bridgit.language.evaluator.function;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.VariableEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.BooleanEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.DoubleEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.IntegerEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.NullEvaluator;
import edu.rit.se.bridgit.language.evaluator.term.StringEvaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class ParameterEvaluator extends Evaluator{

	
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Evaluator value;
	public Evaluator getValue() {
		return value;
	}

	public void setValue(Evaluator value) {
		this.value = value;
	}

	private boolean isArgument;
	
	public ParameterEvaluator(String name, String pseudoType)
	{
		this.setName(name);
		this.setType(pseudoType);
		this.isArgument = false;
	}
	
	public ParameterEvaluator(String name, Evaluator value, String pseudoType)
	{
		this.setName(name);
		this.setType(pseudoType);
		this.setValue(value);
		this.isArgument = true;
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {

		Type eval = new Type(null, type);

		if (value != null) {
			eval = value.evaluate(scope);
		} else if (!isArgument) {

			if (this.getType().equalsIgnoreCase("Integer"))
				setValue(new IntegerEvaluator(0));
			else if (this.getType().equalsIgnoreCase("String"))
				setValue(new StringEvaluator(""));
			else if (this.getType().equalsIgnoreCase("Boolean"))
				setValue(new BooleanEvaluator(false));
			else if (this.getType().equalsIgnoreCase("Double"))
				setValue(new BooleanEvaluator(false));

			eval = this.getValue().evaluate(scope);
		}
		scope.addParameter(name, eval);
		return eval;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		
	}

}
