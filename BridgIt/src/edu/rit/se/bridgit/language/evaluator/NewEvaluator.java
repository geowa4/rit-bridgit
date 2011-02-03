package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.bridge.GraphicalModelBridgeFactory;

public class NewEvaluator extends Evaluator
{
	private String pseudoType;

	public NewEvaluator(String pseudoType)
	{
		this.pseudoType = pseudoType;
	}

	@Override
	public Type evaluate(Scope scope) 
	throws InvalidTypeException, NameConflictException
	{
		return new Type(GraphicalModelBridgeFactory.buildBridge(pseudoType), pseudoType);
	}

	@Override
	protected void validateType(Type t) throws InvalidTypeException
	{/* no-op */}

}
