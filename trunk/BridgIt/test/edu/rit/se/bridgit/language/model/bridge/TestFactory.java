package edu.rit.se.bridgit.language.model.bridge;


import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.*;


public class TestFactory {
	
	@Before
	public void addClasses()
	{
		Vector<String> testMethods = new Vector<String>();
		testMethods.add("jump");
		testMethods.add("fly");
		
		GraphicalBridge testBridge = new GraphicalBridge("Ballerina", testMethods);
		GraphicalModelBridgeFactory.getInstance().addPossibleClass(testBridge);
	}
	
	@Test
	public void acquireGraphicalBridgeInstance()
	{
		GraphicalBridge ballerina = GraphicalModelBridgeFactory.getInstance().buildBridge("Ballerina");
		assertEquals("Ballerina", ballerina.getPseudoType());
	}
	
	@Test
	public void callMethods()
	{
		GraphicalBridge ballerina = GraphicalModelBridgeFactory.getInstance().buildBridge("Ballerina");
		try
		{
			ballerina.sendMessage("jump");
		}
		catch(NoMethodFoundException e)
		{
			assertFalse("jump was not found, it should have been", true);
		}
		
		try
		{
			ballerina.sendMessage("panic");
		}
		catch(NoMethodFoundException e)
		{
			assertFalse("panic was not found which is good", false);
		}
	}
}
