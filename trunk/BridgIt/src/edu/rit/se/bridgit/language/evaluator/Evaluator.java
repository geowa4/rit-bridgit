package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public interface Evaluator {
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException;
	
	public void validateType(Type t) throws InvalidTypeException;
}
