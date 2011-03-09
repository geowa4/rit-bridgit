package edu.rit.se.bridgit.language.model.bridge;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

public class GraphicalModelBridgeFactoryTest {
	
	@BeforeClass
	public static void addClasses()
	{
		List<String> testMethods = new Vector<String>();
		testMethods.add("jump");
		testMethods.add("fly");
		GraphicalBridge testBridge = new GraphicalBridge("Ballerina", testMethods, null);
		
		GraphicalModelBridgeFactory.availableclasses.clear();
		GraphicalModelBridgeFactory.currentinstances.clear();
		GraphicalModelBridgeFactory.addPossibleClass(testBridge);
	}
	
//	@Test
//	public void acquireGraphicalBridgeInstance()
//	{
//		GraphicalBridge ballerina = GraphicalModelBridgeFactory.buildBridge("Ballerina");
//		assertEquals("The Pseudo-type of the newly created bridge must be Ballerina", 
//				"Ballerina", ballerina.getPseudoType());
//		assertEquals("The factory must reflect that one instance has been created.", 
//				1, GraphicalModelBridgeFactory.getNumberInstances("Ballerina"));
//	}
}
