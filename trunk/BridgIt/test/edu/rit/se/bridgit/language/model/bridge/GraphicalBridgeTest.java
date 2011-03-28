package edu.rit.se.bridgit.language.model.bridge;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import org.junit.Before;

public class GraphicalBridgeTest
{
	Queue<?> actionQueue;
	GraphicalBridge ballerina;
	
	@Before
	public void createBridges()
	{
		actionQueue = new LinkedList<Object>();
		List<String> methods = new Vector<String>() 
		{
			private static final long serialVersionUID = 6401829779778644565L;
		{
			add("jump");
			add("spin");
		}};
		ballerina = new GraphicalBridge("Ballerina", methods, actionQueue, null);
	}
	
//	@Test
//	public void copyConstructorReferencesSameObjects()
//	{
//		GraphicalBridge temp = new GraphicalBridge(ballerina);
//		assertSame("Action queues are the same.", ballerina.actionQueue, temp.actionQueue);
//		assertSame("The list of available methods are the same.", ballerina.availableMethods, temp.availableMethods);
//		assertSame("The Pseudo-types are the same.", ballerina.pseudoType, temp.pseudoType);
//	}
//	
//	@Test
//	public void callingMethodAddsToActionQueue() throws NoMethodFoundException
//	{
//		ballerina.sendMessage("jump");
//		assertEquals("There must be one item in the queue after sending it a message.", 1, actionQueue.size());
//	}
//	
//	@Test
//	public void callingExistentMethodsDoesNotReturnNull() throws NoMethodFoundException
//	{
//		assertThat("Calling \"jump\" on the Ballerina must not return null.", 
//				ballerina.sendMessage("jump"), notNullValue());
//	}
//	
//	@Test(expected=NoMethodFoundException.class)
//	public void callingNonexistentMethodIsAnError() throws NoMethodFoundException
//	{
//		ballerina.sendMessage("panic");
//	}
}
