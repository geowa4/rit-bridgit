package edu.rit.se.bridgit.language.bridge;

public interface PseudoBridge
{
	public PseudoInstanceBridge buildInstanceBridge(String pseudoType);

	public String getUserInput(String prompt);
}
