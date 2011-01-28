package edu.rit.se.bridgit.language.evaluator;

import edu.rit.se.bridgit.language.model.Type;

public class MockBlockEvaluator extends BlockEvaluator
{
	private int numTimesEvaluated = 0;
	
	public int getNumTimesEvaluated()
	{
		return numTimesEvaluated;
	}
	
	@Override
	public Type evaluate(Scope scope)
	{
		++numTimesEvaluated;
		return null;
	}
}
