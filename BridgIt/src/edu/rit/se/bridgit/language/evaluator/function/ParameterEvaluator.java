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
	private String type;
	
	public ParameterEvaluator(String name, String pseudoType)
	{
		this.name = name;
		this.type = pseudoType;
	}
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		Type eval = new Type(null, type);
		/*
		if(eval.getPseudoType().equalsIgnoreCase("Integer"))
			eval =  new Type(new IntegerEvaluator(0), type);
		else if(eval.getPseudoType().equalsIgnoreCase("String"))
			eval = new Type(new StringEvaluator(""), type);
		else if(eval.getPseudoType().equalsIgnoreCase("Boolean"))
			eval = new Type(new BooleanEvaluator(false), type);
		else if(eval.getPseudoType().equalsIgnoreCase("Double"))
			eval = new Type(new DoubleEvaluator(0.0), type);
		else 
			eval = new Type(new NullEvaluator(), type);
			*/
		scope.addParameter(name, eval);		
		return eval;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {
		if(!t.getType().equals(Boolean.class))
		{
			throw new InvalidTypeException(t.getType(), "Parameter");
		}
	}

}
