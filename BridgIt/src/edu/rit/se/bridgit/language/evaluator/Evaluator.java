package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;

public interface Evaluator {
	public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException;
	
	public void validateType(Type t) throws InvalidTypeException;
}
