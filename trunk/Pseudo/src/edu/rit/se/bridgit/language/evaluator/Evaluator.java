package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public interface Evaluator 
{
	public Type evaluate(Scope scope) throws PseudoException;
	
	public void validateType(Type t) throws PseudoException;
}
