package edu.rit.se.bridgit.language.bridge;

import java.util.List;

import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.NoMethodFoundException;

public interface PseudoInstanceBridge
{
	public Object sendMessage(String methodName, List<Type> arguments, int pseudo_thread) throws NoMethodFoundException;

	public Object sendMessage(String methodName, List<Type> arguments)
			throws NoMethodFoundException;

	public List<String> getAvailableMethods();

	public String getPseudoType();

	public String getThumbnail();

	void setThumbnail(String thumbnail);
}
