package edu.rit.se.bridgit.language.evaluator.function;

import java.util.LinkedList;
import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public class GroupEvaluator extends Evaluator{

	private List<Evaluator> itemList = new LinkedList<Evaluator>();
	
	public boolean addItem(Evaluator expression){
		return itemList.add(expression);
	}
	
	@Override
	public Type evaluate(Scope scope) throws InvalidTypeException,
			NameConflictException {
		for(Evaluator e: itemList){
			Type eval = e.evaluate(scope);
			validateType(eval);
			if(eval.getValue() != null){
				return eval;
			}
		}
		return null;
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException {}

}