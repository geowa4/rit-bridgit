package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public abstract class Evaluator {
	public abstract Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException;
	
	protected abstract void validateType(Type t) throws InvalidTypeException;
}
