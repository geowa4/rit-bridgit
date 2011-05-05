package edu.rit.se.bridgit.language.evaluator.function;

import java.util.List;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.model.Type;

public interface ParameterList extends Evaluator
{
	public void addParam(ParameterEvaluator param);

	public void setArgs(List<Type> args);
}
