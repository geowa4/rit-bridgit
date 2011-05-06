package edu.rit.se.bridgit.language.evaluator.conditional;

import edu.rit.se.bridgit.language.evaluator.Evaluator;

public class ConditionalBlockPair
{
	private Evaluator block;
	private Evaluator conditional;

	public Evaluator getConditional() 
	{
		return conditional;
	}
	public void setConditional(Evaluator conditional) 
	{
		this.conditional = conditional;
	}
	public Evaluator getBlock() 
	{
		return block;
	}
	public void setBlock(Evaluator block) 
	{
		this.block = block;
	}
}
